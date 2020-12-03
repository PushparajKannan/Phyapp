package com.phyapp.root.physiotherapy.Fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.phyapp.root.physiotherapy.APICONFIG.APIConfig;
import com.phyapp.root.physiotherapy.MainCategoreyActivity;
import com.phyapp.root.physiotherapy.PhyProductActivity;
import com.phyapp.root.physiotherapy.R;
import com.phyapp.root.physiotherapy.RegisterActivity;
import com.phyapp.root.physiotherapy.RequestServiceActivity;
import com.phyapp.root.physiotherapy.Utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import dmax.dialog.SpotsDialog;

/**
 * Created by root on 23/3/18.
 */

public class E1Fragment extends Fragment{
    Button ConforMe,ConforOt;

    Button products,dr,diet,pro3,productssss;

    SessionManager session;

    String Language,Userid;

    android.app.AlertDialog pDialog;

    Button[] btns;//=new Button[]{ConforMe,productssss};

   // AlertDialog dialog;

    String[] values;



    Dialog dialog;
    String sid;
    private String useravailable;

    public E1Fragment() {

        getActivity();
       // dialog = new AlertDialog(getContext());
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View rootView=inflater.inflate(R.layout.fragment_e1,container,false);
      return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       dialog = new Dialog(getActivity(),R.style.CustomDialogAnimationss);
        session = new SessionManager(getActivity());

        pDialog=new SpotsDialog(getContext(),R.style.Custom);

        pDialog.setCancelable(false);


       // showpDialog();



       // values=new String[3];

        HashMap<String, String> user = session.getUserDetails();


        Language = user.get(SessionManager.KEY_USERS_Language);

        Userid=user.get(SessionManager.KEY_USERID);


        if(Userid==null)
        {

            useravailable="nouser";



        }else {
            useravailable="user";
        }


        ConforMe=(Button) view.findViewById(R.id.conforme);
       // ConforOt=(Button) view.findViewById(R.id.conforot);
        products=(Button) view.findViewById(R.id.pro);
        dr=(Button) view.findViewById(R.id.pro1);
        diet=(Button) view.findViewById(R.id.pro2);
        productssss=(Button) view.findViewById(R.id.conforot);

        btns=new Button[]{ConforMe,productssss};


        CallSubmenuApi();

        CallLanguageApi();

       // CallSubmenuApi();



        ConforMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent i=new Intent(getActivity(), MainCategoreyActivity.class);
                i.putExtra("mid","1");
                startActivity(i);

              /*  Intent i=new Intent(dialog.getContext(),ConsultForMeActivity.class);
                startActivity(i);
                dialog.dismiss();*/

              //  dialogView();
            }
        });

       /* ConforOt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), RequestServiceActivity.class);
                startActivity(i);
            }
        });*/

        productssss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogView();

            }
        });
       /* dr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), RequestServiceActivity.class);
                startActivity(i);
            }
        });*/

       /* diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), RequestServiceActivity.class);
                startActivity(i);
            }
        });*/

    }

    private void CallLanguageApi() {


       // String url= "http://nbayjobs.com/raksha/api/mainmenu.php?id=1&lang="+Language;

      final  String url= APIConfig.MAIN_API+APIConfig.MainMenu+"id=1&lang="+Language;

        RequestQueue queue = Volley.newRequestQueue(getActivity());  // this = context

       // final String url = "http://nbayjobs.com/raksha/api/service.php?id=1";

// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());

                       // hidepDialog();

                        String sucess,error;

                        try {
                            sucess=response.getString("success");
                            error=response.getString("error");

                          // hidepDialog();


                            if(sucess.equals("1")){
                                Log.i("sucess:",sucess);

                                JSONArray array=new JSONArray(response.getString("src"));


                                for (int i=0;i<array.length();i++) {


                                    Log.i("lan",Language);

                                    //DynamicTabMode dmy=new DynamicTabMode();

                                    JSONObject datas = (JSONObject) array.get(i);


                                    String Englishappoint,TamilAppoint;

                                    Englishappoint=datas.getString("title");
                                    //TamilAppoint=datas.getString("tamil");

                                    btns[i].setText(Englishappoint);


                                   /* if(Language.equals("English")){

                                        btns[i].setText(Englishappoint);

                                    }
                                    else if(Language.equals("Tamil")) {

                                        btns[i].setText(TamilAppoint);


                                    }*/

                                    //btns[i].setText();









                                   /// dmy.setName(titlenae);
                                    //dmy.setDis(titledis);

                                    //name.add(dmy);


                                }

                               // setupViewPager(viewPager1,name);


                               // setFragment();

                                // tab2.
                              /*  tab2.getMenu().getItem(1).setChecked(false);
                                tab2.getMenu().getItem(2).setChecked(false);
                                tab2.getMenu().getItem(3).setChecked(false);
                                tab2.getMenu().getItem(4).setChecked(false);*/
                                //  tab2.getMenu().getItem(5).setChecked(false);


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
                        Log.d("Error.Response", error.toString());

                      //  hidepDialog();



                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);

        getRequest.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));




    }

    private void dialogView() {




        dialog.setContentView(R.layout.dialogalert);

        TextView consme,conth,conth2,consume2;

        TextView[] textViews;

        consme=(TextView) dialog.findViewById(R.id.con_me);
        conth=(TextView) dialog.findViewById(R.id.con_oth);
        conth2=(TextView) dialog.findViewById(R.id.con_oth2);
        consume2=(TextView) dialog.findViewById(R.id.con_me2);

        textViews=new TextView[]{consme,conth,conth2,consume2};


        for (int i=0;i<values.length;i++){


            textViews[i].setText(values[i]);


        }


        consme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i=new Intent(dialog.getContext(), PhyProductActivity.class);
                startActivity(i);
                dialog.dismiss();

            }
        });



        conth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(useravailable.equals("nouser")){

                    alertCreate();
                    dialog.dismiss();


                }else {


                    sid = "2";

              /*  Intent i=new Intent(dialog.getContext(),ConsultForOthersActivity.class);
                startActivity(i);
                dialog.dismiss();*/
                    Intent i = new Intent(getActivity(), RequestServiceActivity.class);
                    i.putExtra("sid", sid);
                    startActivity(i);
                    dialog.dismiss();
                }


            }
        });


        conth2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(useravailable.equals("nouser")){
                    alertCreate();
                    dialog.dismiss();

                }else {
                    sid = "3";

                    Intent i = new Intent(getActivity(), RequestServiceActivity.class);
                    i.putExtra("sid", sid);
                    startActivity(i);
                    dialog.dismiss();
                }

            }
        });

        consume2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (useravailable.equals("nouser")) {


                    alertCreate();
                    dialog.dismiss();
                } else {
                    sid = "4";

                    Intent i = new Intent(getActivity(), RequestServiceActivity.class);
                    i.putExtra("sid", sid);
                    startActivity(i);
                    dialog.dismiss();

                }
            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.show();
    }

    private void CallSubmenuApi() {

       final String url= APIConfig.MAIN_API+APIConfig.SubMenu+"id=1&mid=2&lang="+Language;

      //  String url= "http://nbayjobs.com/raksha/api/submenu.php?id=1&mid=2&lang="+Language;

        RequestQueue queue = Volley.newRequestQueue(getActivity());  // this = context

        // final String url = "http://nbayjobs.com/raksha/api/service.php?id=1";

// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());

                        // hidepDialog();

                        String sucess,error;

                        try {
                            sucess=response.getString("success");
                            error=response.getString("error");

                            if(sucess.equals("1")){
                                Log.i("sucess:",sucess);

                                JSONArray array=new JSONArray(response.getString("src"));

                                values=new String[array.length()];



                                for (int i=0;i<array.length();i++) {


                                    Log.i("lan",Language);

                                    //DynamicTabMode dmy=new DynamicTabMode();

                                    JSONObject datas = (JSONObject) array.get(i);


                                    String Englishappoint,id;

                                    Englishappoint=datas.getString("title");

                                    values[i]=Englishappoint;



                                    //TamilAppoint=datas.getString("tamil");

                                   // btns[i].setText(Englishappoint);


                                   /* if(Language.equals("English")){

                                        btns[i].setText(Englishappoint);

                                    }
                                    else if(Language.equals("Tamil")) {

                                        btns[i].setText(TamilAppoint);


                                    }*/

                                    //btns[i].setText();









                                    /// dmy.setName(titlenae);
                                    //dmy.setDis(titledis);

                                    //name.add(dmy);


                                }

                                // setupViewPager(viewPager1,name);


                                // setFragment();

                                // tab2.
                              /*  tab2.getMenu().getItem(1).setChecked(false);
                                tab2.getMenu().getItem(2).setChecked(false);
                                tab2.getMenu().getItem(3).setChecked(false);
                                tab2.getMenu().getItem(4).setChecked(false);*/
                                //  tab2.getMenu().getItem(5).setChecked(false);


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
                        Log.d("Error.Response", error.toString());

                        //  hidepDialog();



                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);

        getRequest.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));




    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    private void alertCreate() {

        final androidx.appcompat.app.AlertDialog.Builder alert=new androidx.appcompat.app.AlertDialog.Builder(getActivity(),R.style.CustomDialogTheme);
        alert.setCancelable(false);
        alert.setTitle(Html.fromHtml("<font color='#dd0000'>Alert!</font>"));
        alert.setMessage(Html.fromHtml("<font color='#000000'>You must login to access this page</font>"));
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent i=new Intent(getActivity(),RegisterActivity.class);
                startActivity(i);
                //finish();

            }

        });
        alert.setNegativeButton("Cancel",null);
        androidx.appcompat.app.AlertDialog alrt= alert.create();
        alrt.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        alrt.show();
    }

}
