package com.phyapp.root.physiotherapy;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

public class ConsultForOthersActivity extends AppCompatActivity  {

    private Spinner spinner_states_activity;
    private Spinner spinner_cities_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_for_others);















    }
}
    //  spinner_states_activity = (Spinner)findViewById(R.id.spinner_states_main);
    //  spinner_states_activity.setOnItemSelectedListener(this);
    //adapter = ArrayAdapter.createFromResource(
    //  this, R.array.state_array, android.R.layout.simple_spinner_item);
    // adapter.setDropDownViewResource(R.layout.myspinner);
// my layout for spinners u can use urs or defalut. k?
    // spinner_states_activity.setAdapter(adapter);

    // spinner_cities_activity = (Spinner)findViewById(R.id.spinner_cities_main);
    // spinner_cities_activity.setOnItemSelectedListener(this);
       /* List l = new ArrayList();
        l.add("Select State");
        l.add("Tamil Nadu");
        l.add("Delhi");
        l.add("kerla");
        l.add("Andrapradesh");
        l.add("orisa");
        l.add("ksnnsn");
        l.add("ksnnsn");
        l.add("ksnnsn");
        l.add("Maharastra");
        l.add("Goa");
        l.add("Karnataka");
        ArrayAdapter ary = new ArrayAdapter(ConsultForOthersActivity.this, android.R.layout.simple_spinner_item, l);
        ary.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_states_activity.setAdapter(ary);






        spinner_states_activity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                List l = new ArrayList();
               // ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#757575"));
               // ((TextView) parent.getChildAt(0)).setTextSize(5);

                if (spinner_states_activity.getSelectedItem().toString().equals("Select State")) {

                    spinner_cities_activity.setVisibility(View.GONE);

                } else if (spinner_states_activity.getSelectedItem().toString().equals("Goa")) {
                    spinner_cities_activity.setVisibility(View.VISIBLE);
                    l.add("Panji");
                    l.add("Margao");
                    l.add("Vaco Da Gama");
                } else if (spinner_states_activity.getSelectedItem().toString().equals("Karnataka")) {

                    *//*view.animate()
                            .translationY(view.getHeight())
                            .alpha(0.0f)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    spinner_cities_activity.setVisibility(View.VISIBLE);
                                }
                            });*//*

                    spinner_cities_activity.setVisibility(View.VISIBLE);


                    l.add("Bengluru");
                    l.add("Manglore");
                    l.add("Hubli");
                }else  if (spinner_states_activity.getSelectedItem().toString().equals("Maharastra"))
                {

                    spinner_cities_activity.setVisibility(View.VISIBLE);
                    l.add("mumbai");
                    l.add("madurai");
                    l.add("mumbai");
                    l.add("mumbai");
                    l.add("mumbai");
                }else  if (spinner_states_activity.getSelectedItem().toString().equals("tamil"))
                {
                    spinner_cities_activity.setVisibility(View.VISIBLE);
                    l.add("Mumbai");
                    l.add("Pune");
                    l.add("Nagpur");
                }
                ArrayAdapter ary = new ArrayAdapter(ConsultForOthersActivity.this, android.R.layout.simple_spinner_item, l);
                ary.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_cities_activity.setAdapter(ary);
            }

            @Override
            public void onNothingSelected(AdapterView parent) {

            }
        });*/



//and in function onItemSelected

       /* int pos,pos2;
        pos = spinner_states_activity.getSelectedItemPosition();
        int iden=parent.getId();
        if(iden==R.id.spinner_states_main)
        {
            pos2 = 0;
            switch (pos)
            {
                case 0: unit_adapter= ArrayAdapter.createFromResource(
                        this, R.array.States1Cities_array, android.R.layout.simple_spinner_item);
                    break;
                case 1: unit_adapter= ArrayAdapter.createFromResource(
                        this, R.array.States3Cities_array, android.R.layout.simple_spinner_item);
                    break;
                // all the StatesxCities entires....
                default:
                    break;
            }

            spinner_cities_activity.setAdapter(unit_adapter);
            unit_adapter.setDropDownViewResource(R.layout.myspinner);
        }*/



   /* @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
*/