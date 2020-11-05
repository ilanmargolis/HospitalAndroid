package com.example.hospital.service;

import com.example.hospital.model.Internado;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface InternadoService {

    @POST("internado")
    Call<Internado> create(@Body Internado internado);

    @PUT("internado")
    Call<Internado> updateInternado(@Query("id") long id, @Body Internado internado);

    @GET("internado")
    Call<List<Internado>> getAllInternados();

    @GET("internado")
    Call<Internado> getInternado(@Query("id") long id);

    @DELETE("internado")
    Call<Internado> delete(@Query("id") long id);
}
