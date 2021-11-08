package com.example.checktask;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class PromodoroActivity extends AppCompatActivity implements View.OnClickListener {
    long timeCountInMilliSeconds; // millisecond = 25 second
    boolean timerIsWork = false;
    ProgressBar progressBar;
    TextView txTimerCounter;
    Button buttonToStartFocus, buttonToPauseFocus, buttonToResumeFocus, buttonToStopFocus;
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promodoro);
        definition();
        buttonClickListener();
        setTimer(timeCountInMilliSeconds);

    }

    // Define each component
    public void definition() {
        progressBar = findViewById(R.id.progressBarCircle);
        txTimerCounter = findViewById(R.id.textViewTime);

        buttonToStartFocus = findViewById(R.id.btn_start_fouc_id);
        buttonToPauseFocus = findViewById(R.id.btn_pause_fouc_id);
        buttonToResumeFocus = findViewById(R.id.btn_resume_fouc_id);
        buttonToStopFocus = findViewById(R.id.btn_stop_fouc_id);
    }

    // Make Buttons Clickable
    public void buttonClickListener() {
        buttonToStartFocus.setOnClickListener(this);
        buttonToPauseFocus.setOnClickListener(this);
        buttonToResumeFocus.setOnClickListener(this);
        buttonToStopFocus.setOnClickListener(this);
    }

    // which Button was Clicked
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_fouc_id:
                startTimer();
                break;
            case R.id.btn_pause_fouc_id:
                pauseTimer();
                break;
            case R.id.btn_resume_fouc_id:
                resumeTimer();
                break;
            case R.id.btn_stop_fouc_id:
                resetTimer();
                break;
        }
    }

    /**
     * method to initialize the values for count down timer work
     */
    private void SetTimerValues(long initialValue) {
        timeCountInMilliSeconds = (initialValue);
    }


    // set textView to default Value (25:00)
    public void setTimer(long startValueOfTimer) {
        txTimerCounter.setText(hmsTimeFormatter(startValueOfTimer));
    }

    //  Continue from the last value of timer
    public void resumeTimer() {
        showPauseButton();
        startCountDownTimer();
    }

    /**
     * method to reset count down timer
     */
    public void resetTimer() {
        showStartButton();
        SetTimerValues((25 * 60000));
        setProgressBarValues();
        txTimerCounter.setText(hmsTimeFormatter(timeCountInMilliSeconds));
        stopCountDownTimer();

    }


    /**
     * method to set circular progress bar values
     */

    private void setProgressBarValues() {
        progressBar.setMax((int) timeCountInMilliSeconds / 1000);
        progressBar.setProgress((int) timeCountInMilliSeconds / 1000);
    }

    /**
     * method to stop count down timer
     */
    private void stopCountDownTimer() {
        countDownTimer.cancel();
    }

    /**
     * method to Start Counter Down timer
     */
    public void startTimer() {
        timerIsWork = true;
        SetTimerValues((25 * 60000));
        setProgressBarValues();
        showPauseButton();
        startCountDownTimer();
    }

    /**
     * Start break time 5 minutes
     */
    public void startBreak() {
        SetTimerValues(30000);
        setProgressBarValues();
        startCountDownTimer();
    }

    public void dialogToAskBreak() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Good Job!");
        builder.setCancelable(false);
        builder.setMessage("Take a break for 5 second ?");
        builder.setPositiveButton("Yes", (dialog, which) -> startBreak());
        builder.setNegativeButton("Start work again", (dialog, which) -> startTimer());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void pauseTimer() {
        timerIsWork = false;
        showResetAndStopBtn();
        stopCountDownTimer();
    }

    public void showResetAndStopBtn() {
        buttonToPauseFocus.setVisibility(View.GONE);
        buttonToResumeFocus.setVisibility(View.VISIBLE);
        buttonToStopFocus.setVisibility(View.VISIBLE);
    }

    public void showPauseButton() {
        buttonToPauseFocus.setVisibility(View.VISIBLE);
        buttonToStopFocus.setVisibility(View.INVISIBLE);
        buttonToStartFocus.setVisibility(View.INVISIBLE);
        buttonToResumeFocus.setVisibility(View.INVISIBLE);
    }

    /**
     * method to visible start stop icon
     */
    public void showStartButton() {
        buttonToStopFocus.setVisibility(View.INVISIBLE);
        buttonToResumeFocus.setVisibility(View.INVISIBLE);
        buttonToPauseFocus.setVisibility(View.INVISIBLE);
        buttonToStartFocus.setVisibility(View.VISIBLE);
    }


    private void startCountDownTimer() {
        long startPoint =timeCountInMilliSeconds;
        countDownTimer = new CountDownTimer(startPoint, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txTimerCounter.setText(hmsTimeFormatter(millisUntilFinished));
                progressBar.setProgress((int) (millisUntilFinished / 1000));
                timeCountInMilliSeconds = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                //The count check end of the task
                txTimerCounter.setText(hmsTimeFormatter(timeCountInMilliSeconds));
                setProgressBarValues();
                dialogToAskBreak();
            }
        }.start();

    }



    /**
     * method to convert millisecond to time format
     *
     * @param milliSeconds
     * @return mm:ss time formatted string
     */
    private String hmsTimeFormatter(long milliSeconds) {
        @SuppressLint("DefaultLocale") String hms = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
        return hms;
    }


}