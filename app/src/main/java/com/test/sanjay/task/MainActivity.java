package com.test.sanjay.task;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.test.sanjay.task.Utility.Config;
import com.test.sanjay.task.adpater.AlbumsAdapter;
import com.test.sanjay.task.adpater.ViewPagerAdapter;
import com.test.sanjay.task.application.Task;
import com.test.sanjay.task.databinding.ActivityMainBinding;
import com.test.sanjay.task.pojo.Album;
import com.test.sanjay.task.pojo.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    ActivityMainBinding activityMainBinding;
    private List<Item> mItemList;
    private List<Album> mAlbumList;
    private ImageView[] dots;
    private int dotsCount = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setLauncheractivity(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        init();
        initNavigationDrawer();
        setListener();

    }

    private void init()

    {
       //set up dummy top chart
         setTopChart();
        ViewPagerAdapter mAdapter = new ViewPagerAdapter(this, mItemList);
        activityMainBinding.mainContent.viewpager.setAdapter(mAdapter);

        //setup dummy album list
        setAlbums();
        AlbumsAdapter albumsAdapter = new AlbumsAdapter(this, mAlbumList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        activityMainBinding.mainContent.recyclerView.setLayoutManager(mLayoutManager);
        activityMainBinding.mainContent.recyclerView.setItemAnimator(new DefaultItemAnimator());
        activityMainBinding.mainContent.recyclerView.setAdapter(albumsAdapter);
        setUiPageViewController();



        //set viewpager height according to screen

        ViewGroup.LayoutParams params = activityMainBinding.mainContent.innerConstraint.getLayoutParams();
        Task task= (Task) getApplication();
        params.height =(task.getScreen_height()*45)/100;
        activityMainBinding.mainContent.innerConstraint.setLayoutParams(params);

    }

    /**
     ******************* set top 5 albums in top swipe ****************************
     */
    private void setTopChart() {
        mItemList = new ArrayList();

        Item item1 = new Item();
        item1.setUrl("http://s3.amazonaws.com/hiphopdx-production/2017/04/Kendrick-Lamar-DAMN-album-cover-featured-827x620.jpg");
        item1.setTitle("DAMN");
        item1.setSubtitle("by Kendrick Lamar (2017)");
        item1.setVideoId("l0vrOYZ6GRc");
        mItemList.add(item1);

        Item item2 = new Item();
        item2.setUrl("http://bhsblueprint.org/wp-content/uploads/2017/04/mounteerie.jpg");
        item2.setTitle("A Crow Looked At Me");
        item2.setSubtitle("by Mount Eerie (2017)");
        item2.setVideoId("zGESP0iePmQ");
        mItemList.add(item2);

        Item item3 = new Item();
        item3.setUrl("http://s3.amazonaws.com/hiphopdx-production/2017/04/Kendrick-Lamar-DAMN-album-cover-featured-827x620.jpg");
        item3.setTitle("DAMN");
        item3.setSubtitle("by Kendrick Lamar (2017)");
        item3.setVideoId("zGESP0iePmQ");

        mItemList.add(item3);

        Item item4 = new Item();
        item4.setUrl("http://s3.amazonaws.com/hiphopdx-production/2017/04/Kendrick-Lamar-DAMN-album-cover-featured-827x620.jpg");
        item4.setTitle("DAMN");
        item4.setSubtitle("by Kendrick Lamar (2017)");
        item4.setVideoId("zGESP0iePmQ");

        mItemList.add(item4);

        Item item5 = new Item();
        item5.setUrl("http://s3.amazonaws.com/hiphopdx-production/2017/04/Kendrick-Lamar-DAMN-album-cover-featured-827x620.jpg");
        item5.setTitle("DAMN");
        item5.setSubtitle("by Kendrick Lamar (2017)");
        item5.setVideoId("zGESP0iePmQ");

        mItemList.add(item5);

        changeAlbum(0);

    }

    /**
     *********************** set dummy album list *********************
     */
    private void setAlbums()
    {
        mAlbumList = new ArrayList<>();
        for(int i=1;i<=10;i++)
        {
            Album album=new Album(getResources().getString(R.string.title),getResources().getString(R.string.time),"",getResources().getString(R.string.description));
            mAlbumList.add(album);
        }
    }

    private void setUiPageViewController() {

        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(8, 0, 8, 0);

            activityMainBinding.mainContent.viewPagerCountDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    private void setListener() {
        activityMainBinding.mainContent.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                changeAlbum(position);

                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
                }

                dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        /**
         *********************** //bind player view the event ******************
         */
        activityMainBinding.mainContent.imagePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,PlayerActivity.class);
                intent.putExtra(Config.ITEM,mItemList.get(activityMainBinding.mainContent.viewpager.getCurrentItem()));

                startActivity(intent);
            }
        });
    }

    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.home:
                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.settings:
                        Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.trash:
                        Toast.makeText(getApplicationContext(), "Trash", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        finish();

                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView tv_email = (TextView) header.findViewById(R.id.tv_email);
        tv_email.setText(getResources().getString(R.string.email));
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    /**
     **************** change item title and subtitle in top view pager slide *****************
     * @param position
     */
    private void changeAlbum(int position)
    {
        activityMainBinding.mainContent.txtTitle.setText(mItemList.get(position).getTitle());
        activityMainBinding.mainContent.txtSubtitle.setText(mItemList.get(position).getSubtitle());

    }

}