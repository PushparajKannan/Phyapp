package com.phyapp.root.physiotherapy.BackgroundServices;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.phyapp.root.physiotherapy.APICONFIG.APIConfig;
import com.phyapp.root.physiotherapy.Utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//public class BackgroundAPI extends Service implements  {
    public class BackgroundAPI extends Service implements LocationListener {


    private boolean isGPS,isNetwork;

    public BackgroundAPI() {


    }


    int mStartMode;       // indicates how to behave if the service is killed
    IBinder mBinder;      // interface for clients that bind
    boolean mAllowRebind;
    SessionManager session;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    // indicates whether onRebind should be used

    String latitude, longititude, android_id, userID;
    private LocationManager mLocationManager;

    final String TAG = "GPS";
    String phid, ahid;

    boolean canGetLocation = true;
    Location loc;

    boolean checknetgpc=false;


    @Override
    public void onCreate() {
        // The service is being created

        //android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        session = new SessionManager(this);

        HashMap<String, String> user = session.getUserDetails();

        userID = user.get(session.KEY_USERID);




        mLocationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);


        isGPS = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetwork = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        //phid=


        // getLocation(userID,android_id);


        // Toast.makeText(this, "service created", Toast.LENGTH_SHORT).show();

        if (!isGPS && !isNetwork) {
            Log.d("con", "Connection off");
           // showSettingsAlert();
            getLastLocation();
        } else {
            Log.d("conn", "Connection on");
            // check permissions
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissionsToRequest.size() > 0) {
                    requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                            ALL_PERMISSIONS_RESULT);
                    Log.d("checkpermission", "Permission requests");
                    canGetLocation = false;
                }
            }*/

            // get location
            getLocation(userID);
        }
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()

        //  Toast.makeText(this, "service Started", Toast.LENGTH_SHORT).show();


        //phid=intent.getStringExtra("phid") ;
        //  ahid=intent.getStringExtra("ahid") ;


        //getLocation(userID);


        return mStartMode;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // A client is binding to the service with bindService()
        // Toast.makeText(this, "service onbind", Toast.LENGTH_SHORT).show();
        return mBinder;
    }


    @Override
    public boolean onUnbind(Intent intent) {
        // All clients have unbound with unbindService()
        // Toast.makeText(this, "service unbind", Toast.LENGTH_SHORT).show();
        return mAllowRebind;
    }

    @Override
    public void onRebind(Intent intent) {


        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called

        // Toast.makeText(this, "service rebind", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {

        // Toast.makeText(this, "Invoke background service onDestroy method.", Toast.LENGTH_LONG).show();
        super.onDestroy();


        // The service is no longer used and is being destroyed

        // Toast.makeText(this, "service Destroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {

        latitude = String.valueOf(location.getLatitude());
        longititude = String.valueOf(location.getLongitude());

      //  Log.i("location background", String.valueOf(location.getLatitude()) + " " + String.valueOf(location.getLongitude()));

        // Toast.makeText(this, String.valueOf(location.getLongitude()), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

       /* Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);


        getLastLocation();
*/


        if (mLocationManager != null) {
            mLocationManager.removeUpdates(this);
        }





    }


   /* void getLocation(String userid) {
        try {
            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            // mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, (LocationListener) this);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 0, this);

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, this);


            // mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            //mLocationManager.getLastKnownLocation()


            callLatLong(userid);

          *//*  if(longititude==null && longititude==null){

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
*//*


            //  gettiocationss(userID);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }*/

    private void callLatLong(final String userIDs) {

        if (longititude == null && longititude == null) {

            // Toast.makeText(this, "null location", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    //   Toast.makeText(MainActivity.this, ""+latitude+longititude, Toast.LENGTH_SHORT).show();

                    callLatLong(userIDs);

                }
            }, 3000);

        } else {
            //Toast.makeText(this, ""+latitude+longititude, Toast.LENGTH_SHORT).show();

            // showpDialog();

            getDriverdedatils(latitude, longititude, userIDs);


        }
    }


    private void getDriverdedatils(final String latitude, final String longititude, final String userID) {


        // http://www.nbayjobs.com/raksha/api/doctor/doctorlatlong.php?phid=2&lat=1&long=1
        String url = APIConfig.MAIN_API + APIConfig.Doctor_Location;
// http://nbayjobs.com/leo-agrinew/driverlocation.php?uid=11&latitude=9.91625&longtitude=-81.578934&device=123

       /* Log.i("username", username);
        Log.i("password", password);
        Log.i("androidid", androidid);*/

        Log.i(" background API :", "hit");
        //   Log.i(" background phid :", phid);
        // Log.i(" background ahid :", ahid);


        RequestQueue queue = Volley.newRequestQueue(this);

        // Toast.makeText(this, "API hit", Toast.LENGTH_SHORT).show();


        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response
                Log.d("Response", response);


                // hidepDialog();

                String sucess, msg, userid;

                try {
                    JSONObject object = new JSONObject(response);

                    sucess = object.getString("success");


                    if (sucess.equals("1")) {
                        //hidepDialog();

                        // userid = object.getString("User_Id");

                        runcircle(userID);


                    } else if (sucess.equals("0")) {
                        // hidepDialog();
                        runcircle(userID);
                        // msg = object.getString("error_msg");
                        ///  Toast.makeText(Backgroundservices.this, "", Toast.LENGTH_SHORT).show();
                        //  Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        //  Toast.makeText(LoginActivity.this,"Invalid Username Password" , Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                Log.d("Error.Response", error.toString());
                // hidepDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("lat", latitude);
                params.put("long", longititude);
                params.put("phid", userID);


                return params;
            }
        };
        queue.add(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }

    private void runcircle(final String userID) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getLocation(userID);
                // gettiocationss(userID);

                // runcircle(userID);

                //callLatLong(userID, android_id);

            }
        }, 60000);


    }


    private void getLastLocation() {
        try {
            Criteria criteria = new Criteria();
            String provider = mLocationManager.getBestProvider(criteria, false);
            Location location = mLocationManager.getLastKnownLocation(provider);
            Log.d("tag", provider);
            Log.d("tag", location == null ? "NO LastLocation" : location.toString());
        } catch (SecurityException e) {
            e.printStackTrace();
        }


    }


    private void getLocation(String userid) {
        try {
            if (canGetLocation) {
                Log.d(TAG, "Can get location");
                if (isGPS) {
                    // from GPS
                    Log.d(TAG, "GPS on");
                    mLocationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    checknetgpc=true;

                    if (mLocationManager != null) {
                        loc = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else if (isNetwork) {
                    // from Network Provider
                    Log.d(TAG, "NETWORK_PROVIDER on");
                    mLocationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    checknetgpc=true;

                    if (mLocationManager != null) {
                        loc = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else {
                    loc.setLatitude(0);
                    loc.setLongitude(0);
                    updateUI(loc);
                }

                if(checknetgpc){

                    callLatLong(userid);

                }


            } else {
                Log.d(TAG, "Can't get location");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }


    }

    private void updateUI(Location loc) {

        latitude = String.valueOf(loc.getLatitude());
        longititude = String.valueOf(loc.getLongitude());


    }

    public void showSettingsAlert() {
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
    }

}

