package com.jainmaitri.app;
import android.text.InputFilter;
import android.text.Spanned;

public class NoNewlineInputFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        for (int i = start; i < end; i++) {
            if (source.charAt(i) == '\n') {
                return "";
            }
        }
        return null; // Accept the original characters
    }
}