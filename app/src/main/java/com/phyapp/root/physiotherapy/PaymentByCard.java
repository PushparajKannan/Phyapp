package com.phyapp.root.physiotherapy;

import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.phyapp.root.physiotherapy.widget.SimpleDatePickerDialog;
import com.phyapp.root.physiotherapy.widget.SimpleDatePickerDialogFragment;

import java.util.Calendar;
import java.util.Locale;

public class PaymentByCard extends AppCompatActivity implements SimpleDatePickerDialog.OnDateSetListener {

TextView Months,Years;
Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_by_card);
        Months=(TextView) findViewById(R.id.Month);
        Years=(TextView) findViewById(R.id.Year);
     //   b=(Button) findViewById(R.id.btnnn);

       /* b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySimpleDatePickerDialogFragment();
            }
        });*/

        Months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySimpleDatePickerDialogFragment();
            }
        });
 Years.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySimpleDatePickerDialogFragment();
            }
        });








    }
    private void displaySimpleDatePickerDialogFragment() {
        SimpleDatePickerDialogFragment datePickerDialogFragment;
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        datePickerDialogFragment = SimpleDatePickerDialogFragment.getInstance(
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
        datePickerDialogFragment.setOnDateSetListener(this);
       datePickerDialogFragment.setStyle(DialogFragment.STYLE_NORMAL,R.style.AlertDialogTheme);
        datePickerDialogFragment.show(getSupportFragmentManager(),null);




      /*  // Get screen width and height in pixels
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // The absolute width of the available display size in pixels.
        int displayWidth = displayMetrics.widthPixels;
        // The absolute height of the available display size in pixels.
        int displayHeight = displayMetrics.heightPixels;

     //   Window dialogWindow = datePickerDialogFragment.getWindow();


        // Initialize a new window manager layout parameters
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        // Copy the alert dialog window attributes to new layout parameter instance
        layoutParams.copyFrom(datePickerDialogFragment.);
        layoutParams.copyFrom(datePickerDialogFragment.getActivity().getWindow().getAttributes());

        // Set the alert dialog window width and height
        // Set alert dialog width equal to screen width 90%
        // int dialogWindowWidth = (int) (displayWidth * 0.9f);
        // Set alert dialog height equal to screen height 90%
        // int dialogWindowHeight = (int) (displayHeight * 0.9f);

        // Set alert dialog width equal to screen width 70%
        int dialogWindowWidth = (int) (displayWidth * 0.7f);
        // Set alert dialog height equal to screen height 70%
        int dialogWindowHeight = (int) (displayHeight * 0.7f);

        // Set the width and height for the layout parameters
        // This will bet the width and height of alert dialog
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        // Apply the newly created layout parameters to the alert dialog window
        datePickerDialogFragment.getActivity().getWindow().setAttributes(layoutParams);

*/
    }

    @Override
    public void onDateSet(int year, int monthOfYear) {

        Months.setText(String.valueOf(monthOfYear));
        Years.setText(String.valueOf(year));



    }
}
