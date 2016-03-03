package com.paanxis.mojoy.app.net;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Message;

/**
 * 注册.
 * Created by zhengxiaoyao0716 on 2016/3/2.
 */
public class SignUp extends HttpThread {
    private String email;
    private String phone;
    private String smsCode;
    private String password;

    public SignUp(Activity activity, String email, String phone, String smsCode, String password) {
        super(activity);
        this.email = email;
        this.phone = phone;
        this.smsCode = smsCode;
        this.password = password;

        //记录account
        SharedPreferences preferences = activity.getSharedPreferences("config", Context.MODE_PRIVATE);
        preferences.edit().putString("account", phone).putString("password", password).apply();
    }

    @Override
    void run(Message message) {
        //TODO
    }
}
