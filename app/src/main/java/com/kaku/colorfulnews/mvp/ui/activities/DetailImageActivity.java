package com.kaku.colorfulnews.mvp.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.kaku.colorfulnews.R;
import com.kaku.colorfulnews.widget.DragPhotoView;


public class DetailImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image);
        DragPhotoView dragPhotoView = (DragPhotoView) findViewById(R.id.dragPhotoView);
        String url = getIntent().getStringExtra("哈哈");
        Glide.with(this).load(url).into(dragPhotoView);
       // dragPhotoView.setImageResource(R.drawable.ic_load_fail);
        
        dragPhotoView.setOnExitListener(new DragPhotoView.OnExitListener() 
        {
            @Override
            public void onExit(DragPhotoView dragPhotoView, float v, float v1, float v2, float v3) 
            {
                
            }
        });
        dragPhotoView.setOnTapListener(new DragPhotoView.OnTapListener() 
        {
            @Override
            public void onTap(DragPhotoView dragPhotoView) {
                
            }
        });
    }
}
