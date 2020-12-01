package com.phyapp.root.physiotherapy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.phyapp.root.physiotherapy.APICONFIG.APIConfig;
import com.phyapp.root.physiotherapy.ModelClass.AddlayoutdetailsModel;
import com.phyapp.root.physiotherapy.ModelClass.MenuCategoryModel;
import com.phyapp.root.physiotherapy.ModelClass.MenuSubListModel;
import com.phyapp.root.physiotherapy.Retofit.ApiClient;
import com.phyapp.root.physiotherapy.Retofit.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class ServiceCompletedActivity extends AppCompatActivity {


    TextView confrim,add;
    LinearLayout addss;
    String ahid, phid;
    AlertDialog pDialog;
    TextView pnames, psers, pdates, ptimes, paddress,prs;
    RadioGroup radioGroup;
    LinearLayout layout;

    ToggleButton toggle;

    Dialog alerts;
    LinearLayout extra;

    Spinner servsub;
    Spinner servadd;
    LinearLayout container;
    EditText rs;

    String idss1,idss2,idss3;
    int addlay;




    private ArrayList<MenuCategoryModel.Src> Menulistdata;
    private SharedPreferences sharedpreferences;
    private ArrayList<MenuSubListModel.Src> subMenulist;

    ArrayList<AddlayoutdetailsModel> addlays;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_completed);

        callTitleCenterFont();

        pDialog = new SpotsDialog(this, R.style.Custom);
        pDialog.setCancelable(true);

        alerts = new Dialog(this);

        Menulistdata=new ArrayList<MenuCategoryModel.Src>();
        addlays=new ArrayList<AddlayoutdetailsModel>();


        pnames = (TextView) findViewById(R.id.pat_name_serve_comp);
        psers = (TextView) findViewById(R.id.pat_service_serve_comp);
        prs = (TextView) findViewById(R.id.pat_rs_serve_comp);
        pdates = (TextView) findViewById(R.id.pat_date_serve_comp);
        ptimes = (TextView) findViewById(R.id.pat_time_serve_comp);
        paddress = (TextView) findViewById(R.id.pat_addre_serve_comp);
        add = (TextView) findViewById(R.id.addlayout);
        toggle = (ToggleButton) findViewById(R.id.serviceonoff);
        addss = (LinearLayout) findViewById(R.id.add);
        servadd = (Spinner) findViewById(R.id.servivcelist);
        servsub = (Spinner) findViewById(R.id.servicessub);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroups);
        layout = (LinearLayout) findViewById(R.id.insert_point);
        container = (LinearLayout) findViewById(R.id.container);
        extra=(LinearLayout) findViewById(R.id.addingleni);
        rs=(EditText) findViewById(R.id.rsedit);


        showpDialog();

        ahid = getIntent().getStringExtra("ahid");
        phid = getIntent().getStringExtra("phid");
        addlay=0;

        callMenulist();



        callPatientlocationReachedAPI(ahid, phid, "Reached");

        confrim = (TextView) findViewById(R.id.confrim);
        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showpDialog();


                String showallPrompt = "";

                int childCount = container.getChildCount();
               // showallPrompt += "childCount: " + childCount + "\n\n";

                for(int c=0; c<childCount; c++){
                    View childView = container.getChildAt(c);
                    TextView childTextView1= (TextView)(childView.findViewById(R.id.textoutid1));
                    TextView childTextView2= (TextView)(childView.findViewById(R.id.textoutid2));
                    TextView childTextView3= (TextView)(childView.findViewById(R.id.textout3));
                    String childTextViewText1 = (String)(childTextView1.getText());
                    String childTextViewText2 = (String)(childTextView2.getText());
                    String childTextViewText3 = (String)(childTextView3.getText());


                    showallPrompt +="1:"+ childTextViewText1 +":"+ childTextViewText2 +":"+childTextViewText3+",";
                }

/*
                Toast.makeText(ServiceCompletedActivity.this,
                        showallPrompt,
                        Toast.LENGTH_LONG).show();
*/


                callCompletedclickAPI(ahid,phid,"1",showallPrompt);


             //   AddlayoutdetailsModel al=new AddlayoutdetailsModel();

            }
        });

        addss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle.setChecked(true);
                extra.setVisibility(View.VISIBLE);


            }
        });

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggle.isChecked()){
                    extra.setVisibility(View.VISIBLE);
                }else{
                    extra.setVisibility(View.GONE);
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!TextUtils.isEmpty(rs.getText().toString())) {


                    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.your_layout, null);
                    TextView textOut1 = (TextView) addView.findViewById(R.id.textout1);
                    TextView textOut2 = (TextView) addView.findViewById(R.id.textout2);
                    TextView textOut3 = (TextView) addView.findViewById(R.id.textout3);
                    TextView textOut4 = (TextView) addView.findViewById(R.id.textoutid1);
                    TextView textOut5 = (TextView) addView.findViewById(R.id.textoutid2);
                    final TextView buttonRemove = (TextView) addView.findViewById(R.id.remove);

                    textOut1.setText(servadd.getSelectedItem().toString());
                    textOut2.setText(servsub.getSelectedItem().toString());
                    textOut3.setText(rs.getText().toString());
                    textOut4.setText(idss1);
                    textOut5.setText(idss2);


                    //  final AddlayoutdetailsModel la=new AddlayoutdetailsModel();



             /*   la.setId1(idss1);
                la.setId2(idss2);
                la.setRs(rs.getText().toString());
*/


                    //  final int finalI = i;

                    buttonRemove.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {


                            ((LinearLayout) addView.getParent()).removeView(addView);


                        }
                    });


                    container.addView(addView);


                    toggle.setChecked(false);
                    extra.setVisibility(View.GONE);

                    ;

                }else{
                    Toast.makeText(ServiceCompletedActivity.this, "Enter Additional Services Amount", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void callCompletedclickAPI(final String aids, final String phids, final String complet, final String adtional) {

      //  http://nbayjobs.com/raksha/api/doctor/doctorcomplete.php?aid=5&phid=1&complete=1&additional=1:2:3:100,2:3:4:100

        String url=APIConfig.MAIN_API+APIConfig.Doctor_workcompleted;
        RequestQueue queue=Volley.newRequestQueue(this);

      //  url = "http://httpbin.org/post";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        //hidepDialog();
                        String sucess,msg;

                        try {
                            JSONObject object=new JSONObject(response);

                            sucess=object.getString("success");

                            if(sucess.equals("1")){
                                hidepDialog();

                                Toast.makeText(ServiceCompletedActivity.this, "Service completed successfully", Toast.LENGTH_SHORT).show();

                                Intent i=new Intent(ServiceCompletedActivity.this,PhyHistoryActivity.class);
                                startActivity(i);

                                finish();


                            }else if(sucess.equals("0")){
                                hidepDialog();
                                msg=object.getString("message");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                            hidepDialog();
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
                params.put("aid", aids);
                params.put("phid",phids);
                params.put("complete", complet);
                params.put("additional",adtional );

                return params;
            }
        };
        queue.add(postRequest);

        postRequest.setRetryPolicy(new DefaultRetryPolicy(500000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }


        /*add.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {





               *//* alerts.setContentView(R.layout.your_layout);

                TextView s1, sub1;

                s1 = (TextView) findViewById(R.id.textservice);
                sub1 = (TextView) findViewById(R.id.textsubservice);


                s1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callMenulist();


                    }
                });*//*







                // Layout inflater
                LayoutInflater layoutInflater = getLayoutInflater();
                View view;

                for (int i = 1; i < 3; i++) {
                    // Add the text layout to the parent layout
                    view = layoutInflater.inflate(R.layout.your_layout, layout, false);

                    // In order to get the view we have to use the new view with text_layout in it
                    LinearLayout textView = (LinearLayout) view.findViewById(R.id.a_text_view);
                    //textView.setText("Row " + i);

                    // Add the text view to the parent layout
                    layout.addView(textView);
                }
            }
        });


    }*/


    public void callTitleCenterFont() {
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View vr = inflator.inflate(R.layout.titleview, null);

        ActionBar.LayoutParams p = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);

//if you need to customize anything else about the text, do it here.
//I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
        ((TextView) vr.findViewById(R.id.title)).setText(this.getTitle());

//assign the view to the actionbar
        //  this.getSupportActionBar().setCustomView(vr,p);
        this.getSupportActionBar().setCustomView(vr,p);
    }


    private void callPatientlocationReachedAPI(final String ahi, final String phi, final String sat) {

        //http://www.nbayjobs.com/raksha/api/doctor/appreach.php?aid=1&phid=1&status=Reached


        String url = APIConfig.MAIN_API + APIConfig.Doctor_PatienLocation_Reached;

        RequestQueue queue = Volley.newRequestQueue(this);


        // url = "http://httpbin.org/post";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                hidepDialog();
                // response
                Log.d("Response", response);


                String pay, success, aid, payby, patient, service, date, time, address, phid,charge;

                try {
                    JSONObject obj = new JSONObject(response);

                    success = obj.getString("success");

                    if (success.equals("1")) {


                        aid = obj.getString("aid");
                        phid = obj.getString("phid");
                        payby = obj.getString("payby");
                        patient = obj.getString("patient_name");
                        service = obj.getString("service");
                        date = obj.getString("date");
                        time = obj.getString("time");
                        address = obj.getString("address");
                        charge = obj.getString("charge");


                        pnames.setText(patient);
                        psers.setText(service);
                        pdates.setText(date);
                        ptimes.setText(time);
                        paddress.setText(address);
                        prs.setText(charge);

                        pay = payby.toLowerCase();


                        if (pay.equals("cash")) {

                            radioGroup.check(R.id.pay_cash);

                        } else if (pay.equals("card")) {

                            radioGroup.check(R.id.pay_card);
                        }


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

                hidepDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("aid", ahi);
                params.put("phid", phi);
                params.put("status", sat);

                return params;
            }
        };
        queue.add(postRequest);

        postRequest.setRetryPolicy(new DefaultRetryPolicy(500000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }

    private void showpDialog() {
        if (!pDialog.isShowing()) pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing()) pDialog.dismiss();
    }


    private void callMenulist() {


        final ApiInterface service = ApiClient.getClient().create(ApiInterface.class);

        final Call<MenuCategoryModel> Menulist = service.menulist("1", "1", "English");

        Menulist.enqueue(new Callback<MenuCategoryModel>() {
            @Override
            public void onResponse(Call<MenuCategoryModel> call, retrofit2.Response<MenuCategoryModel> response) {

                Log.i("Responce", response.raw().toString());

                String sucess, error;

                sucess = String.valueOf(response.body().getSuccess());

                if (sucess.equals("1")) {
                    hidepDialog();

                    Menulistdata = response.body().getSrc();


                    //  sharedpreferences = getSharedPreferences(Insert_plate_number.MyPREFERENCES, Context.MODE_PRIVATE);
// You need to create an Array list and fill it with string you have in SharedPreferences
                    ArrayList<String> list;
                    list = new ArrayList<String>();
                    final ArrayList<Integer> name;
                    name = new ArrayList<Integer>();
                    // int arraysize = sharedpreferences.getInt("list_size", 0);
                    //  int arraysize = Menulistdata.get(0).getTitle();
                    for (int i = 0; i < Menulistdata.size(); i++) {
                        list.add(Menulistdata.get(i).getTitle());
                    }


                    for (int i = 0; i < Menulistdata.size(); i++) {

                        name.add(Menulistdata.get(i).getSid());
                    }
                    // Spinner spinner = (Spinner) findViewById(R.id.edit_text);
                    ArrayAdapter<String> adp = new ArrayAdapter<String>(ServiceCompletedActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
                    adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    servadd.setAdapter(adp);


                    servadd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            name.get(position);

                            idss1=String.valueOf(name.get(position));

                            Log.i("ids1", idss1);

                            CallSubmelist(name.get(position));





                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                } else if (sucess.equals("0")) {
                    hidepDialog();

                    error = response.body().getErrorMsg();

                    Log.i("Error", error);

                }


            }

            @Override
            public void onFailure(Call<MenuCategoryModel> call, Throwable t) {

                hidepDialog();
            }
        });


    }
        private void CallSubmelist( final int sid) {

            final ApiInterface service= ApiClient.getClient().create(ApiInterface.class);

            Call<MenuSubListModel> descriptionCall= service.menusublist("1","1",String.valueOf(sid),"English");

            descriptionCall.enqueue(new Callback<MenuSubListModel>() {
                @Override
                public void onResponse(Call<MenuSubListModel> call, retrofit2.Response<MenuSubListModel> response) {

                    Log.i("Responce",response.raw().toString());

                    String sucess,error;

                    sucess= String.valueOf(response.body().getSuccess());

                    if(sucess.equals("1")){
                        hidepDialog();

                        subMenulist=response.body().getSrc();

                        final ArrayList<String> list;
                        list = new ArrayList<String>();
                        // int arraysize = sharedpreferences.getInt("list_size", 0);
                        //  int arraysize = Menulistdata.get(0).getTitle();
                        for (int i = 0; i < subMenulist.size(); i++) {
                            list.add(subMenulist.get(i).getTitle());
                        }

final ArrayList<String> nam;
                        nam = new ArrayList<String>();
                        // int arraysize = sharedpreferences.getInt("list_size", 0);
                        //  int arraysize = Menulistdata.get(0).getTitle();
                        for (int i = 0; i < subMenulist.size(); i++) {
                            nam.add(subMenulist.get(i).getIid());
                        }



                        ArrayAdapter ary = new ArrayAdapter(ServiceCompletedActivity.this, android.R.layout.simple_spinner_item, list);
                        ary.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        servsub.setAdapter(ary);


                        servsub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                                nam.get(position);
                                Log.i("postion", String.valueOf(position));
                                idss2=nam.get(position);
                                Log.i("ids2", idss2);





                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }else if(sucess.equals("0")){
                        hidepDialog();

                        error=response.body().getErrorMsg();

                        Log.i("error",error);


                    }

                }

                @Override
                public void onFailure(Call<MenuSubListModel> call, Throwable t) {


                    hidepDialog();
                }
            });


        }




}
