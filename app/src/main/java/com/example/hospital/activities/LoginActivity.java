package com.example.hospital.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hospital.R;
import com.example.hospital.config.RoomConfig;
import com.example.hospital.model.Funcionario;
import com.example.hospital.model.Medico;
import com.example.hospital.model.Paciente;
import com.example.hospital.model.Usuario;

import java.util.ArrayList;
import java.util.List;

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

        etUsername.addTextChangedListener(new GeneticTextListener(btLogin));
        etPassword.addTextChangedListener(new GeneticTextListener(btLogin));

        carregaUsuarios();

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUsername.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();
                Intent intent = null;

                Usuario userDados = procuraUsuario(user);

                if (userDados != null && userDados.getSenha().equals(pass)) {
                    if (userDados instanceof Paciente) {
                        intent = new Intent(LoginActivity.this, MenuPacienteActivity.class);
                    } else if (userDados instanceof Medico) {
                        intent = new Intent(LoginActivity.this, MenuMedicoActivity.class);
                    } else if (userDados instanceof Funcionario) {
                        if (((Funcionario) userDados).getSetor().equals("admin")) {
                            intent = new Intent(LoginActivity.this, MenuAdministrativoActivity.class);
                        } else {
                            intent = new Intent(LoginActivity.this, MenuRecepcaoActivity.class);
                        }
                    }

                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Usuário ou login não encontrado!", Toast.LENGTH_LONG).show();
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

    // provisório enquanto não tem o banco de dados
    private Usuario procuraUsuario(String user) {
        for (int i = 0; i < usuarioList.size(); i++) {
            if (usuarioList.get(i).getEmail().trim().equals(user)) {
                return usuarioList.get(i);
            }
        }

        return null;
    }

    // provisório enquanto não tem o banco de dados
    private void carregaUsuarios() {
        usuarioList = new ArrayList<>();

        // carrega médicos
        Usuario m1 = new Medico("Carlos", "carlos@gmail.com", "cacau");
        usuarioList.add(m1);

        // carrega pacientes
        Usuario p1 = new Paciente("Maria", "maria@gmail.com", "maria");
        usuarioList.add(p1);

        // carrega funcionario
        Usuario f1 = new Funcionario("Adriana", "adriana@gmail.com", "adriana", "recepção");
        Usuario f2 = new Funcionario("Douglas", "douglas@gmail.com", "douglas", "admin");
        usuarioList.add(f1);
        usuarioList.add(f2);
    }

    @Override
    protected void onResume() {
        super.onResume();

        etUsername.setText(null);
        etPassword.setText(null);
    }

    private class GeneticTextListener implements TextWatcher {

        private View view;

        public GeneticTextListener(View v) {
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
