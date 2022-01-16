package com.dashwood.dashwoodgym.handler;

import java.util.Locale;

public class HandlerReturnValue {
    public static String secondToTime(String seconds) {
        long sec = Long.parseLong(seconds);
        long hours = sec / 3600;
        long minutes = (sec % 3600) / 60;
        long second = sec % 60;
        return String.format(Locale.ROOT, "%02d:%02d:%02d", hours, minutes, second);
    }
}
