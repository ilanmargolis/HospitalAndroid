package com.example.hospital.config;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.hospital.dao.AltaDao;
import com.example.hospital.dao.CbosDao;
import com.example.hospital.dao.ConselhoDao;
import com.example.hospital.dao.FuncionarioDao;
import com.example.hospital.dao.InternarDao;
import com.example.hospital.dao.LeitoDao;
import com.example.hospital.dao.MedicamentoDao;
import com.example.hospital.dao.MedicoDao;
import com.example.hospital.dao.PacienteDao;
import com.example.hospital.dao.PrescreveDao;
import com.example.hospital.dao.SetorDao;
import com.example.hospital.dao.TerminologiaDao;
import com.example.hospital.dao.UnidadeDao;
import com.example.hospital.model.Alta;
import com.example.hospital.model.Cbos;
import com.example.hospital.model.Conselho;
import com.example.hospital.model.Funcionario;
import com.example.hospital.model.Internado;
import com.example.hospital.model.Leito;
import com.example.hospital.model.Medicamento;
import com.example.hospital.model.Medico;
import com.example.hospital.model.Paciente;
import com.example.hospital.model.Prescreve;
import com.example.hospital.model.Setor;
import com.example.hospital.model.Terminologia;
import com.example.hospital.model.Unidade;

@Database(entities = {Alta.class, Cbos.class, Conselho.class, Funcionario.class, Internado.class,
        Leito.class, Medicamento.class, Medico.class, Paciente.class, Prescreve.class, Setor.class,
        Terminologia.class, Unidade.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class RoomConfig extends RoomDatabase {

    private static RoomConfig instance = null;

    public static RoomConfig getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    RoomConfig.class,
                    "hospital.db")
                    .allowMainThreadQueries() //Permite que o room rode na main principal
                    .fallbackToDestructiveMigration()  // receia o database se necessário
                    .build();
        }

        return instance;
    }

    public abstract AltaDao altaDao();

    public abstract CbosDao cbosDao();

    public abstract ConselhoDao conselhoDao();

    public abstract FuncionarioDao funcionarioDao();

    public abstract InternarDao internarDao();

    public abstract LeitoDao leitoDao();

    public abstract MedicoDao medicoDao();

    public abstract MedicamentoDao medicamentoDao();

    public abstract PacienteDao pacienteDao();

    public abstract PrescreveDao prescreveDao();

    public abstract SetorDao setorDao();

    public abstract TerminologiaDao terminologiaDao();

    public abstract UnidadeDao unidadeDao();
}