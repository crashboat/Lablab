package com.chengyong.lablab;

import androidx.fragment.app.ListFragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyListFragment extends ListFragment {
    int mCurCheckPosition = 0;
    boolean mSingleActivity;
    @Override
    public void onActivityCreated(Bundle savedInstanceSatte){
        super.onActivityCreated(savedInstanceSatte);

        setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_activated_1,DummyData.DATA_HEADINGS));

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        View contentFrame = getActivity().findViewById(R.id.content);
        mSingleActivity = contentFrame!=null && contentFrame.getVisibility() == View.VISIBLE;

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
        showContent(position);
    }

    void showContent(int index){

        mCurCheckPosition = index;

       if(mSingleActivity){
           getListView().setItemChecked(index, true);
           ListItemFragment content = (ListItemFragment)getFragmentManager().findFragmentById(R.id.content);
           if(content==null || content.getShownIndex()!=index){
               content = ListItemFragment.newInstance(index);
               FragmentTransaction ft = getChildFragmentManager().beginTransaction();
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
