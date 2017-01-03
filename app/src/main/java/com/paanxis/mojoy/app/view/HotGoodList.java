package com.paanxis.mojoy.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 热门物品列表.
 * Created by zhengxiaoyao0716 on 2016/3/11.
 */
public class HotGoodList extends LinearLayout {
    public HotGoodList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void addGoods(JSONArray goods)
    {
        for (int index = 0; index < goods.length(); index++)
        {
            JSONObject good = goods.optJSONObject(index);
            //todo 就一个url???
            String picUrl1 = good.optString("pic_url");
        }
    }
}
