package com.chengyong.lablab;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.util.Log;
import java.util.Random;
import android.widget.TextView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class CoinTossActivity extends AppCompatActivity {

Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_toss);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.i("Activity Lifecycle","onCreate");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Activity Lifecycle", "onPause");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        Bundle extra = getIntent().getExtras();
        String name = extra.getString("extraName");
        Toast.makeText(getApplicationContext(),"Thisi si the extra infrom:"+name, Toast.LENGTH_LONG).show();

        return true;
    }

    @Override
    public void finish(){
        Log.i("Activity Lifecycle","finish");
        Intent data = new Intent();
        TextView coinTossView = (TextView) findViewById(R.id.coinTossView1);
        String resString = coinTossView.getText().toString();
        data.putExtra("ResponseString",resString);
        setResult(RESULT_OK,data);
        super.finish();
    }

    private String getCoinToss(){
        if(random.nextBoolean()){
            return getString(R.string.coinTossResult1);
        }
        return getString(R.string.coinTossResult2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Activity Lifecycle","onResume");
        TextView coinTossView = (TextView) findViewById(R.id.coinTossView1);
        String result = getCoinToss();
        coinTossView.setText(result);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {



            return true;
        }

        return super.onOptionsItemSelected(item);
    }





}