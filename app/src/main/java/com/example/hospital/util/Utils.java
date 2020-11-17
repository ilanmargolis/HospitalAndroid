package com.example.hospital.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Spinner;

import com.example.hospital.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {

    public static int getIndex(Spinner spinner, String texto) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(texto)) {
                return i;
            }
        }

        return 0;
    }

    public static ArrayList<String> geraHoras() {
        ArrayList<String> horasDia = new ArrayList<>();

        for (int i = 1; i <= 24; i++) {
            horasDia.add(String.format("%02d:00", i));
        }

        return horasDia;
    }

    public static int posHora(ArrayList<String> horasDia, String hora) {
        for (int i = 0; i < horasDia.size(); i++) {
            if (horasDia.get(i).equals(hora)) {
                return i;
            }
        }

        return -1;
    }

    public static boolean hasInternet(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return false;
    }

    public static boolean isCPFValido(String cpf) {

        String cpfAux = "";

        if (cpf.length() == 14) {
            // retira os pontos e hífem
            cpf = cpf.replace(".", "").replace("-", "");

            // CPF sem o digito verificador
            cpfAux = cpf.substring(0, 9);

            for (int j = 0; j < 2; j++) {
                int soma = 0;

                for (int i = 0; i < cpfAux.length(); i++) {
                    soma += Integer.parseInt(cpfAux.substring(i, i + 1)) * (cpfAux.length() + 1 - i);
                }

                int digito = 11 - (soma % 11);

                cpfAux += String.valueOf(digito);
            }
        }

        return (cpf.equals(cpfAux));
    }

    public static ArrayList<String> lerCSV(Context context, String arquivoCSV) {
        BufferedReader br = null;
        ArrayList<String> stringList = new ArrayList<>();

        try {
            AssetManager assetMgr = context.getAssets();
            InputStream arquivo = assetMgr.open(arquivoCSV);

            String linha = "";

            br = new BufferedReader(new InputStreamReader(arquivo, "ISO-8859-1"));

            while ((linha = br.readLine()) != null) {
                Log.println(Log.INFO, "t", linha);
                stringList.add((String) linha);
            }

        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return stringList;
    }

    public static Date stringToDate(String aData, String aFormato) {

        if (aData == null) {
            return null;
        }

        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat sdf = new SimpleDateFormat(aFormato);
        Date data = sdf.parse(aData, pos);

        return data;
    }

    public static String dateToString(Date aData, String aFormato) {

        SimpleDateFormat sdf = new SimpleDateFormat(aFormato);

        return sdf.format(aData);
    }

    public static int zebrarGrid(Context context, int i) {
        // Zebrar lista
        if (i % 2 == 0) {
            return context.getResources().getColor(R.color.cinza_1, null);
        } else {
            return context.getResources().getColor(R.color.branco, null);
        }
    }
}
