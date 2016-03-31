package net.materialforum.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {

    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public static String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1, input.length());
    }

    public static String formatDate(Date input) {
        return dateFormat.format(input);
    }
    
    public static String formatDateElapsed(Date input) {
        long seconds = (System.currentTimeMillis() - input.getTime()) / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long weeks = days / 7;
        long months = days / 30;
        long years = days / 365;
        
        if (years > 0)
            return plurar(years, "rok temu", "%d lata temu", "%d lat temu");
        if (months > 0)
            return plurar(months, "miesiąc temu", "%d miesiące temu", "%d miesięcy temu");
        if (weeks > 0)
            return plurar(weeks, "tydzień temu", "%d tygodnie temu", "%d tygodni temu");
        if (days > 0)
            return plurar(days, "dzień temu", "%d dni temu", "%d dni temu");
        if (hours > 0)
            return plurar(hours, "godzinę temu", "%d godziny temu", "%d godzin temu");
        if (minutes > 0)
            return plurar(minutes, "minutę temu", "%d minuty temu", "%d minut temu");
        return plurar(seconds, "sekundę temu", "%d sekundy temu", "%d sekund temu");
    }
    
    public static String plurar(long number, String single, String some, String more) {
        if (number > 20 && number % 10 > 1 && number % 10 < 5)
            return String.format(some, number);
        
        if (number < 2)
            return String.format(single, number);
        else if (number < 5)
            return String.format(some, number);
        else
            return String.format(more, number);
    }

    public static String removeHtml(String input) {
        return input.replace("<", "&lt;").replace(">", "&gt;");
    }
    
    public static String encodeUrl(String url) {
        try {
            return URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException ex) { return null; }
    }

}
