package com.phyapp.root.physiotherapy;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phyapp.root.physiotherapy.Fragment.E4Fragment;
import com.phyapp.root.physiotherapy.Fragment.NotificationFragment;
import com.phyapp.root.physiotherapy.Utils.SessionManager;

import java.util.HashMap;
import java.util.Objects;

public class NotificationDetailsActivity extends AppCompatActivity {

    TabLayout tabLayout;
    private NotificationDetailsActivity instance;
    Fragment fragmentOne,fragmentTwo;
    SessionManager session;
    String Language;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_appointment);
        setSupportActionBar(toolbar);

        callTitleCenterFont();


      //  getSupportActionBar().setDisplayShowHomeEnabled(true);

        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        session=new SessionManager(this);


        HashMap<String,String> user=session.getUserDetails();


        Language=user.get(session.KEY_USERS_Language);

        //instance=this;
        getAllWidgets();
        bindWidgetsWithAnEvent();
        setupTabLayout();




    }


    private void getAllWidgets() {
        tabLayout = (TabLayout) findViewById(R.id.tabs_appointment);

    }
    private void setupTabLayout() {
        fragmentOne = new NotificationFragment();
        fragmentTwo = new E4Fragment();
        tabLayout.addTab(tabLayout.newTab().setText("Appointments"),true);
        tabLayout.addTab(tabLayout.newTab().setText("Completed"));
    }
    private void bindWidgetsWithAnEvent()
    {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    private void setCurrentTabFragment(int tabPosition)
    {
        switch (tabPosition)
        {
            case 0 :
                replaceFragment(fragmentOne);
                break;
            case 1 :
                replaceFragment(fragmentTwo);
                break;
        }
    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.change_appointments, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    public void   callTitleCenterFont()
    {
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View vr = inflator.inflate(R.layout.titleview, null);

        ActionBar.LayoutParams p = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);

//if you need to customize anything else about the text, do it here.
//I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
        ((TextView)vr.findViewById(R.id.title)).setText(this.getTitle());

//assign the view to the actionbar
        this.getSupportActionBar().setCustomView(vr);
    }

}



