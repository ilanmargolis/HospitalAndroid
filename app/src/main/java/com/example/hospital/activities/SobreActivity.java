package com.example.hospital.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hospital.R;

public class SobreActivity extends AppCompatActivity {

    private ImageView ivSobreLogo;
    private TextView tvSobreEquipe, tvSobreComponentes;
    private int cont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        ivSobreLogo = (ImageView) findViewById(R.id.ivSobreLogo);
        tvSobreEquipe = (TextView) findViewById(R.id.tvSobreEquipe);
        tvSobreComponentes = (TextView) findViewById(R.id.tvSobreComponentes);

        // remove a barra de título apenas desta janela
        getSupportActionBar().hide();

        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/vanishing.ttf");
        tvSobreEquipe.setTypeface(typeFace);

        tvSobreComponentes.setText(Html.fromHtml(getString(R.string.sobre_componentes)));

        blink();
    }

    private void blink() {
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 400;

                try {
                    Thread.sleep(timeToBlink);
                } catch (Exception e) {
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (cont % 2 == 0) {
                            ivSobreLogo.setBackgroundTintList(getResources().getColorStateList(R.color.azul_5, null));
                            tvSobreEquipe.setTextColor(getResources().getColor(R.color.azul_5, null));
                        } else if (cont % 3 == 0) {
                            ivSobreLogo.setBackgroundTintList(getResources().getColorStateList(R.color.branco, null));
                            tvSobreEquipe.setTextColor(getResources().getColor(R.color.branco, null));
                        } else {
                            ivSobreLogo.setBackgroundTintList(getResources().getColorStateList(R.color.preto, null));
                            tvSobreEquipe.setTextColor(getResources().getColor(R.color.preto, null));
                        }

                        cont++;

                        blink();
                    }
                });
            }
        }).start();
    }
}