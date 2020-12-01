package com.phyapp.root.physiotherapy.DynamicTabview;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.phyapp.root.physiotherapy.R;

//public class OneFragment {
    public class OneFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_SECTION_NAME = "section_name";
        private static final String ARG_SECTION_DISCRIPTION = "section_discription";
        private static final String ARG_SECTION_Image = "section_image";

        private int sectionNumber;
        String sectionName;
        String sectionDis;
        String selectioimg;

        public OneFragment() {
        }

        public static OneFragment newInstance(int sectionNumber,String title,String dis,String img) {
            OneFragment fragment = new OneFragment();
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_NAME,title);
            args.putString(ARG_SECTION_DISCRIPTION,dis);
            args.putString(ARG_SECTION_Image,img);
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_one, container, false);

            sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            sectionName=getArguments().getString(ARG_SECTION_NAME);
            sectionDis=getArguments().getString(ARG_SECTION_DISCRIPTION);
            selectioimg=getArguments().getString(ARG_SECTION_Image);
            TextView textView = (TextView) rootView.findViewById(R.id.txtTabItemNumber);
            TextView textpara = (TextView) rootView.findViewById(R.id.textpara);
            ImageView imges=(ImageView) rootView.findViewById(R.id.image_des);
            textpara.setText(sectionDis);
            textView.setText( sectionName);

            Glide.with(OneFragment.this)
                    .load(selectioimg)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.noimaga).into(imges);
            //Glide.


            return rootView;
        }
    }
//}
