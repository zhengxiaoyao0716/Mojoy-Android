package com.paanxis.mojoy.app.net.account;

import android.app.Activity;
import android.widget.Toast;
import com.paanxis.mojoy.app.net.HttpConnect;
import com.paanxis.mojoy.app.net.HttpHandler;
import com.paanxis.mojoy.app.net.HttpThread;
import com.paanxis.mojoy.app.util.Encryption;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 忘记密码.
 * Created by zhengxiaoyao0716 on 2016/3/9.
 */
public class MissPwd extends HttpThread {
    private JSONObject content;
    public MissPwd(Activity activity, String phone, String smsCode, String password) {
        super(activity, new MissPwdHandler(activity));

        JSONObject content = new JSONObject();
        try {
            content.put("phone", phone);
            content.put("code", smsCode);
            content.put("password", Encryption.INSTANCE.hmacSha256Hex(password));
            this.content = content;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getMessageObject() throws IOException {
        return HttpConnect.post("/userController.do?resetPsw", content);
    }

    private static class MissPwdHandler extends HttpHandler
    {

        public MissPwdHandler(Activity activity) {
            super(activity);
        }

        @Override
        public void success(Activity activity, JSONObject content) {
            Toast.makeText(activity, "修改密码成功，请用新密码登录", Toast.LENGTH_LONG).show();
            activity.finish();
        }
    }
}
