package com.paanxis.mojoy.app.net.account;

import android.app.Activity;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;
import com.paanxis.mojoy.app.R;
import com.paanxis.mojoy.app.net.HttpConnect;
import com.paanxis.mojoy.app.net.HttpHandler;
import com.paanxis.mojoy.app.net.HttpThread;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 请求发送短信验证码.
 * Created by zhengxiaoyao0716 on 2016/3/5.
 */
public class SendCode extends HttpThread {
    private JSONObject content;
    public SendCode(Activity activity, String phone, int type) {
        super(activity, new RqSmsCodeHandler(activity));
        JSONObject content = new JSONObject();
        try {
            content.put("phone", phone);
            content.put("type", type);
            this.content = content;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getMessageObject() throws IOException {
        return HttpConnect.post("/identifyCodeController.do?getIdentifyCode", content);
    }

    private static class RqSmsCodeHandler extends HttpHandler {

        public RqSmsCodeHandler(Activity activity) {
            super(activity);
        }

        private TextView sendSmsCodeButton;
        private int coolTime;
        private String sendSmsCodeStr;
        @Override
        public void success(Activity activity, JSONObject content) {
            Toast.makeText(activity, "验证码已发送，请注意查收", Toast.LENGTH_SHORT).show();

            sendSmsCodeButton = (TextView) activity.findViewById(R.id.sendSmsCode);
            coolTime = 30000;
            sendSmsCodeStr = activity.getString(R.string.global_sendSmsCodeText);

            //30s倒计时
            Runnable coolTimeDown = new Runnable() {
                @Override
                public void run() {
                    coolTime -= 1000;
                    if (coolTime > 0)
                    {
                        sendSmsCodeButton.setText(sendSmsCodeStr + "(" + coolTime / 1000 + "s)");
                        new Handler().postDelayed(this, 1000);
                    }
                    else
                    {
                        sendSmsCodeButton.setText(sendSmsCodeStr);
                        sendSmsCodeButton.setClickable(true);
                    }
                }
            };
            sendSmsCodeButton.setText(sendSmsCodeStr + "(" + coolTime / 1000 + "s)");
            new Handler().postDelayed(coolTimeDown, 1000);
        }

        @Override
        public void failed(Activity activity, int errorCode, String desc) {
            super.failed(activity, errorCode, desc);

            activity.findViewById(R.id.sendSmsCode).setClickable(true);
            Toast.makeText(activity, desc, Toast.LENGTH_LONG).show();
        }

        @Override
        public void noneConnect(Activity activity) {
            super.noneConnect(activity);

            activity.findViewById(R.id.sendSmsCode).setClickable(true);
        }
    }
}
