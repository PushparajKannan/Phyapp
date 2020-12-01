package com.phyapp.root.physiotherapy;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PhyNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phy_notification);
        final RecyclerView clv = (RecyclerView) findViewById(R.id.cli);

        final ArrayList<RecyclerViewModel> pname = new ArrayList<RecyclerViewModel>();
        pname.add(new RecyclerViewModel("Neurological Physiotherapy"));
        pname.add(new RecyclerViewModel("Musculoskeletal Outpatients"));
        pname.add(new RecyclerViewModel("Orthopaedics"));
        pname.add(new RecyclerViewModel("Respiratory"));
        pname.add(new RecyclerViewModel("Neurological Physiotherapy"));
        pname.add(new RecyclerViewModel("Musculoskeletal Outpatients"));
        pname.add(new RecyclerViewModel("Orthopaedics"));
        pname.add(new RecyclerViewModel("Physiotherapy"));
        pname.add(new RecyclerViewModel("Musculoskeletal"));
        pname.add(new RecyclerViewModel("JRespiratory"));
        pname.add(new RecyclerViewModel("Orthopaedics"));
        pname.add(new RecyclerViewModel("Musculoskeletal"));
        pname.add(new RecyclerViewModel("Outpatients"));
        pname.add(new RecyclerViewModel("Nougat"));

       /* final RecyclerViewAdapter itemsAdapter = new RecyclerViewAdapter(PhyNotificationActivity.this, pname, null);
        clv.setLayoutManager(new LinearLayoutManager(PhyNotificationActivity.this));
        clv.setHasFixedSize(true);
        clv.setAdapter(itemsAdapter);*/



    }
}
