package com.example.a41p;
//Student Name: Xinyao Cheng
//SIT304 task4.1
//Student Num: 223122637

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    NumberPicker workoutHoursPicker, workoutMinutesPicker,workoutSecondsPicker,
                 restHoursPicker, restMinutesPicker, restSecondsPicker;
    Button startButton, pauseButton, cancleBUtton;
    ProgressBar progressBar;
    TextView remainingTimeView, statusText;
    androidx.constraintlayout.widget.ConstraintLayout workoutTimePickers, restTimePickers;

    long workoutTime, restTime, currentTotalTime, remainingTime;
    boolean isPaused =false;
    boolean isResting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


       startButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(!isPaused){
                   //user start to work out first time
                   showProgressBar();
                   startCountdown();
               }else{
                   isPaused = false;
                   startCountdown();
               }

           }
       });

       pauseButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(!isPaused){
                   countDownTimer.cancel();
                   progressBar.clearAnimation();
                   isPaused = true;
                   startButton.setVisibility(View.VISIBLE);
                   pauseButton.setVisibility(View.INVISIBLE);
               }
           }
       });

       cancleBUtton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               progressBar.clearAnimation();
               countDownTimer.cancel();
               isPaused = false;
               showTimePickers();

           }
       });
    }

    private void showTimePickers() {
        cancleBUtton.setEnabled(false);
        progressBar.setVisibility(View.INVISIBLE);
        remainingTimeView.setVisibility(View.INVISIBLE);
        statusText.setVisibility(View.INVISIBLE);
        workoutTimePickers.setVisibility(View.VISIBLE);
        restTimePickers.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.VISIBLE);
        pauseButton.setVisibility(View.INVISIBLE);
    }

    private void initView() {
        workoutHoursPicker = findViewById(R.id.workoutHoursPicker);
        workoutMinutesPicker = findViewById(R.id.workoutMinutesPicker);
        workoutSecondsPicker = findViewById(R.id.workoutSecondsPicker);
        restHoursPicker = findViewById(R.id.restHoursPicker);
        restMinutesPicker = findViewById(R.id.restMinutesPicker);
        restSecondsPicker = findViewById(R.id.restSecondsPicker);
        workoutTimePickers = findViewById(R.id.workoutTimePickers);
        restTimePickers = findViewById(R.id.restTimePickers);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        cancleBUtton = findViewById(R.id.cancleButton);
        progressBar = findViewById(R.id.progressBar);
        remainingTimeView = findViewById(R.id.remainingTime);
        statusText = findViewById(R.id.statusText);



        workoutHoursPicker.setMinValue(0);
        workoutHoursPicker.setMaxValue(23);
        workoutMinutesPicker.setMinValue(0);
        workoutMinutesPicker.setMaxValue(59);
        workoutSecondsPicker.setMinValue(0);
        workoutSecondsPicker.setMaxValue(59);
        restHoursPicker.setMinValue(0);
        restHoursPicker.setMaxValue(23);
        restMinutesPicker.setMinValue(0);
        restMinutesPicker.setMaxValue(59);
        restSecondsPicker.setMinValue(0);
        restSecondsPicker.setMaxValue(59);
        pauseButton.setVisibility(View.INVISIBLE);
    }

    private void showProgressBar() {
        cancleBUtton.setEnabled(true);
        workoutTimePickers.setVisibility(View.GONE);
        restTimePickers.setVisibility(View.GONE);
        remainingTimeView.setVisibility(View.VISIBLE);
        statusText.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        pauseButton.setVisibility(View.VISIBLE);
    }

    private long getRestTimeMillis() {
        int restHours = restHoursPicker.getValue();
        int restMinutes = restMinutesPicker.getValue();
        int restSeconds = restSecondsPicker.getValue();
        restTime = (restHours * 3600+restMinutes * 60+ restSeconds) * 1000;
        return restTime;
    }

    private long getWorkoutTimeMillis() {
        int workoutHours = workoutHoursPicker.getValue();
        int workoutMinutes = workoutMinutesPicker.getValue();
        int workoutSeconds = workoutSecondsPicker.getValue();
        workoutTime = (workoutHours * 3600 + workoutMinutes * 60 + workoutSeconds) * 1000;
        return workoutTime;
    }

    private void startCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if(!isResting){
            statusText.setText("working out left over:");
            remainingTime = currentTotalTime = getWorkoutTimeMillis();
        }else{
            statusText.setText("rest left over:");
            remainingTime = currentTotalTime = getRestTimeMillis();
        }
        countDownTimer = new CountDownTimer(remainingTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(!isPaused){
                    remainingTime = millisUntilFinished/1000;
                }
                updateProgress(millisUntilFinished);
                updateRemainingTime();

            }

            @Override
            public void onFinish() {
                isResting = !isResting;
                startCountdown();
            }
        }.start();
    }

    private void updateProgress(long millisUntilFinished) {
        long progress = currentTotalTime - millisUntilFinished;
        Log.v("new progress",String.valueOf((int)progress));
        progressBar.setMax((int)currentTotalTime);
        progressBar.setProgress((int)progress);
    }

    private void updateRemainingTime() {
        String remainingTimeString = String.format(Locale.getDefault(),
                "%02d:%02d:%02d",
                remainingTime/3600,
                (remainingTime%3600)/60,
                remainingTime%60);
        remainingTimeView.setText(remainingTimeString);
    }

    }
