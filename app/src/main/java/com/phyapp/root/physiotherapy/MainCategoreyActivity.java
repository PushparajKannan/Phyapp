package com.phyapp.root.physiotherapy;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.phyapp.root.physiotherapy.Adapters.MaincatogrylistAdapter;
import com.phyapp.root.physiotherapy.ModelClass.MenuCategoryModel;
import com.phyapp.root.physiotherapy.Retofit.ApiClient;
import com.phyapp.root.physiotherapy.Retofit.ApiInterface;
import com.phyapp.root.physiotherapy.Utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainCategoreyActivity extends AppCompatActivity  {

 String mainID,Language,UserID;
 SessionManager session;
 private ArrayList<MenuCategoryModel.Src> Menulistdata;

 MaincatogrylistAdapter itemsAdapter;
 RecyclerView clv;
 android.app.AlertDialog pDialog;

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_categorey);
        callTitleCenterFont();

/*
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

  pDialog=new SpotsDialog(this,R.style.Custom);

  showpDialog();

        session=new SessionManager(this);

        HashMap<String,String> user=session.getUserDetails();

        Language=user.get(session.KEY_USERS_Language);
        UserID=user.get(session.KEY_USERID);

  clv = (RecyclerView) findViewById(R.id.consulists);
  clv.setLayoutManager(new LinearLayoutManager(MainCategoreyActivity.this));
  clv.setHasFixedSize(true);


 /* int resId = R.anim.layout_animation_fall_down;
   int resIdright= R.anim.layout_animation_from_right;
  LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getApplicationContext(),resId );


  clv.setLayoutAnimation(animation);
*/


  MaincatogrylistAdapter itemsAdapter = new MaincatogrylistAdapter(MainCategoreyActivity.this, Menulistdata, null,UserID,"1");

  clv.setAdapter(itemsAdapter);




      /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });*/



     //   Needforconlist=(Button) findViewById(R.id.);

     mainID=getIntent().getStringExtra("mid");

     //Log.i("mainID",mainID);



     callMenulist("1",Language);








/*
        Needforcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clv.setVisibility(View.VISIBLE);
            }
        });*/







      //  final ArrayList<RecyclerViewModel> pname = new ArrayList<RecyclerViewModel>();

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
        pname.add(new RecyclerViewModel("Nougat"));*/
/*

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

      //  final MaincatogrylistAdapter itemsAdapter = new MaincatogrylistAdapter(MainCategoreyActivity.this, pname, null);









    }

 private void callMenulist(final String mainID, String Language) {


  final ApiInterface service= ApiClient.getClient().create(ApiInterface.class);

  final Call<MenuCategoryModel> Menulist= service.menulist("1",mainID,Language);

  Menulist.enqueue(new Callback<MenuCategoryModel>() {
   @Override
   public void onResponse(Call<MenuCategoryModel> call, Response<MenuCategoryModel> response) {

    Log.i("Responce",response.raw().toString());

    String sucess,error;

    sucess= String.valueOf(response.body().getSuccess());

    if(sucess.equals("1")) {
     hidepDialog();

     Menulistdata = response.body().getSrc();

     if(Menulistdata.size()!=0 && !Menulistdata.isEmpty()){



      itemsAdapter = new MaincatogrylistAdapter(MainCategoreyActivity.this, Menulistdata, null,UserID,mainID);

      clv.setAdapter(itemsAdapter);


      itemsAdapter.notifyDataSetChanged();


      int resId = R.anim.layout_animation_fall_down;
      int resIdright = R.anim.layout_animation_from_right;
      LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(MainCategoreyActivity.this, resIdright);


      clv.setLayoutAnimation(animation);

     }else {
      itemsAdapter.notifyDataSetChanged();
     }


    }else if(sucess.equals("0")){
     hidepDialog();

     error=response.body().getErrorMsg();

     Log.i("Error",error);

    }





   }

   @Override
   public void onFailure(Call<MenuCategoryModel> call, Throwable t) {

    hidepDialog();
   }
  });




/*

  descriptionCall.enqueue(new Callback<MenuCategoryModel>() {
   @Override
   public void onResponse(Call<MenuCategoryModel> call, Response<MenuCategoryModel> response) {

    Log.i("Responce",response.raw().toString());
    String sucess= String.valueOf(response.body().getSuccess());


    Log.i("sucess",sucess);

    if(sucess.equals("1")) {

     resultmodel = response.body().getSrc();

     String tit,content,image;

     tit = resultmodel.get(0).getTitle();

     content=resultmodel.get(0).getContent();

     image=resultmodel.get(0).getImage();




     Log.i("title", tit);

     product_tite.setText(tit);
     product_content.setText(content);

     Glide.with(MainCategoreyActivity.this)
             .load(image)
             .asBitmap()
             .diskCacheStrategy(DiskCacheStrategy.ALL)
             .placeholder(R.drawable.noimage).into(product_image);
     //.transform(new CircleTransform(getApplicationContext())).

    }else if(sucess.equals("0"))
    {
     String errormsg;//=response.body().getErrorMsg();



     errormsg=response.body().getErrorMsg();

     Log.i("errormsg",errormsg);


    }








   }

   @Override
   public void onFailure(Call<DescriptionProductModel> call, Throwable t) {

   }
  });


*/

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
  this.getSupportActionBar().setCustomView(vr);
 }



}
