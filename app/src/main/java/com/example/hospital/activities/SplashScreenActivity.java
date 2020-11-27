package com.example.hospital.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.hospital.R;
import com.example.hospital.controller.CbosCtrl;
import com.example.hospital.controller.ConselhoCtrl;
import com.example.hospital.controller.SetorCtrl;
import com.example.hospital.controller.TerminologiaCtrl;
import com.example.hospital.model.Cbos;
import com.example.hospital.model.Conselho;
import com.example.hospital.model.Setor;
import com.example.hospital.model.Terminologia;
import com.example.hospital.util.Utils;
import com.facebook.stetho.Stetho;

import java.io.File;
import java.util.ArrayList;


public class SplashScreenActivity extends AppCompatActivity {

    TextView tvLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // remove a barra de título apenas desta janela
        getSupportActionBar().hide();

        Stetho.initializeWithDefaults(this);

        File dbFile = this.getDatabasePath("hospital.db");

        if (!dbFile.exists()) {
            importarTabelas();
        }

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

    private void importarTabelas() {
        // Importar CBOS
        ArrayList<String> cbosList = Utils.lerCSV(this, "tabelas/cbos.csv");
        Cbos cbos = new Cbos();

        for (String c : cbosList) {
            String[] item = c.split(";");

            if (!item[0].equalsIgnoreCase("CODIGO")) {
                cbos = new Cbos();
                cbos.setCodigo(item[0]);
                cbos.setDescricao(item[1]);
                new CbosCtrl(this).insert(cbos);
            }
        }

        // Importar conselho
        ArrayList<String> conselhoStrings = Utils.lerCSV(this, "tabelas/conselho.csv");
        Conselho conselho = new Conselho();

        for (String t : conselhoStrings) {
            String[] item = t.split(";");

            if (!item[0].equalsIgnoreCase("CODIGO")) {
                conselho = new Conselho();
                conselho.setCodigo(item[0]);
                conselho.setSigla(item[1]);
                conselho.setDescricao(item[2]);
                new ConselhoCtrl(this).insert(conselho);
            }
        }

        // Importar terminologia
        ArrayList<String> termStrings = Utils.lerCSV(this, "tabelas/terminologia.csv");
        Terminologia terminologia = new Terminologia();

        for (String t : termStrings) {
            String[] item = t.split(";");

            if (!item[0].equalsIgnoreCase("CODIGO")) {
                terminologia = new Terminologia();
                terminologia.setCodigo(item[0]);
                terminologia.setSigla(item[1]);
                terminologia.setDescricao(item[2]);
                new TerminologiaCtrl(this).insert(terminologia);
            }
        }

        // Adicionar setor Administrativo e Recepção pois preciso deles internamente
        new SetorCtrl(this).insert(new Setor("Administrativo", "", false));
        new SetorCtrl(this).insert(new Setor("Recepção", "", false));
    }

}