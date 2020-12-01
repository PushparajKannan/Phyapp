package com.phyapp.root.physiotherapy;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PatientDetailsActivity extends AppCompatActivity {

    TextView Accept,Deny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        Accept=(TextView) findViewById(R.id.patient_accpet);
        Deny=(TextView) findViewById(R.id.patient_deny);


        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(PatientDetailsActivity.this,PhysiotherapiestActivity.class);
                startActivity(i);
                finish();
            }
        });

        Deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
