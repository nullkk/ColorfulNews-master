package com.kaku.colorfulnews.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/8 11:00
 * @Version: 1.0
 * @Description: 富文本，支持带有图片的文本格式
 */

public class RichTextView extends TextView implements  Drawable.Callback,View.OnAttachStateChangeListener
{

    private GlideImageGetter glideImageGetter;
    private OnImageClickListener imageClickListener;
    
    public RichTextView(Context context) {
        this(context,null);
    }

    public RichTextView(Context context, AttributeSet attrs) {
       this(context, attrs,0);
    }

    public RichTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setImageClickListener(OnImageClickListener imageClickListener) {
        this.imageClickListener = imageClickListener;
    }

    //图片点击回调
    public interface OnImageClickListener
    {
        /**
         * 图片被点击的回调
         * @param imageUrls 本篇富文本内容的全部图片
         * @param postion 点击图片在imageUrls中的位置
         * @param widget 当前被点击的View
         */
        void imageClicked(List<String> imageUrls, int postion,View widget);
    }

    @Override
    public void onViewAttachedToWindow(View v) {

    }

    @Override
    public void onViewDetachedFromWindow(View v) 
    {
        //释放内存
         glideImageGetter.recycle();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void invalidateDrawable(Drawable who) {

        invalidateOutline();

    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {
    }
    
    //重要方法。设置富文本
    public void setRichText( String text)
    {
        if (TextUtils.isEmpty(text))
            throw new RuntimeException("参数不能为空");
        glideImageGetter = new GlideImageGetter(getContext(), this);
        Spanned spanned = Html.fromHtml(text, glideImageGetter, null);
        SpannableStringBuilder stringBuilder;
        if (spanned instanceof SpannableStringBuilder)
        {
            stringBuilder = (SpannableStringBuilder) spanned;
        }else
            stringBuilder = new SpannableStringBuilder(spanned);
        //处理图片点击事件
        ImageSpan[] imageSpans = stringBuilder.getSpans(0, stringBuilder.length(), ImageSpan.class);
        final List<String> imageUrls = new ArrayList<>();
        int size = imageSpans.length;
        for (int i = 0; i < size; i++) 
        {
            ImageSpan imageSpan = imageSpans[i];
            String imgUrl = imageSpan.getSource();
            int start = stringBuilder.getSpanStart(imageSpan);
            int end = stringBuilder.getSpanEnd(imageSpan);
            imageUrls.add(imgUrl);
            //设置监听
            final int finalI = i;
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) 
                {
                    if (imageClickListener!=null)
                        imageClickListener.imageClicked(imageUrls, finalI,widget);
                }
            };
            //做什么？
            ClickableSpan[] clickableSpans = stringBuilder.getSpans(start, end, ClickableSpan.class);
            if (clickableSpans!=null && clickableSpans.length!=0)
            {
                for (ClickableSpan span : clickableSpans) 
                {
                  stringBuilder.removeSpan(span);   
                }
            }
            stringBuilder.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            
        }
        super.setText(spanned);
        setMovementMethod(LinkMovementMethod.getInstance());
    }
}
