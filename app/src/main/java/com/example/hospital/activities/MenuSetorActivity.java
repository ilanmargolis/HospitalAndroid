package com.example.hospital.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;
import com.example.hospital.model.Funcionario;

public class MenuSetorActivity extends AppCompatActivity {

    private Funcionario funcionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_setor);

        // Altera o título da activity
        funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");

        setTitle(getTitle() + " (" + funcionario.getSetor().getNome().toLowerCase() + ")");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_setor, menu);

        // Esconder do menu a atual tela
        menu.findItem(R.id.action_setor_refresh).setVisible(false);
        menu.findItem(R.id.action_setor_add).setVisible(false);
        menu.findItem(R.id.action_setor_medicacao).setVisible(false);
        menu.findItem(R.id.action_setor_menu).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_setor_logoff:
                finish();

                break;

            default:
                return super.onOptionsItemSelected(item);

        }

        return true;
    }
}