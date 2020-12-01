package com.phyapp.root.physiotherapy.DialogFragment;

//public class FullScreenDialog {

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phyapp.root.physiotherapy.R;

public class FullScreenDialog extends DialogFragment {

        public static final String TAG = "FullScreenDialog";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
           // setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
        }

        @Override
        public void onStart() {
            super.onStart();

            Dialog dialog = getDialog();
            if (dialog != null) {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setLayout(width, height);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle state) {
            super.onCreateView(inflater, parent, state);

            View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_layout, parent, false);
            return view;
        }
    }


