package com.phyapp.root.physiotherapy;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by root on 22/3/18.
 */

class ViewPagerAdapters extends FragmentPagerAdapter {

    private String title[] = {"One", "Two", "Three"};
    public ViewPagerAdapters(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return new TabFragment();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}



