package com.test.sanjay.task.adpater;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.test.sanjay.task.R;
import com.test.sanjay.task.pojo.Item;

import java.util.List;

/**
 * Created by Wasim on 11-06-2015.
 */
public class ViewPagerAdapter extends PagerAdapter {
 
    private Context mContext;
    private List<Item> mItemList;
 
    public ViewPagerAdapter(Context mContext, List<Item> mItemList) {
        this.mContext = mContext;
        this.mItemList=mItemList;
    }
 
    @Override
    public int getCount() {
        return mItemList.size();
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ConstraintLayout) object);
    }
 
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.viewpager_item, container, false);
 
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

        container.addView(itemView);
        //load image from url
        Glide.with(mContext).load(mItemList.get(position).getUrl())
                .thumbnail(0.5f)
                .placeholder(R.drawable.place_holder)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        return itemView;
    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);
    }
}