package com.example.hospital.config;

import androidx.room.TypeConverter;

import com.example.hospital.model.Unidade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Converters {

    private static ObjectMapper objectMapper;

    @TypeConverter // Converter um tipo em uma string
    public static String listToJson(Unidade value) {
        objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{'Error':'Convert error'}";
        }
    }

    @TypeConverter //Converter uma string em um tipo
    public static Unidade jsonToList(String value) {
        objectMapper = new ObjectMapper();
        Unidade arr = new Unidade();
        try {
            arr = objectMapper.readValue(value, Unidade.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }
}