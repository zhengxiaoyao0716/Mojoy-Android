package com.paanxis.mojoy.app.net.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.paanxis.mojoy.app.net.HttpConnect;
import com.paanxis.mojoy.app.net.HttpHandler;
import com.paanxis.mojoy.app.net.HttpThread;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 注销.
 * Created by zhengxiaoyao0716 on 2016/2/26.
 */
public class Logout extends HttpThread {

    public Logout(Activity activity) { super(activity, new PullOrdersHandler(activity)); }

    @Override
    public Object getMessageObject() throws IOException {
        return HttpConnect.get("/logout");
    }

    private static class PullOrdersHandler extends HttpHandler
    {
        public PullOrdersHandler(Activity activity) {
            super(activity);
        }

        @Override
        public void success(Activity activity, JSONObject content) {
            //注销
            SharedPreferences preferences = activity.getSharedPreferences("config", Context.MODE_PRIVATE);
            preferences.edit().remove("password").apply();

            Intent intent = new Intent(activity, null);
            activity.startActivity(intent);
            activity.finish();
        }
    }
}
