package com.paanxis.mojoy.app.net.account;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import com.paanxis.mojoy.app.activity.ActivityManage;
import com.paanxis.mojoy.app.activity.HomeActivity;
import com.paanxis.mojoy.app.net.HttpConnect;
import com.paanxis.mojoy.app.net.HttpHandler;
import com.paanxis.mojoy.app.net.HttpThread;
import com.paanxis.mojoy.app.util.Encryption;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 登录.
 * Created by zhengxiaoyao0716 on 2016/2/24.
 */
public class Login extends HttpThread {
    public static String token;

    private JSONObject content;
    public Login(Activity activity, String account, String password, int type)
    {
        //有token登录记录为quickLogin
        super(activity, new LoginHandler(activity));

        JSONObject content = new JSONObject();
        try {
            content.put("account", account);
            content.put("password", Encryption.INSTANCE.hmacSha256Hex(password));
            content.put("type", type);
            this.content = content;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (password != null)
        {
            //记录account
            SharedPreferences preferences = activity.getSharedPreferences("config", Context.MODE_PRIVATE);
            preferences.edit().putString("account", account).apply();
        }
    }

    @Override
    public Object getMessageObject() throws IOException {
        return HttpConnect.post("/userController.do?login", content);
    }

    private static class LoginHandler extends HttpHandler {
        public LoginHandler(Activity activity) {
            super(activity);
        }

        @Override
        public void success(Activity activity, JSONObject content) {
            //记录token
            token = content.optString("token");
            SharedPreferences preferences = activity.getSharedPreferences("config", Context.MODE_PRIVATE);
            preferences.edit().putString("token", token).apply();

            ActivityManage.jumpNew(activity, HomeActivity.class);
        }
    }
}
