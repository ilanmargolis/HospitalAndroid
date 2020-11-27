package com.example.hospital.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hospital.R;

public class RecuperarSenhaActivity extends AppCompatActivity {

    EditText etRecuperarEmail;
    Button btRecuperarEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        etRecuperarEmail = (EditText) findViewById(R.id.etRecuperarEmail);
        btRecuperarEnviar = (Button) findViewById(R.id.btRecuperarEnviar);

        etRecuperarEmail.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ativar o botão de enviar
                String email = etRecuperarEmail.getText().toString().trim();
                boolean bAtivar = email.length() > 7 &&
                        email.indexOf('@') > -1 && email.indexOf('.') > -1;
                btRecuperarEnviar.setEnabled(bAtivar);

                if (bAtivar) {
                    etRecuperarEmail.setTextColor(getResources().getColor(R.color.preto));
                } else {
                    etRecuperarEmail.setTextColor(getResources().getColor(R.color.vermelho));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btRecuperarEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecuperarSenhaActivity.this,
                        "Enviamos um e-mail com as instruções para recuperar o seu acesso ao sistema!",
                        Toast.LENGTH_LONG).show();

                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_logoff, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logoff:
                finish();

                break;

            default:
                return super.onOptionsItemSelected(item);

        }

        return true;
    }
}