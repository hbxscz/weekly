package com.figo.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Created by figo on 14/12/14.
 */
public class PurchaseComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Purchase p1 = (Purchase) o1;
        Purchase p2 = (Purchase) o2;
        SimpleDateFormat format = new SimpleDateFormat("MMM-dd-yy HH:mm:ss z", Locale.ENGLISH);
        int flag = 0;
        try {
            Date d1 = format.parse(p1.getPurchaseDate());
            Date d2 = format.parse(p2.getPurchaseDate());
            flag = d1.compareTo(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
