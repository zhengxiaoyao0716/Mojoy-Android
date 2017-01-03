package com.paanxis.mojoy.app.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.paanxis.mojoy.app.R;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签按钮.
 * Created by zhengxiaoyao0716 on 2016/3/9.
 */
public class LabelBar extends HorizontalScrollView {
    private Context context;
    private Resources resources;
    private final LinearLayout labelBarContent;
    private List<LinearLayout> items;
    public LabelBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        resources = getResources();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_label_bar, this);
        labelBarContent = (LinearLayout) findViewById(R.id.labelBarContent);

        items = new ArrayList<LinearLayout>();
    }

    public void setLabels(JSONArray labels)
    {
        for (int index = 0; index < labels.length(); index++)
        {
            JSONObject label = labels.optJSONObject(index);
            int id = label.optInt("id");
            String picUrl = label.optString("pic_url");
            String name = label.optString("name");
            String addTime = label.optString("add_time");
        }
    }
    private void addLabel(Bitmap bitmap, CharSequence name, int id)
    {
        addLabel(new BitmapDrawable(context.getResources(), bitmap), name, id);
    }
    public void addLabel(Drawable image, CharSequence name, int id)
    {
        LinearLayout itemLL = new LinearLayout(context);
        itemLL.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(resources.getDimensionPixelOffset(R.dimen.home_label_mL), 0, 0, 0);
        itemLL.setLayoutParams(params);
        int padding = resources.getDimensionPixelOffset(R.dimen.home_labelItem_padding);
        itemLL.setPadding(padding, padding, padding, padding);

        ImageView labelImage = new ImageView(context);
        labelImage.setImageDrawable(image);
        labelImage.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        itemLL.addView(labelImage);

        TextView labelText = new TextView(context);
        labelText.setText(name);
        labelText.setTextSize(resources.getDimension(R.dimen.home_labelTextSize));
        labelText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        itemLL.addView(labelText);


        itemLL.setId(id);
        items.add(itemLL);
        labelBarContent.addView(itemLL);
    }

    public void setOnItemClickListener(OnClickListener listener) {
        for (LinearLayout item : items)
        {
            item.setClickable(true);
            item.setOnClickListener(listener);
        }
    }
}
