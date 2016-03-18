package com.example.skamb_000.eierwekker;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerString;
    Button button;
    SeekBar seekBar;
    CountDownTimer timer;

    int timerDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerString = (TextView) findViewById(R.id.timer);
        button = (Button) findViewById(R.id.button);

        timerDuration = 30;

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timerDuration = progress;

                setTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar.setProgress((int) timerDuration);
    }

    public void setTimer(int progress){
        int minutes = progress / 60;
        int seconds = progress - minutes * 60;
        String time = minutes+":"+seconds;
        if(seconds < 10){
            time = minutes+":0"+seconds;
        }
        timerString.setText(time);
    }


    public void toggleTimer(View view){

        if(timer != null){
            button.setText("Start Timer");
            timer.cancel();
            seekBar.setEnabled(true);
            timer = null;
        } else {
            button.setText("Pauzeer Timer");
            timer = new CountDownTimer(timerDuration * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timerDuration = (int) millisUntilFinished / 1000;
                    setTimer(timerDuration);
                    seekBar.setProgress(timerDuration);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                    timerString.setText("0:00");
                    button.setText("Start Timer");
                    seekBar.setEnabled(true);
                }
            };
            seekBar.setEnabled(false);
            timer.start();
        }
    }

}
