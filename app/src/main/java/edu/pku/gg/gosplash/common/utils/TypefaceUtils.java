package edu.pku.gg.gosplash.common.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.widget.TextView;

public class TypefaceUtils {

    public static void setTypeface(Context c, TextView t) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            t.setTypeface(Typeface.createFromAsset(c.getAssets(), "fonts/couriernew.ttf"));
        }
    }
}
