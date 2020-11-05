package com.example.hospital.service;

import com.example.hospital.model.Medico;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface MedicoService {

    @POST("medico")
    Call<Medico> create(@Body Medico medico);

    @PUT("medico")
    Call<Medico> updateMedico(@Query("id") long id, @Body Medico medico);

    @GET("medico")
    Call<List<Medico>> getAllMedicos();

    @GET("medico")
    Call<Medico> getMedico(@Query("id") long id);

    @DELETE("medico")
    Call<Medico> delete(@Query("id") long id);
}
