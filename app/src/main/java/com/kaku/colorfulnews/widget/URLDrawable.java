package com.kaku.colorfulnews.widget;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/8 09:02
 * @Version:1.0
 * @Description: html富文本解析的一个类
 */

public class URLDrawable extends BitmapDrawable implements Drawable.Callback
{
    private Drawable drawable;

    public URLDrawable() {
    }

    @Override
    public void draw(Canvas canvas) 
    {
        super.draw(canvas);
        if (drawable!=null)
            drawable.draw(canvas);
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        
        this.drawable = drawable;
    }
//实现的三个借口
    @Override
    public void invalidateDrawable(Drawable who) 
    {
     invalidateSelf();   
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when)
    {
        scheduleSelf(what, when);
    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) 
    {
        unscheduleSelf(what);

    }


    
}
