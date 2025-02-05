package com.example.hospital.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.hospital.R;
import com.example.hospital.controller.FuncionarioCtrl;
import com.example.hospital.controller.MedicoCtrl;
import com.example.hospital.controller.PacienteCtrl;
import com.example.hospital.model.Funcionario;
import com.example.hospital.model.Medico;
import com.example.hospital.model.Paciente;
import com.example.hospital.model.Usuario;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {

    private TextView tvEsqueceuSenha;
    private Button btLogin;
    private EditText etUsername, etPassword;
    private ImageView ivCadeado;
    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvEsqueceuSenha = (TextView) findViewById(R.id.tvEsqueceuSenha);
        btLogin = (Button) findViewById(R.id.btLogin);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        ivCadeado = findViewById(R.id.ivCadeado);

        etUsername.addTextChangedListener(new ValidarLogin(btLogin));
        etPassword.addTextChangedListener(new ValidarLogin(btLogin));

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUsername.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();
                int setor = 0;

                Usuario userDados = procuraUsuario(user);

                if (userDados != null && userDados.getSenha() != null && userDados.getSenha().equals(pass)) {
                    if (userDados instanceof Paciente) {
                        intent = new Intent(LoginActivity.this, MenuPacienteActivity.class);
                    } else if (userDados instanceof Medico) {
                        intent = new Intent(LoginActivity.this, MenuMedicoActivity.class);
                        intent.putExtra("medico", (Serializable) userDados);
                    } else if (userDados instanceof Funcionario) {
                        setor = (int) ((Funcionario) userDados).getSetor().getId();

                        switch (setor) {
                            case 1:
                                intent = new Intent(LoginActivity.this, MenuAdministrativoActivity.class);
                                break;

                            case 2:
                                intent = new Intent(LoginActivity.this, MenuRecepcaoActivity.class);
                                break;

                            default:
                                intent = new Intent(LoginActivity.this, MenuSetorActivity.class);
                                intent.putExtra("funcionario", (Serializable) ((Funcionario) userDados));
                        }
                    }

                    mostraAnimacao(true);
                } else if (user.equals("admin@admin.com") && pass.equals("admin")) {

                    intent = new Intent(LoginActivity.this, MenuAdministrativoActivity.class);

                    mostraAnimacao(true);

                } else {

                    mostraAnimacao(false);

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

    @Override
    protected void onResume() {
        super.onResume();

        etUsername.setText(null);
        etPassword.setText(null);

        etUsername.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_login, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_login_esqueceu:
                tvEsqueceuSenha.callOnClick();

                break;

            case R.id.action_login_sobre:
                Intent intent = new Intent(this, SobreActivity.class);
                startActivity(intent);

                break;

            case R.id.action_login_sair:
                checkExit();

                break;

            default:
                return super.onOptionsItemSelected(item);

        }

        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            checkExit();

            return true;

        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void checkExit() {
        new AlertDialog.Builder(this)
                .setTitle("Sair do aplicativo")
                .setMessage("Deseja realmente sair?")
                .setPositiveButton("sim", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        etUsername.requestFocus();
                    }
                })
                .show();
    }

    private void mostraAnimacao(boolean abrirActivity) {

        int id = 0;
        int loop = 1;
        MediaPlayer mediaPlayer;

        try {
            if (abrirActivity) {
                id = R.drawable.class.getField("cadeado_aberto").getInt(null);
                loop = 1;
            } else {
                id = R.drawable.class.getField("cadeado_fechado").getInt(null);
                loop = 2;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        int finalLoop = loop;

        Glide.with(LoginActivity.this)
                .asGif()
                .listener(new RequestListener<GifDrawable>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                        ivCadeado.setVisibility(View.VISIBLE);
                        btLogin.setEnabled(false);

                        resource.setLoopCount(finalLoop);

                        resource.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
                            @Override
                            public void onAnimationEnd(Drawable drawable) {
                                //do whatever after specified number of loops complete
                                ivCadeado.setVisibility(View.INVISIBLE);

                                if (abrirActivity) {
                                    startActivity(intent);
                                }
                            }
                        });

                        return false;
                    }
                })
                .load(id)
                .into(ivCadeado);

        if (abrirActivity) {
            mediaPlayer = MediaPlayer.create(this, R.raw.cadeado_abrir);
        } else {
            mediaPlayer = MediaPlayer.create(this, R.raw.cadeado_travado);
        }

        mediaPlayer.start();
    }

    @Nullable
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