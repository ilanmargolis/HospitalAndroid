package com.example.hospital.config;

import androidx.room.TypeConverter;

import com.example.hospital.model.Cbos;
import com.example.hospital.model.Conselho;
import com.example.hospital.model.Internado;
import com.example.hospital.model.Leito;
import com.example.hospital.model.Medicamento;
import com.example.hospital.model.Medico;
import com.example.hospital.model.Paciente;
import com.example.hospital.model.Setor;
import com.example.hospital.model.Terminologia;
import com.example.hospital.model.Unidade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.Time;
import java.util.Date;

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
    public static Unidade jsonToListUnidade(String value) {
        objectMapper = new ObjectMapper();
        Unidade arr = new Unidade();
        try {
            arr = objectMapper.readValue(value, Unidade.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    @TypeConverter // Converter um tipo em uma string
    public static String listToJson(Setor value) {
        objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{'Error':'Convert error'}";
        }
    }

    @TypeConverter //Converter uma string em um tipo
    public static Setor jsonToListSetor(String value) {
        objectMapper = new ObjectMapper();
        Setor arr = new Setor();
        try {
            arr = objectMapper.readValue(value, Setor.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    @TypeConverter // Converter um tipo em uma string
    public static String listToJson(Leito value) {
        objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{'Error':'Convert error'}";
        }
    }

    @TypeConverter //Converter uma string em um tipo
    public static Leito jsonToListLeito(String value) {
        objectMapper = new ObjectMapper();
        Leito arr = new Leito();
        try {
            arr = objectMapper.readValue(value, Leito.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    @TypeConverter // Converter um tipo em uma string
    public static String listToJson(Paciente value) {
        objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{'Error':'Convert error'}";
        }
    }

    @TypeConverter //Converter uma string em um tipo
    public static Paciente jsonToListPaciente(String value) {
        objectMapper = new ObjectMapper();
        Paciente arr = new Paciente();
        try {
            arr = objectMapper.readValue(value, Paciente.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    @TypeConverter // Converter um tipo em uma string
    public static String listToJson(Terminologia value) {
        objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{'Error':'Convert error'}";
        }
    }

    @TypeConverter //Converter uma string em um tipo
    public static Terminologia jsonToListTerminologia(String value) {
        objectMapper = new ObjectMapper();
        Terminologia arr = new Terminologia();
        try {
            arr = objectMapper.readValue(value, Terminologia.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    @TypeConverter // Converter um tipo em uma string
    public static String listToJson(Conselho value) {
        objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{'Error':'Convert error'}";
        }
    }

    @TypeConverter //Converter uma string em um tipo
    public static Conselho jsonToListConselho(String value) {
        objectMapper = new ObjectMapper();
        Conselho arr = new Conselho();
        try {
            arr = objectMapper.readValue(value, Conselho.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    @TypeConverter // Converter um tipo em uma string
    public static String listToJson(Internado value) {
        objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{'Error':'Convert error'}";
        }
    }

    @TypeConverter //Converter uma string em um tipo
    public static Internado jsonToListInternado(String value) {
        objectMapper = new ObjectMapper();
        Internado arr = new Internado();
        try {
            arr = objectMapper.readValue(value, Internado.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    @TypeConverter // Converter um tipo em uma string
    public static String listToJson(Medico value) {
        objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{'Error':'Convert error'}";
        }
    }

    @TypeConverter //Converter uma string em um tipo
    public static Medico jsonToListMedico(String value) {
        objectMapper = new ObjectMapper();
        Medico arr = new Medico();
        try {
            arr = objectMapper.readValue(value, Medico.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    @TypeConverter // Converter um tipo em uma string
    public static String listToJson(Medicamento value) {
        objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{'Error':'Convert error'}";
        }
    }

    @TypeConverter //Converter uma string em um tipo
    public static Medicamento jsonToListMedicamento(String value) {
        objectMapper = new ObjectMapper();
        Medicamento arr = new Medicamento();
        try {
            arr = objectMapper.readValue(value, Medicamento.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    @TypeConverter // Converter um tipo em uma string
    public static String listToJson(Cbos value) {
        objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{'Error':'Convert error'}";
        }
    }

    @TypeConverter //Converter uma string em um tipo
    public static Cbos jsonToListCbos(String value) {
        objectMapper = new ObjectMapper();
        Cbos arr = new Cbos();
        try {
            arr = objectMapper.readValue(value, Cbos.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static long fromDate(Date date){
        return date == null ? null :date.getTime();
    }
}