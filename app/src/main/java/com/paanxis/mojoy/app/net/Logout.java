package com.paanxis.mojoy.app.net;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.widget.Toast;
import com.paanxis.mojoy.app.R;
import org.json.JSONException;

import java.io.IOException;

/**
 * 注销.
 * Created by zhengxiaoyao0716 on 2016/2/26.
 */
public class Logout extends HttpThread {

    public Logout(Activity activity)
    {
        super(activity);
        handler = new PullOrdersHandler(activity);
    }

    @Override
    void run(Message message) {
        try {

            message.obj = HttpConnect.get("/logout");
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

    private static class PullOrdersHandler extends HttpHandler
    {
        public PullOrdersHandler(Activity activity) {
            super(activity);
        }

        @Override
        void handleMessage(Activity activity, Message message) {
            if (message.what != 1)
            {
                Toast.makeText(activity, R.string.entry_logoutFailed, Toast.LENGTH_SHORT).show();
                return;
            }

            //注销
            SharedPreferences preferences = activity.getSharedPreferences("config", Context.MODE_PRIVATE);
            preferences.edit().remove("password").apply();

            Intent intent = new Intent(activity, null);
            activity.startActivity(intent);
            activity.finish();
        }
    }
}
