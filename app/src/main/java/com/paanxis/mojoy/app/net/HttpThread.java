package com.paanxis.mojoy.app.net;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;

/**
 * HttpThread.
 * Created by zhengxiaoyao0716 on 2016/2/24.
 */
public abstract class HttpThread extends Thread {
    private final boolean netConnected;
    private final Handler handler;

    public HttpThread(Activity activity, Handler handler)
    {
        netConnected = HttpConnect.checkNet(activity);
        this.handler = handler;
    }

    /**
     * 整合了网络状态检测.
     */
    @Override
    public void run() {
        Message message = handler.obtainMessage();

        //检查网络状态
        if (!netConnected)
        {
            message.what = -1;
            handler.sendMessage(message);
            return;
        }

        //获取数据
        try {
            message.obj = getMessageObject();
            message.what = message.obj == null ? 0 : 1;
        } catch (IOException e) {
            e.printStackTrace();
            message.what = -1;
        }

        handler.sendMessage(message);

    }
    public abstract Object getMessageObject() throws IOException;
}
