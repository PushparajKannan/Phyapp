package com.phyapp.root.physiotherapy;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import androidx.transition.TransitionManager;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.phyapp.root.physiotherapy.ModelClass.ProfileShowModel;
import com.phyapp.root.physiotherapy.Retofit.ApiClient;
import com.phyapp.root.physiotherapy.Retofit.ApiInterface;
import com.phyapp.root.physiotherapy.Utils.SessionManager;
import com.squareup.timessquare.CalendarPickerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class AppointmentActivity extends AppCompatActivity {

    Calendar myCalendar1, myCalendar;
    TextView heading;
    String froms, tos;
    TextView setdays;

    EditText From, To;
    DatePickerDialog.OnDateSetListener date, date1;
    private String dates;
    private String datess;
    TextView charge,Total;
    int price=100;
    LinearLayout ll,cusll,lll;
    TextView Days,diffdays,Textdays;
    private Object mExpandedPosition=-1;
    private Object positions=-1;
    EditText getdays;
    Button add;
    String latituted ="",longitude="";
    String consultfordata,comments;


   // String[] alreadyselcdate;

    List<Date>  alreadyselcdate;

    String selectedDateTo,selectedDateFrom;

    CharSequence[] values = {" Male "," Female "};

    CharSequence[] consultsFor={" Consult For Me "," Consult For Others "};

    // AlertDialog.Builder alrt;
    AlertDialog alert;

    ImageView cash,card;
    Spinner dateFrom,dateTo;

    String sid,iid;
    SessionManager session;

    String patient_name,patient_ID;

    TextView textcash,textcard,consultfor,consultaddress;
    EditText nameAppoint,age;
    TextView sex;

    private BottomSheetDialog mBottomSheetDialog;
    private int checkedlanguage,checkconsult;
    private Dialog alertDialog1;

    AlertDialog pDialog;


    //DateUtils du;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
              //  NavUtils.navigateUpFromSameTask(this);

                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);


        callTitleCenterFont();


        session=new SessionManager(this);

        alertDialog1=new Dialog(this);
      //  alertDialog1=new Dialog(this,R.style.CustomDialogAnimationss);

        HashMap<String,String> user=session.getUserDetails();

        patient_name=user.get(session.KEY_USER_NAME);

        patient_ID=user.get(session.KEY_USERID);

        pDialog=new SpotsDialog(this,R.style.Custom);

        pDialog.setCancelable(false);

        alreadyselcdate=new ArrayList<Date>();


        showpDialog();





        sid=getIntent().getStringExtra("subid");
        iid=getIntent().getStringExtra("iid");
        comments=getIntent().getStringExtra("comments");



        if(iid==null){
            iid="0";
        }

        if(comments==null){
            comments="";
        }
       Log.i("sid",sid);
       // Log.i("iid",iid);


        checkedlanguage=0;
        checkconsult=-1;
        consultfordata="0";




        From = (EditText) findViewById(R.id.frodate);
        setdays=(TextView) findViewById(R.id.dayscount);
        nameAppoint=(EditText) findViewById(R.id.name_service_appoinment);
        age=(EditText) findViewById(R.id.age_service_appointment);
        sex=(TextView) findViewById(R.id.sex_service_appointment);

        To = (EditText) findViewById(R.id.todate);
        heading = (TextView) findViewById(R.id.headingtext);
        charge=(TextView) findViewById(R.id.charge);
        diffdays=(TextView) findViewById(R.id.Select_appoinment_days);
        Total=(TextView) findViewById(R.id.total);
        ll=(LinearLayout) findViewById(R.id.line);
        Textdays=(TextView) findViewById(R.id.text_days);
       // lll=(LinearLayout) findViewById(R.id.lll);
        Days = (TextView) findViewById(R.id.title11);
        cusll=(LinearLayout) findViewById(R.id.customdays);
       // final AlertDialog dialog=alrt.create();
        cash=(ImageView) findViewById(R.id.by_cash);
        card=(ImageView) findViewById(R.id.by_cards);
        dateFrom=(Spinner)  findViewById(R.id.DateFrom);
        dateTo=(Spinner) findViewById(R.id.DateTo);
        textcash=(TextView) findViewById(R.id.text_cash);
        textcard=(TextView) findViewById(R.id.text_card);
        consultfor=(TextView) findViewById(R.id.select_consult_for);
        consultaddress=(TextView) findViewById(R.id.address_for_consult);



        View bottomSheet = findViewById(R.id.framelayout_bottom_sheet);

        CallchargeApi();

        sex.setText("Male");
        //consultfor.setText("Consult For Me");

        sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateAlertDialogWithRadioButtonGroup();

            }
        });


        consultfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                consultAppointFor();

            }
        });

        consultaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent i=new Intent(AppointmentActivity.this,MapSelectionActivity.class);
                //i.setClassName("com.example.root.physiotherapy",AppointmentActivity);
                startActivityForResult(i,90);

/*
                *//* Start Activity *//*
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setClassName("com.thinoo.ActivityTest", "com.thinoo.ActivityTest.NewActivity");
                    startActivityForResult(intent,90);
                }*/


              /*  *//* Called when the second activity's finished *//*
                protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                    switch(requestCode) {
                        case 90:
                            if (resultCode == RESULT_OK) {
                                Bundle res = data.getExtras();
                                String result = res.getString("param_result");
                                Log.d("FIRST", "result:"+result);
                            }
                            break;
                    }
                }*/
            }
        });


        /*sex.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return false;
            }
        });*/


        if(patient_name==null||patient_name.isEmpty()){

            //alertCreate();
            showpDialog();

            CallProileApi(patient_ID);



        }else {

            nameAppoint.setText(patient_name);

        }



        //callBottomDialog();

        //Spinner DATEFROM


        final List list=new ArrayList();
       // list.add("From");
        list.add("09:00 AM");
        list.add("10:00 AM");
        list.add("11:00 AM");
       // list.add("12:00");
        list.add("12:00 PM");
        list.add("01.00 PM");
        list.add("02.00 PM");
        list.add("03.00 PM");
        list.add("04.00 PM");
        list.add("05.00 PM");
        list.add("06.00 PM");
        list.add("07.00 PM");
       // list.add("08.00 PM");
/*

final List lists=new ArrayList();
        lists.add("To");
        lists.add("09:00");
        lists.add("10:00");
        lists.add("11:00");
        lists.add("12:00");
        lists.add("12:00");
        lists.add("1.00");
        lists.add("2.00");
        lists.add("3.00");
        lists.add("4.00");
        lists.add("5.00");
        lists.add("6.00");
        lists.add("7.00");
        lists.add("8.00");

*/



        ArrayAdapter ary = new ArrayAdapter(AppointmentActivity.this, android.R.layout.simple_spinner_item, list);
        ary.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateFrom.setAdapter(ary);

       // getAppointmentApi();



        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(dateFrom);

            // Set popupWindow height to 500px
            popupWindow.setHeight(700);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }



        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(dateTo);

            // Set popupWindow height to 500px
            popupWindow.setHeight(700);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }


        Textdays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaysSelection();
            }
        });


        dateFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
               // List l = new ArrayList();
                // ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#757575"));
                // ((TextView) parent.getChildAt(0)).setTextSize(5);

                //((TextView)view).setGravity(Gravity.FILL);

                List lists=new ArrayList();
               // lists.add("To");
            /*    lists.add("09:00");
                lists.add("10:00");
                lists.add("11:00");
                lists.add("12:00");
                lists.add("12:00");
                lists.add("1.00");
                lists.add("2.00");
                lists.add("3.00");
                lists.add("4.00");
                lists.add("5.00");
                lists.add("6.00");
                lists.add("7.00");
                lists.add("8.00");
*/

                if (dateFrom.getSelectedItem().toString().equals("09:00 AM"))
                {

                    lists.add("10:00 AM");
                    lists.add("11:00 AM");
                    lists.add("12:00 PM");
                    //lists.add("12:00");
                    lists.add("01.00 PM");
                    lists.add("02.00 PM");
                    lists.add("03.00 PM");
                    lists.add("04.00 PM");
                    lists.add("05.00 PM");
                    lists.add("06.00 PM");
                    lists.add("07.00 PM");
                    lists.add("08.00 PM");



                  //  dateTo.setVisibility(View.GONE);
                    //lists.remove("9.00");

                } else if (dateFrom.getSelectedItem().toString().equals("10:00 AM")) {
                  //  dateTo.setVisibility(View.VISIBLE);
                 //   l.add("Panji");
                  //  l.add("Margao");
                   // l.add("Vaco Da Gama");

                  //  lists.remove("9.00");
                  // lists.remove("10.00");
                    lists.add("11:00 AM");
                    lists.add("12:00 PM");
                    //lists.add("12:00");
                    lists.add("01.00 PM");
                    lists.add("02.00 PM");
                    lists.add("03.00 PM");
                    lists.add("04.00 PM");
                    lists.add("05.00 PM");
                    lists.add("06.00 PM");
                    lists.add("07.00 PM");
                    lists.add("08.00 PM");
                } else if (dateFrom.getSelectedItem().toString().equals("11:00 AM")) {

                    /*view.animate()
                            .translationY(view.getHeight())
                            .alpha(0.0f)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    spinner_cities_activity.setVisibility(View.VISIBLE);
                                }
                            });*/

                    //dateTo.setVisibility(View.VISIBLE);


                   // l.add("Bengluru");
                   // l.add("Manglore");
                   // l.add("Hubli");
                  //  lists.remove("9.00");
                  //  lists.remove("10.00");
                  //  lists.remove("11.00");
                    lists.add("12:00 PM");
                    //lists.add("12:00");
                    lists.add("01.00 PM");
                    lists.add("02.00 PM");
                    lists.add("03.00 PM");
                    lists.add("04.00 PM");
                    lists.add("05.00 PM");
                    lists.add("06.00 PM");
                    lists.add("07.00 PM");
                    lists.add("08.00 PM");
                }else  if (dateFrom.getSelectedItem().toString().equals("12:00 PM"))
                {

                    lists.add("01.00 PM");
                    lists.add("02.00 PM");
                    lists.add("03.00 PM");
                    lists.add("04.00 PM");
                    lists.add("05.00 PM");
                    lists.add("06.00 PM");
                    lists.add("07.00 PM");
                    lists.add("08.00 PM");
                   // dateTo.setVisibility(View.VISIBLE);
                   // l.add("mumbai");
                  //  l.add("madurai");
                  //  l.add("mumbai");
                  //  l.add("mumbai");
                   // l.add("mumbai");
                }else  if (dateFrom.getSelectedItem().toString().equals("01.00 PM"))
                {
                  // dateTo.setVisibility(View.VISIBLE);
                  //  l.add("Mumbai");
                  //  l.add("Pune");
                   // l.add("Nagpur");
                    lists.add("02.00 PM");
                    lists.add("03.00 PM");
                    lists.add("04.00 PM");
                    lists.add("05.00 PM");
                    lists.add("06.00 PM");
                    lists.add("07.00 PM");
                    lists.add("08.00 PM");
                }else  if (dateFrom.getSelectedItem().toString().equals("02.00 PM"))
                {
                  // dateTo.setVisibility(View.VISIBLE);
                  //  l.add("Mumbai");
                  //  l.add("Pune");
                   // l.add("Nagpur");
                    //lists.add("2.00");
                    lists.add("03.00 PM");
                    lists.add("04.00 PM");
                    lists.add("05.00 PM");
                    lists.add("06.00 PM");
                    lists.add("07.00 PM");
                    lists.add("08.00 PM");
                }else  if (dateFrom.getSelectedItem().toString().equals("03.00 PM"))
                {
                  // dateTo.setVisibility(View.VISIBLE);
                  //  l.add("Mumbai");
                  //  l.add("Pune");
                   // l.add("Nagpur");
                   // lists.add("3.00");
                    lists.add("04.00 PM");
                    lists.add("05.00 PM");
                    lists.add("06.00 PM");
                    lists.add("07.00 PM");
                    lists.add("08.00 PM");
                }else  if (dateFrom.getSelectedItem().toString().equals("04.00 PM"))
                {
                  // dateTo.setVisibility(View.VISIBLE);
                  //  l.add("Mumbai");
                  //  l.add("Pune");
                   // l.add("Nagpur");
                    //lists.add("4.00");
                    lists.add("05.00 PM");
                    lists.add("06.00 PM");
                    lists.add("07.00 PM");
                    lists.add("08.00 PM");
                }else  if (dateFrom.getSelectedItem().toString().equals("05.00 PM"))
                {
                  // dateTo.setVisibility(View.VISIBLE);
                  //  l.add("Mumbai");
                  //  l.add("Pune");
                   // l.add("Nagpur");
                   // lists.add("5.00");
                    lists.add("06.00 PM");
                    lists.add("07.00 PM");
                    lists.add("08.00 PM");
                }else  if (dateFrom.getSelectedItem().toString().equals("06.00 PM"))
                {
                  // dateTo.setVisibility(View.VISIBLE);
                  //  l.add("Mumbai");
                  //  l.add("Pune");
                   // l.add("Nagpur");
                    lists.add("07.00 PM");
                    lists.add("08.00 PM");
                }else  if (dateFrom.getSelectedItem().toString().equals("07.00 PM"))
                {
                  // dateTo.setVisibility(View.VISIBLE);
                  //  l.add("Mumbai");
                  //  l.add("Pune");
                   // l.add("Nagpur");
                    lists.add("08.00 PM");
                }
                ArrayAdapter ary = new ArrayAdapter(AppointmentActivity.this, android.R.layout.simple_spinner_item, lists);
                ary.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dateTo.setAdapter(ary);


                 selectedDateFrom = parent.getItemAtPosition(position).toString();
              //  Toast.makeText(AppointmentActivity.this, selectedDateFrom, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView parent) {

            }
        });




        dateTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedDateTo = parent.getItemAtPosition(position).toString();
               // Toast.makeText(AppointmentActivity.this, selectedDateTo, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        diffdays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //CalendarPickerView calendar_view = (CalendarPickerView) findViewById(R.id.calendar_view);




DaysSelection();





            }
        });






        //fetch dates
        //List<Date> dates = calendar_view.getSelectedDates();
       // getdays=(EditText) findViewById(R.id.getday);
       // add=(Button) findViewById(R.id.addday);










        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Toast.makeText(AppointmentActivity.this, "Thankyou", Toast.LENGTH_SHORT).show();

                Intent i=new Intent(AppointmentActivity.this,ServiceActivity.class);
                startActivity(i);
*/
               /*Intent i=new Intent(AppointmentActivity.this,MainActivity.class);

                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(i);


               finish();*/


            }
        });

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   callBottomDialog();


               /* Intent i=new Intent(AppointmentActivity.this,PaymentByCard.class);
                startActivity(i);*/

            }
        });


        textcash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(!TextUtils.isEmpty(Textdays.getText().toString()) &&
                        !TextUtils.isEmpty(age.getText().toString()) &&
                        !TextUtils.isEmpty(sex.getText().toString()) &&
                        !TextUtils.isEmpty(nameAppoint.getText().toString()) &&
                        !TextUtils.isEmpty(consultfor.getText().toString()) &&
                        !TextUtils.isEmpty(consultaddress.getText().toString()))

                {
                    if(selectedDateFrom.isEmpty() && selectedDateTo.isEmpty()){

                        Toast.makeText(AppointmentActivity.this, "Select Dates", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        showpDialog();

                       // Toast.makeText(AppointmentActivity.this, "All Selected", Toast.LENGTH_SHORT).show();


                        getAppointmentApi(nameAppoint.getText().toString(),age.getText().toString(),
                                sex.getText().toString(),Textdays.getText().toString(),selectedDateFrom,
                                selectedDateTo,String.valueOf(price),setdays.getText().toString(),Total.getText().toString(),consultaddress.getText().toString(),latituted,longitude,consultfordata,comments);



                    }
                }else if(TextUtils.isEmpty(age.getText().toString())){

                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Enter Age",Snackbar.LENGTH_LONG);
                    snackbar.show();

                }else if(TextUtils.isEmpty(Textdays.getText().toString())){

                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Select Appointment Days",Snackbar.LENGTH_LONG);
                    snackbar.show();

                }else if(TextUtils.isEmpty(nameAppoint.getText().toString())){

                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Enter Name",Snackbar.LENGTH_LONG);
                    snackbar.show();

                }else if(TextUtils.isEmpty(sex.getText().toString())){

                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Select Gender",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else if(TextUtils.isEmpty(consultfor.getText().toString())){

                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Select Consult For",Snackbar.LENGTH_LONG);
                    snackbar.show();

                }else if(TextUtils.isEmpty(consultaddress.getText().toString())){

                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Select Address",Snackbar.LENGTH_LONG);
                    snackbar.show();

                }




               /* Toast.makeText(AppointmentActivity.this, "Thankyou", Toast.LENGTH_SHORT).show();

                Intent i=new Intent(AppointmentActivity.this,ServiceActivity.class);
                startActivity(i);
*/


            }
        });

        textcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callBottomDialog();


               /* Intent i=new Intent(AppointmentActivity.this,PaymentByCard.class);
                startActivity(i);*/

            }
        });




      //  charge.setText(String.valueOf(price));








        String name = getIntent().getStringExtra("nameoftheropy");

        System.out.println("NAME:" + name);
        heading.setText(name);





        Days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(ll.getVisibility()==View.GONE)
                {
                    ll.setVisibility(View.VISIBLE);
                }
                else
                {
                    ll.setVisibility(View.GONE);
                }


            }
        });



        // DateTimeUtils obj = new DateTimeUtils();


        myCalendar1 = Calendar.getInstance();
        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };


        formtext();
        totext();


        //calendar_view.addView();



























    }

    private void CallchargeApi() {

        RequestQueue queue=Volley.newRequestQueue(this);


       // String url="http://nbayjobs.com/raksha/api/charge.php?id=1";
        String urls=APIConfig.MAIN_API+APIConfig.Charge;

        //url = "http://httpbin.org/post";
        StringRequest postRequest = new StringRequest(Request.Method.POST, urls,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        hidepDialog();

                        String sucess,error,charges;

                        try {
                            JSONObject object=new JSONObject(response);

                            sucess=object.getString("success");


                            if(sucess.equals("1")){

                                charges=object.getString("charge");
                                price=Integer.parseInt(charges);

                                charge.setText(String.valueOf(price));
                                Log.i("price",String.valueOf(price));

                            }else if(sucess.equals("0")){

                                charge.setText(String.valueOf(price));

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
                params.put("id", "1");


                return params;
            }
        };
        queue.add(postRequest);

    }

    private void getAppointmentApi(final String name, final String age, final String sex,
                                   final String days, final String starttime, final String endtime,
                                   final String charge, final String nofdays, final String total, final String address, final String lati, final String longi, final String consultdata, final String cmd)
    {

        Log.i("name :",name);
        Log.i("age :",age);
        Log.i("sex :",sex);
        Log.i("days :",days);
        Log.i("stime :",starttime);
        Log.i("etime :",endtime);
        Log.i("charge :",charge);
        Log.i("nodays :",nofdays);
        Log.i("address :",address);
        Log.i("longi :",longi);
        Log.i("lati :",lati);
        Log.i("comments :",cmd);
        Log.i("consultdata :",consultdata);
        Log.i("iid :",iid);


        RequestQueue queue= Volley.newRequestQueue(this);

      //  url = "http://httpbin.org/post";

     //   http://nbayjobs.com/raksha/api/appointment.php?mid=1&sid=11&iid=144&pid=1&
        // patient_name=abu&age=20&sex=male&start_date=2018-06-12,2018-06-13&start_time=03:00:AM&
        // end_time=06:00:PM&charge_visit=100&nodays=3&total=100&payby=cash

        String url= APIConfig.MAIN_API+APIConfig.Appointment;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        hidepDialog();

                        String success,msg;


                        try {
                            JSONObject object=new JSONObject(response);

                            success=object.getString("success");

                            msg=object.getString("message");

                            if(success.equals("1"))
                            {

                                Toast.makeText(AppointmentActivity.this, "Appointment Booked Successfully", Toast.LENGTH_SHORT).show();

                                Intent i=new Intent(AppointmentActivity.this,MainActivity.class);
                               // i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.putExtra("indent","mpk");
                                startActivity(i);
                                finish();
                            }
                            else if(success.equals("0"))
                            {
                                Log.i("msg",msg);

                                Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),msg,Snackbar.LENGTH_LONG);
                                snackbar.show();



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
                params.put("mid", "1");
                params.put("sid", sid);
                params.put("iid", iid);
                params.put("pid",patient_ID );
                params.put("patient_name", name );
                params.put("age", age );
                params.put("sex", sex);
                params.put("start_date", days);
                params.put("start_time", starttime);
                params.put("end_time", endtime);
                params.put("charge_visit", charge);
                params.put("nodays", nofdays );
                params.put("total", total );
                params.put("address", address );
                params.put("latitude", lati );
                params.put("longitude", longi );
                params.put("consultfor", consultdata );
                params.put("comments", cmd );
                params.put("payby", "cash");

                return params;
            }
        };
        queue.add(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }

    private void callBottomDialog() {

        final View bottomSheetLayout = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
        (bottomSheetLayout.findViewById(R.id.button_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });
        (bottomSheetLayout.findViewById(R.id.button_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
               // Toast.makeText(getApplicationContext(), "Ok button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.setContentView(bottomSheetLayout);
        mBottomSheetDialog.show();
    }

    public void formtext() {
        From.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                // From.setEnabled(true);
                //From.setFocusable(true);

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //  if(event.getRawX() >= (From.getRight() - From.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    // your action here
                    new DatePickerDialog(AppointmentActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                    // return true;
                    // }
                }
                return false;
            }
        });

    }

    public void totext() {
        To.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;


                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (To.getRight() - To.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        new DatePickerDialog(AppointmentActivity.this, date1, myCalendar1
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar1.get(Calendar.DAY_OF_MONTH)).show();

                        return true;
                    }
                }
                return false;
            }
        });


    }


    private void updateLabel() {
        // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
      /*  Date date1 = simpleDateFormat.parse("10/10/2013 11:30:10");
        Date date2 = simpleDateFormat.parse("13/10/2013 20:35:55");*/


        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        //getTextview(sdf);

        //  ed.setText(sdf.format(myCalendar.getTime()));


        From.setText(sdf.format(myCalendar.getTime()));

        froms = From.getText().toString();
        System.out.println("from time:" + froms);


        if(tos!=null)
        {
            datechanged();
        }
        else
        {
            System.out.println("from time:" + froms);
        }

        // From.setEnabled(false);
        // From.setFocusable(false);

    }

    private void updateLabel1() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        //getTextview(sdf);

        //  ed.setText(sdf.format(myCalendar.getTime()));


        To.setText(sdf.format(myCalendar1.getTime()));
        tos = To.getText().toString();

        System.out.println("To :" + tos);

        datechanged();


       /* try{
            datechanged();

        }catch (Exception e)
        {

        }
*/
    }

   /* private void getTextview(SimpleDateFormat sdf) {

    }
*/


    public void datechanged() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
       // DateUtils du=new DateUtils();


        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(froms);
            d2 = format.parse(tos);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long different = d2.getTime() - d1.getTime();

        System.out.println("startDate : " + d1);
        System.out.println("endDate : " + d2);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;
        long tot=elapsedDays*price;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);

        // long diff = d2.getTime() - d1.getTime();
        // System.out.println("days:"+diff);

        String day=String.valueOf(elapsedDays);
        System.out.println("No.of Day:"+ day);
        System.out.println("total:"+tot);

        String ftotal=String.valueOf(tot);


        setdays.setText(day);
        Total.setText(ftotal);



    }


    public void printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);




    }



    public  void DaysSelection()
    {

        AlertDialog.Builder alrt= new AlertDialog.Builder(AppointmentActivity.this);
        LayoutInflater inflater=getLayoutInflater();


                /*LayoutInflater inflater = (LayoutInflater) this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);*/
        View dialoglayout = inflater.inflate(R.layout.calendar_layout, null);

        alrt.setView(dialoglayout);

        final CalendarPickerView calendar_view = (CalendarPickerView) dialoglayout.findViewById(R.id.calendar_view);
        Button btn_show_dates = (Button) dialoglayout.findViewById(R.id.btn_show_dates);

        final AlertDialog dialog = alrt.create();

        //getting current

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        Date today = new Date();

        //Textdays.clearComposingText();

        Textdays.setText(null);


//add one year to calendar from todays date
        calendar_view.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.MULTIPLE);
        final List<Date> dates = calendar_view.getSelectedDates();

        if(!alreadyselcdate.isEmpty())
        {
            for (int i=0;i<alreadyselcdate.size();i++){

                calendar_view.selectDate(alreadyselcdate.get(i));

                Date date = alreadyselcdate.get(i);
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String strDate = dateFormat.format(date);
                System.out.println("Converted String: " + strDate);
                Textdays.setText(Textdays.getText()+"  "+strDate+",");


            }
        }




        calendar_view.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {

                //Textdays.setText("");

                dates.add(date);
                alreadyselcdate.add(date);

               // Toast.makeText(getApplicationContext(),"Selected Date is : " +date.toString(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onDateUnselected(Date date) {

                dates.remove(date);
                alreadyselcdate.remove(date);

               // Toast.makeText(getApplicationContext(),"UnSelected Date is : " +date.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        //Displaying all selected dates while clicking on a button
        //   Button btn_show_dates = (Button) dialoglayout.findViewById(R.id.btn_show_dates);
        btn_show_dates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Textdays.setText(null);




                for (int i = 0; i< calendar_view.getSelectedDates().size();i++){

                    //here you can fetch all dates
                    //Toast.makeText(getApplicationContext(),calendar_view.getSelectedDates().get(i).toString(),Toast.LENGTH_SHORT).show();

                    System.out.println("DATEDS :"+calendar_view.getSelectedDates().get(i).toString());



                    Date date = calendar_view.getSelectedDates().get(i);
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String strDate = dateFormat.format(date);
                    System.out.println("Converted String: " + strDate);





                    Textdays.setText(Textdays.getText()+"  "+strDate+",");








                    // String s1,s2="";

                    //s1=strDate;
                    //  strDate=s2;








                    //  Total.setText(strDate);




                }


              //  Toast.makeText(AppointmentActivity.this, ""+dates.size(), Toast.LENGTH_SHORT).show();

                String no=String.valueOf(alreadyselcdate.size());
                setdays.setText(no);

                long tot=price*alreadyselcdate.size();
                Total.setText(String.valueOf(tot));




                // dialog.dismiss();
               /* alert.dismiss();
                alert.cancel();*/

              /*  if(cusll.getVisibility()==View.GONE)
                {
                    cusll.setVisibility(View.VISIBLE);
                }
                else
                {
                    cusll.setVisibility(View.GONE);
                }
*/
                dialog.dismiss();


            }
        });

        dialog.show();

        // alrt.setView(dialoglayout);
        // alrt.show();

        //  AlertDialog alertDialog=alrt.create();

/*

                if(cusll.getVisibility()==View.GONE)
                {
                    cusll.setVisibility(View.VISIBLE);
                }
                else
                {
                    cusll.setVisibility(View.GONE);
                }
*/





        }

    private void alertCreate() {

        androidx.appcompat.app.AlertDialog.Builder alert=new androidx.appcompat.app.AlertDialog.Builder(AppointmentActivity.this,R.style.CustomDialogTheme);
        alert.setCancelable(false);
        alert.setTitle(Html.fromHtml("<font color='#dd0000'>Alert!</font>"));
        alert.setMessage(Html.fromHtml("<font color='#000000'>You must update your profile</font>"));
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent i=new Intent(AppointmentActivity.this,EditProfileActivity.class);
                startActivity(i);
                finish();

            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });
       // alert.create();
        androidx.appcompat.app.AlertDialog alrt=alert.create();

        alrt.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        alrt.show();
    }



    public void CreateAlertDialogWithRadioButtonGroup()
    {


        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AppointmentActivity.this, R.style.CustomDialogTheme);

        builder.setTitle(Html.fromHtml("<font color='#dd0000'>Select Gender</font>"));




        builder.setSingleChoiceItems(values, checkedlanguage, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:

                       // tab2.getMenu().getItem(0).setChecked(true);

                        // tab2.setSelectedItemId(id);

                        //name.clear();
                        checkedlanguage=0;

                        sex.setText("Male");

                       // callBackgroundDiscrpitionApi("Tamil");


                      //  session.CreatelanguageSelect("Tamil");

                       // alertDialog1.dismiss();

                        //Toast.makeText(MainActivity.this, "Tamil Selected", Toast.LENGTH_LONG).show();
                        break;
                    case 1:

                        //tab2.getMenu().getItem(0).setChecked(true);


                        //name.clear();
                        checkedlanguage=1;
                        sex.setText("Female");
                       // callBackgroundDiscrpitionApi("English");
                        //session.CreatelanguageSelect("English");

                        //alertDialog1.dismiss();

                        //Toast.makeText(MainActivity.this, "English Selected", Toast.LENGTH_LONG).show();
                        break;
                    /*case 2:

                        Toast.makeText(MainActivity.this, "Third Item Clicked", Toast.LENGTH_LONG).show();
                        break;*/
                }

                dialog.dismiss();
            }
        });
        androidx.appcompat.app.AlertDialog alrt = builder.create();
        alrt.show();

    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }



    public void consultAppointFor()
    {


        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AppointmentActivity.this, R.style.CustomDialogTheme);

        builder.setTitle(Html.fromHtml("<font color='#dd0000'>Select Consult For</font>"));




        builder.setSingleChoiceItems(consultsFor, checkconsult, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:

                        // tab2.getMenu().getItem(0).setChecked(true);

                        // tab2.setSelectedItemId(id);

                        //name.clear();
                        checkconsult=0;


                       consultfor.setText("Consult For Me");
                       consultfordata="1";

                        // callBackgroundDiscrpitionApi("Tamil");


                        //  session.CreatelanguageSelect("Tamil");

                        // alertDialog1.dismiss();

                        //Toast.makeText(MainActivity.this, "Tamil Selected", Toast.LENGTH_LONG).show();
                        break;
                    case 1:

                        //tab2.getMenu().getItem(0).setChecked(true);


                        //name.clear();
                        checkconsult=1;
                        consultfor.setText("Consult For Others");
                        consultfordata="2";
                        // callBackgroundDiscrpitionApi("English");
                        //session.CreatelanguageSelect("English");

                        //alertDialog1.dismiss();

                        //Toast.makeText(MainActivity.this, "English Selected", Toast.LENGTH_LONG).show();
                        break;
                    /*case 2:

                        Toast.makeText(MainActivity.this, "Third Item Clicked", Toast.LENGTH_LONG).show();
                        break;*/
                }

                dialog.dismiss();
            }
        });

        androidx.appcompat.app.AlertDialog alrt = builder.create();


        alrt.show();

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case 90:
                if (resultCode == RESULT_OK) {
                    Bundle res = data.getExtras();
                    String address;
                   // String result = res.getString("results");


                    latituted=res.getString("latitude");
                    longitude=res.getString("longitude");
                    address=res.getString("address");
                    consultaddress.setText(address);

                    Log.d("FIRST", "lati:"+latituted+"longitude:"+longitude+"address"+address);
                }
                break;
        }
    }

    public void   callTitleCenterFont()
    {
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View vr = inflator.inflate(R.layout.titleview, null);

        ActionBar.LayoutParams p = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);

//if you need to customize anything else about the text, do it here.
//I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
        ((TextView)vr.findViewById(R.id.title)).setText(this.getTitle());

//assign the view to the actionbar
        this.getSupportActionBar().setCustomView(vr);
    }


    private void CallProileApi(final String userIds) {

        // String Url="http://nbayjobs.com/raksha/api/profileshow.php?pid=1";

        // final String Url= APIConfig.MAIN_API+APIConfig.ShowProfile+"pid="+UserId;

        final ApiInterface service= ApiClient.getClient().create(ApiInterface.class);

        Call<ProfileShowModel> descriptionCall= service.Profilelist(userIds);


        descriptionCall.enqueue(new Callback<ProfileShowModel>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<ProfileShowModel> call, retrofit2.Response<ProfileShowModel> response) {

                Log.i("Responce",response.raw().toString());

                Log.i("Responcefull",response.toString());

                String sucess,error;

                sucess= String.valueOf(response.body().getSuccess());

                if(sucess.equals("1")){

                    // Toast.makeText(getActivity(), "not null", Toast.LENGTH_SHORT).show();


                    hidepDialog();

                    String name,phone,address,email;

                    name=response.body().getName();

                    phone=response.body().getPhone();
                    address=response.body().getAddress();
                    email=response.body().getEmail();

                    //  Log.i("name",name);
                    //Log.i("address",address);
                    // Log.i("email",email);
                    // Log.i("phone",phone);

                    if(name==null)
                    {

                        alertCreate();


                       /* Intent i=new Intent(getActivity(),EditProfileActivity.class);

                        i.putExtra("number",phone);

                        Pair[] pair=new Pair[1];

                        pair[0]=new Pair<View,String>(profilepic,"imageTransfrom");

                        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(getActivity(),pair);

                        startActivity(i,options.toBundle());*/

                    }else
                    {

                        // Toast.makeText(getActivity(), "not null", Toast.LENGTH_SHORT).show();

                        Log.i("name",name);
                        Log.i("address",address);
                        Log.i("email",email);
                        Log.i("phone",phone);



                       // Profileaddress.setText(address);
                        nameAppoint.setText(name);
                      //  Profilenumber.setText(phone);
                       // Prifilemail.setText(email);

                        //  session.createLoginSession(userIds,"Patients");

                        session.createLoginSession(userIds,"Patient",name,phone,email,address);



                    }


                }
                else if(sucess.equals("0"))
                {

                    //  Toast.makeText(getActivity(), "not null", Toast.LENGTH_SHORT).show();


                    hidepDialog();

                }


            }

            @Override
            public void onFailure(Call<ProfileShowModel> call, Throwable t) {

                hidepDialog();
            }
        });



    }




}

       /* SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // Make sure user insert date into edittext in this format.
        Date dateObject;

        try{
            String dob=(From.getText().toString());
            dateObject = formatter.parse(dob);
            dates = new SimpleDateFormat("dd/MM/yyyy").format(dateObject);


        }catch (Exception e)
        {

        }

try{
            String dob=(To.getText().toString());
            dateObject = formatter.parse(dob);
            datess = new SimpleDateFormat("dd/MM/yyyy").format(dateObject);


        }catch (Exception e)
        {

        }*/







/*
try {
    Date d2=null;
    Date d1=null;

    try {
         d1 = format.parse(froms);
    } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    try {
         d2 = format.parse(tos);
    } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    long diff = d2.getTime() - d1.getTime();
    System.out.println("days:"+diff);


} catch (Exception e)
{
e.getStackTrace();
}
*/

       /* try{


            Date d1 = null;
            Date d2 = null;
            try {
                d1 = format.parse(froms);
                d2 = format.parse(tos);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long diff = d2.getTime() - d1.getTime();
            System.out.println("days:"+diff);
            //datechanged();
        }
        catch (Exception e){

        }
*/
// datechanged();










     /*   From.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

               // From.setEnabled(true);
                //From.setFocusable(true);

                if(event.getAction() == MotionEvent.ACTION_UP) {
                  //  if(event.getRawX() >= (From.getRight() - From.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        new DatePickerDialog(AppointmentActivity.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                       // return true;
                   // }
                }
                return false;
            }
        });



 To.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;


                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (To.getRight() - To.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        new DatePickerDialog(AppointmentActivity.this, date1, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                        return true;
                    }
                }
                return false;
            }
        });*/


/*
        From.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               // From.setText("DD/MM/YY");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //formtext();

            }

            @Override
            public void afterTextChanged(Editable s) {
                From.setText(froms);
                // From.setText();

            }
        });*/

       /* To.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               // To.setText("DD/MM/YY");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

               // totext();


            }

            @Override
            public void afterTextChanged(Editable s) {
                To.setText(tos);
                datechanged();



            }
        });*/


// date format