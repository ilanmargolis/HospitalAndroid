package com.example.hospital.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Spinner;

import java.util.ArrayList;

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

        return isConnected;
    }
}
