package com.phyapp.root.physiotherapy.Fragment;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;

import android.text.Html;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.phyapp.root.physiotherapy.EditProfileActivity;
import com.phyapp.root.physiotherapy.ModelClass.ProfileShowModel;
import com.phyapp.root.physiotherapy.R;
import com.phyapp.root.physiotherapy.Retofit.ApiClient;
import com.phyapp.root.physiotherapy.Retofit.ApiInterface;
import com.phyapp.root.physiotherapy.Utils.SessionManager;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 23/3/18.
 */

public class E2Fragment extends Fragment{


    CircleImageView profilepic;
    ImageView iv;
    SessionManager session;
    String Language,UserId;
    AlertDialog pDialog;
    Dialog dialogs;
    TextView Profilename,Prifilemail,Profileaddress,Profilenumber;
    private Context context;
    boolean pressed;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this.getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_e2,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        profilepic=(CircleImageView) view.findViewById(R.id.profilepic);

        session=new SessionManager(getActivity());

        pDialog=new SpotsDialog(getContext(),R.style.Custom);

        pDialog.setCancelable(false);

        dialogs=new Dialog(getActivity());
        pressed=false;


        showpDialog();


        HashMap<String,String> user=session.getUserDetails();

        UserId=user.get(SessionManager.KEY_USERID);


        iv=(ImageView) view.findViewById(R.id.penimg);
        profilepic=(CircleImageView) view.findViewById(R.id.profilepic);
        Prifilemail=(TextView) view.findViewById(R.id.Profile_view_email);
        Profilename=(TextView) view.findViewById(R.id.Profile_view_name);
        Profileaddress=(TextView) view.findViewById(R.id.Profile_view_address);
        Profilenumber=(TextView) view.findViewById(R.id.Profile_view_number);

        CallProileApi(UserId);



        iv.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {




                Intent i=new Intent(getActivity(), EditProfileActivity.class);

                Pair[] pair=new Pair[1];

                pair[0]=new Pair<View,String>(profilepic,"imageTransfrom");

                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(getActivity(),pair);


                startActivity(i,options.toBundle());


           }
        });


      // profilepic.getHierarchy().setPlaceholderImage(R.drawable.ic_person_black_24dp);



        //clv = (RecyclerView) view.findViewById(R.id.clistss);

        CardView clv=(CardView) view.findViewById(R.id.cardl);


        int resId = R.anim.layout_animation_fall_down;
        int resIdright = R.anim.layout_animation_from_right;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);


        clv.setLayoutAnimation(animation);

    }

    private void CallProileApi(final String userIds) {

       // String Url="http://nbayjobs.com/raksha/api/profileshow.php?pid=1";

       // final String Url= APIConfig.MAIN_API+APIConfig.ShowProfile+"pid="+UserId;

        final ApiInterface service= ApiClient.getClient().create(ApiInterface.class);

        Call<ProfileShowModel> descriptionCall= service.Profilelist(userIds);


        descriptionCall.enqueue(new Callback<ProfileShowModel>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<ProfileShowModel> call, Response<ProfileShowModel> response) {

                Log.i("Responce",response.raw().toString());

                Log.i("Responcefull",response.toString());

                String sucess,error;

                sucess= String.valueOf(response.body().getSuccess());

                if(sucess.equals("1")){

                   // Toast.makeText(getActivity(), "not null", Toast.LENGTH_SHORT).show();


                    hidepDialog();

                    String name,phone,address,email,proimg="";

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

                        if(!pressed){

                            alertCreate();

                        }

                        Profilenumber.setText(phone);


                       /*Intent i=new Intent(getActivity(),EditProfileActivity.class);

                       i.putExtra("number",phone);

                        Pair[] pair=new Pair[1];

                        pair[0]=new Pair<View,String>(profilepic,"imageTransfrom");

                        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(getActivity(),pair);

                        startActivity(i,options.toBundle());*/

                        }else
                            {

                               // Toast.makeText(getActivity(), "not null", Toast.LENGTH_SHORT).show();

                                Log.i("name",name);
                                Log.i("address",address);
                                Log.i("email",email);
                                Log.i("phone",phone);
                                //Log.i("image",proimg);

                                if(!proimg.isEmpty())
                                    Glide.with(context).load(proimg).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.person).into(profilepic);


                                Profileaddress.setText(address);
                             Profilename.setText(name);
                             Profilenumber.setText(phone);
                             Prifilemail.setText(email);



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

    private void alertCreate() {
        pressed=true;


       /* final Snackbar snackbar=Snackbar.make(getActivity().findViewById(android.R.id.content),"You must update your profile",Snackbar.LENGTH_LONG);

       // final View views=snackbar.getView();



        snackbar.setAction("ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),EditProfileActivity.class);
                startActivity(i);
                snackbar.dismiss();
            }
        });

        snackbar.show();*/

      //  android.support.v7.app.AlertDialog alrt= alert.create();

        final androidx.appcompat.app.AlertDialog.Builder alert=new androidx.appcompat.app.AlertDialog.Builder(context,R.style.CustomDialogTheme);
        alert.setCancelable(false);
        //alrt= alert.create();
        alert.setTitle(Html.fromHtml("<font color='#dd0000'>Alert!</font>"));
        alert.setMessage(Html.fromHtml("<font color='#000000'>You must update your profile</font>"));
        alert.setPositiveButton("ok",null );

      //  alert.setNegativeButton("cancel",null);

        androidx.appcompat.app.AlertDialog alrt= alert.create();

        alrt.show();

       /* alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });*/
        // alert.create();


       // android.support.v7.app.AlertDialog alrt=alert.create();

        //alrt.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
       // alert.show();
    }


   /* spinner_states_activity = (Spinner)findViewById(R.id.spinner_states_main);
        spinner_states_activity.setOnItemSelectedListener(this);
    adapter = ArrayAdapter.createFromResource(
            this, R.array.state_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.myspinner);
// my layout for spinners u can use urs or defalut. k?
        spinner_states_activity.setAdapter(adapter);

    spinner_cities_activity = (Spinner)findViewById(R.id.spinner_cities_main);
        spinner_cities_activity.setOnItemSelectedListener(this);

//and in function onItemSelected

    int pos,pos2;
    pos = spinner_states_activity.getSelectedItemPosition();
    int iden=parent.getId();
        if(iden==R.id.spinner_states_main)
    {
        pos2 = 0;
        switch (pos)
        {
            case 0: unit_adapter= ArrayAdapter.createFromResource(
                    this, R.array.States1Cities_array, android.R.layout.simple_spinner_item);
                break;
            case 1: unit_adapter= ArrayAdapter.createFromResource(
                    this, R.array.States3Cities_array, android.R.layout.simple_spinner_item);
                break;
            // all the StatesxCities entires....
            default:
                break;
        }

        spinner_cities_activity.setAdapter(unit_adapter);
        unit_adapter.setDropDownViewResource(R.layout.myspinner);
    }*/

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();

        showpDialog();


        HashMap<String,String> user=session.getUserDetails();

        UserId=user.get(SessionManager.KEY_USERID);

        CallProileApi(UserId);



    }
}
