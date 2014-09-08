package com.dave.beeper;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MyBeeper extends ActionBarActivity {
    double dist = 20.0;
    TextView curSpeed;
    int counter = 0;
    double speed = 8.0;
    final Handler myHandler = new Handler();
    final Timer myTimer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_beeper);
        //update screen with current speed
        curSpeed = (TextView) findViewById(R.id.kmh);
        // Timer
        TimerTask myTask = new TimerTask() {
            public void run() {
                updateUI(); // updateUI method
            }
        };
        myTimer.schedule(myTask,0,100); // TimerTask, delay, period [100=0.1seconds]
    }

    // Runnable method
    final Runnable myRunnable = new Runnable() {
        public void run() {
            curSpeed.setText(String.valueOf(speed)); // update your text
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
        return dist / (kmph * 0.277778);
    }

    public void updateUI() {
        //this is called very 0.1 sec

        if (speed < 20.0) {
            speed++;
            //this is executed
            myHandler.post(myRunnable);

        } else {
            myTimer.cancel();
        }
    }


        //while (speed < 11.0) {
        //    //main loop run every 1 min
         //curSpeed.setText(Double.toString(speed) + "kmph");
         //call function to mkae nosie every x seconds for 1 min
        //    double beep = beeper(speed)*1000;
        //    long seconds = System.currentTimeMillis(); //60
        //    long end = seconds + 60000; //60 seconds
        //    long tempo = beepTime + seconds; //double

        //    while (seconds < end) {
                //should loop for 60 seconds
        //        if (seconds > tempo) {
        //            try {
                        //Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        //Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                        //r.play();
         //               Log.e("n", "beep");
         //               MediaPlayer mp = MediaPlayer.create(this, R.raw.beep);
         //               mp.start();

         //               tempo = beepTime + System.currentTimeMillis();
         //               curSpeed.setText(Double.toString(speed) + "kmph");

         //           } catch (Exception e) {
         //               e.printStackTrace();
          //          }

                    //Log.e("n", Double.toString(seconds) + " - " + Double.toString(target));

          //      }
          //      seconds = System.currentTimeMillis();
          //  }
          //  playSound((long)beep);
          //  speed = speed + 0.5;
          //  MediaPlayer mp = MediaPlayer.create(this, R.raw.beep);
          //  mp.start();
           // Log.e("n", "beep2");
            //todo: add stop button!
            //todo: fix screen update hhttp://www.lucazanini.eu/2013/android/updating-frequently-a-textview-inside-a-loop/?lang=en
        //}



        //}
    }