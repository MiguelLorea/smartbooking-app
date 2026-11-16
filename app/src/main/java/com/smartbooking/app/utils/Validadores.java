package com.smartbooking.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Validadores {
    public static String password(String pass) {
        if (pass == null || pass.length() <= 8) {
            return "Tiene que tener mas de 8 caracteres";
        }
        boolean letra = false;
        boolean numero = false;
        boolean especial = false;
        for (int i = 0; i < pass.length(); i++) {
            char c = pass.charAt(i);
            if (Character.isLetter(c)) {
                letra = true;
            } else if (Character.isDigit(c)) {
                numero = true;
            } else {
                especial = true;
            }
        }
        if (!letra || !numero) {
            return "Tiene que tener letras y numeros";
        }
        if (!especial) {
            return "Tiene que tener un caracter especial";
        }
        return null;
    }

    public static String horario(String inicio, String fin) {
        if (inicio == null || fin == null || inicio.isEmpty() || fin.isEmpty()) {
            return "Completa el horario";
        }
        if (fin.compareTo(inicio) <= 0) {
            return "La hora de fin tiene que ser posterior";
        }
        return null;
    }

    public static boolean esPasada(String fecha, String hora) {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
            formato.setLenient(false);
            Date cuando = formato.parse(fecha + " " + hora);
            return cuando.before(new Date());
        } catch (ParseException e) {
            return true;
        }
    }
}
