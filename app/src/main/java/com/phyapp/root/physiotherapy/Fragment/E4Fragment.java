package com.phyapp.root.physiotherapy.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phyapp.root.physiotherapy.Adapters.PatientHistoryAdapter;
import com.phyapp.root.physiotherapy.ModelClass.PatientHistoryModel;
import com.phyapp.root.physiotherapy.R;
import com.phyapp.root.physiotherapy.Retofit.ApiClient;
import com.phyapp.root.physiotherapy.Retofit.ApiInterface;
import com.phyapp.root.physiotherapy.Utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 23/3/18.
 */

public class E4Fragment extends Fragment {

    CardView welcome, otps, otpveri, Welcomes;
    Animation moveup;
    FloatingActionButton otplogin, otplogins;
    TextView skip, skips;
    ImageView close;


    PatientHistoryAdapter itemsAdapter;
    RecyclerView clv;

    ArrayList<PatientHistoryModel.Src> pname;
    SessionManager session;

    String patientid,Language;
    AlertDialog pDialog;
    LinearLayout layouts;

    Dialog pdialog;

    SwipeRefreshLayout mySwipeRefreshLayout;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_e4, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //  welcome=(CardView) findViewById(R.id.card_wel);

        session = new SessionManager(getActivity());
        layouts = (LinearLayout) view.findViewById(R.id.empty);

        HashMap<String, String> user = session.getUserDetails();

        patientid = user.get(session.KEY_USERID);
        Language = user.get(session.KEY_USERS_Language);

        pdialog = new Dialog(E4Fragment.this.getActivity());


        pname = new ArrayList<PatientHistoryModel.Src>();

        pDialog = new SpotsDialog(getContext(), R.style.Custom);

        pDialog.setCancelable(false);


        mySwipeRefreshLayout = view.findViewById(R.id.swiperefresh);

        mySwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1, R.color.refresh_progress_2);

        //  mySwipeRefreshLayout.setProgressBackgroundColorSchemeResource(Color.parseColor("#dd0000"));


        clv = (RecyclerView) view.findViewById(R.id.hisrecycle);


       /* HashMap<String,String> user=session.getUserDetails();

        doctorid=user.get(session.KEY_USERID);
*/

        Log.i("doc_id", patientid);

        layouts.setVisibility(View.VISIBLE);






      /*  pname.add(new RecyclerViewModel("neurological physiotherapy"));
        pname.add(new RecyclerViewModel("musculoskeletal outpatients"));
        pname.add(new RecyclerViewModel("orthopaedics"));
        pname.add(new RecyclerViewModel("respiratory"));
        pname.add(new RecyclerViewModel("Pilates"));
        pname.add(new RecyclerViewModel("Posture Correction"));
        pname.add(new RecyclerViewModel("Soft Tissue Injury Care"));
        pname.add(new RecyclerViewModel("Strength Exercises"));
        pname.add(new RecyclerViewModel("Glucosamine"));
        pname.add(new RecyclerViewModel("Vestibular Physio"));
        pname.add(new RecyclerViewModel("Swiss Ball Exercises"));
        pname.add(new RecyclerViewModel("TENS Machine"));
        pname.add(new RecyclerViewModel("Real Time Ultrasound"));
        pname.add(new RecyclerViewModel("Joint manipulation"));*/




/*
        int resId = R.anim.layout_animation_fall_down;
        int resIdright = R.anim.layout_animation_from_right;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);


        clv.setLayoutAnimation(animation);
*/

        //clv.setAdapter(itemsAdapter);

        itemsAdapter = new PatientHistoryAdapter(E4Fragment.this.getActivity(), pname, null);

        clv.setAdapter(itemsAdapter);


        clv.setLayoutManager(new LinearLayoutManager(E4Fragment.this.getActivity()));
        clv.setHasFixedSize(true);


        showpDialog();

        CallServiceListAPI(patientid,Language);

        // runLayoutAnimation(clv);
        //clv.setAdapter(itemsAdapter);


      /*  ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (viewHolder instanceof ClasicRecyclerAdapter.ViewHolder) {
                    // get the removed item name to display it in snack bar
                    String name = pname.get(viewHolder.getAdapterPosition()).getmItemName();

                    // backup of removed item for undo purpose
                    final RecyclerViewModel deletedItem = pname.get(viewHolder.getAdapterPosition());
                    final int deletedIndex = viewHolder.getAdapterPosition();

                    // remove the item from recycler view
                    itemsAdapter.removeItem(viewHolder.getAdapterPosition());

                    // showing snack bar with Undo option
                    Snackbar snackbar = Snackbar
                            .make(getView(), name + " removed from cart!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // undo is selected, restore the deleted item
                            itemsAdapter.restoreItem(deletedItem, deletedIndex);
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }

            }
        };*/

        //runLayoutAnimation(clv);

           /* @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
                if (viewHolder instanceof ClasicRecyclerAdapter.ViewHolder) {
                    // get the removed item name to display it in snack bar
                    String name = pname.get(viewHolder.getAdapterPosition()).getmItemName();

                    // backup of removed item for undo purpose
                    final RecyclerViewModel deletedItem = pname.get(viewHolder.getAdapterPosition());
                    final int deletedIndex = viewHolder.getAdapterPosition();

                    // remove the item from recycler view
                    itemsAdapter.removeItem(viewHolder.getAdapterPosition());

                    // showing snack bar with Undo option
                    Snackbar snackbar = Snackbar
                            .make(getView(), name + " removed from cart!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // undo is selected, restore the deleted item
                            itemsAdapter.restoreItem(deletedItem, deletedIndex);
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }
            }*/


       /* ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(clv);
*/



        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("LOg", "onRefresh called from SwipeRefreshLayout");

                // This method performs the actual data-refresh operation.
                // The method calls setRefreshing(false) when it's finished.
                myUpdateOperation();

                mySwipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    private void myUpdateOperation() {
        showpDialog();

        CallServiceListAPI(patientid,Language);
    }


    private void CallServiceListAPI(String id,String lang) {

        Log.i("doc_id", id);


      //  http://nbayjobs.com/raksha/api/notificationcomplete.php?pid=232&lang=English

        final ApiInterface service = ApiClient.getClient().create(ApiInterface.class);

        Call<PatientHistoryModel> descriptionCall = service.PatientCompletedhistorylist(id,lang);

        descriptionCall.enqueue(new Callback<PatientHistoryModel>() {
            @Override
            public void onResponse(Call<PatientHistoryModel> call, Response<PatientHistoryModel> response) {

                try {
                    Log.i("Responce", response.raw().toString());


                    String sucess="0", error;

                    sucess = String.valueOf(response.body().getSuccess());

                    if (sucess.equals("1")) {
                        hidepDialog();

                        pname = response.body().getSrc();

                        if (!pname.isEmpty() && pname.size() != 0) {

                            // hidepDialog();

                            if (layouts.getVisibility() == View.VISIBLE) {
                                //button.setVisibility(View.GONE);
                                layouts.setVisibility(View.GONE);

                            }

                            if (getActivity() != null) {

                                itemsAdapter = new PatientHistoryAdapter(E4Fragment.this.getActivity(), pname, null);

                                clv.setAdapter(itemsAdapter);

                                itemsAdapter.notifyDataSetChanged();

                                int resId = R.anim.layout_animation_fall_down;
                                int resIdright = R.anim.layout_animation_from_right;
                                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);


                                clv.setLayoutAnimation(animation);
                            } else {
                                // Toast.makeText(getActivity(), "null Activity", Toast.LENGTH_SHORT).show();
                            }

                        } else {

                            itemsAdapter = new PatientHistoryAdapter(E4Fragment.this.getActivity(), pname, null);

                            clv.setAdapter(itemsAdapter);

                            itemsAdapter.notifyDataSetChanged();

                        }


                    } else if (sucess.equals("0")) {
                        hidepDialog();

                        error = response.body().getErrorMsg();

                        Log.i("error", error);
                        pname.clear();
                        itemsAdapter.notifyDataSetChanged();



                    }
                }catch (Exception e)
                {
                    hidepDialog();

                }



                // itemsAdapter = new ClasicRecyclerAdapter(MainFragment.this.getActivity(), pname, null);

                // clv.setAdapter(itemsAdapter);

            }

            @Override
            public void onFailure(Call<PatientHistoryModel> call, Throwable t) {
                hidepDialog();


            }
        });


    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_right);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    private void showpDialog() {
        if (!pDialog.isShowing()) pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing()) pDialog.dismiss();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
