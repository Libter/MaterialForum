package net.materialforum.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class StringUtils {
    
    private StringUtils() {}
    
    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    
    public static String capitalize(String input) {
       return input.substring(0, 1).toUpperCase() + input.substring(1, input.length());
    }
    
    public static String formatDate(Date input) { 
        return dateFormat.format(input);
    }
    
    public static String removeHtml(String input) {
        return input.replace("<", "lt;").replace(">", "gt;");
    }
    
}
