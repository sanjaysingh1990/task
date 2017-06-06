package com.test.sanjay.task.adpater;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.sanjay.task.R;
import com.test.sanjay.task.Utility.Colors;
import com.test.sanjay.task.pojo.CategoryModel;
import com.test.sanjay.task.pojo.ChannelInfo;

import java.util.ArrayList;
import java.util.List;


public class RecyclerChannelsAdapter extends RecyclerView.Adapter<RecyclerChannelsAdapter.CustomViewHolder> {
    private List<ChannelInfo> mChannelList;
    private Context mContext;
    private Colors[] colors;

    public RecyclerChannelsAdapter(Context context, ArrayList<ChannelInfo> mChannelList) {
        this.mChannelList=mChannelList;
        this.mContext = context;
          colors = Colors.values();

    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View viewONE = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_channel, parent, false);
                CustomViewHolder rowONE = new CustomViewHolder(viewONE);
                return rowONE;


    }


    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
       /* holder.mCategory.setBackgroundResource(R.drawable.shape_circle);  //drawable id
        holder.mCategory.setText(mCategoryList.get(position).getCate_name().substring(0,1));
        GradientDrawable gd = (GradientDrawable)holder.mCategory.getBackground().getCurrent();
        gd.setColor(Color.parseColor(colors[position].getColorCode())); //set color*/
        holder.mChannelName.setText(mChannelList.get(position).getChannel_name());
        setAnimation(holder.container,position);

    }

    private void setAnimation(LinearLayout container, int position) {
        Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
        container.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return mChannelList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView mChannelName;
        LinearLayout container;

        public CustomViewHolder(View itemView) {
            super(itemView);
           mChannelName= (TextView) itemView.findViewById(R.id.text_channel_name);
           container= (LinearLayout) itemView.findViewById(R.id.container);

        }
    }
}