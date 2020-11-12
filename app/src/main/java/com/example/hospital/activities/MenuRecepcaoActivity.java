package com.example.hospital.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.hospital.R;

public class MenuRecepcaoActivity extends AppCompatActivity {

    private LinearLayout llRecepInternacao;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_recepcao);

        llRecepInternacao = (LinearLayout) findViewById(R.id.llRecepInternacao);

        llRecepInternacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuRecepcaoActivity.this, PacienteActivity.class);
                startActivity(intent);
            }
        });
    }
}