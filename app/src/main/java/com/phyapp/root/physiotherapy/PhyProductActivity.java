package com.phyapp.root.physiotherapy;

import android.app.AlertDialog;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.phyapp.root.physiotherapy.Adapters.ImageAdapter;
import com.phyapp.root.physiotherapy.ModelClass.ImageModel;
import com.phyapp.root.physiotherapy.Utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class PhyProductActivity extends AppCompatActivity implements ImageAdapter.ItemListener {

    RecyclerView recyclerView;
    private ArrayList<ImageModel> arrayList;
    AlertDialog pDialog;
    ImageAdapter adapter;
    String Language;
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phy_product);

        callTitleCenterFont();

        pDialog = new SpotsDialog(this,R.style.Custom);
        pDialog.setCancelable(false);

        session=new SessionManager(this);
        HashMap<String,String> user=session.getUserDetails();
        Language=user.get(session.KEY_USERS_Language);

showpDialog();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        arrayList = new ArrayList<ImageModel>();


        callProductsApi();





       /* arrayList.add(new ImageModel("physiothearapy"));
        arrayList.add(new ImageModel("Musculoskeltal"));
        arrayList.add(new ImageModel("Orthopdics"));
        arrayList.add(new ImageModel("Respiratory"));
        arrayList.add(new ImageModel("physiothearapy"));
        arrayList.add(new ImageModel("Neurological"));
        arrayList.add(new ImageModel("physiothearapy"));
        arrayList.add(new ImageModel("Respiratory"));
        arrayList.add(new ImageModel("Musculoskeletal"));
        arrayList.add(new ImageModel("Musculoskeletal"));
        arrayList.add(new ImageModel("Musculoskeletal"));
*/
         adapter = new ImageAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);


        /**
         AutoFitGridLayoutManager that auto fits the cells by the column width defined.
         **/

        /*AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);
        recyclerView.setLayoutManager(layoutManager);*/


        /**
         Simple GridLayoutManager that spans two columns
         **/
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    private void callProductsApi() {


        RequestQueue requestQueue= Volley.newRequestQueue(this);

        final String  url= APIConfig.MAIN_API+APIConfig.PRODUCT;

      //  http://nbayjobs.com/raksha/api/product.php?id=1&lang=English
      // final String url="http://nbayjobs.com/raksha/api/product.php";


        StringRequest postreq=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                hidepDialog();

                Log.d("Response", response);

                try {
                    JSONObject object=new JSONObject(response);

                    String  success, error;

                    success=object.getString("success");
                    error=object.getString("error");


                    JSONArray array=new JSONArray(object.getString("src"));

                    for(int i=0;i<array.length();i++)

                    {

                        ImageModel imgmodel=new ImageModel();

                        JSONObject arayobj=(JSONObject) array.get(i);

                        String pid,title,image;

                        pid=arayobj.getString("pid");
                        title=arayobj.getString("title");
                        image=arayobj.getString("image");

                        imgmodel.setId(pid);
                        imgmodel.setName(title);
                        imgmodel.setImage(image);

                        arrayList.add(imgmodel);

                        }


                        adapter=new ImageAdapter(PhyProductActivity.this,arrayList,null);



                   // adapter = new ImageAdapter(this,arrayList,this)this, arrayList, this);
                    recyclerView.setAdapter(adapter);

                    adapter.notifyDataSetChanged();






                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                hidepDialog();


                Log.d("Error.Response", error.toString());

            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("id", "1");
                params.put("lang", Language);
              //  params.put("domain", "http://itsalif.info");

                return params;
            }
        };


        requestQueue.add(postreq);
        postreq.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));





    }

   /* @Override
    public void onItemClick(ImageModel item) {


    }*/


    @Override
    public void onItemClick(ImageModel pName, int position) {

        //Intent i=new Intent(PhyProductActivity.this,ProductsDescriptionActivity.class);
       // i.putExtra("id",pName.getId());
       // startActivity(i);
      //  Toast.makeText(getApplicationContext(), pName.toString() + " is clicked", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemClick(ImageModel item) {
        Toast.makeText(getApplicationContext(), item.getName() + " is clicked", Toast.LENGTH_SHORT).show();

      //  Intent i=new Intent(PhyProductActivity.this,ProductsDescriptionActivity.class);
      //  startActivity(i);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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


    /*@Override
    public void onItemClick(ImageModel item) {

    }*/
}

