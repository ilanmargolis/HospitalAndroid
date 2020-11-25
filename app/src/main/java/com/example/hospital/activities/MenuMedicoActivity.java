package com.example.hospital.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.hospital.R;
import com.example.hospital.model.Medicamento;
import com.example.hospital.model.Medico;

import java.io.Serializable;

public class MenuMedicoActivity extends AppCompatActivity {

    LinearLayout llMedicoAlta, llMedicoPrescreve;
    private Intent intent;
    private Medico medico;

    public static final int TELA_MENU = -1;
    public static final int TELA_LOGIN = 0;
    public static final int TELA_PRESCREVE = 1;
    public static final int TELA_ALTA = 2;
    public static final int TELA_MEDICAMENTO = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_medico);

        medico = (Medico) getIntent().getSerializableExtra("medico");

        llMedicoPrescreve = (LinearLayout) findViewById(R.id.llMedicoPrescreve);
        llMedicoAlta = (LinearLayout) findViewById(R.id.llMedicoAlta);

        llMedicoPrescreve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuMedicoActivity.this, InternadoActivity.class);
                intent.putExtra("tela", getResources().getString(R.string.tela_prescrever));
                intent.putExtra("medico", (Serializable) medico);
                startActivityForResult(intent, TELA_PRESCREVE);
            }
        });

        llMedicoAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuMedicoActivity.this, InternadoActivity.class);
                intent.putExtra("tela", getResources().getString(R.string.tela_alta));
                intent.putExtra("medico", (Serializable) medico);
                startActivityForResult(intent, TELA_ALTA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode >= TELA_PRESCREVE) {
            switch (resultCode) {
                case TELA_LOGIN:
                    finish();
                    break;

                case TELA_PRESCREVE:
                    llMedicoPrescreve.callOnClick();
                    break;

                case TELA_ALTA:
                    llMedicoAlta.callOnClick();
                    break;

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_medico, menu);

        // Esconder do menu a atual tela
        menu.findItem(R.id.action_medico_refresh).setVisible(false);
        menu.findItem(R.id.action_medico_add).setVisible(false);
        menu.findItem(R.id.action_medico_medicamento).setVisible(false);
        menu.findItem(R.id.action_medico_menu).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_medico_refresh:
                onResume();

                break;

            case R.id.action_medico_prescrever:
                llMedicoPrescreve.callOnClick();

                break;

            case R.id.action_medico_alta:
                llMedicoAlta.callOnClick();

                break;

            case R.id.action_medico_logoff:
                finish();

                break;

            default:
                return super.onOptionsItemSelected(item);

        }

        return true;
    }
}

