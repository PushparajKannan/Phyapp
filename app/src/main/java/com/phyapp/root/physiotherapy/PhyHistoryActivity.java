package com.phyapp.root.physiotherapy;

import android.app.AlertDialog;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phyapp.root.physiotherapy.Adapters.RecyclerHistroyAdapter;
import com.phyapp.root.physiotherapy.ModelClass.PhyHistoryModel;
import com.phyapp.root.physiotherapy.Retofit.ApiClient;
import com.phyapp.root.physiotherapy.Retofit.ApiInterface;
import com.phyapp.root.physiotherapy.Utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhyHistoryActivity extends AppCompatActivity {

    SessionManager session;
    String id;
    AlertDialog pDialog;
    ArrayList<PhyHistoryModel.Src> pname;
    private RecyclerHistroyAdapter itemsAdapter;
    RecyclerView clv;
    SwipeRefreshLayout mySwipeRefreshLayout;
    LinearLayout layouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phy_history);

        callTitleCenterFont();

        session=new SessionManager(this);

        pDialog=new SpotsDialog(this,R.style.Custom);
        pDialog.setCancelable(false);
        layouts=(LinearLayout) findViewById(R.id.empty);

        mySwipeRefreshLayout=findViewById(R.id.swiperefresh);

        mySwipeRefreshLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2);


        showpDialog();

        HashMap<String,String> user=session.getUserDetails();

        id=user.get(session.KEY_USERID);

        layouts.setVisibility(View.VISIBLE);






        pname = new ArrayList<PhyHistoryModel.Src>();
/*
        pname.add(new RecyclerViewModel("Pushparaj kannan"));
        pname.add(new RecyclerViewModel("Saravana Moorthi"));
        pname.add(new RecyclerViewModel("Karthi Keyan"));
        pname.add(new RecyclerViewModel("Palani"));
        pname.add(new RecyclerViewModel("Veeranan"));
        pname.add(new RecyclerViewModel("Muthu Kumar"));
        pname.add(new RecyclerViewModel("Jothi Rajan"));
        pname.add(new RecyclerViewModel("Pandi Durai"));
        pname.add(new RecyclerViewModel("IceCreamSandwich"));
        pname.add(new RecyclerViewModel("Kannnan"));
        pname.add(new RecyclerViewModel("Viginesh"));
        pname.add(new RecyclerViewModel("Lollipop"));
        pname.add(new RecyclerViewModel("MarshMallow"));
        pname.add(new RecyclerViewModel("Nougat"));*/




        callPhyHistoryNotificationAPI(id,"1");


        itemsAdapter = new RecyclerHistroyAdapter(PhyHistoryActivity.this, pname, null);
        clv = (RecyclerView) findViewById(R.id.recycler_history);



/*
        int resId = R.anim.layout_animation_fall_down;
        int resIdright= R.anim.layout_animation_from_right;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getApplicationContext(),resId );


        clv.setLayoutAnimation(animation);*/



        clv.setLayoutManager(new LinearLayoutManager(PhyHistoryActivity.this));
        clv.setHasFixedSize(true);
        // runLayoutAnimation(clv);
        clv.setAdapter(itemsAdapter);



        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("LOg", "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        myUpdateOperation();

                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

    }

    private void myUpdateOperation() {
        showpDialog();

        callPhyHistoryNotificationAPI(id,"1");

    }

    private void callPhyHistoryNotificationAPI(String ids,String sts) {


        final ApiInterface service= ApiClient.getClient().create(ApiInterface.class);

        Call<PhyHistoryModel> descriptionCall= service.DoctorCompletedhistorylist(ids,sts);

        descriptionCall.enqueue(new Callback<PhyHistoryModel>() {
            @Override
            public void onResponse(Call<PhyHistoryModel> call, Response<PhyHistoryModel> response) {


                Log.i("Responce",response.raw().toString());


                String sucess,error;

                sucess= String.valueOf(response.body().getSuccess());

                if(sucess.equals("1")){
                    hidepDialog();

                    pname=response.body().getSrc();

                    if (!pname.isEmpty() && pname.size()!=0){

                        // hidepDialog();


                        if( layouts.getVisibility()==View.VISIBLE){
                            //button.setVisibility(View.GONE);
                            layouts.setVisibility(View.GONE);

                        }


                        itemsAdapter = new RecyclerHistroyAdapter(PhyHistoryActivity.this, pname, null);

                        clv.setAdapter(itemsAdapter);

                        itemsAdapter.notifyDataSetChanged();

                        int resId = R.anim.layout_animation_fall_down;
                        int resIdright = R.anim.layout_animation_from_right;
                        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(PhyHistoryActivity.this, resId);


                        clv.setLayoutAnimation(animation);


                    }else {

                       // itemsAdapter = new RecyclerHistroyAdapter(PhyHistoryActivity.this, pname, null);

                       // clv.setAdapter(itemsAdapter);

                        itemsAdapter.notifyDataSetChanged();

                    }


                }else if(sucess.equals("0")){
                    hidepDialog();

                    error=response.body().getErrorMsg();

                    Log.i("error",error);

                    pname.clear();

                    itemsAdapter.notifyDataSetChanged();



                }



            }

            @Override
            public void onFailure(Call<PhyHistoryModel> call, Throwable t) {

            }
        });

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


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}