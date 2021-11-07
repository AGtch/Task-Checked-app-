package com.example.checktask;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class PromodoroActivity extends AppCompatActivity {
    long timeCountInMilliSeconds = (25 * 60000); // millisecond = 25 second
    int counter = 0;
    boolean timerIsWork = false;
    ProgressBar progressBar;
    TextView txTimerCounter;
    Button buttonToStartFocus, buttonToPauseFocus, buttonToResumeFocus, buttonToStopFocus;
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promodoro);
        progressBar = findViewById(R.id.progressBarCircle);
        txTimerCounter = findViewById(R.id.textViewTime);

        buttonToStartFocus = findViewById(R.id.btn_start_fouc_id);
        buttonToPauseFocus = findViewById(R.id.btn_pause_fouc_id);
        buttonToResumeFocus = findViewById(R.id.btn_resume_fouc_id);

        buttonToStopFocus = findViewById(R.id.btn_stop_fouc_id);

        setTimer(timeCountInMilliSeconds);
        buttonToStartFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();
            }
        });

        buttonToPauseFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();
            }
        });

        buttonToStopFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        buttonToResumeFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resumeTimer();
            }
        });
    }

    /**
     * method to start and stop count down timer break
     */
    private void breakStartStop() {
        timerIsWork = false;

        // call to initialize the timer values
        setTimer(timeCountInMilliSeconds);
        // call to initialize the progress bar values
        setProgressBarValues();
        // call to start the count down timer
        startCountDownTimer();


    }

    // set textView to default Value
    public void setTimer(long startValueOfTimer) {
        txTimerCounter.setText(hmsTimeFormatter(startValueOfTimer));
    }

    public void resumeTimer() {
        showPauseButton();
        startCountDownTimer();
    }

    /**
     * method to reset count down timer
     */
    public void resetTimer() {
        counter = 0;
        showStartButton();
        workSetTimerValues();
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
     * Start Counter Down timer
     */
    public void startTimer() {
        timerIsWork = true;
        workSetTimerValues();
        setProgressBarValues();
        showPauseButton();
        // call to start the count down timer
        startCountDownTimer();
    }

    public void pauseTimer() {
        timerIsWork = false;
        showResetAndStopBtn();
        // call to start the count down timer
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
                counter++;
                txTimerCounter.setText(hmsTimeFormatter(timeCountInMilliSeconds));
                setProgressBarValues();
            }

        }.start();
    }


    /**
     * method to initialize the values for count down timer work
     */
    private void workSetTimerValues() {
        timeCountInMilliSeconds = (25 * 60000);
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