package com.example.hospital.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hospital.R;


public class MenuAdministrativoActivity extends AppCompatActivity {

    private LinearLayout llAdminUnidade, llAdminLeito, llAdminSetor, llAdminMedico,
            llAdminMedicamento, llAdminFuncionario;
    private Intent intent;

    public static final int TELA_MENU = -1;
    public static final int TELA_LOGIN = 0;
    public static final int TELA_UNIDADE = 1;
    public static final int TELA_SETOR = 2;
    public static final int TELA_LEITO = 3;
    public static final int TELA_MEDICAMENTO = 4;
    public static final int TELA_FUNCIONARIO = 5;
    public static final int TELA_MEDICO = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_administrativo);

        llAdminUnidade = (LinearLayout) findViewById(R.id.llAdminUnidade);
        llAdminSetor = (LinearLayout) findViewById(R.id.llAdminSetor);
        llAdminLeito = (LinearLayout) findViewById(R.id.llAdminLeito);
        llAdminMedico = (LinearLayout) findViewById(R.id.llAdminMedico);
        llAdminMedicamento = (LinearLayout) findViewById(R.id.llAdminMedicamento);
        llAdminFuncionario = (LinearLayout) findViewById(R.id.llAdminFuncionario);

        llAdminUnidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuAdministrativoActivity.this, UnidadeActivity.class);
                startActivityForResult(intent, TELA_UNIDADE);
            }
        });

        llAdminSetor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuAdministrativoActivity.this, SetorActivity.class);
                startActivityForResult(intent, TELA_SETOR);
            }
        });

        llAdminLeito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuAdministrativoActivity.this, LeitoActivity.class);
                startActivityForResult(intent, TELA_LEITO);
            }
        });

        llAdminMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuAdministrativoActivity.this, MedicamentoActivity.class);
                startActivityForResult(intent, TELA_MEDICAMENTO);
            }
        });

        llAdminFuncionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuAdministrativoActivity.this, FuncionarioActivity.class);
                startActivityForResult(intent, TELA_FUNCIONARIO);
            }
        });

        llAdminMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuAdministrativoActivity.this, MedicoActivity.class);
                startActivityForResult(intent, TELA_MEDICO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode >= TELA_UNIDADE) {
           switch (resultCode) {
               case TELA_LOGIN:
                   finish();
                   break;
               case TELA_UNIDADE:
                   llAdminUnidade.callOnClick();
                   break;
               case TELA_SETOR:
                   llAdminSetor.callOnClick();
                   break;
               case TELA_LEITO:
                   llAdminLeito.callOnClick();
                   break;
               case TELA_MEDICAMENTO:
                   llAdminMedicamento.callOnClick();
                   break;
               case TELA_FUNCIONARIO:
                   llAdminFuncionario.callOnClick();
                   break;
               case TELA_MEDICO:
                   llAdminMedico.callOnClick();
                   break;
           }
        }
    }
}