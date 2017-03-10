package com.example.bgfvg.qq.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by BGFVG on 2017/3/10.
 */

public class ToastUtils {

    public static Toast sToast;

    public static void showToast(Context context, String message) {
        if (sToast == null)
            sToast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
        sToast.setText(message);
        sToast.show();
    }
}
