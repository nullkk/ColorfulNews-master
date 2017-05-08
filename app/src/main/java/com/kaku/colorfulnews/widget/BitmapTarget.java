package com.kaku.colorfulnews.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.kaku.colorfulnews.utils.DensityUtil;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/8 10:35
 * @Version:
 * @Description:
 */

public class BitmapTarget extends SimpleTarget<Bitmap>
{
    private URLDrawable urlDrawable;
    private TextView textView;
    private Context context;

    
    public BitmapTarget(URLDrawable urlDrawable, TextView textView, Context context) {
        this.urlDrawable = urlDrawable;
        this.textView = textView;
        this.context = context;
    }

    @Override
    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
        Drawable drawable = new BitmapDrawable(context.getResources(), resource);
        int widthScreen = DensityUtil.getDisplayMetrics(context).widthPixels;
        int resHeight = drawable.getIntrinsicHeight();
        int resWidth = drawable.getIntrinsicWidth();
        int hight = resHeight * (widthScreen - 40) / resWidth;
        Rect rect = new Rect(20, 20, widthScreen - 20, hight);
        drawable.setBounds(rect);
        urlDrawable.setBounds(rect);
        urlDrawable.setDrawable(drawable);
        textView.setText(textView.getText());
        textView.invalidate();
    }
}
