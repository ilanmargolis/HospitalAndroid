package com.example.hospital.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hospital.R;
import com.facebook.stetho.Stetho;


public class SplashScreenActivity extends AppCompatActivity {

    TextView tvLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // remove a barra de título apenas desta janela
        getSupportActionBar().hide();

        Stetho.initializeWithDefaults(this);

        tvLoading = (TextView) findViewById(R.id.tvLoading);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvLoading.setText("Abrindo banco de dados");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvLoading.setText("Acessando variáveis de sistema");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tvLoading.setText("Iniciando aplicação");
                            }
                        }, 1000);
                    }
                }, 1000);
            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }, 5000);
    }
}