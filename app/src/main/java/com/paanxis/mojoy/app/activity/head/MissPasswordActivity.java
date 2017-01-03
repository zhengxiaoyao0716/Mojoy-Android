package com.paanxis.mojoy.app.activity.head;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.paanxis.mojoy.app.R;
import com.paanxis.mojoy.app.net.account.MissPwd;
import com.paanxis.mojoy.app.net.account.SendCode;
import com.paanxis.mojoy.app.util.InputReader;

import java.util.Comparator;

/**
 * 忘记密码.
 * Created by zhengxiaoyao0716 on 2016/3/9.
 */
public class MissPasswordActivity extends Activity {
    private InputReader reader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miss_pwd);

        reader = new InputReader(this);
    }

    public void onCloseButtonClick(View view) {
        this.finish();
    }

    public void onSendSmsCodeButtonClick(View view) {
        String phone = reader.getInputPhone(R.id.phone);
        if (phone == null) return;

        findViewById(R.id.smsCode).setClickable(false);
        new SendCode(this, phone, 2).start();
    }

    public void onOkButtonClick(View view) {
        String phone = reader.getInputPhone(R.id.phone);
        String smsCode = reader.getEditTextInput(R.id.smsCode, InputReader.Regex.SMS_CODE);
        String password = reader.getEditTextInput(R.id.password, InputReader.Regex.PASSWORD);
        String surePwd = reader.getEditTextInput(R.id.surePwd, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.equals(rhs) ? 1 : -1;
            }
        }, password);
        if (phone == null || smsCode == null || password == null || surePwd == null)
            return;

        new MissPwd(this, phone, smsCode, password).start();
    }
}
