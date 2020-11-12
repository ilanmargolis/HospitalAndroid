package com.example.hospital.controller;

import android.content.Context;

import com.example.hospital.config.RoomConfig;
import com.example.hospital.model.Funcionario;

import java.util.List;

public class FuncionarioCtrl {

    private final Context context;

    public FuncionarioCtrl(Context context) {
        this.context = context;
    }

    public String insert(Funcionario funcionario){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.funcionarioDao().insert(funcionario);

            return "Funcionario incluído com sucesso";
        } catch (Exception e) {
            return "Erro ao inserir funcionario: " + e;
        }
    }

    public String update(Funcionario funcionario){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.funcionarioDao().update(funcionario);

            return "Funcionário alterado com sucesso";
        } catch (Exception e) {
            return "Erro ao alterar funcionario: " + e;
        }
    }

    public String delete(Funcionario funcionario){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.funcionarioDao().update(funcionario);

            return "Funcionário excluído com sucesso";
        } catch (Exception e) {
            return "Erro ao excluir funcionario: " + e;
        }
    }

    public List<Funcionario> getAll(){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.funcionarioDao().getAll();
        } catch (Exception e) {
            return null;
        }
    }

    public Funcionario getByEmail(String email){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.funcionarioDao().getByEmail(email);
        } catch (Exception e) {
            return null;
        }
    }
}