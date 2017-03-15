package com.example.bgfvg.qq.utils;

import android.text.TextUtils;

/**
 * Created by BGFVG on 2017/3/10.
 */

public class StringUtils {

    public static boolean checkUserName(String username) {
        if (TextUtils.isEmpty(username))
            return false;
        return username.matches("^[a-zA-Z]\\w{2,19}$");
    }

    public static boolean checkPassWord(String password) {
        if (TextUtils.isEmpty(password))
            return false;
        return password.matches("^[0-9]{3,20}$");
    }

    public static String getInitial(String contact) {
        if (TextUtils.isEmpty(contact)) {
            return contact;
        } else {
            return contact.substring(0, 1).toUpperCase();
        }
    }
}



















