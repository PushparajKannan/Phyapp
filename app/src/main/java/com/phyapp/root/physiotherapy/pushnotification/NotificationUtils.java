package com.phyapp.root.physiotherapy.pushnotification;

//public class NotificationUtils {

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import android.text.Html;

import com.phyapp.root.physiotherapy.MainActivity;
import com.phyapp.root.physiotherapy.ModelClass.NotificationVO;
import com.phyapp.root.physiotherapy.NotificationDetailsActivity;
import com.phyapp.root.physiotherapy.PhyMainActivity;
import com.phyapp.root.physiotherapy.R;
import com.phyapp.root.physiotherapy.ServiceActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class NotificationUtils {
    private static final int NOTIFICATION_ID = 200;
    private static final String PUSH_NOTIFICATION = "pushNotification";
    private static final String CHANNEL_ID = "myChannel";
    private static final String URL = "url";
    private static final String ACTIVITY = "activity";
    Map<String, Class> activityMap = new HashMap<>();
    private Context mContext;

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
        //Populate activity map
        activityMap.put("MainActivity", MainActivity.class);
        activityMap.put("SecondActivity", PhyMainActivity.class);
        activityMap.put("PatientNotification", NotificationDetailsActivity.class);
        activityMap.put("Serviceactivity", ServiceActivity.class);



    }

    /**
     * Displays notification based on parameters
     *
     * @param notificationVO
     * @param resultIntent
     */
    public void displayNotification(NotificationVO notificationVO, Intent resultIntent) {
        {


            // Get the layouts to use in the custom notification
            // RemoteViews notificationLayout = new RemoteViews(mContext.getPackageName(), R.layout.notification_small);
            // RemoteViews notificationLayoutExpanded = new RemoteViews(mContext.getPackageName(), R.layout.notification_large);

            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            String message = notificationVO.getMessage();
            String title = notificationVO.getTitle();
            String iconUrl = notificationVO.getIconUrl();
            String action = notificationVO.getAction();
            String destination = notificationVO.getActionDestination();
            Bitmap iconBitMap = null;
            if (iconUrl != null) {
                iconBitMap = getBitmapFromURL(iconUrl);
            }
            final int icon = R.drawable.appi;

            PendingIntent resultPendingIntent;

            if (URL.equals(action)) {

                Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(destination));
               // Intent notificationIntent = new Intent(mContext, MainActivity.class);

                resultPendingIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);

               /* Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(destination));

                resultPendingIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);*/
            } else if (ACTIVITY.equals(action) && activityMap.containsKey(destination)) {
                resultIntent = new Intent(mContext, activityMap.get(destination));

                resultPendingIntent = PendingIntent.getActivity(mContext, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            } else {
                resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                resultIntent.putExtra("check","login");
                resultPendingIntent = PendingIntent.getActivity(mContext, 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            }


            final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, CHANNEL_ID);

            Notification notification;

            if (iconBitMap == null) {
                //When Inbox Style is applied, user can expand the notification
                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();

              //  RemoteViews expandedView = new RemoteViews(mContext.getPackageName(), R.layout.view_expanded_notification);

                //inboxStyle.addLine(message);
                bigTextStyle.setSummaryText(message);
                notification = mBuilder.setSmallIcon(icon)
                        .setTicker(title)
                        .setWhen(System.currentTimeMillis())
                        .setAutoCancel(true)
                        .setContentTitle(title)
                        .setContentIntent(resultPendingIntent)
                        .setStyle(bigTextStyle)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(NotificationManager.IMPORTANCE_HIGH)
                        .setSmallIcon(R.drawable.ic_notifications_active_black_24dp).setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon)).setContentText(message).setSound(uri).build();

            } else {
                //If Bitmap is created from URL, show big icon
                NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
                bigPictureStyle.setBigContentTitle(title);
                bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
                bigPictureStyle.bigPicture(iconBitMap);
                notification = mBuilder.setSmallIcon(icon)
                        .setWhen(System.currentTimeMillis())
                        .setTicker(title).setWhen(0).setAutoCancel(true)
                        .setContentTitle(title)
                        .setContentIntent(resultPendingIntent)
                        .setStyle(bigPictureStyle)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(NotificationManager.IMPORTANCE_HIGH)
                        .setSmallIcon(R.drawable.ic_notifications_active_black_24dp).setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon)).setContentText(message).setSound(uri).build();
            }

            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                assert notificationManager != null;
                mBuilder.setChannelId(CHANNEL_ID);
                notificationManager.createNotificationChannel(notificationChannel);
            }
            assert notification != null;


            notificationManager.notify(NOTIFICATION_ID, notification);
            // builder.setSound(uri);

            // notification.defaults = Notification.DEFAULT_SOUND;
            // final String ringTone = "default ringtone"; // or store in preferences, and fallback to this
            // mBuilder.setSound(Uri.parse(ringTone));






        }
    }

    /**
     * Downloads push notification image before displaying it in
     * the notification tray
     *
     * @param strURL : URL of the notification Image
     * @return : BitMap representation of notification Image
     */
    private Bitmap getBitmapFromURL(String strURL) {


           /* try {
                java.net.URL url = new URL(strURL);
              //  URL url = new URL("http://....");
                Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                return image;
            } catch(IOException e) {
                System.out.println(e);
                e.printStackTrace();
                return null;
            }*/
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

        /**
         * Playing notification sound
         */
        /*public void playNotificationSound() {
          *//*  Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(mContext(), notification);
            r.play();*//*

            try {
                Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://" + mContext.getPackageName() + "/raw/notification");
              //  Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                Ringtone r = RingtoneManager.getRingtone(mContext, alarmSound);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/
//}
