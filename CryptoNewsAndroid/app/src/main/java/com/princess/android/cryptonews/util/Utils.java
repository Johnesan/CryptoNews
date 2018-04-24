package com.princess.android.cryptonews.util;

import android.os.Build;

/**
 * Created by numb3rs on 4/22/18.
 */

public class Utils {
    /**
     * API 21
     *
     * @see Build.VERSION_CODES#LOLLIPOP
     */
    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * API 23
     *
     * @see Build.VERSION_CODES#M
     */
    public static boolean hasMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

}
