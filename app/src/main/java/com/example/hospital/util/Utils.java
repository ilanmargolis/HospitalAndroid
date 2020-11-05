package com.example.hospital.util;

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
        for (int i = 0; i < horasDia.size(); i++ ) {
            if (horasDia.get(i).equals(hora)) {
                return i;
            }
        }

        return -1;
    }
}
