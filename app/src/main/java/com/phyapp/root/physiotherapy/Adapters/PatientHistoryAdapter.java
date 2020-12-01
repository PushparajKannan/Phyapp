package com.phyapp.root.physiotherapy.Adapters;

import android.app.Activity;
import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phyapp.root.physiotherapy.ModelClass.PatientHistoryModel;
import com.phyapp.root.physiotherapy.R;

import java.util.ArrayList;

//public class PatientHistoryAdapter {





    public class PatientHistoryAdapter  extends RecyclerView.Adapter<com.phyapp.root.physiotherapy.Adapters.PatientHistoryAdapter.ViewHolder> {
        private static final String LOG_TAG = com.phyapp.root.physiotherapy.Adapters.ClasicRecyclerAdapter.class.getSimpleName();
        private ArrayList<PatientHistoryModel.Src> mItems;
        com.phyapp.root.physiotherapy.Adapters.PatientHistoryAdapter.ItemListener mListener;
        int animID= R.anim.layout_animation_from_right;
        Context context;
        public static int position;
        private int expandedPosition = -1;
        private int mExpandedPosition = -1;
        private int lastPosition = -1;


        public  PatientHistoryAdapter(Activity context, ArrayList<PatientHistoryModel.Src> program, com.phyapp.root.physiotherapy.Adapters.PatientHistoryAdapter.ItemListener listener) {

            this.context = context;
            mItems = program;
            mListener = listener;
        }

        public void setOnItemClickListener(com.phyapp.root.physiotherapy.Adapters.PatientHistoryAdapter.ItemListener listener) {
            mListener = listener;
        }

        @Override
        public com.phyapp.root.physiotherapy.Adapters. PatientHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.history_list_phy, parent, false);
            context = parent.getContext();
            return new com.phyapp.root.physiotherapy.Adapters. PatientHistoryAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final com.phyapp.root.physiotherapy.Adapters. PatientHistoryAdapter.ViewHolder holder, final int position) {
            holder.setData(mItems.get(position));

            setAnimation(holder.itemView, position);
/*

        if (position == expandedPosition) {
            holder.llExpandArea.setVisibility(View.VISIBLE);
        } else {
            holder.llExpandArea.setVisibility(View.GONE);
        }

*//*
            final String name = holder.pName.getmItemName().toString();



            final boolean isExpanded = position==mExpandedPosition;
            holder.llExpandArea.setVisibility(isExpanded?View.VISIBLE:View.GONE);
            holder.itemView.setActivated(isExpanded);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                //  public android.view.ViewGroup recyclerView;

                @Override
                public void onClick(View v) {
                    mExpandedPosition = isExpanded ? -1:position;
                    TransitionManager.beginDelayedTransition(holder.llExpandArea);
                    notifyDataSetChanged();
                    Toast.makeText(context, ""+holder.pName.getmItemName(), Toast.LENGTH_SHORT).show();

                }
            });


            holder.Appoinment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context,AppointmentActivity.class);
                    i.putExtra("nameoftheropy",name);
                    context.startActivity(i);
                    Toast.makeText(context, "Appoinment", Toast.LENGTH_SHORT).show();
                }
            });

*/




       /* holder.Itemone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "hi"+holder.pName.getmItemName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.Itemtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "how r u", Toast.LENGTH_SHORT).show();
            }
        });
        holder.tilldate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "till", Toast.LENGTH_SHORT).show();
            }
        });

        holder.Appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,AppointmentActivity.class);
                i.putExtra("nameoftheropy",name);
                context.startActivity(i);
                Toast.makeText(context, "Appoinment", Toast.LENGTH_SHORT).show();
            }
        });*/


        }

   /* @Override
    public void onClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        RecyclerViewModel theString = mItems.get(holder.getPosition());

        // Check for an expanded view, collapse if you find one
        if (expandedPosition >= 0) {
            int prev = expandedPosition;
            notifyItemChanged(prev);
        }
        // Set the current position to "expanded"
        expandedPosition = holder.getPosition();
        notifyItemChanged(expandedPosition);

      //  Toast.makeText(mContext, "Clicked: "+theString, Toast.LENGTH_SHORT).show();
    }*/


        @Override
        public int getItemCount() {
            if (mItems != null) {
                return mItems.size();
            }
            return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            //  private final LinearLayout llExpandArea;
            public CardView cardView;
            public PatientHistoryModel.Src pName;
            TextView patienname,date,address,time,service,subservice,extra;
            View textContainer;
            ArrayList<PatientHistoryModel.Src.Item> listser=new ArrayList<PatientHistoryModel.Src.Item>();

            //TextView Itemone,Itemtwo,Appointment,tilldate;
            Button Appoinment;
            LinearLayout adser;

            public ViewHolder(View itemView) {

                super(itemView);
                itemView.setOnClickListener(this);
                // cardView = (CardView) itemView.findViewById(R.id.history_card);
                patienname = (TextView) itemView.findViewById(R.id.phy_his_patientname);
                date = (TextView) itemView.findViewById(R.id.phy_his_date);
                address = (TextView) itemView.findViewById(R.id.phy_his_location);
                time = (TextView) itemView.findViewById(R.id.phy_his_time);
                service = (TextView) itemView.findViewById(R.id.phy_his_service);
                subservice = (TextView) itemView.findViewById(R.id.phy_his_subservice);
                extra = (TextView) itemView.findViewById(R.id.phy_extra_sercives);
                adser = (LinearLayout) itemView.findViewById(R.id.addtionaservices);
                //textContainer = itemView.findViewById(R.id.text_container);
                //  llExpandArea = (LinearLayout) itemView.findViewById(R.id.llExpandArea);
           /* Itemone=(TextView) itemView.findViewById(R.id.itone);
            Itemtwo=(TextView) itemView.findViewById(R.id.ittwo);
            Appointment=(TextView) itemView.findViewById(R.id.ittwo1);
            tilldate=(TextView) itemView.findViewById(R.id.itone1);*/

                // Appoinment = (Button) itemView.findViewById(R.id.appoinment);


            }

            public void setData(PatientHistoryModel.Src pName) {
                this.pName = pName;


                listser=pName.getItem();
                if(listser!=null)

                {

                    if(listser.size()>=0) {

                        adser.setVisibility(View.VISIBLE);


                        StringBuffer stringBuffer = new StringBuffer();
                        for (int i = 0; i < listser.size(); i++) {

                            stringBuffer.append(listser.get(i).getSid() + "\t(" + listser.get(i).getIid() + ")" + "\n");

                            //  stringBuffer.append(listser.get(i).getMid()+"\n");
                            // stringBuffer.append(listser.get(i).getSid()+"\n");

                        }

                        extra.setText(stringBuffer.toString());
                    }



                }




                patienname.setText(pName.getAssignedPhysiotherapist());
                date.setText(pName.getAppointmentDate());
                address.setText(pName.getAddress());
                service.setText(pName.getSid());
                subservice.setText(pName.getIid());
                time.setText(pName.getAppointmentTime());

            }

            @Override
            public void onClick(View v) {


                if (mListener != null) {


               /* if (expandedPosition >= 0) {
                    int prev = expandedPosition;
                    notifyItemChanged(prev);
                }
                // Set the current position to "expanded"
                expandedPosition = getAdapterPosition();
                notifyItemChanged(expandedPosition);*/
                    mListener.onItemClick(pName, getAdapterPosition());
                }

                // Toast.makeText(context, pName.getmItemName(), Toast.LENGTH_SHORT).show();





            }

           /* @Override
            public void preAnimateAddImpl(RecyclerView.ViewHolder holder) {

            }

            @Override
            public void preAnimateRemoveImpl(RecyclerView.ViewHolder holder) {

            }

            @Override
            public void animateAddImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {

                ViewCompat.animate(itemView)
                        .translationY(0)
                        .alpha(1)
                        .setDuration(300)
                        .setListener(listener)
                        .start();

            }

            @Override
            public void animateRemoveImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {

            }
        }*/
        }

        public interface ItemListener {
            void onItemClick(PatientHistoryModel.Src pName, int position);
        }

        private void setAnimation(View viewToAnimate, int position)
        {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition)
            {
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_from_right);
                viewToAnimate.startAnimation(animation);
                // lastPosition = position;
            }
        }


        private void runLayoutAnimation(final RecyclerView recyclerView) {
            final Context context = recyclerView.getContext();
            final LayoutAnimationController controller =
                    AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

            recyclerView.setLayoutAnimation(controller);
            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();
        }


    }







