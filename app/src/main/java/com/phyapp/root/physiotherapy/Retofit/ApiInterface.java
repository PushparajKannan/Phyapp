package com.phyapp.root.physiotherapy.Retofit;

//public class ApiInterface {
   // import info.androidhive.retrofit.model.MoviesResponse;
import com.phyapp.root.physiotherapy.ModelClass.DServiceslistModel;
import com.phyapp.root.physiotherapy.ModelClass.DescriptionProductModel;
import com.phyapp.root.physiotherapy.ModelClass.Login;
import com.phyapp.root.physiotherapy.ModelClass.MenuCategoryModel;
import com.phyapp.root.physiotherapy.ModelClass.MenuSubListModel;
import com.phyapp.root.physiotherapy.ModelClass.NotificationListModel;
import com.phyapp.root.physiotherapy.ModelClass.PatientHistoryModel;
import com.phyapp.root.physiotherapy.ModelClass.PendingworkModel;
import com.phyapp.root.physiotherapy.ModelClass.PhyHistoryModel;
import com.phyapp.root.physiotherapy.ModelClass.ProfileShowModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
public interface ApiInterface {

   /* @GET("api/{mobile}/{device_id}")
    Call<Login> authenticate(@Path("mobile") String mobile, @Path("device_id") String device_id);

    @POST("api/{mobile}/{device_id}")
    Call<Login> registration(@Path("mobile") String email, @Path("device_id") String password);
*/


   /* @POST("sub_url")
    Call post(@Body DescriptionProductModel requestmodel);*/

   // http://nbayjobs.com/raksha/api/product.php?temp=2&lang=English
    @FormUrlEncoded
    @POST("product.php?")
    Call<DescriptionProductModel> post(
            @Field("temp") String temp,
            @Field("lang") String lang);



    //http://nbayjobs.com/raksha/api/submenu.php?id=1&mid=1&lang=English
    @FormUrlEncoded
    @POST("submenu.php?")
    Call<MenuCategoryModel> menulist(
            @Field("id") String temp,
            @Field("mid") String mid,
            @Field("lang") String lang);



    @FormUrlEncoded
    @POST("innermenu.php?")
    Call<MenuSubListModel> menusublist(
            @Field("id") String temp,
            @Field("mid") String mid,
            @Field("sid") String sid,
            @Field("lang") String lang);



     @FormUrlEncoded
    @POST("profileshow.php?")
    Call<ProfileShowModel> Profilelist(
            @Field("pid") String temp);


 @FormUrlEncoded
 @POST("notification.php?")
 Call<NotificationListModel> Notificationlist(
         @Field("pid") String temp,
         @Field("status") String status,
         @Field("lang") String Languages);

 @FormUrlEncoded
 @POST("doctor/notification.php?")
 Call<DServiceslistModel> DoctorServicelist(
         @Field("id") String temp);


 @FormUrlEncoded
 @POST("doctor/doctoraccept.php?")
 Call<PendingworkModel> DoctorPendingServicelist(
         @Field("phid") String temp,
         @Field("accept") String acpt);
        // @Field("status") String status,
        // @Field("lang") String Languages);


 @FormUrlEncoded
 @POST("doctor/notificationcomplete.php?")
 Call<PhyHistoryModel> DoctorCompletedhistorylist(
         @Field("id") String id,
         @Field("status") String sta);
 // @Field("status") String status,
 // @Field("lang") String Languages);


@FormUrlEncoded
 @POST("notificationcomplete.php?")
 Call<PatientHistoryModel> PatientCompletedhistorylist(
         @Field("pid") String id,
         @Field("lang") String sta);
 // @Field("status") String status,
 // @Field("lang") String Languages);




}
