package com.example.hospital.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.hospital.R;

public class MenuRecepcaoActivity extends AppCompatActivity {

    private LinearLayout llRecepInternacao;
    private Intent intent;

    public static final int TELA_MENU = -1;
    public static final int TELA_LOGIN = 0;
    public static final int TELA_INTERNAR = 1;
    public static final int TELA_TRANSFERENCIA = 2;
    public static final int TELA_PREVISAO = 3;
    public static final int TELA_PACIENTE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_recepcao);

        llRecepInternacao = (LinearLayout) findViewById(R.id.llRecepInternacao);

        llRecepInternacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuRecepcaoActivity.this, InternarActivity.class);
                startActivityForResult(intent, TELA_INTERNAR);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode >= TELA_INTERNAR) {
            switch (resultCode) {
                case TELA_LOGIN:
                    finish();
                    break;
                case TELA_INTERNAR:
                    llRecepInternacao.callOnClick();
                    break;
            }
        }
    }
}