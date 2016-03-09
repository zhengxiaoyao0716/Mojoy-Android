package com.paanxis.mojoy.app.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import com.paanxis.mojoy.app.R;
import com.paanxis.mojoy.app.net.account.Login;

public class GuideActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
                String account = preferences.getString("account", null);
                String token = preferences.getString("token", null);
                Login.token = token;

                ActivityManage.jumpNew(GuideActivity.this,
                        (account == null || token == null) ? EntryActivity.class : HomeActivity.class
                );
            }
        }, 1500);
    }
}
