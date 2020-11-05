package com.example.hospital.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface PacienteService {

    @POST("paciente")
    Call<PacienteService> create(@Body PacienteService paciente);

    @PUT("paciente")
    Call<PacienteService> updatePaciente(@Query("id") long id, @Body PacienteService paciente);

    @GET("paciente")
    Call<List<PacienteService>> getAllPaciente();

    @GET("paciente")
    Call<PacienteService> getPaciente(@Query("id") long id);

    @DELETE("paciente")
    Call<PacienteService> delete(@Query("id") long id);
}
