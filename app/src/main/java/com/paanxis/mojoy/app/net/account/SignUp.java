package com.paanxis.mojoy.app.net.account;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import com.paanxis.mojoy.app.activity.ActivityManage;
import com.paanxis.mojoy.app.activity.head.SignUpSucceedActivity;
import com.paanxis.mojoy.app.net.HttpConnect;
import com.paanxis.mojoy.app.net.HttpHandler;
import com.paanxis.mojoy.app.net.HttpThread;
import com.paanxis.mojoy.app.util.Encryption;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 注册.
 * Created by zhengxiaoyao0716 on 2016/3/2.
 */
public class SignUp extends HttpThread {
    private JSONObject content;
    public SignUp(Activity activity, String phone, String smsCode, String password, String email) {
        super(activity, new SignUpHandler(activity));

        JSONObject content = new JSONObject();
        try {
            content.put("phone", phone);
            content.put("code", smsCode);
            content.put("password", Encryption.INSTANCE.hmacSha256Hex(password));
            content.put("email", email);
            this.content = content;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //记录account
        SharedPreferences preferences = activity.getSharedPreferences("config", Context.MODE_PRIVATE);
        preferences.edit().putString("account", phone).apply();
    }

    @Override
    public Object getMessageObject() throws IOException {
        return HttpConnect.post("/userController.do?addUser", content);
    }

    private static class SignUpHandler extends HttpHandler {
        public SignUpHandler(Activity activity) {
            super(activity);
        }

        @Override
        public void success(Activity activity, JSONObject content) {
            //记录token
            Login.token = content.optString("token");
            SharedPreferences preferences = activity.getSharedPreferences("config", Context.MODE_PRIVATE);
            preferences.edit().putString("token", Login.token).apply();

            ActivityManage.jumpNew(activity, SignUpSucceedActivity.class, false);
        }
    }
}
