package com.phyapp.root.physiotherapy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.phyapp.root.physiotherapy.APICONFIG.APIConfig;
import com.phyapp.root.physiotherapy.BackgroundServices.BackgroundAPI;
import com.phyapp.root.physiotherapy.Fragment.CompletedFragment;
import com.phyapp.root.physiotherapy.Fragment.MainFragment;
import com.phyapp.root.physiotherapy.Utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class PhyMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,LocationListener {


    private static final String TAG =MainActivity.class.getSimpleName() ;
    TabLayout tabLayout;
    BottomNavigationView Btmnview;
    TextView headingname;

    CircleImageView profileimage;

    String[] str={"Home","Profile","History","Notification"};
    private LocationManager mLocationManager;

    SessionManager session;
    String doctorname,doctorid,doctorphone,doctormail;
    private boolean doubleBackToExitPressedOnce;
    private android.app.AlertDialog pDialog;
    Fragment fragmentOne,fragmentTwo;
    String checklogintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phy_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        callTitleCenterFont();


        //  getLocation();


        Btmnview = (BottomNavigationView) findViewById(R.id.phy_tabs);


        pDialog = new SpotsDialog(this, R.style.Custom);

        pDialog.setCancelable(false);

        checklogintent = "emp";


        session = new SessionManager(this);

        HashMap<String, String> doctor = session.getUserDetails();

        doctorid = doctor.get(session.KEY_USERID);

        doctorname = doctor.get(session.KEY_USER_NAME);

        doctorphone = doctor.get(session.KEY_PROFILE_NUMBER);

        doctormail = doctor.get(session.KEY_EMAIL);


        checklogintent = getIntent().getStringExtra("check");


        if (checklogintent != null) {



        if (checklogintent.equals("login")) {

            if (doctorid == null) {

                checkligoin();

            }
        }
    }

        CallGpsISON();

        Log.i("doctor:",doctorid+" "+doctorname+" "+doctorphone+" "+doctormail);



       // tabLayout=(TabLayout) findViewById(R.id.phy_tabs);
        //setupTabIcons();


       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerview=navigationView.getHeaderView(0);

        headingname=(TextView) headerview.findViewById(R.id.Nav_phy_name);

        profileimage=(CircleImageView) headerview.findViewById(R.id.profile_image_phy);

       // headingname.setText(doctorname);



        callDoctorProfileAPI(doctorid);

        getAllWidgets();
        bindWidgetsWithAnEvent();

        setupTabLayout();

        //Btmnview.




     /*   for (int i = 0; i < 4; i++) {

            Btmnview.addTab(tabLayout.newTab().setText(str[i]));
        }


        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_person_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_notifications_active_black_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_local_phone_black_24dp);*/
     //  tabLayout.getTabAt(4).setIcon(R.drawable.ic_help_black_24dp);


    /*    TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("ONE");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_favourite, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);
*/
      /*  tabLayout.getTabAt(0).setIcon(R.drawable.physiotherapy);
        tabLayout.getTabAt(1).setIcon(R.drawable.massage_1_);
        tabLayout.getTabAt(2).setIcon(R.drawable.physiotherapy_2_);
        tabLayout.getTabAt(3).setIcon(R.drawable.rehabilitation_2_);
        tabLayout.getTabAt(4).setIcon(R.drawable.rehabilitation_1_);
*/


       // TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
       // Btmnview.getMenu().getItem(0).setChecked(true); // Count Starts From 0

        Btmnview.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_phy_home:


                                SetFragment();

                                break;

                            case R.id.action_phy_history:
                                Intent i=new Intent(PhyMainActivity.this,PhyHistoryActivity.class);
                                startActivity(i);
                                break;

                           /* case R.id.action_phy_notification:

                                break;*/

                            case R.id.action_phy_profile:

                                Intent in=new Intent(PhyMainActivity.this,Phy_Notification_Activity.class);
                                startActivity(in);

                                break;

                        }
                        return true;
                    }
                });


      /*  FragmentManager fmd = getSupportFragmentManager();
        FragmentTransaction ftd = fmd.beginTransaction().replace(R.id.frame_phy, new MainFragment());
        ftd.commit();*/

        SetFragment();




    }

    private void CallGpsISON() {


        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }else
        {

            Log.i("gps","servicestart");


            Intent i = new Intent(PhyMainActivity.this, BackgroundAPI.class);

            startService(i);


        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }  else if (!doubleBackToExitPressedOnce)
        {
            this.doubleBackToExitPressedOnce = true;

            AlertDialog.Builder alertbox = new AlertDialog.Builder(this,R.style.CustomDialogTheme);

            alertbox.setTitle(Html.fromHtml("<font color='#dd0000'>Alert!</font>"));
            alertbox.setMessage(Html.fromHtml("<font color='#000000'>Do you want to exit application?</font>"));
            //alertbox.setTitle("<font color='#000000'>Alert!</font>");
            alertbox.setPositiveButton("No", new DialogInterface.OnClickListener() {

                // do something when the button is clicked
                public void onClick(DialogInterface arg0, int arg1) {

                    // finish();
                    //close();


                }
            })
                    .setNegativeButton("Yes", new DialogInterface.OnClickListener() {

                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {
                            // finish();
                            finishAffinity();

                        }
                    });

            AlertDialog alrt=alertbox.create();
            alrt.getWindow().getAttributes().windowAnimations = R.style.dialog_animation2;

            alrt.show();
            // Button buttonPositive = alertbox.getButton(DialogInterface.BUTTON_POSITIVE);
            // buttonPositive.setTextColor(ContextCompat.getColor(this, R.color.alert_text));
            // Button buttonNegative = alertbox.getButton(DialogInterface.BUTTON_NEGATIVE);
            //buttonNegative.setTextColor(ContextCompat.getColor(this, R.color.alert_text));
            //  alertbox.getWindow().setBackgroundDrawableResource(R.color.colorPrimary);
            // Toast.makeText(this,"Please click BACK again to exit.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 50);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.phy_main, menu);


       // menu.findItem(R.id.action_refresh).setVisible(showMenu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {

            SetFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        Btmnview.getMenu().getItem(0).setChecked(true);


        SetFragment();
    }

    public void  SetFragment() {

       // getAllWidgets();
        //bindWidgetsWithAnEvent();

        tabLayout.getTabAt(0).select();
        replaceFragmentone();
       // setupTabLayout();
/*
        FragmentManager fmd = getSupportFragmentManager();
        FragmentTransaction ftd = fmd.beginTransaction().replace(R.id.frame_phy, new MainFragment());
        ftd.commit();*/
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.phy_profile) {
            // Handle the camera action
            Intent in=new Intent(PhyMainActivity.this,Phy_Notification_Activity.class);
            startActivity(in);


        }else if(id==R.id.phy_history)
        {
            Intent i=new Intent(PhyMainActivity.this,PhyHistoryActivity.class);
            startActivity(i);
        }
        else if(id==R.id.phy_home)
        {

            SetFragment();

        }else if(id==R.id.phy_logout)
        {
            showDialoglogout();
        }
        /*else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("HOME");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home_black_24dp, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("PROFILE");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_person_black_24dp, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("HISTORY");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_notifications_active_black_24dp, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("NOTIFICATION");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_notifications_active_black_24dp, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);



    }
    void getLocation() {
        try {
            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            // mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, (LocationListener) this);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5000,0, (LocationListener) this);

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0, (LocationListener) this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onLocationChanged(Location loc) {
        Toast.makeText(getApplicationContext(), "Long:"+loc.getLongitude()+"lat"+loc.getLatitude(), Toast.LENGTH_SHORT).show();
        //locationText.setText("Current Location: " + location.getLatitude() + ", " + location.getLongitude());



        // editLocation.setText("");
        // pb.setVisibility(View.INVISIBLE);
        Toast.makeText(
                getBaseContext(),
                "Location changed: Lat: " + loc.getLatitude() + " Lng: "
                        + loc.getLongitude(), Toast.LENGTH_SHORT).show();
        String longitude = "Longitude: " + loc.getLongitude();
        Log.i(TAG, longitude);
        String latitude = "Latitude: " + loc.getLatitude();
        Log.i(TAG, latitude);

        /*------- To get city name from coordinates -------- */
        String cityName = null,StateName=null;
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(),
                    loc.getLongitude(), 1);
            Log.i(TAG,addresses.toString());
            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
                StateName=addresses.get(0).getAdminArea();
                System.out.println("State :"+StateName);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
                + cityName;

        Toast.makeText(PhyMainActivity.this, ""+s, Toast.LENGTH_SHORT).show();

//        Snackbar.make(view,"snack :"+s,Snackbar.LENGTH_LONG).show();
        // editLocation.setText(s);



    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(PhyMainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Provider :"+provider.toString(), Toast.LENGTH_SHORT).show();

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
        //  this.getSupportActionBar().setCustomView(vr,p);
        this.getSupportActionBar().setCustomView(vr);
    }



    public void showDialoglogout()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomDialogTheme);
        builder.setTitle(Html.fromHtml("<font color='#dd0000'>LogOut</font"));
        builder.setMessage(Html.fromHtml("<font color='#000000'>Are you sure want to logout?</font"))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        showpDialog();

                        CallLogotuAPI(doctorid,dialog);





                        // finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();


        alert.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        alert.show();
    }

    private void CallLogotuAPI(final String did, final DialogInterface dialogInterface) {


        RequestQueue queue=Volley.newRequestQueue(this);

        String url=APIConfig.MAIN_API+APIConfig.Doctor_Logout;


       // http://www.nbayjobs.com/raksha/api/doctor/doctorlogout.php?phid=1



       // url = "http://httpbin.org/post";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        String sucess,msg;

                        try {
                            JSONObject object=new JSONObject(response);

                            sucess=object.getString("success");
                            msg=object.getString("message");

                            if(sucess.equals("1")){

                                hidepDialog();

                                Toast.makeText(PhyMainActivity.this, msg, Toast.LENGTH_SHORT).show();

                                session.logoutUser();
                                // Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(PhyMainActivity.this, BackgroundAPI.class);
                                stopService(intent);

                                dialogInterface.cancel();
                                //logoutviewFalse();
                                finishAffinity();


                            }else if(sucess.equals("0"))
                            {

                                hidepDialog();

                                dialogInterface.cancel();

                                Toast.makeText(PhyMainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("phid",did);


                return params;
            }
        };
        queue.add(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(500000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));



    }

    private void callDoctorProfileAPI(final String doctorid) {

        RequestQueue queue= Volley.newRequestQueue(this);



        String url= APIConfig.MAIN_API+APIConfig.Doctor_Profile;

        //url =  http://nbayjobs.com/raksha/api/doctor/profile.php?id=1;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                       // hidepDialog();

                        final String sucess,name,mail,address,phone,id,image;

                        try {
                            JSONObject obj=new JSONObject(response);

                            sucess=obj.getString("success");
                            if(sucess.equals("1")){

                                id=obj.getString("doctorid");
                                name=obj.getString("fullname");
                                phone=obj.getString("mobile");
                                mail=obj.getString("email");
                                address=obj.getString("address");
                                image=obj.getString("image");

                               /* runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Glide.with(Phy_Notification_Activity.this).load(image).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.user_ic).into(dpimg);

                                    }
                                });
*/
                                headingname.setText(doctorname);

                                Glide.with(PhyMainActivity.this).load(image).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ic_person).into(profileimage);

/*
                                new Handler().postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       Glide.with(Phy_Notification_Activity.this).load(image).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ic_person).into(dpimg);

                                   }
                               },1000);*/


                                //   Glide.with(Phy_Notification_Activity.this).load(image).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.user_ic).into(dpimg);


                              /*  profilename.setText(name);
                                profileaddrss.setText(address);
                                profilephone.setText(phone);
                                profilemail.setText(mail);
*/

                                session.createLoginSession(id,"Doctor",name,phone,mail,address);

                                // locateaddressdetails(lat,lon);






                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        //hidepDialog();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("id",doctorid );
                //params.put("domain", "http://itsalif.info");

                return params;
            }
        };
        queue.add(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(500000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    private void buildAlertMessageNoGps() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this,R.style.CustomDialogTheme);
        builder.setTitle(Html.fromHtml("<font color='#dd0000'>Alert!</font>"));
        builder.setMessage(Html.fromHtml("<font color='#000000'>Your GPS seems to be disabled, you must enable it to continue</font>"))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                     startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),11);

                        dialog.cancel();
                    }
                });
        // .setNegativeButton("No", null);
        final android.app.AlertDialog alert = builder.create();

        alert.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        //alert.show();
        alert.show();
    }

private void checkligoin() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this,R.style.CustomDialogTheme);
        builder.setTitle(Html.fromHtml("<font color='#dd0000'>Login!</font>"));
        builder.setMessage(Html.fromHtml("<font color='#000000'>You must login to continue</font>"))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {


                   //  startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),11);

                        startActivity(new Intent(PhyMainActivity.this,PhyLoginActivity.class));


                        dialog.cancel();

                        finishAffinity();
                    }
                });
        // .setNegativeButton("No", null);
        final android.app.AlertDialog alert = builder.create();

        alert.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        //alert.show();
        alert.show();
    }

    public void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch(requestCode) {
            case 11:

                //CreateAlertDialogForProced();


                CallGpsISON();

                break;
        }

    }


    private void getAllWidgets() {
        tabLayout = (TabLayout) findViewById(R.id.tabs_appointment);

    }
    private void setupTabLayout() {
        fragmentOne = new MainFragment();
        fragmentTwo = new CompletedFragment();
        tabLayout.addTab(tabLayout.newTab().setText("Appointments"),true);
        tabLayout.addTab(tabLayout.newTab().setText("Pending Services"));
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

                setCurrentTabFragment(tab.getPosition());


            }
        });
    }
    private void setCurrentTabFragment(int tabPosition)
    {
        switch (tabPosition)
        {
            case 0 :
                replaceFragmentone();
                break;
            case 1 :
                replaceFragmenttwo();
                break;
        }
    }
    public void replaceFragmentone() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_phy,new  MainFragment());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();


      /*  getFragmentManager().beginTransaction()
                .detach(fragment)
                .attach(fragment)
                .commit();*/
    }
    public void replaceFragmenttwo() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_phy, new CompletedFragment());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();


      /*  getFragmentManager().beginTransaction()
                .detach(fragment)
                .attach(fragment)
                .commit();*/
    }



}

