package com.example.hospital.service;

import com.example.hospital.model.Terminologia;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TerminologiaService {

    @GET("terminologia")
    Call<Terminologia> getTerminologia(@Query("id") long id);
}
