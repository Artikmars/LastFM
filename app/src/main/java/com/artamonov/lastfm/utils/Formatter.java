package com.artamonov.lastfm.utils;

import java.text.DecimalFormat;

public class Formatter {

    public static String formatPlayCount(String playCount) {
        Integer playCountInt = Integer.parseInt(playCount);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(playCountInt);
    }

    public static String formatPlayCount(Integer playCount) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(playCount);
    }
}