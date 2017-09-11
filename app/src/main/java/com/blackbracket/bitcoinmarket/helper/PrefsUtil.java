package com.blackbracket.bitcoinmarket.helper;

import android.content.Context;

/**
 * Created by Anish on 18/5/16.
 */
public class PrefsUtil {


    private static String isIntroShown = "isIntroShown";

    public static void setIntroStatus(Context context, Boolean isShown) {
        Prefs.with(context).save(isIntroShown, isShown);
    }

    public static boolean isIntroShown(Context context) {
        return Prefs.with(context).getBoolean(isIntroShown, false);
    }
}
