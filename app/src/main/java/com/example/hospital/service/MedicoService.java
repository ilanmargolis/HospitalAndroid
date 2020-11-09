package com.example.hospital.service;

import com.example.hospital.model.Medico;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MedicoService {

    @POST("medico")
    Call<Medico> create(@Body Medico medico);

    @PUT("medico/{id}")
    Call<Medico> update(@Path("id") long id, @Body Medico medico);

    @GET("medico")
    Call<List<Medico>> getAll();

    @GET("medico/{id}")
    Call<Medico> get(@Path("id") long id);

    @DELETE("medico/{id}")
    Call<Medico> delete(@Path("id") long id);
}
