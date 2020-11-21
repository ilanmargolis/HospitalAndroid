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
            if (setor.getId() <= 2) {
                return "Esse setor faz parte do perfil de funcionários e, por isso, não será possível excluir!";
            } else if (getByLeito(setor.getId()).size() > 0) {
                return "Não é possível excluir esse setor, ele está sendo utilizada em leitos!";
            } else {
                db.setorDao().delete(setor);
            }

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

    public Setor getById(long id){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.setorDao().getById(id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Setor> getByLeito(long setor_id){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.setorDao().getByLeito(setor_id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Setor> getGeraLeitos(){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.setorDao().getGeraLeitos();
        } catch (Exception e) {
            return null;
        }
    }
}