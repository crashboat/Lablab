package com.chengyong.lablab;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListItemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_INDEX = "index";



    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
    private int mIndex;
    MyViewModel myViewModel;
    View mInflatedView;

    public int getShownIndex(){
        return mIndex;
    }

    public ListItemFragment() {

        Log.i("Activity Lifecycle", "ListItemFragment empty constructor");
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param index Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment ListItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListItemFragment newInstance(int index) {
        ListItemFragment fragment = new ListItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIndex = getArguments().getInt(ARG_INDEX);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        myViewModel = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        myViewModel.selectItem(mIndex);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        Log.i(this.getClass().getSimpleName()+"Observer", "onCreateView");
        mInflatedView = inflater.inflate(R.layout.fragment_list_item,container, false);

        final Observer<Item> itemObserver = new Observer<Item>(){
            @Override
            public void onChanged(@NonNull final Item item){
                ImageView image = (ImageView) mInflatedView.findViewById(R.id.imageView_image);
                image.setImageBitmap(item.getImage());

                TextView text = (TextView)mInflatedView.findViewById(R.id.listItemTextView);
                text.setText(item.getDescription());
            }
        };

        myViewModel.getSelectedItem().observe(getViewLifecycleOwner(),itemObserver);

     //   TextView text = (TextView)inflatedView.findViewById(R.id.listItemTextView);
       // text.setText(DummyData.DATA_CONTENT[mIndex]);
        return mInflatedView;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_list_item, container, false);
//    }
}