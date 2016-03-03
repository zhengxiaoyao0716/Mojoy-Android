package com.paanxis.mojoy.app.net;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Message;
import android.widget.Toast;
import com.paanxis.mojoy.app.R;
import com.paanxis.mojoy.app.activity.ActivityManage;
import com.paanxis.mojoy.app.activity.EntryActivity;
import com.paanxis.mojoy.app.activity.HomeActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 登录.
 * Created by zhengxiaoyao0716 on 2016/2/24.
 */
public class Login extends HttpThread {
    private String account;
    private String token;
    private String password;

    public Login(Activity activity, String account, String token, String password)
    {
        super(activity);
        this.account = account;
        this.token = token;
        this.password = password;

        //token登录记录为quickLogin
        handler = new LoginHandler(activity, token == null);
        if (password != null)
        {
            //记录account
            SharedPreferences preferences = activity.getSharedPreferences("config", Context.MODE_PRIVATE);
            preferences.edit().putString("account", account).putString("password", password).apply();
        }
    }

    @Override
    void run(Message message) {
        JSONObject content = new JSONObject();
        try {
            content.put("account", account);
            content.put("token", token);
            content.put("password", password);
            message.obj = HttpConnect.post("/login", content);
            message.what = message.obj == null ? 0 : 1;
        } catch (JSONException e) {
            e.printStackTrace();
            message.what = 0;
        } catch (IOException e) {
            e.printStackTrace();
            message.what = 0;
        }

        handler.sendMessage(message);
    }

    private static class LoginHandler extends HttpHandler {
        private boolean quickLogin;
        public LoginHandler(Activity activity, boolean quickLogin) {
            super(activity, !quickLogin, R.string.entry_loginTip);
            this.quickLogin = quickLogin;
        }

        @Override
        void handleMessage(Activity activity, Message message) {
            if (message.what != 1)
            {
                if (quickLogin) ActivityManage.jumpNew(activity, EntryActivity.class);
                else Toast.makeText(activity, R.string.entry_loginFailed, Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject response = (JSONObject) message.obj;

            //记录token
            SharedPreferences preferences = activity.getSharedPreferences("config", Context.MODE_PRIVATE);
            preferences.edit().putString("token", response.optString("token")).apply();

            Toast.makeText(activity, R.string.entry_loginSucceed, Toast.LENGTH_SHORT).show();
            ActivityManage.jumpNew(activity, HomeActivity.class);
        }
    }
}
