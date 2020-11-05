package com.example.hospital.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.hospital.R;


public class MenuAdministrativoActivity extends AppCompatActivity {

    private LinearLayout llAdminUnidade, llAdminLeito, llAdminMedico, llAdminMedicamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_administrativo);

        llAdminUnidade = (LinearLayout) findViewById(R.id.llAdminUnidade);
        llAdminLeito = (LinearLayout) findViewById(R.id.llAdminLeito);
        llAdminMedico = (LinearLayout) findViewById(R.id.llAdminMedico);
        llAdminMedicamento = (LinearLayout) findViewById(R.id.llAdminMedicamento);

        llAdminUnidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.hospital.activities.MenuAdministrativoActivity.this, UnidadeActivity.class);
                startActivity(intent);
            }
        });

        llAdminLeito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.hospital.activities.MenuAdministrativoActivity.this, LeitoActivity.class);
                startActivity(intent);
            }
        });
/*
        llAdminMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.hospital.activities.MenuAdministrativoActivity.this, MedicoActivity.class);
                startActivity(intent);
            }
        });

        llAdminMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.hospital.activities.MenuAdministrativoActivity.this, InternacaoActivity.class);
                startActivity(intent);
            }
        });

 */
    }
}