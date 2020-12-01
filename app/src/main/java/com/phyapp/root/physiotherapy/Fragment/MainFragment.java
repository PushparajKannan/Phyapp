package com.phyapp.root.physiotherapy.Fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;


import com.phyapp.root.physiotherapy.Adapters.ClasicRecyclerAdapter;
import com.phyapp.root.physiotherapy.ModelClass.DServiceslistModel;
import com.phyapp.root.physiotherapy.PhyLoginActivity;
import com.phyapp.root.physiotherapy.R;
import com.phyapp.root.physiotherapy.Retofit.ApiClient;
import com.phyapp.root.physiotherapy.Retofit.ApiInterface;
import com.phyapp.root.physiotherapy.Utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {
    //private Context ctx;
    ClasicRecyclerAdapter itemsAdapter;
    RecyclerView clv;

    ArrayList<DServiceslistModel.Src> pname;
    SessionManager session;

    String doctorid;
    AlertDialog pDialog;
    LinearLayout layouts;

    Dialog pdialog;

    SwipeRefreshLayout mySwipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        session=new SessionManager(getActivity());
        layouts=(LinearLayout) view.findViewById(R.id.empty);

        HashMap<String,String> user=session.getUserDetails();

        doctorid=user.get(session.KEY_USERID);

        pdialog=new Dialog(MainFragment.this.getActivity());


        pname = new ArrayList<DServiceslistModel.Src>();

        pDialog=new SpotsDialog(getContext(),R.style.Custom);

        pDialog.setCancelable(false);


        if(doctorid==null)
        {

            checkligoin();
        }



        mySwipeRefreshLayout=view.findViewById(R.id.swiperefresh);

        mySwipeRefreshLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2);

      //  mySwipeRefreshLayout.setProgressBackgroundColorSchemeResource(Color.parseColor("#dd0000"));


        clv = (RecyclerView) view.findViewById(R.id.clistss);


       /* HashMap<String,String> user=session.getUserDetails();

        doctorid=user.get(session.KEY_USERID);
*/

      //  Log.i("doc_id",doctorid);




        layouts.setVisibility(View.VISIBLE);






      /*  pname.add(new RecyclerViewModel("neurological physiotherapy"));
        pname.add(new RecyclerViewModel("musculoskeletal outpatients"));
        pname.add(new RecyclerViewModel("orthopaedics"));
        pname.add(new RecyclerViewModel("respiratory"));
        pname.add(new RecyclerViewModel("Pilates"));
        pname.add(new RecyclerViewModel("Posture Correction"));
        pname.add(new RecyclerViewModel("Soft Tissue Injury Care"));
        pname.add(new RecyclerViewModel("Strength Exercises"));
        pname.add(new RecyclerViewModel("Glucosamine"));
        pname.add(new RecyclerViewModel("Vestibular Physio"));
        pname.add(new RecyclerViewModel("Swiss Ball Exercises"));
        pname.add(new RecyclerViewModel("TENS Machine"));
        pname.add(new RecyclerViewModel("Real Time Ultrasound"));
        pname.add(new RecyclerViewModel("Joint manipulation"));*/




/*
        int resId = R.anim.layout_animation_fall_down;
        int resIdright = R.anim.layout_animation_from_right;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);


        clv.setLayoutAnimation(animation);
*/

        //clv.setAdapter(itemsAdapter);

        itemsAdapter = new ClasicRecyclerAdapter(MainFragment.this.getActivity(), pname, null);

         clv.setAdapter(itemsAdapter);


        clv.setLayoutManager(new LinearLayoutManager(MainFragment.this.getActivity()));
        clv.setHasFixedSize(true);


        showpDialog();

        CallServiceListAPI(doctorid);

        // runLayoutAnimation(clv);
        //clv.setAdapter(itemsAdapter);


      /*  ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (viewHolder instanceof ClasicRecyclerAdapter.ViewHolder) {
                    // get the removed item name to display it in snack bar
                    String name = pname.get(viewHolder.getAdapterPosition()).getmItemName();

                    // backup of removed item for undo purpose
                    final RecyclerViewModel deletedItem = pname.get(viewHolder.getAdapterPosition());
                    final int deletedIndex = viewHolder.getAdapterPosition();

                    // remove the item from recycler view
                    itemsAdapter.removeItem(viewHolder.getAdapterPosition());

                    // showing snack bar with Undo option
                    Snackbar snackbar = Snackbar
                            .make(getView(), name + " removed from cart!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // undo is selected, restore the deleted item
                            itemsAdapter.restoreItem(deletedItem, deletedIndex);
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }

            }
        };*/

        //runLayoutAnimation(clv);

           /* @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
                if (viewHolder instanceof ClasicRecyclerAdapter.ViewHolder) {
                    // get the removed item name to display it in snack bar
                    String name = pname.get(viewHolder.getAdapterPosition()).getmItemName();

                    // backup of removed item for undo purpose
                    final RecyclerViewModel deletedItem = pname.get(viewHolder.getAdapterPosition());
                    final int deletedIndex = viewHolder.getAdapterPosition();

                    // remove the item from recycler view
                    itemsAdapter.removeItem(viewHolder.getAdapterPosition());

                    // showing snack bar with Undo option
                    Snackbar snackbar = Snackbar
                            .make(getView(), name + " removed from cart!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // undo is selected, restore the deleted item
                            itemsAdapter.restoreItem(deletedItem, deletedIndex);
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }
            }*/


       /* ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(clv);
*/



        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
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

        CallServiceListAPI(doctorid);
    }


    private void CallServiceListAPI(String id) {

     //   Log.i("doc_id",doctorid);


       // http://nbayjobs.com/raksha/api/doctor/notification.php?id=2


        final ApiInterface service= ApiClient.getClient().create(ApiInterface.class);

        Call<DServiceslistModel> descriptionCall= service.DoctorServicelist(id);

        descriptionCall.enqueue(new Callback<DServiceslistModel>() {
            @Override
            public void onResponse(Call<DServiceslistModel> call, Response<DServiceslistModel> response) {

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

                        if(getActivity()!=null) {

                            itemsAdapter = new ClasicRecyclerAdapter(MainFragment.this.getActivity(), pname, null);

                            clv.setAdapter(itemsAdapter);

                            itemsAdapter.notifyDataSetChanged();

                            int resId = R.anim.layout_animation_fall_down;
                            int resIdright = R.anim.layout_animation_from_right;
                            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);


                            clv.setLayoutAnimation(animation);
                        }else {
                           // Toast.makeText(getActivity(), "null Activity", Toast.LENGTH_SHORT).show();
                        }

                    }else {

                        itemsAdapter = new ClasicRecyclerAdapter(MainFragment.this.getActivity(), pname, null);

                        clv.setAdapter(itemsAdapter);

                        itemsAdapter.notifyDataSetChanged();

                    }


                }else if(sucess.equals("0")){
                    hidepDialog();

                    error=response.body().getErrorMsg();

                    Log.i("error",error);



                    pname.clear();


                    itemsAdapter.notifyDataSetChanged();





                }


               // itemsAdapter = new ClasicRecyclerAdapter(MainFragment.this.getActivity(), pname, null);

               // clv.setAdapter(itemsAdapter);

            }

            @Override
            public void onFailure(Call<DServiceslistModel> call, Throwable t) {



            }
        });








    }

    private void runLayoutAnimation ( final RecyclerView recyclerView){
            final Context context = recyclerView.getContext();
            final LayoutAnimationController controller =
                    AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_right);

            recyclerView.setLayoutAnimation(controller);
            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();
        }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void checkligoin() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext(),R.style.CustomDialogTheme);
        builder.setTitle(Html.fromHtml("<font color='#dd0000'>Login!</font>"));
        builder.setMessage(Html.fromHtml("<font color='#000000'>You must login to continue</font>"))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {


                        //  startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),11);

                        startActivity(new Intent(getActivity(),PhyLoginActivity.class));


                        dialog.cancel();

                        getActivity().finishAffinity();
                    }
                });
        // .setNegativeButton("No", null);
        final android.app.AlertDialog alert = builder.create();

       // alert.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;

        alert.getWindow().getAttributes().windowAnimations = R.style.dialog_animation2;
        //alert.show();
        alert.show();
    }



  /*  @Override
    public void onItemClick(DServiceslistModel.Src pName, int position) {


        creatServicedialog(pName.getPatientName(),pName.getService(),pName.getDate(),pName.getTime(),pName.getAddress(),pName.getPhid(),pName.getAid());


    }


    public void creatServicedialog(String n,String s,String d,String t,String a,int phid,int aid)
    {

        pdialog.setContentView(R.layout.activity_patient_details);

        final String pjid,ajid;

        pjid=String.valueOf(phid);
        ajid=String.valueOf(aid);

        ImageView close;
        TextView accept,deny,name,services,date,time,address;

        accept=pdialog.findViewById(R.id.patient_accpet);
        deny=pdialog.findViewById(R.id.patient_deny);
        close=pdialog.findViewById(R.id.imgclose);
        name=pdialog.findViewById(R.id.Id_ser_name);
        services=pdialog.findViewById(R.id.Id_ser_serv);
        date=pdialog.findViewById(R.id.Id_ser_date);
        time=pdialog.findViewById(R.id.Id_ser_time);
        address=pdialog.findViewById(R.id.Id_ser_addres);


        name.setText(n);
        services.setText(s);
        date.setText(d);
        time.setText(t);
        address.setText(a);




        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainFragment.this.getActivity(),PhysiotherapiestActivity.class);
                i.putExtra("phid",pjid);
                i.putExtra("ahid",ajid);
                startActivity(i);
                // finish();
                pdialog.dismiss();
            }
        });

        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallPatientDetailsAPI(pjid,ajid,"0");

                // pdialog.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdialog.dismiss();
            }
        });

        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        pdialog.show();

       *//* AlertDialog.Builder alrt= new AlertDialog.Builder(context);
        LayoutInflater inflater=context.getApplicationContext().getLayoutInflater();


                *//**//*LayoutInflater inflater = (LayoutInflater) this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);*//**//*
        View dialoglayout = inflater.inflate(R.layout.calendar_layout, null);

        alrt.setView(dialoglayout);

        final CalendarPickerView calendar_view = (CalendarPickerView) dialoglayout.findViewById(R.id.calendar_view);
        Button btn_show_dates = (Button) dialoglayout.findViewById(R.id.btn_show_dates);

        final AlertDialog dialog = alrt.create();
*//*
    }


    private void
    CallPatientDetailsAPI(final String phhid, final String ahhid, final String status) {

        // http://nbayjobs.com/raksha/api/doctor/notification_single.php?phid=1&aid=152


        //   url = "http://httpbin.org/post";
        RequestQueue queue= Volley.newRequestQueue(MainFragment.this.getActivity());


        String url= APIConfig.MAIN_API+APIConfig.Doctor_Notification;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        // hidepDialog();

                        String sucess,msg,service,date,time,address,patient;

                        try {
                            JSONObject object=new JSONObject(response);

                            sucess=object.getString("success");

                            if(sucess.equals("1")){

                                pdialog.dismiss();

                               itemsAdapter.notifyDataSetChanged();

                                service=object.getString("service");
                                date=object.getString("date");
                                time=object.getString("time");
                                address=object.getString("address");
                                patient=object.getString("patient_name");

*//*

                                names.setText(patient);
                                addresss.setText(address);
                                dates.setText(date);
                                times.setText(time);
                                services.setText(service);
*//*


                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener()
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
                params.put("phid",phhid );
                params.put("aid", ahhid);
                params.put("status", status);

                return params;
            }
        };
        queue.add(postRequest);

        postRequest.setRetryPolicy(new DefaultRetryPolicy(500000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }


*/


}