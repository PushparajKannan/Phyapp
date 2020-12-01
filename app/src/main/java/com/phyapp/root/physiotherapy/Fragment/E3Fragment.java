package com.phyapp.root.physiotherapy.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.phyapp.root.physiotherapy.APICONFIG.APIConfig;
import com.phyapp.root.physiotherapy.R;
import com.phyapp.root.physiotherapy.Utils.SessionManager;

import java.util.HashMap;

import dmax.dialog.SpotsDialog;

/**
 * Created by root on 23/3/18.
 */

public class E3Fragment extends Fragment {

    WebView helpcontent;

    SessionManager session;

    AlertDialog pDialog;

    String Language;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_e3,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        session = new SessionManager(getActivity());

        helpcontent=(WebView) view.findViewById(R.id.webview_help);


        pDialog=new SpotsDialog(getContext(),R.style.Custom);
        pDialog.setCancelable(false);

        showpDialog();

        HashMap<String,String> user=session.getUserDetails();

        Language=user.get(SessionManager.KEY_USERS_Language);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

               // http://phyapp.webtoall.in/help.php?lang=English


                String s= APIConfig.Helplinknbays+Language;

              //  String s="http://phyapp.webtoall.in/help.php?lang="+Language;

                helpcontent.setWebViewClient(new WebViewClient());
                helpcontent.loadUrl(s);

                helpcontent.setWebViewClient(new WebViewClient() {

                    public void onPageFinished(WebView view, String url) {
                        // do your stuff here
                        hidepDialog();

                    }

                    @Override
                    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                        super.onReceivedError(view, request, error);
                        hidepDialog();
                    }

                });
              //  helpcontent.loadUrl(s,);


            }
        },1000);






        // http://nbayjobs.com/raksha/help.php?lang=English



    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
