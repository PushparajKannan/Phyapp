package com.phyapp.root.physiotherapy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;

import com.phyapp.root.physiotherapy.ModelClass.MenuSubListModel;
import com.phyapp.root.physiotherapy.Retofit.ApiClient;
import com.phyapp.root.physiotherapy.Retofit.ApiInterface;
import com.phyapp.root.physiotherapy.Utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultForMeActivity extends AppCompatActivity {

    Button Needforcon;
    SessionManager session;
    String Language,UserID;
    private ArrayList<MenuSubListModel.Src> subMenulist;
    RecyclerViewAdapter itemsAdapter;
    RecyclerView clv;
    String sid,mainId;

    android.app.AlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_for_me);
        callTitleCenterFont();


        pDialog=new SpotsDialog(this,R.style.Custom);

        pDialog.setCancelable(false);

        session=new SessionManager(this);

        sid=getIntent().getStringExtra("sid");

     //   mainId=getIntent().getStringExtra("mid");

        HashMap<String,String> user=session.getUserDetails();

        Language=user.get(session.KEY_USERS_Language);

        UserID=user.get(session.KEY_USERID);



        showpDialog();
/*

        // Create an icon
        ImageView icon = new ImageView(this);
        icon.setImageResource(R.drawable.ic_person_black_24dp);

        com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton actionButton = new com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        // repeat many times:
        ImageView itemIcon1 = new ImageView(this);
        itemIcon1.setImageResource(R.drawable.ic_home_black_24dp);

        ImageView itemIcon2 = new ImageView(this);
        itemIcon2.setImageResource(R.drawable.ic_notifications_active_black_24dp);

        ImageView itemIcon3 = new ImageView(this);
        itemIcon3.setImageResource(R.drawable.ic_person_black_24dp);

        SubActionButton button1 = itemBuilder.setContentView(itemIcon1).build();
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();
        SubActionButton button3 = itemBuilder.setContentView(itemIcon3).build();

        //attach the sub buttons to the main button
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .attachTo(actionButton)
                .build();


*/









      /*  // in Activity Context
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageDrawable( );
*/
        Needforcon=(Button) findViewById(R.id.needcon);

         clv = (RecyclerView) findViewById(R.id.clists);



/*
        Needforcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clv.setVisibility(View.VISIBLE);
            }
        });*/







/*

        final ArrayList<RecyclerViewModel> pname = new ArrayList<RecyclerViewModel>();

       */
/* pname.add(new RecyclerViewModel("Alpha"));
        pname.add(new RecyclerViewModel("Beta"));
        pname.add(new RecyclerViewModel("Cupcake"));
        pname.add(new RecyclerViewModel("Donut"));
        pname.add(new RecyclerViewModel("Eclairs"));
        pname.add(new RecyclerViewModel("Froyo"));
        pname.add(new RecyclerViewModel("GingerBread"));
        pname.add(new RecyclerViewModel("HoneyComb"));
        pname.add(new RecyclerViewModel("IceCreamSandwich"));
        pname.add(new RecyclerViewModel("JellyBean"));
        pname.add(new RecyclerViewModel("KitKat"));
        pname.add(new RecyclerViewModel("Lollipop"));
        pname.add(new RecyclerViewModel("MarshMallow"));
        pname.add(new RecyclerViewModel("Nougat"));*//*


        pname.add(new RecyclerViewModel("Neurological Physiotherapy"));
        pname.add(new RecyclerViewModel("Musculoskeletal Outpatients"));
        pname.add(new RecyclerViewModel("Orthopaedics"));
        pname.add(new RecyclerViewModel("Respiratory"));
        pname.add(new RecyclerViewModel("Neurological Physiotherapy"));
        pname.add(new RecyclerViewModel("Musculoskeletal Outpatients"));
        pname.add(new RecyclerViewModel("Orthopaedics"));
        pname.add(new RecyclerViewModel("Physiotherapy"));
        pname.add(new RecyclerViewModel("Musculoskeletal"));
        pname.add(new RecyclerViewModel("JRespiratory"));
        pname.add(new RecyclerViewModel("Orthopaedics"));
        pname.add(new RecyclerViewModel("Musculoskeletal"));
        pname.add(new RecyclerViewModel("Outpatients"));
        pname.add(new RecyclerViewModel("Nougat"));
*/

          itemsAdapter = new RecyclerViewAdapter(ConsultForMeActivity.this, subMenulist, null,UserID,sid);
        clv.setLayoutManager(new LinearLayoutManager(ConsultForMeActivity.this));
        clv.setHasFixedSize(true);
        /*int resId = R.anim.layout_animation_fall_down;
        int resIdright= R.anim.layout_animation_from_right;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getApplicationContext(),resId );


        clv.setLayoutAnimation(animation);
*/
        CallSubmelist(Language,sid);



       // clv.setAdapter(itemsAdapter);

/*

        Fragment fragment= new MainFragment();

        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction().replace(R.id.listre,fragment);

        ft.commit();
*/



    }

    private void CallSubmelist(String language, final String sid) {

       final ApiInterface service= ApiClient.getClient().create(ApiInterface.class);

        Call<MenuSubListModel> descriptionCall= service.menusublist("1","1",sid,language);

        descriptionCall.enqueue(new Callback<MenuSubListModel>() {
            @Override
            public void onResponse(Call<MenuSubListModel> call, Response<MenuSubListModel> response) {

                Log.i("Responce",response.raw().toString());

                String sucess,error;

                sucess= String.valueOf(response.body().getSuccess());

                if(sucess.equals("1")){
                    hidepDialog();

                    subMenulist=response.body().getSrc();

                    if (!subMenulist.isEmpty() && subMenulist.size()!=0){

                       // hidepDialog();

                          itemsAdapter = new RecyclerViewAdapter(ConsultForMeActivity.this, subMenulist, null,UserID,sid);

                        clv.setAdapter(itemsAdapter);

                        itemsAdapter.notifyDataSetChanged();

                        int resId = R.anim.layout_animation_fall_down;
                        int resIdright = R.anim.layout_animation_from_right;
                        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(ConsultForMeActivity.this, resIdright);


                        clv.setLayoutAnimation(animation);


                    }


                }else if(sucess.equals("0")){
                    hidepDialog();

                    error=response.body().getErrorMsg();

                    Log.i("error",error);


                }

            }

            @Override
            public void onFailure(Call<MenuSubListModel> call, Throwable t) {


                hidepDialog();
            }
        });


    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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

}
