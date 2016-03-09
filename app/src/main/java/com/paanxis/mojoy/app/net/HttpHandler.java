package com.paanxis.mojoy.app.net;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.paanxis.mojoy.app.R;
import org.json.JSONObject;

/**
 * HttpHandler.
 * Created by zhengxiaoyao0716 on 2016/2/24.
 */
public abstract class HttpHandler extends Handler {
    private final Activity activity;
    private ProgressDialog progressDialog;

    /**
     * 建立带有等待进度圈默认提示文字的Handler，HttpHandler(this, true, R.string.waiting).
     * @param activity Activity
     */
    public HttpHandler(Activity activity)
    {
        this(activity, true, R.string.global_waiting);
    }
    public HttpHandler(Activity activity, boolean showProgress, int waitTipId)
    {
        this.activity = activity;
        progressDialog = null;
        if (showProgress)
        {
            //加载效果
            progressDialog = new ProgressDialog(activity);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(activity.getString(waitTipId));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    public void handleMessage(Message message)
    {
        if (progressDialog != null) progressDialog.dismiss();

        if (message.what == 1)
        {
            JSONObject msgBody = ((JSONObject) message.obj).optJSONObject("msgBody");
            if (msgBody.optBoolean("status")) success(activity, msgBody.optJSONObject("content"));
            else failed(activity, msgBody.optInt("errorCode"), msgBody.optString("desc"));
        }
        else if (message.what == -1) noneConnect(activity);
        else connectErr(activity);
    }

    public abstract void success(Activity activity, JSONObject content);

    public void failed(Activity activity, int errorCode, String desc) { Toast.makeText(activity, desc, Toast.LENGTH_SHORT).show(); }

    public void noneConnect(Activity activity) { Toast.makeText(activity, R.string.global_noneConnect, Toast.LENGTH_SHORT).show(); }

    public void connectErr(Activity activity) { noneConnect(activity); }
}
