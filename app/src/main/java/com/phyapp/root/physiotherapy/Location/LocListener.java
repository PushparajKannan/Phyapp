package com.phyapp.root.physiotherapy.Location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

//import static androidx.constraintlayout.Constraints.TAG;

public class LocListener implements LocationListener
{
    private static double lat =0.0;
    private static double lon = 0.0;
    private static double alt = 0.0;
    private static double speed = 0.0;

    public static double getLat()
    {
        return lat;
    }

    public static double getLon()
    {
        return lon;
    }

    public static double getAlt()
    {
        return alt;
    }

    public static double getSpeed()
    {
        return speed;
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
        alt = location.getAltitude();
        speed = location.getSpeed();

      /* // Toast.makeText(
           //     Context(),
              //  "Location changed: Lat: " + location.getLatitude() + " Lng: "
                   //     + location.getLongitude(), Toast.LENGTH_SHORT).show();
       // Toast.makeText(, "", Toast.LENGTH_SHORT).show();
        String longitude = "Longitude: " + location.getLongitude();
        Log.v(TAG, longitude);
        String latitude = "Latitude: " + location.getLatitude();
        Log.v(TAG, latitude);

        *//*------- To get city name from coordinates -------- *//*
        String cityName = null;
      //  Geocoder gcd = new Geocoder(, Locale.getDefault());
        List<Address> addresses;
        try {
           // addresses = gcd.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
                + cityName;
      //  editLocation.setText(s);
    }
*/
    }
    @Override
    public void onProviderDisabled(String provider) {}
    @Override
    public void onProviderEnabled(String provider) {}
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}