package com.paanxis.mojoy.app.activity.head;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.paanxis.mojoy.app.R;
import com.paanxis.mojoy.app.activity.ActivityManage;
import com.paanxis.mojoy.app.activity.body.HomeActivity;
import com.paanxis.mojoy.app.net.account.Login;
import com.paanxis.mojoy.app.net.account.SendCode;
import com.paanxis.mojoy.app.net.account.SignUp;
import com.paanxis.mojoy.app.util.InputReader;

/**
 * 注册or登录.
 * Created by zhengxiaoyao0716 on 2016/2/28.
 */
public class EntryActivity extends Activity {
    private View signUpButton;
    private View loginButton;
    private View signUpView;
    private View loginView;

    private InputReader reader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        signUpButton = findViewById(R.id.signUpButton);
        loginButton = findViewById(R.id.loginButton);

        signUpView = findViewById(R.id.signUpView);
        loginView = findViewById(R.id.loginView);

        String account = getSharedPreferences("config", MODE_PRIVATE).getString("account", null);
        if (account != null) {
            onEntryMethodTagClick(loginView);
            ((EditText) loginView.findViewById(R.id.loginPhone)).setText(account);
        }

        reader = new InputReader(this);
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
        String phone = reader.getInputPhone(R.id.signUpPhone);
        String smsCode = reader.getEditTextInput(R.id.signUpSmsCode, InputReader.Regex.SMS_CODE);
        String password = reader.getEditTextInput(R.id.signUpPassword, InputReader.Regex.PASSWORD);
        String email = reader.getEditTextInput(R.id.signUpEmail, InputReader.Regex.EMAIL);
        if (phone == null || smsCode == null || password == null || email == null)
            return;

        new SignUp(this, phone, smsCode, password, email).start();
        //ActivityManage.jumpNew(this, HomeActivity.class, false);
    }
    //发送验证码
    public void onSendSmsCodeButtonClick(View view)
    {
        String phone = reader.getInputPhone(R.id.signUpPhone);
        if (phone == null) return;

        findViewById(R.id.sendSmsCode).setClickable(false);
        new SendCode(this, phone, 1).start();
    }

    //登录
    public void onLoginButtonClick(View view)
    {
        String phone = reader.getInputPhone(R.id.loginPhone);
        String password = reader.getEditTextInput(R.id.loginPassword, InputReader.Regex.PASSWORD);
        if (phone == null || password == null)
            return;

        new Login(this, phone, password, 0).start();
    }
    //忘记密码
    public void onMissPasswordTipClick(View view)
    {
        ActivityManage.jumpNew(this, MissPasswordActivity.class, false);
    }

    //快捷注册
    public void onQuickSignUpButtonClick(View view)
    {
        //TODO
        ActivityManage.jumpNew(this, HomeActivity.class);
    }
    //随便逛逛
    public void onJumpEntryButtonClick(View view) {
        //Client Side Only.
        ActivityManage.jumpNew(this, HomeActivity.class, false);
    }
}
