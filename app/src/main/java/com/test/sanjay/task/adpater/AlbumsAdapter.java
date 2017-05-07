package com.test.sanjay.task.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.sanjay.task.R;
import com.test.sanjay.task.pojo.Album;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Album> albumList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtTitle;
        private TextView mTxtTimeAgo;
        private TextView mTxtDescription;
        private ImageView mImgAlbumArt;

        public MyViewHolder(View view) {
            super(view);
            mTxtTitle = (TextView) view.findViewById(R.id.txt_title);
            mTxtTimeAgo = (TextView) view.findViewById(R.id.txt_time_ago);
            mTxtDescription = (TextView) view.findViewById(R.id.txt_description);
            mImgAlbumArt = (ImageView) view.findViewById(R.id.image_album_art);
        }
    }


    public AlbumsAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_album_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
         Album album = albumList.get(position);
          holder.mTxtTitle.setText(album.getTitle());
          holder.mTxtTimeAgo.setText(album.getTimeago());
          holder.mTxtDescription.setText(album.getDescription());

        // loading album cover using Glide library
         //Glide.with(mContext).load(album.getAlbumArt()).into(holder.mImgAlbumArt);


    }


    @Override
    public int getItemCount() {
        return albumList.size();
    }
}