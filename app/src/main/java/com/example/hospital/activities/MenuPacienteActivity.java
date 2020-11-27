package com.example.hospital.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.hospital.R;

public class MenuPacienteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_paciente);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_paciente, menu);

        // Esconder do menu a atual tela
        menu.findItem(R.id.action_paciente_refresh).setVisible(false);
        menu.findItem(R.id.action_paciente_add).setVisible(false);
        menu.findItem(R.id.action_paciente_resultado).setVisible(false);
        menu.findItem(R.id.action_paciente_menu).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_paciente_logoff:
                finish();

                break;

            default:
                return super.onOptionsItemSelected(item);

        }

        return true;
    }
}