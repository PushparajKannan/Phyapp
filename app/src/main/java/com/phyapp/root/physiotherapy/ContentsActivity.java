package com.phyapp.root.physiotherapy;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContentsActivity extends AppCompatActivity {
    TextView start;
    CheckBox checkBox;
    LinearLayout ll;
    FrameLayout fm;
    Button Reg,sign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents);


        AppEULA app= new AppEULA(this);
        app.show();










        //checkBox=(CheckBox) findViewById(R.id.checkboxs);
        //start=(TextView) findViewById(R.id.Start);
        //ll=(LinearLayout) findViewById(R.id.LL_ca);

       // new AppEULA(ContentsActivity.this).show();
       // fm=(FrameLayout) findViewById(R.id.frm_layout);
      /*  checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

             //visibleStart();
                new AppEULA(ContentsActivity.this).show();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ContentsActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

    }
    public void visibleStart()
    {
        ll.setVisibility(View.INVISIBLE);
       // fm.setSystemUiVisibility(View.VISIBLE);
        start.setVisibility(View.VISIBLE);
*/

      sign=(Button) findViewById(R.id.login);
      Reg=(Button) findViewById(R.id.newregister);
      Reg.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i=new Intent(ContentsActivity.this,RegisterActivity.class);
             // Intent i=new Intent(ContentsActivity.this,PhyLoginActivity.class);
             // Intent i=new Intent(ContentsActivity.this,MapActivity.class);
              startActivity(i);
          }
      });
      sign.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i=new Intent(ContentsActivity.this,LoginActivity.class);
              startActivity(i);
          }
      });

    }
}
