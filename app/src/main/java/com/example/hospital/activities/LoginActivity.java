package com.example.hospital.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hospital.R;
import com.example.hospital.controller.CbosCtrl;
import com.example.hospital.controller.ConselhoCtrl;
import com.example.hospital.controller.FuncionarioCtrl;
import com.example.hospital.controller.MedicoCtrl;
import com.example.hospital.controller.PacienteCtrl;
import com.example.hospital.controller.TerminologiaCtrl;
import com.example.hospital.model.Cbos;
import com.example.hospital.model.Conselho;
import com.example.hospital.model.Funcionario;
import com.example.hospital.model.Medico;
import com.example.hospital.model.Paciente;
import com.example.hospital.model.Terminologia;
import com.example.hospital.model.Usuario;
import com.example.hospital.util.Utils;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private TextView tvEsqueceuSenha;
    private Button btLogin;
    private EditText etUsername, etPassword;
    private ArrayList<Usuario> usuarioList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvEsqueceuSenha = (TextView) findViewById(R.id.tvEsqueceuSenha);
        btLogin = (Button) findViewById(R.id.btLogin);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        etUsername.addTextChangedListener(new ValidarLogin(btLogin));
        etPassword.addTextChangedListener(new ValidarLogin(btLogin));

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUsername.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();
                Intent intent = null;

                Usuario userDados = procuraUsuario(user);

                if (userDados != null && userDados.getSenha() != null && userDados.getSenha().equals(pass)) {
                    if (userDados instanceof Paciente) {
                        intent = new Intent(LoginActivity.this, MenuPacienteActivity.class);
                    } else if (userDados instanceof Medico) {
                        intent = new Intent(LoginActivity.this, MenuMedicoActivity.class);
                    } else if (userDados instanceof Funcionario) {
                        if (((Funcionario) userDados).getSetor() == 0) {
                            intent = new Intent(LoginActivity.this, MenuAdministrativoActivity.class);
                        } else {
                            intent = new Intent(LoginActivity.this, MenuRecepcaoActivity.class);
                        }
                    }

                    startActivity(intent);
                } else if (user.equals("admin@admin.com") && pass.equals("admin")) {
                    intent = new Intent(LoginActivity.this, MenuAdministrativoActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Usuário e/ou senha não conferem!", Toast.LENGTH_LONG).show();
                }
            }
        });

        tvEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RecuperarSenhaActivity.class);
                startActivity(intent);
            }
        });
    }

    private Usuario procuraUsuario(String user) {

        Usuario usuario = null;

        usuario = new FuncionarioCtrl(this).getByEmail(user);

        if (usuario != null) {
            return usuario;
        } else {
            usuario = new MedicoCtrl(this).getByEmail(user);

            if (usuario != null) {
                return usuario;
            } else {
                usuario = new PacienteCtrl(this).getByEmail(user);

                if (usuario != null) {
                    return usuario;
                }
            }

        }

        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();

        etUsername.setText(null);
        etPassword.setText(null);
    }

    private class ValidarLogin implements TextWatcher {

        private View view;

        public ValidarLogin(View v) {
            this.view = v;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = etUsername.getText().toString();

            view.setEnabled(email.indexOf('@') > -1 && email.indexOf('.') > -1 &&
                    email.length() > 7 && etPassword.length() > 4);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}