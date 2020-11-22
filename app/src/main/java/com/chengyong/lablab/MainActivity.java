package com.chengyong.lablab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import static android.net.Uri.parse;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_DIALOG_RESPONSE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openCoinToss(View view){
        Intent openCoinTossIntent= new Intent(getApplicationContext(), CoinTossActivity.class);
        openCoinTossIntent.putExtra("extraName","extra information");
//        startActivity(openCoinTossIntent);
        startActivityForResult(openCoinTossIntent,REQUEST_DIALOG_RESPONSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.i("Activity Lifecycle","onActivityResult");
        super.onActivityResult(requestCode,resultCode,data);
      if(resultCode == RESULT_OK && requestCode == REQUEST_DIALOG_RESPONSE){
          Log.i("Activity Lifecycle","inresult ok");
          String responseString = data.getExtras().getString("ResponseString");
          Toast.makeText(getApplicationContext(), "This is response:"+responseString, Toast.LENGTH_LONG).show();
      }
    }


    protected void onPause() {
        super.onPause();
    }

    public void openURL(View view){
        Intent openImplicitIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.co.uk"));
        startActivity(openImplicitIntent);
    }

    public void openList(View view){
        Intent openListIntent = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(openListIntent);
    }

}