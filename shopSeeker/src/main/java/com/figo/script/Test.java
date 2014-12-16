package com.figo.script;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by figo on 14/12/16.
 */
public class Test {
    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("MMM-dd-yy HH:mm:ss z", Locale.ENGLISH);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = "Sep-21-14 13:26:14 PST";
        try {
            Date date = format.parse(s);
            System.out.println(format1.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
