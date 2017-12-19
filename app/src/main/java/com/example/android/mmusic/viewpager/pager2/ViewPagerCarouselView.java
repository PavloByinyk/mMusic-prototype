package com.example.android.mmusic.viewpager.pager2;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.android.mmusic.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerCarouselView extends RelativeLayout {

              // FragmentManager for managing the fragments withing the ViewPager
    private ViewPager vpCarousel;                           // ViewPager for the Carousel view
    private LinearLayout llPageIndicatorContainer;          // Carousel view item indicator, the little bullets at the bottom of the carousel
    private ArrayList<ImageView> carouselPageIndicators;    // Carousel view item, the little bullet at the bottom of the carousel


    ViewPagerCarouselAdapter viewPagerCarouselAdapter;
    Context context;

    private List<Drawable> list;

    int possitionFragment = 0;

    public ViewPagerCarouselView(Context context) {
        super(context);
        initView(context);
    }

    public ViewPagerCarouselView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ViewPagerCarouselView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_pager_carousel_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        vpCarousel =  this.findViewById(R.id.vp_carousel);
        llPageIndicatorContainer = this.findViewById(R.id.ll_page_indicator_container);
    }

    public void setData(Context context, List<Drawable> list) {
        this.list = list;
        this.context = context;
        initData();
        initCarousel();

    }

    public void setCurrentItem(int position){
        vpCarousel.setCurrentItem(position);
    }


    /**
     * Initialize the data for the carousel
     */
    private void initData() {
        carouselPageIndicators = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ImageView obj = new ImageView(getContext());
            obj.setImageResource(R.drawable.selector_indicator_view);
            obj.setEnabled(false);
            obj.setPadding(15, 0, 15, 0); // left, top, right, bottom
            llPageIndicatorContainer.addView(obj);
            carouselPageIndicators.add(obj);
        }
    }

    /**
     * Initialize carousel views, each item in the carousel view is a fragment
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void initCarousel() {
        carouselPageIndicators.get(0).setImageResource(R.drawable.selector_indicator_view);
        carouselPageIndicators.get(0).setEnabled(true);

        viewPagerCarouselAdapter = new ViewPagerCarouselAdapter(context, list);

        vpCarousel.setAdapter(viewPagerCarouselAdapter);

        vpCarousel.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                possitionFragment = position;
                for (int i = 0; i < carouselPageIndicators.size(); i++) {
                    if (i != position) {
                        carouselPageIndicators.get(i).setEnabled(false);
                    } else {
                        carouselPageIndicators.get(i).setEnabled(true);

                    }


                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }
}

