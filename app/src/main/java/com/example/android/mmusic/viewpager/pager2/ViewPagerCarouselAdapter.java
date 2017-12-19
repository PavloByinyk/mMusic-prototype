package com.example.android.mmusic.viewpager.pager2;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.android.mmusic.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ViewPagerCarouselAdapter extends PagerAdapter {

    private List<Drawable> list;
    LayoutInflater inflater;

    public ViewPagerCarouselAdapter(Context context, List<Drawable> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return (list == null) ? 0: list.size();
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View layout = inflater.inflate(R.layout.item_view_pager, container, false);

        final ImageView ivMain = layout.findViewById(R.id.iv_indicator);

        ivMain.setImageDrawable(list.get(position));

        container.addView(layout, 0);

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}
