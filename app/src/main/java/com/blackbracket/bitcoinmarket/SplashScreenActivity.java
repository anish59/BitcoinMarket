package com.blackbracket.bitcoinmarket;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.blackbracket.bitcoinmarket.widgets.CAnimTextView;

public class SplashScreenActivity extends AppCompatActivity {

    private android.widget.ImageView imgSuperMan;
    private com.blackbracket.bitcoinmarket.widgets.CAnimTextView txtAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        init();
    }

    private void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();
            }
        }, 2500);
    }


    private void initViews() {
        setContentView(R.layout.activity_splash_screen);
        this.txtAppName = (CAnimTextView) findViewById(R.id.txtAppName);
        this.imgSuperMan = (ImageView) findViewById(R.id.imgSuperMan);
    }

    @Override
    protected void onResume() {
        super.onResume();
        txtAppName.animateText(getString(R.string.bitcoin_market));

    }
}
