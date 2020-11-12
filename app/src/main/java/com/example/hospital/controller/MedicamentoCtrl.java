package com.example.hospital.controller;

import android.content.Context;

import com.example.hospital.config.RoomConfig;
import com.example.hospital.model.Funcionario;
import com.example.hospital.model.Medicamento;

import java.util.List;

public class MedicamentoCtrl {

    private final Context context;

    public MedicamentoCtrl(Context context) {
        this.context = context;
    }

    public String insert(Medicamento medicamento){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.medicamentoDao().insert(medicamento);

            return "Medicamento incluído com sucesso";
        } catch (Exception e) {
            return "Erro ao inserir medicamento: " + e;
        }
    }

    public String update(Medicamento medicamento){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.medicamentoDao().update(medicamento);

            return "Medicamento alterado com sucesso";
        } catch (Exception e) {
            return "Erro ao alterar medicamento: " + e;
        }
    }

    public String delete(Medicamento medicamento){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.medicamentoDao().delete(medicamento);

            return "Medicamento excluído com sucesso";
        } catch (Exception e) {
            return "Erro ao excluir medicamento: " + e;
        }
    }

    public List<Medicamento> getAll(){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.medicamentoDao().getAll();
        } catch (Exception e) {
            return null;
        }
    }
}
