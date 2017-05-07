package com.kaku.colorfulnews.widget;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;

import com.kaku.colorfulnews.mvp.ui.activities.DetailImageActivity;

import org.xml.sax.XMLReader;

import java.util.Locale;

/**
 * @author 作者：Shay-Patrick-Cormac
 * @datetime 创建时间：2017/5/6 23:51
 * @email 邮箱： 574583006@qq.com
 * @content 说明：我们只需实现TagHandler的handleTag方法来处理img标签则可，主要是给内容设置一个ClickableSpan
 */
public class CustomTagHandler implements Html.TagHandler {
    private Context context;

    public CustomTagHandler(Context context) {
        
        this.context = context.getApplicationContext();
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader)
    {
        //处理<img>
        if (tag.toLowerCase(Locale.getDefault()).equals("img"))
        {
            //获取长度
            int len = output.length();
            //获取图片的地址
            ImageSpan[] imgs = output.getSpans(len - 1, len, ImageSpan.class);
            String imgUrl = imgs[0].getSource();
            //使图片可点击并监听点击事件
            Log.i("imgUrl值为：", imgUrl);
            output.setSpan(new ClickableImage(context,imgUrl),len-1,len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        
    }
    
    //可点击的图片
    private class ClickableImage extends ClickableSpan
    {
        private String url;
        private Context context;

        ClickableImage(Context context, String url) {
            this.context = context;
            this.url = url;
        }

        @Override
        public void onClick(View widget) 
        {
            //todo 做你自己想做的，譬如跳到另外一个activity,展示图片
           // Toast.makeText(context, "得到的字符串为:"+url, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, DetailImageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            intent.putExtra("哈哈", url);
            context.startActivity(intent);
            
        }
    }
}
