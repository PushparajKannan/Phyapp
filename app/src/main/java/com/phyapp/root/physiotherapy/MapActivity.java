package com.phyapp.root.physiotherapy;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MapActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String[] pageTitle={"one","two","three"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

     // android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.main_toolbar);
       /// setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        viewPager = (ViewPager)findViewById(R.id.viewpagers);

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);






        for (int i = 0; i <3; i++)
        {

            tabLayout.addTab(tabLayout.newTab().setText(pageTitle[i]));
        }

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_menu_camera);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_menu_send);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_menu_gallery);



        //set gravity for tab bar
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);




        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        //change Tab selection when swipe ViewPager
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });












/*

        viewPager = (ViewPager)findViewById(R.id.viewpagers);

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);






        for (int i = 0; i <3; i++)
        {

            tabLayout.addTab(tabLayout.newTab().setText(pageTitle[i]));
        }
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_menu_camera);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_menu_gallery);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_menu_send);


        //set gravity for tab bar
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);




        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        //change Tab selection when swipe ViewPager
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


*/

    }
}
