package com.dashwood.dashwoodgym.handler;

import android.text.TextUtils;

import com.google.android.material.textfield.TextInputLayout;

public class HandlerCheckValue {

    public static boolean checkEmptyOrNullValue(String value) {
        if (TextUtils.isEmpty(value)) {
            return true;
        }
        if (value.equals("null")) {
            return true;
        }
        return value.equals("NULL");
    }

    public static boolean isNumber(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (Exception p) {
            return false;
        }
    }

    public static boolean isNumberOrDecimal(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (Exception p) {
            return isDouble(value);
        }
    }

    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (Exception p) {
            return false;
        }
    }

    public static boolean isInvalidChar(String value) {
        for (char c : value.toCharArray()) {
            if (c == '.' || isNumberOrDecimal(String.valueOf(c))) {
                continue;
            }
            return true;
        }
        return false;
    }

    public static boolean isValidCharDecimal(String value, TextInputLayout inputLayout, String message) {
        if (HandlerCheckValue.isInvalidChar(value)) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(message);
            return false;
        }
        inputLayout.setErrorEnabled(false);
        return true;
    }

    public static boolean isValidCharInt(String value, TextInputLayout inputLayout, String message) {
        if (!HandlerCheckValue.isNumber(value)) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(message);
            return false;
        }
        inputLayout.setErrorEnabled(false);
        return true;
    }
}