package com.example.hospital.service;

import com.example.hospital.model.Paciente;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PacienteService {

    @POST("paciente")
    Call<Paciente> create(@Body Paciente paciente);

    @PUT("paciente/{id}")
    Call<Paciente> update(@Path("id") long id, @Body Paciente paciente);

    @GET("paciente")
    Call<List<Paciente>> getAll();

    @GET("paciente/{id}")
    Call<Paciente> get(@Path("id") long id);

    @DELETE("paciente/{id}")
    Call<Paciente> delete(@Path("id") long id);
}
