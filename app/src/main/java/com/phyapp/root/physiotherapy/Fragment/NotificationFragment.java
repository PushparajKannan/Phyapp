package com.phyapp.root.physiotherapy.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.phyapp.root.physiotherapy.APICONFIG.APIConfig;
import com.phyapp.root.physiotherapy.Adapters.NotificationAdapter;
import com.phyapp.root.physiotherapy.ModelClass.NotificationListModel;
import com.phyapp.root.physiotherapy.R;
import com.phyapp.root.physiotherapy.Retofit.ApiClient;
import com.phyapp.root.physiotherapy.Retofit.ApiInterface;
import com.phyapp.root.physiotherapy.Utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {


    SessionManager session;
    String UserID;
    AlertDialog pDialog;
    NotificationAdapter itemsAdapter;
    private ArrayList<NotificationListModel.Src> notificationlist;
    RecyclerView recyclerView;
    String adminNumber,Language;
    LinearLayout layout;
    TextView button;
    SwipeRefreshLayout mySwipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     //   return super.onCreateView(inflater, container, savedInstanceState);


     //   return inflater.inflate(R.layout.fragmentnotification,container,false);

        View rootView=inflater.inflate(R.layout.fragmentnotification,container,false);
        return rootView;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        session=new SessionManager(getActivity());

        HashMap<String,String> user=session.getUserDetails();

        UserID=user.get(session.KEY_USERID);

        Language=user.get(session.KEY_USERS_Language);

        notificationlist = new ArrayList<NotificationListModel.Src>();



        pDialog=new SpotsDialog(getContext(),R.style.Custom);

        pDialog.setCancelable(false);

        mySwipeRefreshLayout=view.findViewById(R.id.swiperefresh);

        mySwipeRefreshLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2);

        recyclerView = (RecyclerView) view.findViewById(R.id.notificationrecycle);
        layout=(LinearLayout) view.findViewById(R.id.empty);
        button=(TextView) view.findViewById(R.id.but);


        showpDialog();


      /*  int resId = R.anim.layout_animation_fall_down;
        int resIdright= R.anim.layout_animation_from_right;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(),resId );


        recyclerView.setLayoutAnimation(animation);
*/

        Log.i("Language :",Language);

        layout.setVisibility(View.VISIBLE);
       // button.setVisibility(View.VISIBLE);
       // button.setElevation(5);


        callNotificationList(UserID,"New",Language);



        itemsAdapter = new NotificationAdapter(NotificationFragment.this.getActivity(), notificationlist, null);

        recyclerView.setAdapter(itemsAdapter);




        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

      //  recyclerView.setAdapter(itemsAdapter);



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

        callNotificationList(UserID,"New",Language);
    }

    private void callNotificationList(String userIds,String status,String Languages) {

        final ApiInterface service= ApiClient.getClient().create(ApiInterface.class);

        Call<NotificationListModel> descriptionCall= service.Notificationlist(userIds,status,Languages);


        descriptionCall.enqueue(new Callback<NotificationListModel>() {
            @Override
            public void onResponse(Call<NotificationListModel> call, Response<NotificationListModel> response) {
              //  hidepDialog();

                Log.i("Responce",response.raw().toString());

                String sucess,error;

                sucess= String.valueOf(response.body().getSuccess());

                if(sucess.equals("1")){
                    hidepDialog();

                    notificationlist=response.body().getSrc();

                    if (!notificationlist.isEmpty() && notificationlist.size()!=0) {


                        if (layout.getVisibility() == View.VISIBLE) {
                            //button.setVisibility(View.GONE);
                            layout.setVisibility(View.GONE);

                        }

                        // hidepDialog();

                        // itemsAdapter = new RecyclerViewAdapter(ConsultForMeActivity.this, subMenulist, null,UserID,sid);

                        itemsAdapter = new NotificationAdapter(getActivity(), notificationlist, null);

                        recyclerView.setAdapter(itemsAdapter);

                        itemsAdapter.notifyDataSetChanged();


                        int resId = R.anim.layout_animation_fall_down;
                        int resIdright = R.anim.layout_animation_from_right;
                        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resIdright);


                        recyclerView.setLayoutAnimation(animation);


                    }else {
                        itemsAdapter.notifyDataSetChanged();
                    }


                     //   layout.setVisibility(View.VISIBLE);
                      //  button.setVisibility(View.VISIBLE);




                }else if(sucess.equals("0")){
                    hidepDialog();

                    error=response.body().getErrorMsg();

                    Log.i("error",error);

                 //   itemsAdapter=new NotificationAdapter(getActivity(),notificationlist,null);

                    //recyclerView.setAdapter(itemsAdapter);

                    notificationlist.clear();

                    itemsAdapter.notifyDataSetChanged();



                }




            }

            @Override
            public void onFailure(Call<NotificationListModel> call, Throwable t) {

                hidepDialog();




            }
        });


        }

        private void showpDialog()
        {
            if (!pDialog.isShowing())
                pDialog.show();
        }

        private void hidepDialog()
        {
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    private void callAdminNumberAPI() {

        //   http://nbayjobs.com/raksha/api/number.php
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());

        final String url= APIConfig.MAIN_API+APIConfig.AdminNo;

        // final String url="http://nbayjobs.com/raksha/api/number.php";

// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONObject>()
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
                new com.android.volley.Response.ErrorListener()
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

    }


