package com.kaku.colorfulnews.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.Target;
import com.kaku.colorfulnews.R;

import java.util.HashSet;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/8 10:41
 * @Version:
 * @Description:
 */

public class GlideImageGetter implements Html.ImageGetter 
{
    private HashSet<Target> targets;
    private HashSet<GifDrawable> gifDrawables;
    private Context context;
    private TextView textView;

    public GlideImageGetter(Context context, TextView textView) 
    {
        this.context = context;
        this.textView = textView;
        targets = new HashSet<>();
        gifDrawables = new HashSet<>();
        //绑定
        textView.setTag(R.id.img_tag, this);
    }

    @Override
    public Drawable getDrawable(String url)
    {
        URLDrawable urlDrawable = new URLDrawable();
        GenericRequestBuilder load;
        Target target;
        //判断是gif图还是静态图片
        if (isGif(url))
        {
            load = Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE);
            target = new GifTarget(urlDrawable, context, gifDrawables, textView);
        }else 
        {
            load = Glide.with(context).load(url).asBitmap();
            target = new BitmapTarget(urlDrawable, textView, context);
        }
        targets.add(target);
        load.into(target);
        return urlDrawable;
    }

    private boolean isGif(String url) 
    {
        int index = url.lastIndexOf('.');
        
        return index>0 && "gif".toUpperCase().equals(url.substring(index+1).toUpperCase());
    }

    /**
     * 回收？？
     */
    public  void recycle() {
        targets.clear();
        for (GifDrawable gifDrawable : gifDrawables) {
            gifDrawable.setCallback(null);
            gifDrawable.recycle();
        }
        gifDrawables.clear();
    }
}
