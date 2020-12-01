package com.phyapp.root.physiotherapy.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.phyapp.root.physiotherapy.APICONFIG.APIConfig;
import com.phyapp.root.physiotherapy.ModelClass.DServiceslistModel;
import com.phyapp.root.physiotherapy.PhyMainActivity;
import com.phyapp.root.physiotherapy.PhysiotherapiestActivity;
import com.phyapp.root.physiotherapy.R;

import java.util.ArrayList;


import androidx.transition.TransitionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ClasicRecyclerAdapter  extends RecyclerView.Adapter<ClasicRecyclerAdapter.ViewHolder> implements LocationListener {
        private static final String LOG_TAG = ClasicRecyclerAdapter.class.getSimpleName();
   // private final LinearLayout llExpandArea;
    private ArrayList<DServiceslistModel.Src> mItems;
    ClasicRecyclerAdapter.ItemListener mListener;
    int animID= R.anim.layout_animation_from_right;
        Context contexts;
        public static int position;
        private int expandedPosition = -1;
        private int mExpandedPosition = -1;
    private int lastPosition = -1;
    Dialog pdialog;

    String Latitude,Longtitude;
    private LocationManager mLocationManager;


    public  ClasicRecyclerAdapter(Activity context, ArrayList<DServiceslistModel.Src> program,ClasicRecyclerAdapter.ItemListener listener) {

            this.contexts = context;
            mItems = program;
            mListener = listener;
            pdialog=new Dialog(contexts,R.style.CustomDialogAnimationss);

       // Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
        }

        public void setOnItemClickListener(ClasicRecyclerAdapter.ItemListener listener) {
            mListener = listener;
        }

        @Override
        public ClasicRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.clasic_list, parent, false);
            contexts = parent.getContext();
            return new ClasicRecyclerAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ClasicRecyclerAdapter.ViewHolder holder, final int position) {
            holder.setData(mItems.get(position));

            setAnimation(holder.itemView, position);



            final String name = holder.pName.getMessage().toString();

            // holder.name.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_keyboard_arrow_up,0);






/*

        if (position == expandedPosition) {
            holder.llExpandArea.setVisibility(View.VISIBLE);
        } else {
            holder.llExpandArea.setVisibility(View.GONE);
        }

*//*
            final String name = holder.pName.getmItemName().toString();



            final boolean isExpanded = position==mExpandedPosition;
            holder.llExpandArea.setVisibility(isExpanded?View.VISIBLE:View.GONE);
            holder.itemView.setActivated(isExpanded);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                //  public android.view.ViewGroup recyclerView;

                @Override
                public void onClick(View v) {
                    mExpandedPosition = isExpanded ? -1:position;
                    TransitionManager.beginDelayedTransition(holder.llExpandArea);
                    notifyDataSetChanged();
                    Toast.makeText(context, ""+holder.pName.getmItemName(), Toast.LENGTH_SHORT).show();

                }
            });


            holder.Appoinment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context,AppointmentActivity.class);
                    i.putExtra("nameoftheropy",name);
                    context.startActivity(i);
                    Toast.makeText(context, "Appoinment", Toast.LENGTH_SHORT).show();
                }
            });

*/




       /* holder.Itemone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "hi"+holder.pName.getmItemName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.Itemtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "how r u", Toast.LENGTH_SHORT).show();
            }
        });
        holder.tilldate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "till", Toast.LENGTH_SHORT).show();
            }
        });

        holder.Appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,AppointmentActivity.class);
                i.putExtra("nameoftheropy",name);
                context.startActivity(i);
                Toast.makeText(context, "Appoinment", Toast.LENGTH_SHORT).show();
            }
        });*/


        }

   /* @Override
    public void onClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        RecyclerViewModel theString = mItems.get(holder.getPosition());

        // Check for an expanded view, collapse if you find one
        if (expandedPosition >= 0) {
            int prev = expandedPosition;
            notifyItemChanged(prev);
        }
        // Set the current position to "expanded"
        expandedPosition = holder.getPosition();
        notifyItemChanged(expandedPosition);

      //  Toast.makeText(mContext, "Clicked: "+theString, Toast.LENGTH_SHORT).show();
    }*/


        @Override
        public int getItemCount() {
            if (mItems != null) {
                return mItems.size();
            }
            return 0;
        }

    @Override
    public void onLocationChanged(Location location) {

            Latitude= String.valueOf(location.getLatitude());
            Longtitude= String.valueOf(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {



    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
          //  private final LinearLayout llExpandArea;
            public CardView cardView;
            public DServiceslistModel.Src pName;
            TextView therapyname,patientname,textservices,textarea,textservicessub;
            View textContainer;
            LinearLayout llExpandArea;
           // public RelativeLayout viewBackground, viewForeground;

            //TextView Itemone,Itemtwo,Appointment,tilldate;
            Button Appoinment;


            public ViewHolder(View itemView) {

                super(itemView);
                itemView.setOnClickListener(this);
                cardView = (CardView) itemView.findViewById(R.id.cvItem);
                patientname = (TextView) itemView.findViewById(R.id.list_text);
                textservices = (TextView) itemView.findViewById(R.id.doct_services);
                textservicessub = (TextView) itemView.findViewById(R.id.doct_sub_services);
                textarea = (TextView) itemView.findViewById(R.id.doct_area);
                textContainer = itemView.findViewById(R.id.text_container);
               // patientname =(TextView) itemView.findViewById(R.id.list_textss);

              //  viewBackground = itemView.findViewById(R.id.view_background);
               // viewForeground = itemView.findViewById(R.id.view_foreground);



              //  llExpandArea = (LinearLayout) itemView.findViewById(R.id.llExpandArea);
           /* Itemone=(TextView) itemView.findViewById(R.id.itone);
            Itemtwo=(TextView) itemView.findViewById(R.id.ittwo);
            Appointment=(TextView) itemView.findViewById(R.id.ittwo1);
            tilldate=(TextView) itemView.findViewById(R.id.itone1);*/

               // Appoinment = (Button) itemView.findViewById(R.id.appoinment);


            }

            public void setData(DServiceslistModel.Src pName) {
                this.pName = pName;

                patientname.setText(pName.getPatientName());
                textservicessub.setText(pName.getService());
                textservices.setText(pName.getServicecat());
                textarea.setText(pName.getAddress());


            }

            @Override
            public void onClick(View v) {


                if (mListener != null) {


               /* if (expandedPosition >= 0) {
                    int prev = expandedPosition;
                    notifyItemChanged(prev);
                }
                // Set the current position to "expanded"
                expandedPosition = getAdapterPosition();
                notifyItemChanged(expandedPosition);*/
                    mListener.onItemClick(pName, getAdapterPosition());

                }


              //  Toast.makeText(context, pName.getmItemName(), Toast.LENGTH_SHORT).show();

              //  Intent i=new Intent(context, PatientDetailsActivity.class);

              //  context.startActivity(i);

                creatServicedialog(pName.getPatientName(),pName.getServicecat(),pName.getDate(),pName.getTime()
                ,pName.getAddress(),pName.getPhid(),pName.getAid());



            }

           /* @Override
            public void preAnimateAddImpl(RecyclerView.ViewHolder holder) {

            }

            @Override
            public void preAnimateRemoveImpl(RecyclerView.ViewHolder holder) {

            }

            @Override
            public void animateAddImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {

                ViewCompat.animate(itemView)
                        .translationY(0)
                        .alpha(1)
                        .setDuration(300)
                        .setListener(listener)
                        .start();

            }

            @Override
            public void animateRemoveImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {

            }
        }*/
        }

        public interface ItemListener {
            void onItemClick(DServiceslistModel.Src pName, int position);
        }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(contexts, R.anim.item_animation_from_right);
            viewToAnimate.startAnimation(animation);
           // lastPosition = position;
        }
    }


    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    public void removeItem(int position) {
        mItems.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(DServiceslistModel.Src item, int position) {
        mItems.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }


    public void creatServicedialog(String n, String s, String d, String t, String a, final int phid, int aid)
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

                final LocationManager manager = (LocationManager) contexts.getSystemService( Context.LOCATION_SERVICE );

                if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    buildAlertMessageNoGps();
                }else
                    {
                        ((PhyMainActivity)contexts).showpDialog();


                        getLocation(pjid,ajid);


                  /*  Intent i = new Intent(contexts, PhysiotherapiestActivity.class);
                    i.putExtra("phid", pjid);
                    i.putExtra("ahid", ajid);
                    contexts.startActivity(i);
                    // finish();
                    pdialog.dismiss();*/
                }
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

      /*  AlertDialog.Builder alrt= new AlertDialog.Builder(context);
        LayoutInflater inflater=context.getApplicationContext().getLayoutInflater();


                LayoutInflater inflater = (LayoutInflater) this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialoglayout = inflater.inflate(R.layout.calendar_layout, null);

        alrt.setView(dialoglayout);

        final CalendarPickerView calendar_view = (CalendarPickerView) dialoglayout.findViewById(R.id.calendar_view);
        Button btn_show_dates = (Button) dialoglayout.findViewById(R.id.btn_show_dates);

        final AlertDialog dialog = alrt.create();*/

    }


    private void
    CallPatientDetailsAPI(final String phhid, final String ahhid, final String status) {

        // http://nbayjobs.com/raksha/api/doctor/notification_single.php?phid=1&aid=152


        //   url = "http://httpbin.org/post";
        RequestQueue queue= Volley.newRequestQueue(contexts);


        String url= APIConfig.MAIN_API+APIConfig.Doctor_Notification;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
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

                               // notifyDataSetChanged();


                                service=object.getString("service");
                                date=object.getString("date");
                                time=object.getString("time");
                                address=object.getString("address");
                                patient=object.getString("patient_name");


/*
                                names.setText(patient);
                                addresss.setText(address);
                                dates.setText(date);
                                times.setText(time);
                                services.setText(service);*/


                                ((PhyMainActivity)contexts).SetFragment();

                                Toast.makeText(contexts, "Rejected Successfully", Toast.LENGTH_SHORT).show();


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
                params.put("phid",phhid );
                params.put("aid", ahhid);
                params.put("status", status);

                return params;
            }
        };
        queue.add(postRequest);

        postRequest.setRetryPolicy(new DefaultRetryPolicy(500000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }




    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(contexts);
        builder.setMessage("Your GPS seems to be disabled, you must enable it to continue")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        contexts.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                        pdialog.dismiss();
                        dialog.cancel();
                    }
                });
               // .setNegativeButton("No", null);
        final AlertDialog alert = builder.create();
        alert.show();
    }


    void getLocation(String Pjid,String Ajid) {
        try {
            mLocationManager = (LocationManager) contexts.getSystemService(Context.LOCATION_SERVICE);
            // mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, (LocationListener) this);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 0, this);

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, this);


            // mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            //mLocationManager.getLastKnownLocation()


            callLatLong(Pjid,Ajid);

          /*  if(longititude==null && longititude==null){

                Toast.makeText(this, "null location", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {



                        Toast.makeText(MainActivity.this, ""+latitude+longititude, Toast.LENGTH_SHORT).show();


                    }
                },3000);

            }else {
                Toast.makeText(this, ""+latitude+longititude, Toast.LENGTH_SHORT).show();
            }
*/


            //  gettiocationss(userID);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void callLatLong(final String pjid, final String ajid) {

        if (Latitude == null && Longtitude == null) {

            // Toast.makeText(this, "null location", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    //   Toast.makeText(MainActivity.this, ""+latitude+longititude, Toast.LENGTH_SHORT).show();

                    callLatLong(pjid,ajid);

                }
            }, 3000);

        } else {
            //Toast.makeText(this, ""+latitude+longititude, Toast.LENGTH_SHORT).show();

            // showpDialog();

            ((PhyMainActivity)contexts).hidepDialog();

            Intent i = new Intent(contexts, PhysiotherapiestActivity.class);
            i.putExtra("phid", pjid);
            i.putExtra("ahid", ajid);
            i.putExtra("lat", Latitude);
            i.putExtra("long", Longtitude);
            contexts.startActivity(i);
            // finish();
            pdialog.dismiss();

        }
    }



}




