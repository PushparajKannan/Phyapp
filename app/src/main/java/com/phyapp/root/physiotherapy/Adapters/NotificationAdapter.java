package com.phyapp.root.physiotherapy.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.phyapp.root.physiotherapy.APICONFIG.APIConfig;
import com.phyapp.root.physiotherapy.ModelClass.NotificationListModel;
import com.phyapp.root.physiotherapy.NotificationActivity;
import com.phyapp.root.physiotherapy.R;

import java.util.ArrayList;

//public class NotificationAdapter {

   // package com.example.root.physiotherapy.Adapters;

import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;


import androidx.transition.TransitionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;


public class NotificationAdapter  extends RecyclerView.Adapter<com.phyapp.root.physiotherapy.Adapters.NotificationAdapter.ViewHolder> {
        private static final String LOG_TAG = com.phyapp.root.physiotherapy.Adapters.NotificationAdapter.class.getSimpleName();
    private final AlertDialog pDialog;
    // private final LinearLayout llExpandArea;
        private ArrayList<NotificationListModel.Src> mItems;
        com.phyapp.root.physiotherapy.Adapters.NotificationAdapter.ItemListener mListener;
        int animID= R.anim.layout_animation_from_right;
        Context context;
        public static int position;
        private int expandedPosition = -1;
        private int mExpandedPosition = -1;
        private int lastPosition = -1;


        public  NotificationAdapter(Activity context, ArrayList<NotificationListModel.Src> program, com.phyapp.root.physiotherapy.Adapters.NotificationAdapter.ItemListener listener) {

            this.context = context;
            mItems = program;
            mListener = listener;

            pDialog=new SpotsDialog(this.context,R.style.Custom);
            pDialog.setCancelable(false);


        }

        public void setOnItemClickListener(com.phyapp.root.physiotherapy.Adapters.NotificationAdapter.ItemListener listener) {
            mListener = listener;
        }

        @Override
        public com.phyapp.root.physiotherapy.Adapters.NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.listnotifications, parent, false);
            context = parent.getContext();
            return new com.phyapp.root.physiotherapy.Adapters.NotificationAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final com.phyapp.root.physiotherapy.Adapters.NotificationAdapter.ViewHolder holder, final int position) {

           // setAnimation(holder.itemView, position);

            holder.setData(mItems.get(position));

            setAnimation(holder.itemView, position);


            holder.calladmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String adminNumber=holder.pName.getAdminMobile().toString();

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+adminNumber));
                    context.startActivity(intent);

                }
            });


            holder.GetDirection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  showpDialog();

                    callApiLoctaion(String.valueOf(holder.pName.getPhid()),String.valueOf(holder.pName.getAid()));



                }
            });



            //final String name = holder.pName.getAssignedPhysiotherapist().toString();

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

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            //  private final LinearLayout llExpandArea;
            public CardView cardView;
            public NotificationListModel.Src pName;
            TextView therapyname,patientname,therapytypename,date,time,doctor,dotctnum;
            View textContainer;
            TextView rupess,calladmin,GetDirection;
            LinearLayout llExpandArea;
            // public RelativeLayout viewBackground, viewForeground;

            //TextView Itemone,Itemtwo,Appointment,tilldate;
            Button Appoinment;


            public ViewHolder(View itemView) {

                super(itemView);
                itemView.setOnClickListener(this);
                cardView = (CardView) itemView.findViewById(R.id.cvItem);
                therapyname = (TextView) itemView.findViewById(R.id.noti_service_name);
                // = (TextView) itemView.findViewById(R.id.noti_service_name);
                therapytypename = (TextView) itemView.findViewById(R.id.noti_service_type);
                date = (TextView) itemView.findViewById(R.id.noti_service_date);
                time = (TextView) itemView.findViewById(R.id.noti_service_time);
                doctor = (TextView) itemView.findViewById(R.id.Doctor);
                textContainer = itemView.findViewById(R.id.text_container);
                patientname =(TextView) itemView.findViewById(R.id.list_text);
                dotctnum =(TextView) itemView.findViewById(R.id.doctor_num);
                rupess=(TextView) itemView.findViewById(R.id.rupess);
                calladmin=(TextView) itemView.findViewById(R.id.calladmin);
                llExpandArea=(LinearLayout) itemView.findViewById(R.id.get_direction_phy_ll);
                GetDirection=(TextView) itemView.findViewById(R.id.get_direction_phy);

                //  viewBackground = itemView.findViewById(R.id.view_background);
                // viewForeground = itemView.findViewById(R.id.view_foreground);



                //  llExpandArea = (LinearLayout) itemView.findViewById(R.id.llExpandArea);
           /* Itemone=(TextView) itemView.findViewById(R.id.itone);
            Itemtwo=(TextView) itemView.findViewById(R.id.ittwo);
            Appointment=(TextView) itemView.findViewById(R.id.ittwo1);
            tilldate=(TextView) itemView.findViewById(R.id.itone1);*/

                // Appoinment = (Button) itemView.findViewById(R.id.appoinment);


            }

            public void setData(NotificationListModel.Src pName) {
                this.pName = pName;


                if(pName.getIid()==null || pName.getIid().isEmpty())
                {
                    therapytypename.setVisibility(View.GONE);
                }
                else
                    {
                    if (therapytypename.getVisibility()==View.GONE){

                        therapytypename.setVisibility(View.VISIBLE);

                    }
                    therapytypename.setText(pName.getIid());
                }


                if(pName.getAssignedPhysiotherapist()==null||pName.getAssignedPhysiotherapist().isEmpty()){

                    llExpandArea.setVisibility(View.GONE);
                    patientname.setText("Physiotherapist assigned soon");


                }else {
                    llExpandArea.setVisibility(View.VISIBLE);

                    patientname.setText(pName.getAssignedPhysiotherapist());

                  //  callApiLoctaion(String.valueOf(pName.getPhid()),String.valueOf(pName.getAid()));
                }


                therapyname.setText(pName.getSid());
               // patientname.setText(pName.getAssignedPhysiotherapist());
                dotctnum.setText(pName.getAdminMobile());
                doctor.setText(pName.getAssignedPhysiotherapist());
                date.setText(pName.getAppointmentDate());
                time.setText(pName.getAppointmentTime());
                rupess.setText(pName.getChargeVisit());







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


              //  Toast.makeText(context, pName.getStatus(), Toast.LENGTH_SHORT).show();

              //  Intent i=new Intent(context, NotificationDetailsActivity.class);

              //  context.startActivity(i);




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

        private void callApiLoctaion(final String pid, final String aid) {

           // http://www.nbayjobs.com/raksha/api/patient/doctorgeo.php?phid=1&aid=5

            RequestQueue queue= Volley.newRequestQueue(context);

            String url= APIConfig.MAIN_API+APIConfig.Doctorgoelocation;

            //url = "http://httpbin.org/post";
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);

                            hidepDialog();

                            String sucess,msg,doctorlati,doctorlongi,patientlati,patientlongi;

                            try {
                                JSONObject obj=new JSONObject(response);

                                sucess=obj.getString("success");

                                if(sucess.equals("1")){

                                    doctorlati=obj.getString("doc_latitude");
                                    doctorlongi=obj.getString("doc_longitude");
                                    patientlati=obj.getString("patient_latitude");
                                    patientlongi=obj.getString("patient_longitude");

                                    Intent i=new Intent(context, NotificationActivity.class);
                                    i.putExtra("dlati",doctorlati);
                                    i.putExtra("dlongi",doctorlongi);
                                    i.putExtra("plati",patientlati);
                                    i.putExtra("plongi",patientlongi);
                                    i.putExtra("aid",aid);
                                    i.putExtra("pid",pid);
                                    context.startActivity(i);


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
                            hidepDialog();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("phid",pid );
                    params.put("aid",aid );

                    return params;
                }
            };
            queue.add(postRequest);
            postRequest.setRetryPolicy(new DefaultRetryPolicy(500000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    public interface ItemListener {
            void onItemClick(NotificationListModel.Src pName, int position);
        }

        private void setAnimation(View viewToAnimate, int position)
        {
            // If the bound view wasn't previously displayed on screen, it's animated
            //if (position > lastPosition)
            //{
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_from_right);
                viewToAnimate.startAnimation(animation);

                 //lastPosition = position;
           // }
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

        public void restoreItem(NotificationListModel.Src item, int position) {
            mItems.add(position, item);
            // notify item added by position
            notifyItemInserted(position);
        }












}
