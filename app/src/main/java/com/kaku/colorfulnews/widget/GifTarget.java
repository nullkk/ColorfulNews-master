package com.kaku.colorfulnews.widget;

import android.content.Context;
import android.graphics.Rect;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.kaku.colorfulnews.utils.DensityUtil;

import java.util.HashSet;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/8 09:06
 * @Version: 1.0
 * @Description: 处理Glide的Target
 */

public class GifTarget extends SimpleTarget<GifDrawable>
{
    private URLDrawable urlDrawable;
    private Context context;
    private HashSet<GifDrawable> gifDrawables;
    private TextView textView;

    
    

    public GifTarget(URLDrawable urlDrawable, Context context, HashSet<GifDrawable> gifDrawables, TextView textView) {
        this.urlDrawable = urlDrawable;
        this.context = context;
        this.gifDrawables = gifDrawables;
        this.textView = textView;
    }

    /**
     * 可以发现以上两个Target的实现比较类似，只是可以提供的数据类型的不同，以及对拿到的resource处理的方式不一样。
     * 程序的入口是onResourceReady方法，我们拿到resource后，首先设置它要显示位置的边界。
     * 我们这里默认将每一个图片的宽度设置为屏幕宽度左右各减去20.高度按照原始宽高进行换算。
     * 然后为urlDrawable赋值，并刷新TextView。这样静态图片就能显示了。
     * 但是GIF还不行。因为GIF需要连续的View重绘才行。所以我们需要为GIF的drawable设置Drawable.CallBack回调。
     * 然后在回调函数里单独对TextView进行刷新动作。具体如下：
     * @param resource
     * @param glideAnimation
     */
    @Override
    public void onResourceReady(GifDrawable resource, GlideAnimation<? super GifDrawable> glideAnimation) 
    {
        //获取屏幕的宽度
        int widthScreen = DensityUtil.getDisplayMetrics(context).widthPixels;
        int resHeight = resource.getIntrinsicHeight();
        int resWidth = resource.getIntrinsicWidth();
        int hight = resHeight * (widthScreen - 50) / resWidth;
        //重新绘制这个图片，距离左上各20各像素，有，下
        Rect rect = new Rect(20, 20, widthScreen - 30, hight);
        resource.setBounds(rect);
        urlDrawable.setBounds(rect);
        urlDrawable.setDrawable(resource);
        gifDrawables.add(resource);
        resource.setCallback(textView);
        resource.start();
        resource.setLoopCount(GlideDrawable.LOOP_FOREVER);
        textView.setText(textView.getText());
        textView.invalidate();
    }
}
