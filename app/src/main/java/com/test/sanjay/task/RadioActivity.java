package com.test.sanjay.task;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.test.sanjay.task.Utility.Util;
import com.test.sanjay.task.adpater.RecyclerCategoryAdapter;
import com.test.sanjay.task.adpater.RecyclerChannelsAdapter;
import com.test.sanjay.task.callbacks.CategorySelectedCallback;
import com.test.sanjay.task.callbacks.SnackBarEvent;
import com.test.sanjay.task.databinding.ActivityRadioBinding;
import com.test.sanjay.task.pojo.CategoryModel;
import com.test.sanjay.task.pojo.ChannelInfo;

import java.util.ArrayList;

public class RadioActivity extends AppCompatActivity implements CategorySelectedCallback {
    ActivityRadioBinding binding;
    private LinearLayoutManager mLayoutManager;
    private RecyclerCategoryAdapter mAdapter;
    private ArrayList<CategoryModel> mCategoryList;

    private RecyclerChannelsAdapter mChannelsAdapter;
    private ArrayList<ChannelInfo> mChannelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_radio);
        binding.setRadioActivity(this);
        binding.executePendingBindings();
        init();
    }

    private void init() {

        //disable swipe
        binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        binding.mainContent.textCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawer.openDrawer(GravityCompat.START);

            }
        });
        binding.mainContent.textChannelList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawer.openDrawer(GravityCompat.END);

            }
        });

        binding.mainContent.textCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawer.openDrawer(GravityCompat.START);

            }
        });

         //open drawer on left side click
        binding.rightDrawerContent.textChannelList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawer.closeDrawer(GravityCompat.END);

            }
        });
        binding.leftDrawerContent.textCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawer.closeDrawer(GravityCompat.START);

            }
        });


        mCategoryList=new ArrayList<>();
        mChannelList=new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        //left side recycler view
        binding.leftDrawerContent.recyclerviewLeftdrawer.setLayoutManager(mLayoutManager);
        //right side recycler view

        binding.rightDrawerContent.recyclerviewRightdrawer.setLayoutManager(new LinearLayoutManager(this));
        //recyclerview adapter
        mAdapter = new RecyclerCategoryAdapter(RadioActivity.this, mCategoryList,this);
        mChannelsAdapter=new RecyclerChannelsAdapter(this,mChannelList);
        //set adpater for recyclerview
        binding.leftDrawerContent.recyclerviewLeftdrawer.setAdapter(mAdapter);
        binding.rightDrawerContent.recyclerviewRightdrawer.setAdapter(mChannelsAdapter);
          loadFromServer();
          loadChannels("hindi");

    }

    @Override
    public void categorySelected(String cateName) {
        loadChannels(cateName);
        binding.drawer.closeDrawer(GravityCompat.START);
        binding.drawer.openDrawer(GravityCompat.END);
        binding.rightDrawerContent.progressbar.setVisibility(View.VISIBLE);


    }

    public class ButtonClickEvent {
        /**
         * ********************** BACK BUTTON ***************************
         */
        public void categoryList()

        {
            binding.drawer.openDrawer(GravityCompat.START);

        }

        public void channelList()

        {
            binding.drawer.openDrawer(GravityCompat.END);

        }

    }

    private void loadFromServer() {
        if (Util.getInstance().isOnline(this)) {
          //  activityFavoriteBinding.progressbar.setVisibility(View.VISIBLE);

            //get device id
           /* String android_id = Util.getInstance().getDeviceId(FavoriteActivity.this);
            String favoritebranch="UserFavorites";
            if(getApplicationContext().getPackageName().compareToIgnoreCase("com.rajmoh.mysteriousworld")==0)
            {
                favoritebranch="UserFavoritesMysetriousWorld";
            }*/
            Util.getInstance().getDatabaseReference().child("cate").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                        CategoryModel item = noteDataSnapshot.getValue(CategoryModel.class);
                        Log.e("data",item.getCate_name());
                        mCategoryList.add(item);
                        //save to favorite to database
                     /*   Realm myRealm = Util.getInstance().getRelam(FavoriteActivity.this);
                        myRealm.beginTransaction();
                        // Create an object
                        Item itemsave = myRealm.createObject(Item.class);
                        itemsave.setSubtitle(item.getSubtitle());
                        itemsave.setTitle(item.getTitle());
                        itemsave.setUrl(item.getUrl());
                        itemsave.setVideoId(item.getVideoId());
                        itemsave.setCreateAt(item.getCreateAt());
                        myRealm.commitTransaction();*/


                    }
                   mAdapter.notifyDataSetChanged();
                    if (mCategoryList.size() == 0) {
                        Util.getInstance().showSnackBar(binding.drawer, getResources().getString(R.string.no_category_available), "", false, null);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Log.e("error", databaseError.getMessage());
                    Util.getInstance().showSnackBar(binding.drawer,databaseError.getMessage(), getResources().getString(R.string.retry), true, new SnackBarEvent() {
                        @Override
                        public void retry() {
                            loadFromServer();
                        }
                    });

                }
            });
        }
        else
        {
            Util.getInstance().showSnackBar(binding.drawer, getResources().getString(R.string.no_internet_connecton), getResources().getString(R.string.retry), true, new SnackBarEvent() {
                @Override
                public void retry() {
                    loadFromServer();
                }
            });

        }
    }



    private void loadChannels(String categoryName) {
        if (Util.getInstance().isOnline(this)) {
            //  activityFavoriteBinding.progressbar.setVisibility(View.VISIBLE);

            //get device id
           /* String android_id = Util.getInstance().getDeviceId(FavoriteActivity.this);
            String favoritebranch="UserFavorites";
            if(getApplicationContext().getPackageName().compareToIgnoreCase("com.rajmoh.mysteriousworld")==0)
            {
                favoritebranch="UserFavoritesMysetriousWorld";
            }*/
           Log.e("cn",categoryName);
            Util.getInstance().getDatabaseReference().child(categoryName.toLowerCase()).child("channel").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    binding.rightDrawerContent.progressbar.setVisibility(View.GONE);
                    mChannelList.clear();
                    for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                        ChannelInfo item = noteDataSnapshot.getValue(ChannelInfo.class);
                        Log.e("data",item.getChannel_name());
                        mChannelList.add(item);
                        //save to favorite to database
                     /*   Realm myRealm = Util.getInstance().getRelam(FavoriteActivity.this);
                        myRealm.beginTransaction();
                        // Create an object
                        Item itemsave = myRealm.createObject(Item.class);
                        itemsave.setSubtitle(item.getSubtitle());
                        itemsave.setTitle(item.getTitle());
                        itemsave.setUrl(item.getUrl());
                        itemsave.setVideoId(item.getVideoId());
                        itemsave.setCreateAt(item.getCreateAt());
                        myRealm.commitTransaction();*/


                    }
                    mChannelsAdapter.notifyDataSetChanged();
                    if (mChannelList.size() == 0) {
                        Util.getInstance().showSnackBar(binding.drawer, getResources().getString(R.string.no_category_available), "", false, null);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Log.e("error", databaseError.getMessage());
                    Util.getInstance().showSnackBar(binding.drawer,databaseError.getMessage(), getResources().getString(R.string.retry), true, new SnackBarEvent() {
                        @Override
                        public void retry() {
                            loadFromServer();
                        }
                    });
                    binding.rightDrawerContent.progressbar.setVisibility(View.GONE);

                }
            });
        }
        else
        {
            Util.getInstance().showSnackBar(binding.drawer, getResources().getString(R.string.no_internet_connecton), getResources().getString(R.string.retry), true, new SnackBarEvent() {
                @Override
                public void retry() {
                    loadFromServer();
                }
            });
            binding.rightDrawerContent.progressbar.setVisibility(View.GONE);


        }
    }

}
