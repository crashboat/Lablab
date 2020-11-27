package com.chengyong.lablab;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GestureDetectorCompat;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.util.Log;
import java.util.Random;

import android.widget.ImageView;
import android.widget.TextView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class CoinTossActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

Random random = new Random();

private int mNumberOfTosses;
private boolean mShowingHeads;
private ImageView mHeads;
private ImageView mTails;
private Animator mFlipInAnimator;
private Animator mFlipOutAnimator;
private int mNumFlips;
private int mAnimationCompleteCount;
private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_toss);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.i("Activity Lifecycle","onCreate");

        mFlipInAnimator = AnimatorInflater.loadAnimator(this,R.animator.flip_vertically_top_in);
        mFlipOutAnimator = AnimatorInflater.loadAnimator(this, R.animator.flip_vertically_top_out);

        mHeads = findViewById(R.id.heads);
        mTails = findViewById(R.id.tails);
        mShowingHeads = false;

        mNumFlips = random.nextInt(10);
        mAnimationCompleteCount = 0;
        mDetector = new GestureDetectorCompat(this, this);

        addAnimationListeners();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        View containerView = findViewById(R.id.container);
        View.OnTouchListener touchListener = new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
//                if(event.getActionMasked() == MotionEvent.ACTION_UP){
//                    flipCoin();
//                }
//                return true;
                return mDetector.onTouchEvent(event);
            }
        };
        containerView.setOnTouchListener(touchListener);


        int numberOfTosses = retrievePreviousTosses();

        increaseTossCount();

        Toast.makeText(getApplicationContext(),"The conin has been tossed: "+numberOfTosses+" times.",Toast.LENGTH_LONG).show();

        storePreviousTosses(mNumberOfTosses);

    }
private void increaseTossCount(){
    if(mNumberOfTosses == -1){
        mNumberOfTosses = 1;
    }
    else {
        mNumberOfTosses++;
    }
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

    private void storePreviousTosses(int pNumberOfTosses){
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(
                "com.chengyong.lablab",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("numberOfTosses",pNumberOfTosses);
        editor.commit();
    }

    private int retrievePreviousTosses(){
        int previousTosses = 0;
        SharedPreferences sharedPreferences = this.getApplication().getSharedPreferences(
                "com.chengyong.lablab",Context.MODE_PRIVATE);
        previousTosses = sharedPreferences.getInt("numberOfTosses",01);
        return previousTosses;
    }

    private void flipCoin(){
        String result = "Heads!";
        if(mShowingHeads){
            mShowingHeads = false;
            mFlipInAnimator.setTarget(mTails);
            mFlipOutAnimator.setTarget(mHeads);
            result = "Tails";
        }
        else{
            mShowingHeads = true;
            mFlipInAnimator.setTarget(mHeads);
            mFlipOutAnimator.setTarget(mTails);
            result = "Heads!";
        }
        mFlipInAnimator.start();
        mFlipOutAnimator.start();

        TextView coinTossView = (TextView)findViewById(R.id.coinTossView1);
        coinTossView.setText(result);

    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

       if(v1<-5){
           increaseTossCount();
           flipCoin();
       }

        return false;
    }

    private void animationComplete(){
        if(mAnimationCompleteCount == 2){
            mAnimationCompleteCount = 0;
            if(mNumFlips>0){
                mNumFlips--;
                flipCoin();
            }
            else
            {
                mNumFlips = random.nextInt(10);
            }
        }
    }

    private void addAnimationListeners(){
        mFlipInAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
mAnimationCompleteCount++;
animationComplete();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mFlipOutAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
mAnimationCompleteCount++;
animationComplete();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

}