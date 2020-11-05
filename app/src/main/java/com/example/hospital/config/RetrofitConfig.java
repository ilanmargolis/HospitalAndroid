package com.example.hospital.config;

import com.example.hospital.service.InternadoService;
import com.example.hospital.service.LeitoService;
import com.example.hospital.service.MedicamentoService;
import com.example.hospital.service.MedicoService;
import com.example.hospital.service.PacienteService;
import com.example.hospital.service.TerminologiaService;
import com.example.hospital.service.UnidadeService;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {

    private Retrofit retrofit;

    public RetrofitConfig() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://hospital-recode.herokuapp.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public UnidadeService getUnidadeService() {
        return retrofit.create(UnidadeService.class);
    }

    public LeitoService getLeitoService() {
        return retrofit.create(LeitoService.class);
    }

    public PacienteService getPacienteService() {
        return retrofit.create(PacienteService.class);
    }

    public MedicoService getMedicoService() {
        return retrofit.create(MedicoService.class);
    }

    public MedicamentoService getMedicamentoService() {
        return retrofit.create(MedicamentoService.class);
    }

    public TerminologiaService getTerminologiaService() {
        return retrofit.create(TerminologiaService.class);
    }

    public InternadoService getInternadoService() {
        return retrofit.create(InternadoService.class);
    }
}