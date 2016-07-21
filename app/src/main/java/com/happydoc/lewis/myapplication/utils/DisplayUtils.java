package com.happydoc.lewis.myapplication.utils;

import android.content.Context;

/**
 * Created by Lewis on 2016/7/20.
 */
public class DisplayUtils {

    public static int px2dip(float pxValue, Context context) {
        return (int) (pxValue
                / context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int dip2px(float dipValue, Context context) {
        return (int) (dipValue
                * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
