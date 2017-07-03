package com.example.blackwhite.materialdrawer.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

/**
 * Created by blackwhite on 2017/6/12.
 */

public class ToastUtil {

    private static Toast error;
    private static Toast success;
    private static Toast info;

    public static void errorToast(Context context, String msg) {
        if (error == null) {
            error = Toasty.error(context, msg, Toast.LENGTH_SHORT);
        } else {
            error.cancel();
            error = Toasty.error(context, msg, Toast.LENGTH_SHORT);
        }
        error.show();
    }

    public static void successToast(Context context, String msg) {
        if (success == null) {
            success = Toasty.success(context, msg, Toast.LENGTH_SHORT);
        } else {
            success.cancel();
            success = Toasty.success(context, msg, Toast.LENGTH_SHORT);
        }
        success.show();
    }

    public static void infoToast(Context context, String msg) {
        if (info == null) {
            info = Toasty.info(context, msg, Toast.LENGTH_SHORT);
        } else {
            info.cancel();
            info = Toasty.info(context, msg, Toast.LENGTH_SHORT);
        }
        info.show();
    }
}
