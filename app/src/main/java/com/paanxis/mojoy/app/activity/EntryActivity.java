package com.paanxis.mojoy.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.paanxis.mojoy.app.R;

/**
 * 注册or登录.
 * Created by zhengxiaoyao0716 on 2016/2/28.
 */
public class EntryActivity extends Activity {


    private View signUpButton;
    private View loginButton;
    private View signUpView;
    private View loginView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        signUpButton = findViewById(R.id.signUpButton);
        loginButton = findViewById(R.id.loginButton);

        signUpView = findViewById(R.id.signUpView);
        loginView = findViewById(R.id.loginView);
    }

    //注册or登录切换
    public void onEntryMethodTagClick(View view)
    {
        boolean flag = view.getId() == R.id.signUpButton;

        signUpButton.setEnabled(!flag);
        loginButton.setEnabled(flag);

        signUpView.setVisibility(flag ? View.VISIBLE : View.GONE);
        loginView.setVisibility(!flag ? View.VISIBLE : View.GONE);
    }

    //注册
    public void onSignUpButtonClick(View view)
    {
        //TODO
        ActivityManage.jumpNew(this, HomeActivity.class, false);
    }
    //发送验证码
    public void onSendSmsCodeButtonClick(View view)
    {
    }

    //登录
    public void onLoginButtonClick(View view)
    {
        //TODO
        ActivityManage.jumpNew(this, HomeActivity.class, false);
    }
    //忘记密码
    public void onMissPasswordTipClick(View view)
    {
    }

    //快捷注册
    public void onQuickSignUpButtonClick(View view)
    {
        //TODO
        ActivityManage.jumpNew(this, HomeActivity.class, false);
    }
    //随便逛逛
    public void onJumpEntryButtonClick(View view) {
        //TODO
        ActivityManage.jumpNew(this, HomeActivity.class, false);
    }
}
