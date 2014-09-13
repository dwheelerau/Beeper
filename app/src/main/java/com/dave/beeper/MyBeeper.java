package com.dave.beeper;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
//test comment

public class MyBeeper extends ActionBarActivity {
    double dist = 20.0;
    TextView curSpeed;
    TextView levelText;
    TextView lapText;
    int beepCounter = 0;
    int levelCounter = 0;
    int curLevel = 1; //the actual level
    double increaseBy = 1.0;
    double timeTarget;
    double speed = 8.0;
    int lapCounter = 0;
    final Handler myHandler = new Handler();
    final Timer myTimer = new Timer();
    //MediaPlayer mp;// = MediaPlayer.create(this, R.raw.beep);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_beeper);
        //get starting time based on speed
        timeTarget = beeper(speed); //* 1000;
        curSpeed = (TextView) findViewById(R.id.kmh);
        levelText = (TextView) findViewById(R.id.level);
        lapText = (TextView) findViewById(R.id.lap);
        // Timer
        TimerTask myTask = new TimerTask() {
            public void run() {
                updateUI(); // updateUI method
            }
        };
        myTimer.schedule(myTask,0,100); // TimerTask, delay, period [100=0.1seconds]
        final Button button = (Button) findViewById(R.id.stop);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myTimer.cancel();
                finish();
                System.exit(0);
            }
        });
    }

    // Runnable method
    final Runnable myRunnable = new Runnable() {
        public void run() {
            ToneGenerator toneGenerator= new ToneGenerator(AudioManager.STREAM_DTMF,ToneGenerator.MAX_VOLUME);
            curSpeed.setText("km/hr: "+ String.valueOf(speed));

            if (levelCounter == 0) {
                //reset laps
                lapCounter = 0;
                levelText.setText("Level: " + String.valueOf(curLevel));
                lapText.setText("Lap: " + String.valueOf(lapCounter));
                //this will play tone for 2 seconds. tg.startTone(ToneGenerator.TONE_PROP_BEEP, 200);
                toneGenerator.startTone(ToneGenerator.TONE_DTMF_1, 400);
                toneGenerator.release();
                } else {
                //reset laps
                lapCounter++;
                //this will play tone for 1 seconds.
                toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 400);
                lapText.setText("Lap: " + String.valueOf(lapCounter));
                toneGenerator.release();
                        }
            }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_beeper, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public double beeper(double kmph) {
        return 10*(dist / (kmph * 0.277778));//returns seconds*10 as a double ie7.5=75
            }

    public void updateUI() {
        //this is called very 0.1 sec

        if (beepCounter < 2000.0) {
            //counters
            beepCounter++; //cycle based on speed/time
            levelCounter++; //cycle each 60 seconds
            //this is where we test if counter > speed
            if (beepCounter > timeTarget) {
                //will trigger based on speed
                beepCounter = 0;
                Log.e("n", String.valueOf(timeTarget));
                Log.e("n", String.valueOf(curLevel));
                if (levelCounter>600){
                    //reset very minute and set some flag to make double noise
                    levelCounter = 0;
                    //increase level
                    curLevel++;
                    //increase speed by 1 for first level then 0.5
                    speed = speed + increaseBy;
                    //after first level increase are by only 0.5 km/hr
                    if (curLevel==2){
                        increaseBy=0.5;
                    }
                    //update time target based on new speed
                    timeTarget = beeper(speed);
                    }
                //mp.stop();
                //this is executed
                myHandler.post(myRunnable);
            }
        } else {
            myTimer.cancel();
            //mp.release();
            }
    }
}