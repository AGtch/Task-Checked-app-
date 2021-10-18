package com.example.checktask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SplachActivity extends AppCompatActivity {
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.splashScreen);
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

    }
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isFinishing()){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }
    };

    @Override
    protected void onResume() {
       super.onResume();
       handler.postDelayed(runnable , 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
       handler.removeCallbacks(runnable);
    }
}