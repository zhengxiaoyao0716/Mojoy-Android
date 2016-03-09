package com.paanxis.mojoy.app.util;

import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import com.paanxis.mojoy.app.R;

import java.util.Comparator;
import java.util.regex.Pattern;

/**
 * 正则校验.
 * Created by zhengxiaoyao0716 on 2016/3/8.
 */
public class InputReader {
    private final Activity activity;
    private final Animation anim;
    public InputReader(Activity activity)
    {
        this.activity = activity;
        anim = AnimationUtils.loadAnimation(activity, R.anim.shake);
    }
    public String getInputPhone(int id)
    {
        String phone = getEditTextInput(id, Regex.PHONE);
        return phone == null ? null
                : phone.substring(phone.contains("+86") ? 3 : 0).replace(" ", "");
    }
    public String getEditTextInput(int id, Regex regex)
    {

        EditText editText = (EditText) activity.findViewById(id);
        String input = editText.getText().toString();
        if (!Pattern.matches(regex.regular, input))
        {
            editText.startAnimation(anim);
            return null;
        }
        else return input;
    }
    public String getEditTextInput(int id, Comparator<String> comparator, String cmpToRight)
    {
        EditText editText = (EditText) activity.findViewById(id);
        String input = editText.getText().toString();
        if (comparator.compare(input, cmpToRight) < 0)
        {
            editText.startAnimation(anim);
            return null;
        }
        else return input;
    }
    public enum Regex
    {
        PHONE("^(\\+86)?\\s*((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\s*\\d{4}\\s*\\d{4}$"),
        SMS_CODE("^\\d{6}$"),
        PASSWORD("^[a-zA-Z0-9]{6,16}$"),
        EMAIL("(^\\s*$)|(^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$)");

        private final String regular;
        Regex(String regular) { this.regular = regular; }
    }
}
