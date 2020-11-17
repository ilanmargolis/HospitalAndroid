package com.example.hospital.controller;

import android.content.Context;

import com.example.hospital.config.RoomConfig;
import com.example.hospital.model.Setor;

import java.util.List;

public class SetorCtrl {

    private final Context context;

    public SetorCtrl(Context context) {
        this.context = context;
    }

    public String insert(Setor setor){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.setorDao().insert(setor);

            return "Setor incluído com sucesso";
        } catch (Exception e) {
            return "Erro ao inserir setor: " + e;
        }
    }

    public String update(Setor setor){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.setorDao().update(setor);

            return "Setor alterado com sucesso";
        } catch (Exception e) {
            return "Erro ao alterar setor: " + e;
        }
    }

    public String delete(Setor setor){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.setorDao().update(setor);

            return "Setor excluído com sucesso";
        } catch (Exception e) {
            return "Erro ao excluir setor: " + e;
        }
    }

    public List<Setor> getAll(){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.setorDao().getAll();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Setor> getGeraLeito(){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.setorDao().getGeraLeito();
        } catch (Exception e) {
            return null;
        }
    }

    public Setor get(int id){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.setorDao().get(id);
        } catch (Exception e) {
            return null;
        }
    }


}