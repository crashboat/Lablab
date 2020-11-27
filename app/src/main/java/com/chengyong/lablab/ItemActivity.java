package com.chengyong.lablab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        ListItemFragment content = new ListItemFragment();
        Log.i("Activity Lifecycle", "ItemActivity onCreate");

        content.setArguments(getIntent().getExtras());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Log.i("Activity Lifecycle", "ItemActivity:" + content.toString());
        fragmentTransaction.add(R.id.content, content).commit();

    }
}