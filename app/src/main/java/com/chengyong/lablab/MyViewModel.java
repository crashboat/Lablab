package com.chengyong.lablab;

import android.app.Application;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class MyViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Item>>mItems;
    private MutableLiveData<Item> mSelectedItem;
    private int mSelectedIndex;

    public MyViewModel(@NonNull Application pApplication){
        super(pApplication);
        getItems();
    }

    private void generateItems(){
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("first","Link1","1/11/19", "description asfa 1"));
        items.add(new Item("second","Link2","2/11/19", "description asfa 2"));
        items.add(new Item("third","Link3","3/11/19", "description asfa 3"));
        items.add(new Item("fourth","Link4","4/11/19", "description asfa 4"));
        mItems.setValue(items);

    }

    public LiveData<ArrayList<Item>> getItems(){
        if(mItems==null){
            mItems = new MutableLiveData<ArrayList<Item>>();
            generateItems();
            selectItem(0);
        }
        return mItems;
    }
public void selectItem(int pIndex){
        mSelectedIndex = pIndex;
        Item selectedItem = mItems.getValue().get(mSelectedIndex);
        mSelectedItem = new MutableLiveData<Item>();
        mSelectedItem.setValue(selectedItem);
}
public LiveData<Item>getSelectedItem(){
        if(mSelectedItem==null){
            mSelectedItem = new MutableLiveData<Item>();
            selectItem(mSelectedIndex);
        }
        return mSelectedItem;
}


}
