package com.chengyong.lablab;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MyListFragment extends ListFragment {
    int mCurCheckPosition = 0;
    boolean mSingleActivity;
    MyViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceSatte){
        super.onCreate(savedInstanceSatte);
        mViewModel = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        final Observer<List<Item>> itemObserver = new Observer<List<Item>>(){
            @Override
            public void onChanged(@NonNull final List<Item>items){
                ItemAdaptor itemAdaptor = new ItemAdaptor(getActivity(),mViewModel.getItems().getValue());
                setListAdapter(itemAdaptor);
            }
        };

        mViewModel.getItems().observe(this,itemObserver);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceSatte){
        super.onActivityCreated(savedInstanceSatte);

        setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_activated_1,DummyData.DATA_HEADINGS));

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        View contentFrame = getActivity().findViewById(R.id.content);
        mSingleActivity = contentFrame!=null
                && contentFrame.getVisibility() == View.VISIBLE;

        if(savedInstanceSatte!=null){
            mCurCheckPosition = savedInstanceSatte.getInt("curChoice",0);
        }
        if(mSingleActivity){
            showContent(mCurCheckPosition);
        }
        else{
            getListView().setItemChecked(mCurCheckPosition,true);
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice",mCurCheckPosition);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
       mViewModel.selectItem(position);
        showContent(position);
    }

    void showContent(int index){

        mCurCheckPosition = index;

       if(mSingleActivity){
           getListView().setItemChecked(index, true);
           ListItemFragment content = (ListItemFragment)getFragmentManager()
                   .findFragmentById(R.id.content);
           if(content==null || content.getShownIndex()!=index){
               content = ListItemFragment.newInstance(index);
               FragmentTransaction ft = getFragmentManager().beginTransaction();
//               FragmentTransaction ft = getChildFragmentManager().beginTransaction();  // this statement will cause crash in landscape mode
               ft.replace(R.id.content, content);
               ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
               ft.commit();
           }
       }
       else{
        Intent intent = new Intent();

        intent.setClass(getActivity(),ItemActivity.class);

        intent.putExtra("index",index);
        startActivity(intent);
       }
    }

}
