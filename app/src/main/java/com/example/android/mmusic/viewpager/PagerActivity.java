package com.example.android.mmusic.viewpager;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.android.mmusic.R;
import com.example.android.mmusic.viewpager.pager2.ViewPagerCarouselView;

import java.util.ArrayList;
import java.util.List;



public class PagerActivity extends AppCompatActivity implements Runnable{


    private ViewPagerCarouselView viewPagerCarouselView;
    List<Drawable> images;

    private int handlerDelay = 1000;
    private Handler mHandel = new  Handler();
    private int currentPage = 0;
    private boolean movingToRight = true;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        images = new ArrayList<>();
        images.add(getResources().getDrawable(R.mipmap.ic_launcher));
        images.add(getResources().getDrawable(R.mipmap.ic_launcher));
        images.add(getResources().getDrawable(R.mipmap.ic_launcher));
        images.add(getResources().getDrawable(R.mipmap.ic_launcher));

        viewPagerCarouselView =  findViewById(R.id.main_pager);
        viewPagerCarouselView.setData(this, images);

        mHandel.postDelayed(this, handlerDelay);

    }


    private void startCounting() {
        mHandel.removeCallbacks(this);
        mHandel.postDelayed(this, handlerDelay);
    }


    @Override
    public void run() {

        if (currentPage == 0) {
            movingToRight = true;
            viewPagerCarouselView.setCurrentItem(currentPage + 1);
            currentPage = currentPage + 1;
            startCounting();
            return;
        }

        if (currentPage == 3) {
            movingToRight = false;
            viewPagerCarouselView.setCurrentItem(currentPage - 1);
            currentPage = currentPage - 1;
            startCounting();
            return;
        }

        if (movingToRight) {
            viewPagerCarouselView.setCurrentItem(currentPage + 1);
            currentPage = currentPage + 1;
            startCounting();
        } else {
            viewPagerCarouselView.setCurrentItem(currentPage - 1);
            currentPage = currentPage - 1;
            startCounting();
        }


    }

}
