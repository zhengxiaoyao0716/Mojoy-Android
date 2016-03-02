package com.paanxis.mojoy.app.net;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

/**
 * HttpThread.
 * Created by zhengxiaoyao0716 on 2016/2/24.
 */
public abstract class HttpThread extends Thread {
    private boolean netConnected;
    Handler handler;
    public HttpThread(Activity activity) {
        netConnected = NetManage.connected(activity);
    }

    /**
     * 整合了网络状态检测.
     */
    @Override
    public void run() {
        Message message = handler.obtainMessage();
        if (!netConnected)
        {
            message.what = -1;
            handler.sendMessage(message);
            return;
        }
        run(message);

    }

    /**
     * 实际运行的内容，重写该方法.
     * @param message 用于装载的消息箱
     */
    abstract void run(Message message);
}
