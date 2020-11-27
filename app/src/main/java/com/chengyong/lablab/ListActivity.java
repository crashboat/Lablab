package com.chengyong.lablab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Activity Lifecycle", "ListActivity onCreate");
        setContentView(R.layout.activity_list);
    }
}