package com.paanxis.mojoy.app.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import com.paanxis.mojoy.app.R;
import com.paanxis.mojoy.app.net.Login;

public class GuideActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
        String account = preferences.getString("account", null);
        String token = preferences.getString("token", null);

        if (account == null || token == null)
        {
            //1.5s后跳转到入口界面
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ActivityManage.jumpNew(GuideActivity.this, EntryActivity.class);
                }
            }, 1500);
        }
        //自动登录
        else new Login(this, account, token, null).start();
    }
}
