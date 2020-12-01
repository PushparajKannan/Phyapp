package com.phyapp.root.physiotherapy;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.phyapp.root.physiotherapy.Fragment.E1Fragment;
import com.phyapp.root.physiotherapy.Fragment.E2Fragment;
import com.phyapp.root.physiotherapy.Fragment.MainFragment;

/**
 * Created by root on 23/3/18.
 */





public class BottomPageAdapter extends FragmentPagerAdapter {
        public BottomPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new E1Fragment();
            } else if (position == 1) {
                return new E2Fragment();
            } else if (position == 2) {
                return new MainFragment();
            }



            else

                return new MainFragment();
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

