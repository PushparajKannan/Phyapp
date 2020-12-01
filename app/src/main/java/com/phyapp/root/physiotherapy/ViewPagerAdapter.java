package com.phyapp.root.physiotherapy;

/**
 * Created by root on 12/3/18.
 */

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.phyapp.root.physiotherapy.DynamicTabview.OneFragment;
import com.phyapp.root.physiotherapy.ModelClass.DynamicTabMode;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final List<String> mDiscriptioList = new ArrayList<>();

    private final List<String> mImageList = new ArrayList<>();

    ArrayList<DynamicTabMode> titleAdd=new ArrayList<DynamicTabMode>();


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

   /* public Fragment getItem(int position) {
    }
*/
    @Override
    public Fragment getItem(int position) {


        return OneFragment.newInstance(position + 1,mFragmentTitleList.get(position),mDiscriptioList.get(position),mImageList.get(position));




       // position=null;
       /* if (position == 0) {
            return new E3Fragment();
        } else if (position == 1) {
            return new E3Fragment();
        } else if (position == 2) {
            return new E3Fragment();
        }
        else if (position == 3){
            return new E3Fragment();
        } else if (position==4){
            return new E3Fragment();
        }

        else

        return new E3Fragment();*/
    }

    @Override
    public int getCount() {

        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, DynamicTabMode title)
    {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title.getName());

        mDiscriptioList.add(title.getDis());

        mImageList.add(title.getImg());
        //titleAdd.add(title.getName(),title.getDis());

    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mFragmentTitleList.get(position);
    }
}