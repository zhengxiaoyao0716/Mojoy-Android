package com.paanxis.mojoy.app.activity.head;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import com.paanxis.mojoy.app.R;
import com.paanxis.mojoy.app.activity.ActivityManage;
import com.paanxis.mojoy.app.activity.body.HomeActivity;

public class SignUpSucceedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_succeed);

        //2s后自动跳转进入应用
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityManage.jumpNew(SignUpSucceedActivity.this, HomeActivity.class);
            }
        }, 2000);
    }
}
