package com.paanxis.mojoy.app.activity.body;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.paanxis.mojoy.app.R;
import com.paanxis.mojoy.app.view.LabelBar;

/**
 * 首页.
 * Created by zhengxiaoyao0716 on 2016/2/28.
 */
public class HomeActivity extends Activity {
    private LabelBar labelBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        labelBar = (LabelBar) findViewById(R.id.labelBar);
        //todo 从网络获取
        labelBar.addLabel(getResources().getDrawable(R.drawable.temp_circle_button), "潮流美妆", 0);
        labelBar.addLabel(getResources().getDrawable(R.drawable.temp_circle_button), "潮流美妆", 1);
        labelBar.addLabel(getResources().getDrawable(R.drawable.temp_circle_button), "潮流美妆", 2);
        labelBar.addLabel(getResources().getDrawable(R.drawable.temp_circle_button), "潮流美妆", 3);
        labelBar.addLabel(getResources().getDrawable(R.drawable.temp_circle_button), "潮流美妆", 4);
        labelBar.addLabel(getResources().getDrawable(R.drawable.temp_circle_button), "潮流美妆", 5);
        labelBar.setOnItemClickListener(onLabelItemClick);
    }

    public void onDotClick(View view) {
        //todo
        Log.e("onDotClick", "" + view.getTag());
    }

    public void onSearchClick(View view) {
        //todo
        Log.e("onSearchClick", "" + view.getId());
    }

    private View.OnClickListener onLabelItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //todo
            Log.e("onLabelItemClick", "" + v.getId());
        }
    };

    public void onSeeMoreClick(View view) {
        //todo
        Log.e("onSeeMoreClick", "" + view.getId());
    }
}
