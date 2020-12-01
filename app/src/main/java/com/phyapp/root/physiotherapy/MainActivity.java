package com.phyapp.root.physiotherapy;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.phyapp.root.physiotherapy.APICONFIG.APIConfig;
import com.phyapp.root.physiotherapy.DynamicTabview.OneFragment;
import com.phyapp.root.physiotherapy.Fragment.E1Fragment;
import com.phyapp.root.physiotherapy.Fragment.E2Fragment;
import com.phyapp.root.physiotherapy.Fragment.E3Fragment;
import com.phyapp.root.physiotherapy.ModelClass.DynamicTabMode;
import com.phyapp.root.physiotherapy.ModelClass.ProfileShowModel;
import com.phyapp.root.physiotherapy.Retofit.ApiClient;
import com.phyapp.root.physiotherapy.Retofit.ApiInterface;
import com.phyapp.root.physiotherapy.Utils.SessionManager;
import com.phyapp.root.physiotherapy.customAlertDialog.CustomizeDialog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener,LocationListener {



    private ViewPager viewPager1, viewPager2;
    private TabLayout tabLayout;
    private static final int PERMISSION_REQUEST_CODE = 200;
    View view;
    Context context;
    Fragment mainFragmemnt;
    CustomizeDialog customizeDialog = null;
    int checkedlanguage;
    /** Called when the activity is first created. */

    //  FragmentManager fm = getSupportFragmentManager();
    FrameLayout frameLayout;
    ArrayList<RecyclerViewModel> pname = new ArrayList<RecyclerViewModel>();
    public RecyclerViewModel pName;
    private String[] pageTitle = {"First", "Second", "Third", "fourth", "fifth"};
    CircleImageView img,logoimg;
    RadioButton Tamil,English;
    Button ok;
    private int selected;
    GoogleSignInClient mGoogleSignInClient;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    LoginActivity login;
    SessionManager session;
    private String userId, email, loginType, userName, profilePic,userNumber,Language,adminNumber;
    private Uri uri;
    TextView TextUser,Textemail;
    String LanguageSelect;
    BottomNavigationView  tab2;
    private GoogleApiClient mGoogleApiClient;
    private LocationManager mLocationManager;
    private String TAG=MainActivity.class.getSimpleName();
    ArrayList<DynamicTabMode> name;

    android.app.AlertDialog pDialog;
    private Locale myLocale;
    String useravailable;
    String indentdata;

    boolean badgeselect;

    AlertDialog alertDialog1;
    CharSequence[] values = {" தமிழ் "," English "};
    private boolean doubleBackToExitPressedOnce;


    private void setupViewPager(ViewPager viewPager,ArrayList<DynamicTabMode> name) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int count = 10;
        for (int i=0; i<name.size(); i++){

            OneFragment fView = new OneFragment();
            View view = fView.getView();

            adapter.addFrag(fView,name.get(i));


            //TextView txtTabItemNumber = (TextView) view.findViewById(R.id.txtTabItemNumber);
           // txtTabItemNumber.setText("TAB " + i);
          //  adapter.addFrag(fView,"TAB " + i);
        }

        viewPager.setAdapter(adapter);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Display Custom Dialog */
        context = this;
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       // getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
       // getSupportActionBar().setCustomView(R.layout.abs_layout);


     //   getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.yourimage));

        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View vr = inflator.inflate(R.layout.titleview, null);


        ((TextView)vr.findViewById(R.id.title)).setText(this.getTitle());


       /* ActionBar.LayoutParams p = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);


        AppBarLayout.LayoutParams pd = new AppBarLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);*/



//assign the view to the actionbar
        this.getSupportActionBar().setCustomView(vr);


       /* session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();

        Session_user_type=user.get(SessionManager.KEY_USERS_TYPE);
        usernumber=user.get(SessionManager.KEY_PROFILE_NUMBER);
*/
       useravailable="user";
       pDialog=new SpotsDialog(this,R.style.Custom);

       pDialog.setCancelable(false);

       badgeselect=false;



       indentdata=getIntent().getStringExtra("indent");

       if(indentdata!=null){
           if(indentdata.equals("mpk")){

               Intent intent=new Intent(MainActivity.this,NotificationDetailsActivity.class);
               startActivity(intent);


           }else{

           }
       }

       mainFragmemnt=new E1Fragment();

        //SettingAllert();

        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
              //  getLocation();


            }
        });*/




       // mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //getLocation();

     //   mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
         //       LOCATION_REFRESH_DISTANCE, mLocationListener);


        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



        pref = getSharedPreferences("login", MODE_PRIVATE);

        editor = pref.edit();

        login = new LoginActivity();


        session = new SessionManager(getApplicationContext());


        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        // userId
        userNumber = user.get(SessionManager.KEY_PROFILE_NUMBER);
        // email
       // email = user.get(SessionManager.KEY_EMAIL);
        // loginType
        loginType = user.get(SessionManager.KEY_LOGIN_TYPE);


        Language = user.get(SessionManager.KEY_USERS_Language);


        userId=user.get(SessionManager.KEY_USERID);

        userName=user.get(SessionManager.KEY_USER_NAME);


        adminNumber=user.get(SessionManager.KEY_Admin_number);
        
        
        if(Language==null||Language.isEmpty())
        {
            Language="English";

          //  checkedlanguage=1;
            //Toast.makeText(this, "null lang", Toast.LENGTH_SHORT).show();
        }else {
            if(Language.equals("Tamil")){
                checkedlanguage=0;

            }else if(Language.equals("English")){
                checkedlanguage=1;
            }
        }






      //  userName = user.get(SessionManager.KEY_USER_NAME);

       // profilePic = user.get(SessionManager.KEY_PROFILE_IMAGE);

        System.out.println("Session data:  " + loginType + " " + userNumber);


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


        View navigationheader=navigationView.getHeaderView(0);
       img=(CircleImageView) navigationheader.findViewById(R.id.proimg);
       TextUser=(TextView) navigationheader.findViewById(R.id.txt_name);
       Textemail=(TextView) navigationheader.findViewById(R.id.text_email);
      //  logoimg=(SimpleDraweeView) navigationheader.findViewById(R.id.logoimg);
        tab2 = (BottomNavigationView) findViewById(R.id.slidingtabs);


        if(userId==null||userId.isEmpty())
        {
           // Language="English";

            useravailable="nouser";

            TextUser.setText("Hi, Guest");
           // Textemail.setText("Welcome");


            //Toast.makeText(this, "null lang", Toast.LENGTH_SHORT).show();
        }
        else {
            //if (userName==null||userName.isEmpty()){




               // TextUser.setText("Update Your Profile");
                CallProileApi(userId);

           // }


         //   CallbadageUI();

        }


        final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate);
     //   private int tabIconsFocus [] = new int[]{R.drawable.ic_conversation_focus,R.drawable.ic_contact_people_focus, R.drawable.ic_settings_focus};
     //   private int tabIconsNoFocus [] = new int[]{R.drawable.ic_conversation_no_focus,R.drawable.ic_contact_people_no_focus, R.drawable.ic_settings_no_focus};

       // setupTabIcons(position);


        name = new ArrayList<DynamicTabMode>();

        viewPager1 = (ViewPager) findViewById(R.id.viewpager1);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        showpDialog();

        viewPager2 = (ViewPager) findViewById(R.id.viewpager2);

        tab2 = (BottomNavigationView) findViewById(R.id.slidingtabs);





        if(!session.isLanguageSelected()){

            laguadeDaialog();

          /*  BottomNavigationMenuView bottomNavigationMenuView =
                    (BottomNavigationMenuView) tab2.getChildAt(0);
            View v = bottomNavigationMenuView.getChildAt(1);
            BottomNavigationItemView itemView = (BottomNavigationItemView) v;

            View badge = LayoutInflater.from(this)
                    .inflate(R.layout.notification_badge, bottomNavigationMenuView, false);

            itemView.addView(badge);*/


        }



        if (!session.isLoggedIn()) {
            logoutviewFalse();

            //laguadeDaialog();



            //  Toast.makeText(MainActivity.this, "Please Log In", Toast.LENGTH_LONG).show();
            // startActivity(new Intent(MainActivity.this, LoginActivity.class));
            // finish();   //finish current activity
        } else {
            logoutviewTrue();

           /* if (!profilePic.equals("")) {
                uri = Uri.parse(profilePic);
                img.setImageURI(uri);
            }*/

         //   Toast.makeText(this, Language, Toast.LENGTH_SHORT).show();

            if(userName!=null)
            {
                TextUser.setText(userName);

            }

            Textemail.setText(email);


        }






     //   viewPager2 = (ViewPager) findViewById(R.id.viewpager2);
       // tab2 = (BottomNavigationView) findViewById(R.id.slidingtabs);


        callAdminNumberAPI();

        //callBackgroundDiscrpitionApi(Language);

        //laguadeDaialog();


       // setFragment();








/*

        name.add(new DynamicTabMode("Neurological Physiotherapy","jdhsfcjdhsjfdhsfdkjfhkd"));
        name.add(new DynamicTabMode("Musculoskeletal Outpatients","askdbaskdlasjd"));
        name.add(new DynamicTabMode("Orthopaedics","cdufhdshfjdsh"));
        name.add(new DynamicTabMode("Respiratory","kamnnnan"));
        name.add(new DynamicTabMode("Neurological Physiotherapy","kuamrdvcxsvasdfdssraaeerweq"));
        name.add(new DynamicTabMode("Musculoskeletal Outpatients","kuamrdvcxsvasdfdssraaeerweq"));
        name.add(new DynamicTabMode("Orthopaedics","kuamrdvcxsvasdfdssraaeerweq"));
        name.add(new DynamicTabMode("Physiotherapy","kuamrdvcxsvasdfdssraaeerweq"));
        name.add(new DynamicTabMode("Musculoskeletal","kuamrdvcxsvasdfdssraaeerweq"));
        name.add(new DynamicTabMode("JRespiratory","sgfdasjff"));
        name.add(new DynamicTabMode("Orthopaedics","kamnaifnuihemnf"));
        name.add(new DynamicTabMode("Musculoskeletal","ckjhdsckjhasfuids"));
        name.add(new DynamicTabMode("Outpatients","kannanmorthy"));
        name.add(new DynamicTabMode("Nougat","kanamooorti"));
*/













        tabLayout.setupWithViewPager(viewPager1);


       /* if(mainFragmemnt!=null)
        {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction().replace(R.id.frm, mainFragmemnt);
            ft.commit();

        }*/




       // viewPager = (ViewPager) findViewById(R.id.viewpager);
       // setupViewPager(viewPager);

        //tabLayout = (TabLayout) findViewById(R.id.tabs);
        //tabLayout.setupWithViewPager(viewPager);

       /* AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this,R.style.AlertDialogTheme);

        // Setting Dialog Title
        alertDialog.setTitle("WELCOME");

        // Setting Dialog Message
        alertDialog.setMessage("Happy to invite you");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ic_menu_camera);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                // Write your code here to invoke YES event
                Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
            }
        });*/
/*
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message*/
        // AlertDialog alert=alertDialog.create();
        // alert.show();




      /*  LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialoglayout = inflater.inflate(R.layout.dialog_layout, null);
        Tamil=(RadioButton) dialoglayout.findViewById(R.id.tamil);
       English=(RadioButton) dialoglayout.findViewById(R.id.english);
       // ok=(Button) dialoglayout.findViewById(R.id.btnok);
        int textColor = Color.parseColor("#FFFFFF");
       Tamil.setButtonTintList(ColorStateList.valueOf(textColor));
        English.setButtonTintList(ColorStateList.valueOf(textColor));
       // final String[] language={"TAMIL","ENGLISH"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setTitle("\t\t" + "RAKSHA HOSPITAL");
       // builder.setMessage("Put yourself in our hands.\n Let our years of expertise help you get back on track");
       builder.setView(dialoglayout);
       builder.setCancelable(false);
        *//*builder.setSingleChoiceItems(language, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selected=which;

            }
        });*//*
        builder.setPositiveButton("SELECT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

              //  Toast.makeText(MainActivity.this, "Tamil", Toast.LENGTH_SHORT).show();

            }
        });
       *//* builder.setMessage("Put yourself in our hands.\n" +
                "Let our years of expertise help you get back on track");
*//*

      // builder.setNegativeButton(new DialogInterface.OnClickListener())
       // builder.setCancelable(true);


        builder.show();

        final AlertDialog alertDialog=builder.create();

       *//* ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

*//*


        Tamil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LanguageSelect="Tamil";
                Toast.makeText(MainActivity.this, ""+LanguageSelect.toString(), Toast.LENGTH_SHORT).show();

                // alertDialog.dismiss();
            }
        });

        English.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LanguageSelect ="English";
                Toast.makeText(MainActivity.this, ""+LanguageSelect.toString(), Toast.LENGTH_SHORT).show();


            }
        });*/


       /* if (checkPermission()) {

            Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();

        } else{
            requestPermission();

             Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();
        }*/


        //frameLayout=(FrameLayout) findViewById(R.id.vewfrm);


        //alertDialog.getContext().setTheme(R.style.AlertDialogTheme);

/*
        for (int i = 0; i < 5; i++) {

            tabLayout.addTab(tabLayout.newTab());
        }

        tabLayout.getTabAt(0).setIcon(R.drawable.physiotherapy);
        tabLayout.getTabAt(1).setIcon(R.drawable.massage_1_);
        tabLayout.getTabAt(2).setIcon(R.drawable.physiotherapy_2_);
        tabLayout.getTabAt(3).setIcon(R.drawable.rehabilitation_2_);
        tabLayout.getTabAt(4).setIcon(R.drawable.rehabilitation_1_);*/
/*

for (int i=0;i<pname.size();i++)
{

    int ii=pname.indexOf(i);
    String nam=String.valueOf(ii);
    //nam.valueOf(ii);
    tab2.addTab(tab2.newTab().setText(nam));
    System.out.println("names :"+nam);


}
*/


       /* for (int i = 0; i < 5; i++) {

            tab2.addTab(tab2.newTab());
        }


        tab2.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        tab2.getTabAt(1).setIcon(R.drawable.ic_person_black_24dp);
        tab2.getTabAt(2).setIcon(R.drawable.ic_notifications_active_black_24dp);
        tab2.getTabAt(3).setIcon(R.drawable.ic_local_phone_black_24dp);
        tab2.getTabAt(4).setIcon(R.drawable.ic_help_black_24dp);*/
        // tab2.getTabAt(5).setIcon(R.drawable.);


   //     FragmentManager fmd = getSupportFragmentManager();
  //      FragmentTransaction ftd = fmd.beginTransaction().replace(R.id.frm, new E1Fragment());
   //     ftd.commit();






       /* View view1 = getLayoutInflater().inflate(R.layout.customtab, null);
        view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.chiropractic);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));*/


        //set gravity for tab bar
        //tab2.setTabGravity(TabLayout.GRAVITY_FILL);
        //tabLayout.setSelected(false);
        // tabLayout.setSelectedTabIndicatorHeight(0);
        // tab2.setSelected(false);

        //tabLayout.getTabAt(0).

        //tabLayout.setupWithViewPager(viewPager2);


       // ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        //viewPager1.setAdapter(pagerAdapter);


        // BottomPageAdapter btm=new BottomPageAdapter(getSupportFragmentManager());

        // viewPager2.setAdapter(btm);

        //change Tab selection when swipe ViewPager
        //viewPager1.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


  //      tabLayout.setSelectedTabIndicatorHeight(0);




        /*tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager1.setVisibility(View.VISIBLE);
                tabLayout.setSelectedTabIndicatorHeight(6);
                tab2.setSelectedTabIndicatorHeight(0);
                viewPager1.setCurrentItem(tab.getPosition());
                viewPager2.setVisibility(View.GONE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });*/


/*tabLayout.setSelected(false);

if(tabLayout.isActivated())
{
    viewPager1.setVisibility(View.VISIBLE);
    viewPager1.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager1.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    });

}*/

        // tabLayout.setSelectedTabIndicatorHeight(0);


        //tab2.setupWithViewPager(viewPager2);
        //viewPager2.setPageTransformer(false,null);

      //  viewPager2.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab2));
        // viewPager1.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tab2.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        viewPager1.setVisibility(View.GONE);
                        tabLayout.setSelectedTabIndicatorHeight(0);

                        switch (item.getItemId()) {
                            case R.id.action_home:

                                FragmentManager fm = getSupportFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction().replace(R.id.frm, new E1Fragment());
                                ft.commit();
                                break;

                            case R.id.action_profile:

                                if(useravailable.equals("nouser")){

                                   // tab2.getMenu().getItem(0).setChecked(true);

                                  //  tab2.setSelectedItemId(id);

                                    alertCreate();



                                }else {

                                    //if(session.isBadageSelected()){

                                        if(badgeselect) {

                                          //  Toast.makeText(MainActivity.this, "selected", Toast.LENGTH_SHORT).show();
                                            removeBadge(tab2, 1);

                                            badgeselect=false;
                                        }

                                    //}


                                 //   tab2.getMenu().getItem(0).setChecked(true);

                                 //   BottomNavigationMenuView bottomNavigationMenuView =
                                          //  (BottomNavigationMenuView) tab2.getChildAt(0);


                                   /*if(removeBadgechecked(tab2,1)){


                                   }*/


                                    FragmentManager fm1 = getSupportFragmentManager();
                                    FragmentTransaction ft1 = fm1.beginTransaction().replace(R.id.frm, new E2Fragment());
                                    ft1.commit();
                                }
                                break;

                            case R.id.action_notification:

                                if(useravailable.equals("nouser"))
                                {

                                    tab2.getMenu().getItem(0).setChecked(true);


                                    alertCreate();


                                }else {

                                    tab2.getMenu().getItem(0).setChecked(true);


                                    Intent intent=new Intent(MainActivity.this,NotificationDetailsActivity.class);
                                    startActivity(intent);



                                   /* FragmentManager nfm = getSupportFragmentManager();
                                    FragmentTransaction nft = nfm.beginTransaction().replace(R.id.frm, new NotificationFragment());
                                    nft.commit();*/
                                   // tab2.getMenu().getItem(2).setChecked(true);


                                }


                                break;


                           /* case R.id.action_appointment:

                                Intent i=new Intent(MainActivity.this,NotificationDetailsActivity.class);
                                startActivity(i);


                                break;*/


                                 case R.id.action_call:

                                    // startActivity(new Intent(Intent.ACTION_DIAL));

                                     Intent intent = new Intent(Intent.ACTION_DIAL);
                                     intent.setData(Uri.parse("tel:"+adminNumber));
                                     startActivity(intent);

                                     break;

                                 case R.id.action_help:

                                     FragmentManager fm2 = getSupportFragmentManager();
                                     FragmentTransaction ft2 = fm2.beginTransaction().replace(R.id.frm, new E3Fragment());
                                     ft2.commit();


                                     break;










                        }
                        return true;
                    }


                });
       /* tab2.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                //tabLayout.setVisibility(View.INVISIBLE);
                viewPager1.setVisibility(View.GONE);
                tab2.setSelectedTabIndicatorHeight(6);
                tabLayout.setSelectedTabIndicatorHeight(0);
                //final Drawable icon = tab.getIcon();


                int TabPosition = tab.getPosition();


                final Drawable icon = tab.getIcon();

                //((animation) icon).start();




             *//*   int colorFrom = ((ColorDrawable) toolbar.getBackground()).getColor();
                int colorTo = getColorForTab(tab.getPosition());
                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);


                colorAnimation.setDuration(250);*//*
                //((Animatable) icon).start();
              // ((Animatable) icon).stop();


                //tab2.setSelectedTabIndicatorHeight(4);
                // viewPager2.setVisibility(View.VISIBLE);
                //  viewPager2.setCurrentItem(tab.getPosition());


                //Fragment fragment;

                switch (TabPosition) {

                    case 0:
                        //fragment = new MainFragment();
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction().replace(R.id.frm, new E1Fragment());
                        ft.commit();
                        return;

                    case 1:

                        // fragment = new  E2Fragment();

                        // fragment = new MainFragment();
                        FragmentManager fm1 = getSupportFragmentManager();
                        FragmentTransaction ft1 = fm1.beginTransaction().replace(R.id.frm, new E2Fragment());
                        ft1.commit();




                        return;

                    case 2:


                        // fragment=new E3Fragment();
                        return;

                    case 3:

                      *//*  if (isPermissionGranted()) {
                            call_action();
                        }*//*

                      startActivity(new Intent(Intent.ACTION_DIAL));


                       *//* Intent i = new Intent(Intent.ACTION_DIAL);
                        i.setData(Uri.parse(""));
                        startActivity(i);*//*
                        // fragment=new E4Fragment();
                        return;

                    case 4:


                        //fragment=new MainFragment();
                        return;

                    default:
                        //fragment=new E1Fragment();

                        FragmentManager fmd = getSupportFragmentManager();
                        FragmentTransaction ftd = fmd.beginTransaction().replace(R.id.frm, new E1Fragment());
                        ftd.commit();
                        return;


                     *//*   if(fragment!=null)
                        {
                            FragmentManager fm=getSupportFragmentManager();
                            FragmentTransaction ft=fm.beginTransaction().replace(R.id.frm,fragment);
                            ft.commit();
                        }
                    *//*



                }
               *//* colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        int color = (int) animator.getAnimatedValue();

                        toolbar.setBackgroundColor(color);
                        tabLayout.setBackgroundColor(color);
                        //floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(color));

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(color);
                        }
                    }

                });

                colorAnimation.start();*//*



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager1.setVisibility(View.GONE);
               // tab2.setSelectedTabIndicatorHeight(6);
                //tabLayout.setSelectedTabIndicatorHeight(0);

                //tab2.setSelectedTabIndicatorHeight(4);
                viewPager2.setVisibility(View.VISIBLE);
                viewPager2.setCurrentItem(tab.getPosition());
               *//* int colorFrom = ((ColorDrawable) toolbar.getBackground()).getColor();
                int colorTo = getColorForTab(tab.getPosition());

                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                colorAnimation.setDuration(250);
                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        int color = (int) animator.getAnimatedValue();

                        toolbar.setBackgroundColor(color);
                        tabLayout.setBackgroundColor(color);
                        //fab.setBackgroundTintList(ColorStateList.valueOf(color));

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(color);
                        }
                    }

                });
                colorAnimation.start();
*//*

            }
        });*/

        viewPager1.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                viewPager2.setVisibility(View.GONE);
                tabLayout.setSelectedTabIndicatorHeight(6);
               // tab2.setSelectedTabIndicatorHeight(0);


                //tab2.setSelectedTabIndicatorHeight(4);
                viewPager1.setVisibility(View.VISIBLE);
                viewPager1.setCurrentItem(tab.getPosition());

              /*  int colorFrom = ((ColorDrawable) toolbar.getBackground()).getColor();
                int colorTo = getColorForTab(tab.getPosition());


                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                colorAnimation.setDuration(250);
                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        int color = (int) animator.getAnimatedValue();

                        toolbar.setBackgroundColor(color);
                        tabLayout.setBackgroundColor(color);
                        //fab.setBackgroundTintList(ColorStateList.valueOf(color));

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(color);
                        }
                    }

                });
                colorAnimation.start();
*/


               /* Fragment fragment;

                //tab2.setActivated(false);
                //tab2.setSelectedTabIndicatorHeight(4);
                viewPager2.setVisibility(View.INVISIBLE);
               // tab2.setSelectedTabIndicatorHeight(0);


                if(tab.getPosition()==0)
                {
                   // fragment = new MainFragment();
                    FragmentManager fm=getSupportFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction().replace(R.id.vewfrm, new MainFragment());
                    ft.commit();
                }
                else if(tab.getPosition()==1){

                    FragmentManager fm=getSupportFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction().replace(R.id.vewfrm, new E1Fragment());
                    ft.commit();

                }

else if(tab.getPosition()==2){

                    FragmentManager fm=getSupportFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction().replace(R.id.vewfrm, new E2Fragment());
                    ft.commit();

                }


               *//*
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction().replace(R.id.vewfrm, new MainFragment());
                ft.commit();*//*
*/

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


                viewPager2.setVisibility(View.GONE);
                tabLayout.setSelectedTabIndicatorHeight(6);
               // tab2.setSelectedTabIndicatorHeight(0);

                //tab2.setSelectedTabIndicatorHeight(4);
                viewPager1.setVisibility(View.VISIBLE);
                viewPager1.setCurrentItem(tab.getPosition());

            }
        });


    }

    private void CallbadageUI() {

      //  if(!session.isBadageSelected())
       // {

            //  Toast.makeText(this, "notselc", Toast.LENGTH_SHORT).show();

        badgeselect=true;

        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) tab2.getChildAt(0);
            View v = bottomNavigationMenuView.getChildAt(1);
            BottomNavigationItemView itemView = (BottomNavigationItemView) v;

            View badge = LayoutInflater.from(this).inflate(R.layout.notification_badge, bottomNavigationMenuView, false);

            itemView.addView(badge);
           // session.CreateBadge();


       // }

    }

    private boolean removeBadgechecked(BottomNavigationView tab2, int i) {

        removeBadge(tab2,i);
        return false;
    }

    private void callAdminNumberAPI() {

     //   http://nbayjobs.com/raksha/api/number.php
        RequestQueue requestQueue=Volley.newRequestQueue(this);

       final String url= APIConfig.MAIN_API+APIConfig.AdminNo;

        // final String url="http://nbayjobs.com/raksha/api/number.php";

// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());

                        String phonenumber;

                        try {
                            adminNumber=response.getString("phonenumber");

                            Log.i("PHONE  :",adminNumber);

                            session.createAdminnosession(adminNumber);



                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );

// add it to the RequestQueue
        requestQueue.add(getRequest);
        getRequest.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void CreateAlertDialogWithRadioButtonGroup()
    {


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.CustomDialogTheme);

        builder.setTitle(Html.fromHtml("<font color='#dd0000'>Choose Language</font>"));




        builder.setSingleChoiceItems(values, checkedlanguage, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:

                        tab2.getMenu().getItem(0).setChecked(true);

                       // tab2.setSelectedItemId(id);

                        name.clear();
                        checkedlanguage=0;
                        showpDialog();

                        callBackgroundDiscrpitionApi("Tamil");


                        session.CreatelanguageSelect("Tamil");

                      //  Toast.makeText(MainActivity.this, "Tamil Selected", Toast.LENGTH_LONG).show();
                        break;
                    case 1:

                        tab2.getMenu().getItem(0).setChecked(true);


                        name.clear();
                        checkedlanguage=1;
                        showpDialog();
                        callBackgroundDiscrpitionApi("English");
                        session.CreatelanguageSelect("English");

                       // Toast.makeText(MainActivity.this, "English Selected", Toast.LENGTH_LONG).show();
                        break;
                    /*case 2:

                        Toast.makeText(MainActivity.this, "Third Item Clicked", Toast.LENGTH_LONG).show();
                        break;*/
                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();

    }



    private void SettingAllert() {





      /*  customizeDialog = new CustomizeDialog(context);
        customizeDialog.setTitle("My Title");
        customizeDialog.setMessage("My Custom Dialog Message from \nSmartPhoneBySachin.Blogspot.com ");

        //customizeDialog.
        //customizeDialog.

*//*
      //  customizeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customizeDialog.setContentView(R.layout.radiobutton_dialog);
        List<String> stringList=new ArrayList<>();  // here is list
        String[] langu=new String[]{"Tamil","English"};

        for(int i=0;i<2;i++) {
            stringList.add(langu[i]);
        }
        RadioGroup rg = (RadioGroup) customizeDialog.findViewById(R.id.radio_group);

        for(int i=0;i<stringList.size();i++){
            RadioButton rb=new RadioButton(context); // dynamically creating RadioButton and adding to RadioGroup.
            rb.setText(stringList.get(i));
            rg.addView(rb);
        }

       // dialog.show();*//*

        customizeDialog.show();*/







    }

    private void setFragment() {

        viewPager1.setVisibility(View.GONE);
        tabLayout.setSelectedTabIndicatorHeight(0);

        FragmentManager fmd = getSupportFragmentManager();
        FragmentTransaction ftd = fmd.beginTransaction().replace(R.id.frm, new E1Fragment());
        ftd.commit();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void laguadeDaialog(){


        //session.CreatelanguageSelect("English");


        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialoglayout = inflater.inflate(R.layout.dialog_layout, null);
        Tamil=(RadioButton) dialoglayout.findViewById(R.id.tamil);
        English=(RadioButton) dialoglayout.findViewById(R.id.english);
        // ok=(Button) dialoglayout.findViewById(R.id.btnok);
        int textColor = Color.parseColor("#FFFFFF");
        Tamil.setButtonTintList(ColorStateList.valueOf(textColor));
        English.setButtonTintList(ColorStateList.valueOf(textColor));
        // final String[] language={"TAMIL","ENGLISH"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setTitle("\t\t" + "RAKSHA HOSPITAL");
        // builder.setMessage("Put yourself in our hands.\n Let our years of expertise help you get back on track");
        builder.setView(dialoglayout);
        builder.setCancelable(false);
        /*builder.setSingleChoiceItems(language, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selected=which;

            }
        });*/
        builder.setPositiveButton("SELECT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                if (Tamil.isChecked()){


                    showpDialog();

                    name.clear();

                    checkedlanguage=0;

                    callBackgroundDiscrpitionApi("Tamil");

                    session.CreatelanguageSelect("Tamil");

                   // Toast.makeText(MainActivity.this, "Tamil Selected", Toast.LENGTH_SHORT).show();



                  //  setLocale("ta");


                 //   Toast.makeText(MainActivity.this, "Tamil", Toast.LENGTH_SHORT).show();


                }else if(English.isChecked()){
                    showpDialog();



                    name.clear();

                    checkedlanguage=1;

                    callBackgroundDiscrpitionApi("English");

                    session.CreatelanguageSelect("English");


                   // Toast.makeText(MainActivity.this, "English Selected", Toast.LENGTH_SHORT).show();







                    // setLocale("en");

                   // Toast.makeText(MainActivity.this, "English", Toast.LENGTH_SHORT).show();
                }

                //  Toast.makeText(MainActivity.this, "Tamil", Toast.LENGTH_SHORT).show();

            }
        });
       /* builder.setMessage("Put yourself in our hands.\n" +
                "Let our years of expertise help you get back on track");
*/

        // builder.setNegativeButton(new DialogInterface.OnClickListener())
        // builder.setCancelable(true);


        builder.show();

        final AlertDialog alertDialog=builder.create();

       /* ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

*/


       /* Tamil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LanguageSelect="Tamil";
                Toast.makeText(MainActivity.this, ""+LanguageSelect.toString(), Toast.LENGTH_SHORT).show();

                // alertDialog.dismiss();
            }
        });

        English.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LanguageSelect ="English";
                Toast.makeText(MainActivity.this, ""+LanguageSelect.toString(), Toast.LENGTH_SHORT).show();


            }
        });*/
    }

    private void callBackgroundDiscrpitionApi(String Lang) {




        RequestQueue queue = Volley.newRequestQueue(this);  // this = context

       // http://nbayjobs.com/raksha/api/service.php?id=1&lang=English

        final String url=APIConfig.MAIN_API+APIConfig.Service+"id=1&lang="+Lang;

       // final String url = "http://nbayjobs.com/raksha/api/service.php?id=1&lang="+Lang;

// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());



                        String sucess,error;

                        try {
                            sucess=response.getString("success");
                            error=response.getString("error");

                            if(sucess.equals("1")){
                                Log.i("sucess:",sucess);

                                hidepDialog();

                                JSONArray array=new JSONArray(response.getString("src"));


                                for (int i=0;i<array.length();i++) {

                                    DynamicTabMode dmy=new DynamicTabMode();

                                    JSONObject datas = (JSONObject) array.get(i);


                                    String titlenae,titledis,titleimg;

                                    titlenae=datas.getString("title");
                                    titledis=datas.getString("content");
                                    titleimg=datas.getString("image");

                                    dmy.setName(titlenae);
                                    dmy.setDis(titledis);
                                    dmy.setImg(titleimg);



                                    name.add(dmy);




                                    }

                              //  setupViewPager(viewPager1,name);


                                setFragment();

                               // tab2.
                              /*  tab2.getMenu().getItem(1).setChecked(false);
                                tab2.getMenu().getItem(2).setChecked(false);
                                tab2.getMenu().getItem(3).setChecked(false);
                                tab2.getMenu().getItem(4).setChecked(false);*/
                              //  tab2.getMenu().getItem(5).setChecked(false);


                            }else if(sucess.equals("0")){
                                hidepDialog();
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
                        Log.d("Error.Response", error.toString());

                        hidepDialog();



                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);

        getRequest.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!doubleBackToExitPressedOnce)
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        viewPager1.setVisibility(View.GONE);
        tabLayout.setSelectedTabIndicatorHeight(0);

        //tab2.addOnTabSelectedListener();

        switch (id) {

            case R.id.nav_profile: {

               // tab2.setCheckedItem(R.id.custom_id);
               // navigationView.getMenu().performIdentifierAction(R.id.custom_id, 0);


                //tab2.getMenu().getItem(1).setActionProvider();



              //  tab2.getMenu().performIdentifierAction(id,4);

                if(useravailable.equals("nouser")){

                    alertCreate();


                }else {

                    FragmentManager fm1 = getSupportFragmentManager();
                    FragmentTransaction ft1 = fm1.beginTransaction().replace(R.id.frm, new E2Fragment());
                    ft1.commit();
                    tab2.getMenu().getItem(1).setChecked(true);
                    tab2.setSelectedItemId(id);

                    /*FragmentManager fm1 = getSupportFragmentManager();
                    FragmentTransaction ft1 = fm1.beginTransaction().replace(R.id.frm, new E2Fragment());
                    ft1.commit();*/
                }





              /*  FragmentManager fm1 = getSupportFragmentManager();
                FragmentTransaction ft1 = fm1.beginTransaction().replace(R.id.frm, new E2Fragment());
                ft1.commit();*/
               break;

            }

            case R.id.nav_logout:
            {
                showDialoglogout();
               break;

            }

            case R.id.nav_appointments:


                if(useravailable.equals("nouser")){

                    alertCreate();


                }else {

                    Intent i=new Intent(MainActivity.this,NotificationDetailsActivity.class);
                    startActivity(i);


                }




                break;

            case R.id.nav_notification:
            {


                if(useravailable.equals("nouser")){

                    alertCreate();


                }else {

                    Intent iu=new Intent(MainActivity.this,NotificationDetailsActivity.class);
                    startActivity(iu);

                   /*// tab2.getMenu().getItem(2).setChecked(true);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction().replace(R.id.frm, new NotificationFragment());
                    ft.commit();*/

                    //tab2.setSelectedItemId(id);
                   /* FragmentManager fm1 = getSupportFragmentManager();
                    FragmentTransaction ft1 = fm1.beginTransaction().replace(R.id.frm, new E2Fragment());
                    ft1.commit();*/
                }


                break;

                // tab2.setSelected(true);

            }
            case R.id.nav_setting:
            {

                CreateAlertDialogWithRadioButtonGroup();
                break;

            }
             case R.id.nav_help:
            {
                tab2.getMenu().getItem(4).setChecked(true);

                tab2.setSelectedItemId(id);

                FragmentManager fm2 = getSupportFragmentManager();
                FragmentTransaction ft2 = fm2.beginTransaction().replace(R.id.frm, new E3Fragment());
                ft2.commit();

                break;
            }
            case R.id.nav_home:
            {
                tab2.getMenu().getItem(0).setChecked(true);

                tab2.setSelectedItemId(id);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction().replace(R.id.frm, new E1Fragment());
                ft.commit();
                break;

            }

            default:

                FragmentManager fm1 = getSupportFragmentManager();
                FragmentTransaction ft1 = fm1.beginTransaction().replace(R.id.frm, new E1Fragment());
                ft1.commit();


        }

       /*
        if (id == R.id.nav_profile) {
            // Handle the camera action
        } else if (id == R.id.nav_notification) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_logout) {
            showDialog();

        }
*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    call_action();
                } else {
                   // Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void call_action() {
        // String phnum = etPhoneno.getText().toString();
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + ""));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);

    }
    public void logoutviewFalse() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        for (int menuItemIndex = 0; menuItemIndex < menu.size(); menuItemIndex++) {
            MenuItem menuItem = menu.getItem(menuItemIndex);
            if (menuItem.getItemId() == R.id.nav_logout) {
                menuItem.setVisible(false);
            }

        }
    }
    public void logoutviewTrue() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
       // sign.setVisibility(View.INVISIBLE);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "hi", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(MainActivity.this,EditProfileActivity.class);
                startActivity(i);
            }
        });



        TextUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(MainActivity.this,EditProfileActivity.class);
                startActivity(i);
            }
        });

        for (int menuItemIndex = 0; menuItemIndex < menu.size(); menuItemIndex++) {
            MenuItem menuItem = menu.getItem(menuItemIndex);
            if (menuItem.getItemId() == R.id.nav_logout) {
                menuItem.setVisible(true);
            }

        }
    }

    private void logoutFromGoogle() {


        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                        // Toast.makeText(DrawerActivity.this, "logout", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(),"logotu",Toast.LENGTH_LONG);

                        //Intent i=new Intent(DrawerActivity.this,.class);
                        //startActivity(i);
                        // updateUI(false);
                    }
                });

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

                       /* if (loginType.equals("facebook")) {
                            // LoginManager.getInstance().logOut();
                            //logoutFromfacebook();
                           // login.logoutFromfacebook();
                            session.logoutUser();
                            //Toast.makeText(MainActivity.this, loginType + " Logged Out", Toast.LENGTH_LONG).show();
                        } else if (loginType.equals("google")) {

                            logoutFromGoogle();
                            session.logoutUser();

                           // Toast.makeText(MainActivity.this, loginType+"Logged out", Toast.LENGTH_SHORT).show();
                        } else {
                            session.logoutUser();
                           // Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_LONG).show();
                        }*/


                        /*Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);*/


                        CallLogotuAPI(userId,dialog);





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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {



    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private int getColorForTab(int position) {

        switch (position) {
            case 0:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    return getResources().getColor(R.color.colorPrimaryDark, getTheme());
                return getResources().getColor(R.color.colorPrimaryDark);
            case 1:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    return getResources().getColor(R.color.colorPrimaryBlue, getTheme());
                return getResources().getColor(R.color.colorPrimaryBlue);
            case 2:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    return getResources().getColor(R.color.colorPrimaryDarkYellow, getTheme());
                return getResources().getColor(R.color.colorPrimaryDarkYellow);
            case 3:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    return getResources().getColor(R.color.colorPrimaryRed, getTheme());
                return getResources().getColor(R.color.colorPrimaryRed);
            default:
                return 0;
        }

    }

    void getLocation() {
        try {
            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
          // mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, (LocationListener) this);
           mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10,0,this);

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10,0,this);
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
        String cityName = null;
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(),
                    loc.getLongitude(), 1);
            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
                + cityName;

        Toast.makeText(MainActivity.this, ""+s, Toast.LENGTH_SHORT).show();

//        Snackbar.make(view,"snack :"+s,Snackbar.LENGTH_LONG).show();
       // editLocation.setText(s);



    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Long:"+provider.toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        tab2.getMenu().getItem(0).setChecked(true);

        setFragment();

        CallProileApi(userId);


      /*  FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction().replace(R.id.frm, new E1Fragment());
        ft.commit();*/

    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    public void setLocale(String lang) {



        myLocale = new Locale(lang);

        Resources res = getResources();

        DisplayMetrics dm = res.getDisplayMetrics();

        Configuration conf = res.getConfiguration();

        conf.locale = myLocale;

        res.updateConfiguration(conf, dm);

        Intent refresh = new Intent(this, MainActivity.class);

        startActivity(refresh);

    }

    private void alertCreate()
    {

        final AlertDialog.Builder alert=new AlertDialog.Builder(context,R.style.CustomDialogTheme);
        alert.setCancelable(false);
        alert.setTitle(Html.fromHtml("<font color='#dd0000'>Alert!</font>"));
        alert.setMessage(Html.fromHtml("<font color='#000000'>You must login to access this page</font>"));
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent i=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i);
                //finish();

            }

        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tab2.getMenu().getItem(0).setChecked(true);

            }
        });

        AlertDialog alrt= alert.create();
        alrt.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        //alert.create();
        alrt.show();
    }

    public void removeBadge(BottomNavigationView navigationView, int index)
    {

        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
    View v = bottomNavigationMenuView.getChildAt(index);
    BottomNavigationItemView itemView = (BottomNavigationItemView) v;
    itemView.removeViewAt(itemView.getChildCount()-1);
   // session.CreateBadge();

    }


    private void CallProileApi(final String userIds) {

        // String Url="http://nbayjobs.com/raksha/api/profileshow.php?pid=1";

        // final String Url= APIConfig.MAIN_API+APIConfig.ShowProfile+"pid="+UserId;

        final ApiInterface service= ApiClient.getClient().create(ApiInterface.class);

        Call<ProfileShowModel> descriptionCall= service.Profilelist(userIds);


        descriptionCall.enqueue(new Callback<ProfileShowModel>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<ProfileShowModel> call, retrofit2.Response<ProfileShowModel> response) {

                Log.i("Responce",response.raw().toString());

                Log.i("Responcefull",response.toString());

                String sucess,error;

                sucess= String.valueOf(response.body().getSuccess());

                if(sucess.equals("1")){

                    // Toast.makeText(getActivity(), "not null", Toast.LENGTH_SHORT).show();


                    hidepDialog();

                    String name,phone,address,email,proimg;

                    name=response.body().getName();

                    phone=response.body().getPhone();
                    address=response.body().getAddress();
                    email=response.body().getEmail();
                    proimg=response.body().getImage();

                    //  Log.i("name",name);
                    //Log.i("address",address);
                    // Log.i("email",email);
                    // Log.i("phone",phone);

                    if(name==null)
                    {
                        TextUser.setText("Update Your Profile");

                        CallbadageUI();


                    }else
                    {

                        // Toast.makeText(MainActivity.this, proimg, Toast.LENGTH_SHORT).show();

                        TextUser.setText(name);

                       // Log.i("image",proimg);

                        Glide.with(MainActivity.this).load(proimg).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ic_person).into(img);






                        // SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);


                        //  session.createLoginSession(userIds,"Patients");

                        session.createLoginSession(userIds,"Patient",name,phone,email,address);



                    }


                }
                else if(sucess.equals("0"))
                {

                    //  Toast.makeText(getActivity(), "not null", Toast.LENGTH_SHORT).show();


                    hidepDialog();

                }


            }

            @Override
            public void onFailure(Call<ProfileShowModel> call, Throwable t) {

                hidepDialog();
            }
        });



    }


    private void CallLogotuAPI(final String did, final DialogInterface dialogInterface) {


        RequestQueue queue=Volley.newRequestQueue(this);

        String url=APIConfig.MAIN_API+APIConfig.PatientLogout;


       // http://www.nbayjobs.com/raksha/api/patientlogout.php?pid=1


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

                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                                session.logoutUser();
                                // Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_LONG).show();

                                logoutviewFalse();
                                dialogInterface.cancel();

                                finishAffinity();



                            }else if(sucess.equals("0"))
                            {

                                hidepDialog();

                                dialogInterface.cancel();


                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
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
                params.put("pid",did);


                return params;
            }
        };
        queue.add(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(500000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));



    }








   /* private void setupTabIcons(int pos) {
        switch (pos){
            case 0:
                tabLayout.getTabAt(0).setIcon(tabIconsFocus[0]);
                tabLayout.getTabAt(1).setIcon(tabIconsNoFocus[1]);
                tabLayout.getTabAt(2).setIcon(tabIconsNoFocus[2]);

                break;
            case 1:
                tabLayout.getTabAt(0).setIcon(tabIconsNoFocus[0]);
                tabLayout.getTabAt(1).setIcon(tabIconsFocus[1]);
                tabLayout.getTabAt(2).setIcon(tabIconsNoFocus[2]);

                break;

            case 2:
                tabLayout.getTabAt(0).setIcon(tabIconsNoFocus[0]);
                tabLayout.getTabAt(1).setIcon(tabIconsNoFocus[1]);
                tabLayout.getTabAt(2).setIcon(tabIconsFocus[2]);

                break;
        }
    }*/







/*
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted)
                        Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    else {

                        Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CAMERA}, PERMISSION_REQUEST_CODE);

    }*/
/*private final LocationListener mLocationListener = new LocationListener() {
    @Override
    public void onLocationChanged(final Location location) {
        //your code here
    }
};*/



  /*  BottomNavigationMenuView bottomNavigationMenuView =
            (BottomNavigationMenuView) navigationView.getChildAt(0);
    View v = bottomNavigationMenuView.getChildAt(3);
    BottomNavigationItemView itemView = (BottomNavigationItemView) v;

    View badge = LayoutInflater.from(this)
            .inflate(R.layout.notification_badge, bottomNavigationMenuView, false);

    itemView.addView(badge);*/

}

    /*@Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }*/

