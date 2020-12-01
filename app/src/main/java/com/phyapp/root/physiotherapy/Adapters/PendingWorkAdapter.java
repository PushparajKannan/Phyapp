package com.phyapp.root.physiotherapy.Adapters;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import com.phyapp.root.physiotherapy.ModelClass.PendingworkModel;
import com.phyapp.root.physiotherapy.PhyMainActivity;
import com.phyapp.root.physiotherapy.PhysiotherapiestActivity;
import com.phyapp.root.physiotherapy.R;

import java.util.ArrayList;

//public class PendingWorkAdapter {






    public class PendingWorkAdapter  extends RecyclerView.Adapter<com.phyapp.root.physiotherapy.Adapters.PendingWorkAdapter.ViewHolder> implements LocationListener {
        private static final String LOG_TAG = com.phyapp.root.physiotherapy.Adapters.PendingWorkAdapter.class.getSimpleName();
        // private final LinearLayout llExpandArea;
        private ArrayList<PendingworkModel.Src> mItems;
        com.phyapp.root.physiotherapy.Adapters.PendingWorkAdapter.ItemListener mListener;
        int animID= R.anim.layout_animation_from_right;
        Context context;
        public static int position;
        private int expandedPosition = -1;
        private int mExpandedPosition = -1;
        private int lastPosition = -1;

        final String TAG = "GPssS";
        private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
        private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
        LocationManager locationManager;
        boolean isGPS = false;
        boolean isNetwork = false;
        boolean canGetLocation = true;
        Location loc;

        String Latitude,Longtitude;


        public  PendingWorkAdapter(Activity context, ArrayList<PendingworkModel.Src> program, com.phyapp.root.physiotherapy.Adapters.PendingWorkAdapter.ItemListener listener) {

            this.context = context;
            mItems = program;
            mListener = listener;

            locationManager = (LocationManager) this.context.getSystemService(Service.LOCATION_SERVICE);
            isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        }

        public void setOnItemClickListener(com.phyapp.root.physiotherapy.Adapters.PendingWorkAdapter.ItemListener listener) {
            mListener = listener;
        }

        @Override
        public com.phyapp.root.physiotherapy.Adapters.PendingWorkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.pending_worksss, parent, false);
            context = parent.getContext();
            return new com.phyapp.root.physiotherapy.Adapters.PendingWorkAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final com.phyapp.root.physiotherapy.Adapters.PendingWorkAdapter.ViewHolder holder, final int position) {

            // setAnimation(holder.itemView, position);

            holder.setData(mItems.get(position));

            setAnimation(holder.itemView, position);


            holder.calladmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    if(CallGpsISON()) {

                        ((PhyMainActivity) context).showpDialog();

                        callLatLong(String.valueOf(holder.pName.getPhid()), String.valueOf(holder.pName.getAid()));
                    }else {
                        Toast.makeText(context, "You must enable GPS to continue", Toast.LENGTH_SHORT).show();
                    }


                }
            });





        }


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
            public PendingworkModel.Src pName;
            TextView therapyname,patientname,therapytypename,date,time,doctor,dotctnum,therapytypenamesub;
            View textContainer;
            TextView rupess,calladmin,GetDirection,GetAddress;
            LinearLayout llExpandArea;
            // public RelativeLayout viewBackground, viewForeground;

            //TextView Itemone,Itemtwo,Appointment,tilldate;
            Button Appoinment;


            public ViewHolder(View itemView) {

                super(itemView);
                itemView.setOnClickListener(this);
                cardView = (CardView) itemView.findViewById(R.id.cvItem);
              //  therapyname = (TextView) itemView.findViewById(R.id.noti_service_name);
                // = (TextView) itemView.findViewById(R.id.noti_service_name);
                therapytypenamesub = (TextView) itemView.findViewById(R.id.noti_service_type);
                therapytypename = (TextView) itemView.findViewById(R.id.noti_service_name);
                date = (TextView) itemView.findViewById(R.id.noti_service_date);
                time = (TextView) itemView.findViewById(R.id.noti_service_time);
                doctor = (TextView) itemView.findViewById(R.id.list_text);
                textContainer = itemView.findViewById(R.id.text_container);
               // patientname =(TextView) itemView.findViewById(R.id.list_text);
               // dotctnum =(TextView) itemView.findViewById(R.id.doctor_num);
                rupess=(TextView) itemView.findViewById(R.id.rupess);
                calladmin=(TextView) itemView.findViewById(R.id.calladmin);
                llExpandArea=(LinearLayout) itemView.findViewById(R.id.get_direction_phy_ll);
                GetDirection=(TextView) itemView.findViewById(R.id.get_direction_phy);
                GetAddress=(TextView) itemView.findViewById(R.id.noti_service_address);

                //  viewBackground = itemView.findViewById(R.id.view_background);
                // viewForeground = itemView.findViewById(R.id.view_foreground);



                //  llExpandArea = (LinearLayout) itemView.findViewById(R.id.llExpandArea);
           /* Itemone=(TextView) itemView.findViewById(R.id.itone);
            Itemtwo=(TextView) itemView.findViewById(R.id.ittwo);
            Appointment=(TextView) itemView.findViewById(R.id.ittwo1);
            tilldate=(TextView) itemView.findViewById(R.id.itone1);*/

                // Appoinment = (Button) itemView.findViewById(R.id.appoinment);


            }

            public void setData(PendingworkModel.Src pName) {
                this.pName = pName;


                if(pName.getService()==null)
                {

                    therapytypename.setVisibility(View.GONE);
                }else {

                    therapytypename.setVisibility(View.VISIBLE);

                }






                therapytypenamesub.setText(pName.getService());
                therapytypename.setText(pName.getServiceCat());
               // patientname.setText(pName.getPatientName());
                GetAddress.setText(pName.getAddress());
                doctor.setText(pName.getPatientName());
                date.setText(pName.getDate());
                time.setText(pName.getTime());
               // rupess.setText(pName.getChargeVisit());







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

        public interface ItemListener {
            void onItemClick(PendingworkModel.Src pName, int position);
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

        public void restoreItem(PendingworkModel.Src item, int position) {
            mItems.add(position, item);
            // notify item added by position
            notifyItemInserted(position);
        }



        private void callLatLong(final String pjid, final String ajid) {

            if (Latitude == null && Longtitude == null) {

                // Toast.makeText(this, "null location", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        //   Toast.makeText(MainActivity.this, ""+latitude+longititude, Toast.LENGTH_SHORT).show();


                      getLocation();
                        callLatLong(pjid,ajid);

                    }
                }, 3000);

            } else {
                //Toast.makeText(this, ""+latitude+longititude, Toast.LENGTH_SHORT).show();

                // showpDialog();

                ((PhyMainActivity)context).hidepDialog();

                Intent i = new Intent(context, PhysiotherapiestActivity.class);
                i.putExtra("phid", pjid);
                i.putExtra("ahid", ajid);
                i.putExtra("lat", Latitude);
                i.putExtra("long", Longtitude);
                i.putExtra("mpk","mpkv");
                context.startActivity(i);
                // finish();
               // pdialog.dismiss();

            }
        }



        private void getLocation() {
            try {
                if (canGetLocation) {
                    Log.d(TAG, "Can get location");
                    if (isGPS) {
                        // from GPS
                        Log.d(TAG, "GPS on");
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                        if (locationManager != null) {
                            loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (loc != null)
                                updateUI(loc);
                        }
                    } else if (isNetwork) {
                        // from Network Provider
                        Log.d(TAG, "NETWORK_PROVIDER on");
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                        if (locationManager != null) {
                            loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (loc != null)
                                updateUI(loc);
                        }
                    } else {
                        loc.setLatitude(0);
                        loc.setLongitude(0);
                        updateUI(loc);
                    }
                } else {
                    Log.d(TAG, "Can't get location");
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        private void updateUI(Location loc) {

            Latitude= String.valueOf(loc.getLatitude());
            Longtitude=String.valueOf(loc.getLongitude());
        }

        private boolean CallGpsISON() {


            final LocationManager manager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );

            if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
               return false;
            }else
            {
                return true;
            }
        }

/*
        private void buildAlertMessageNoGps() {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setMessage("Your GPS seems to be disabled, you must enable it to continue")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),11);

                            dialog.cancel();
                        }
                    });
            // .setNegativeButton("No", null);
            final android.app.AlertDialog alert = builder.create();
            alert.show();
        }*/


       /* public void showSettingsAlert() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("GPS is not Enabled!");
            alertDialog.setMessage("Do you want to turn on GPS?");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });

            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            alertDialog.show();
        }*/


    }


