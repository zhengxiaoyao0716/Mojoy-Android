package com.paanxis.mojoy.app.activity;

import android.app.Activity;
import android.content.Intent;

/**
 * 界面管理.
 * Created by zhengxiaoyao0716 on 2016/2/28.
 */
public class ActivityManage {
    public static void jumpNew(Activity activity, Class<?> activityClass) { jumpNew(activity, activityClass, true); }
    public static void jumpNew(Activity activity, Class<?> activityClass, boolean finishNow)
    {
        activity.startActivity(new Intent(activity, activityClass));
        if (finishNow) activity.finish();
    }
}
