package com.paanxis.mojoy.app.activity;

import android.app.Activity;
import android.content.Intent;

/**
 * 界面管理.
 * Created by zhengxiaoyao0716 on 2016/2/28.
 */
public class ActivityUtil {
    public static <T> void jumpNew(Activity activity, Class<T> activityClass)
    {
        Intent intent = new Intent(activity, activityClass);
        activity.startActivity(intent);
        activity.finish();
    }
}
