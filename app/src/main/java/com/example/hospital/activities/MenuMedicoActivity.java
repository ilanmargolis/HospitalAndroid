package com.example.hospital.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.hospital.R;
import com.example.hospital.model.Medicamento;
import com.example.hospital.model.Medico;

import java.io.Serializable;

public class MenuMedicoActivity extends AppCompatActivity {

    LinearLayout llMedicoAlta;
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

        llMedicoAlta = (LinearLayout) findViewById(R.id.llMedicoAlta);

        llMedicoAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuMedicoActivity.this, InternadoActivity.class);
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

                case TELA_ALTA:
                    llMedicoAlta.callOnClick();
                    break;

            }
        }
    }
}

