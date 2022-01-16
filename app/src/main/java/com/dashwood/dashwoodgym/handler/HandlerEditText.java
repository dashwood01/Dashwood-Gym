package com.dashwood.dashwoodgym.handler;


import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.dashwood.dashwoodgym.log.T;
import com.google.android.material.textfield.TextInputLayout;

/**
 * DASHWOOD create this class
 * Checking EditText is empty or not
 */
public class HandlerEditText {
    /**
     * Get array EditText and some message for show in EditText and return true or false
     * if true we have find out this EditText is empty and set error and show message to client
     *
     * @param edt     array EditText[]
     * @param message String message, something u want show to client and say u can't empty this one.
     * @return return boolean and u can find out what to do if return false empty EditText
     */
    public static boolean isEmpty(EditText[] edt, String message) {
        for (EditText editText : edt) {
            if (TextUtils.isEmpty(editText.getText().toString())) {
                editText.setError(message);
                return true;
            }
        }
        return false;

    }

    /**
     * Checking one EditText is empty or not
     *
     * @param edt     get one EditText and checking is Empty or not
     * @param message get message String and set to EditText when is empty
     * @return return boolean for checking that
     */
    public static boolean isEmpty(EditText edt, String message) {
        if (TextUtils.isEmpty(edt.getText().toString())) {
            edt.setError(message);
            return true;
        }
        return false;

    }

    public static boolean isEmpty(TextInputLayout inputLayout, EditText edt, String message) {
        if (TextUtils.isEmpty(edt.getText().toString())) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(message);
            return true;
        }
        return false;

    }

    public static boolean isEmpty(TextInputLayout[] inputLayouts, EditText[] edt, String message) {
        int counter = 0;
        for (EditText editText : edt) {
            if (TextUtils.isEmpty(editText.getText().toString())) {
                inputLayouts[counter].setErrorEnabled(true);
                inputLayouts[counter].setError(message);
                return true;
            }
            counter++;
        }
        return false;

    }

    public static boolean isEmpty(EditText[] edt, String message, Context context) {
        int counter = 0;
        for (EditText editText : edt) {
            if (TextUtils.isEmpty(editText.getText().toString())) {
                T.toast(context, message);
                return true;
            }
            counter++;
        }
        return false;

    }
}