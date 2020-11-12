package com.example.hospital.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.hospital.R;

public class ValidaSenha implements TextWatcher {

    private View view;
    private EditText senha;
    private EditText confirma;

    public ValidaSenha(View view, EditText senha, EditText confirma) {
        this.view = view;
        this.senha = senha;
        this.confirma = confirma;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String senhaAux = senha.getText().toString().trim();
        String confirmaAux = confirma.getText().toString().trim();

        view.setEnabled(!senhaAux.equals("") && !confirmaAux.equals("") &&
                senhaAux.equals(confirmaAux));
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
