package com.phyapp.root.physiotherapy.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.transition.TransitionManager;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phyapp.root.physiotherapy.AppointmentActivity;
import com.phyapp.root.physiotherapy.ConsultForMeActivity;
import com.phyapp.root.physiotherapy.ModelClass.MenuCategoryModel;
import com.phyapp.root.physiotherapy.R;
import com.phyapp.root.physiotherapy.RegisterActivity;

import java.util.ArrayList;


    public class MaincatogrylistAdapter  extends RecyclerView.Adapter<com.phyapp.root.physiotherapy.Adapters.MaincatogrylistAdapter.ViewHolder> {
        private static final String LOG_TAG = com.phyapp.root.physiotherapy.Adapters.MaincatogrylistAdapter.class.getSimpleName();
        private ArrayList<MenuCategoryModel.Src> mItems;
        com.phyapp.root.physiotherapy.Adapters.MaincatogrylistAdapter.ItemListener mListener;
        int animID= R.anim.layout_animation_from_right;
        Context context;
        public static int position;
        private int expandedPosition = -1;
        private int mExpandedPosition = -1;
        private int lastPosition = -1;
        Dialog alert;
        String data;
        String MainID;

        String useravail,loginstat;



        public  MaincatogrylistAdapter(Activity context, ArrayList<MenuCategoryModel.Src> program, ItemListener listener,String useraID,String mainId) {

            this.context = context;
            mItems = program;
            mListener = listener;
            alert=new Dialog(context,R.style.CustomDialogAnimationss);
            useravail=useraID;
            MainID=mainId;


            loginstat="user";

            if(useravail==null||useravail.isEmpty())
            {
                loginstat="nouser";
            }


        }

        public void setOnItemClickListener(com.phyapp.root.physiotherapy.Adapters.MaincatogrylistAdapter.ItemListener listener) {
            mListener = listener;
        }

        @Override
        public com.phyapp.root.physiotherapy.Adapters. MaincatogrylistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.listoftheraopy, parent, false);
            context = parent.getContext();
            return new com.phyapp.root.physiotherapy.Adapters. MaincatogrylistAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final com.phyapp.root.physiotherapy.Adapters. MaincatogrylistAdapter.ViewHolder holder, final int position) {
            holder.setData(mItems.get(position));

            setAnimation(holder.itemView, position);

            final boolean isExpanded = position==mExpandedPosition;

            /*holder.llExpandArea.animate()
                    .alpha(0.0f)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            holder.llExpandArea.setVisibility(isExpanded?View.VISIBLE:View.GONE);                        }
                    });
*/

            holder.llExpandArea.setVisibility(isExpanded?View.VISIBLE:View.GONE);
            holder.itemView.setActivated(isExpanded);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                //  public android.view.ViewGroup recyclerView;

                @Override
                public void onClick(View v) {
                    holder.name.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_keyboard_arrow_up,0);

                    mExpandedPosition = isExpanded ? -1:position;
                    TransitionManager.beginDelayedTransition(holder.llExpandArea);
                    notifyDataSetChanged();

                 //   Toast.makeText(context, ""+holder.pName.getTitle(), Toast.LENGTH_SHORT).show();

                   // Toast.makeText(context, ""+holder.pName.getSid(), Toast.LENGTH_SHORT).show();

                    data=holder.pName.getTitle();

                    // holder.name.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_keyboard_arrow_up,0);
                }
            });


            holder.Appoinment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent i=new Intent(context,AppointmentActivity.class);
                   // i.putExtra("nameoftheropy",name);
                    context.startActivity(i);*/


                    Intent i=new Intent(context,  ConsultForMeActivity.class);
                   // i.putExtra("mid",MainID);
                    i.putExtra("sid",holder.pName.getSid().toString());

                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);

                  //  Toast.makeText(context, "Appoinment", Toast.LENGTH_SHORT).show();
                }
            });


            holder.gethelp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //String holder.pName.getmItemName()

                    dialogView(data,holder.pName.getSid().toString());

                }
            });





        }

        private void dialogView(final String name, final String sid) {

            final EditText comment;
            TextView getAppoint;


            alert.setContentView(R.layout.alertcomment);

            comment=alert.findViewById(R.id.edit_comment_service_comment);
            getAppoint=alert.findViewById(R.id.text_get);

            getAppoint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(loginstat.equals("nouser"))
                    {
                        alertCreate();
                        alert.dismiss();
                    }
                    else
                        {

                        Intent i = new Intent(context, AppointmentActivity.class);
                        i.putExtra("nameoftheropy", name);
                        i.putExtra("subid",sid);
                        i.putExtra("comments",comment.getText().toString());
                        context.startActivity(i);
                        alert.dismiss();
                    }

                }
            });


            alert.show();

            alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

          //  AlertDialog.Builder alert=new AlertDialog.Builder(context);



        }


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
            public MenuCategoryModel.Src pName;
            TextView name;
            LinearLayout llExpandArea;
            View textContainer;
            //TextView Itemone,Itemtwo,Appointment,tilldate;
            TextView Appoinment,gethelp;

            public ViewHolder(View itemView) {

                super(itemView);
                itemView.setOnClickListener(this);
                cardView = (CardView) itemView.findViewById(R.id.listses_card);
                name = (TextView) itemView.findViewById(R.id.listses_name);
                llExpandArea = (LinearLayout) itemView.findViewById(R.id.llExpandArea_service);
                //textContainer = itemView.findViewById(R.id.text_container);
                //  llExpandArea = (LinearLayout) itemView.findViewById(R.id.llExpandArea);
           /* Itemone=(TextView) itemView.findViewById(R.id.itone);
            Itemtwo=(TextView) itemView.findViewById(R.id.ittwo);
            Appointment=(TextView) itemView.findViewById(R.id.ittwo1);
            tilldate=(TextView) itemView.findViewById(R.id.itone1);*/

                 Appoinment = (TextView) itemView.findViewById(R.id.morality);
                 gethelp = (TextView) itemView.findViewById(R.id.get_help);


            }

            public void setData(MenuCategoryModel.Src pName) {
                this.pName = pName;

                name.setText(pName.getTitle());
            }

            @Override
            public void onClick(View v) {


                if (mListener != null) {



                    mListener.onItemClick(pName, getAdapterPosition());
                }

               // Toast.makeText(context, pName.getTitle(), Toast.LENGTH_SHORT).show();

              //  Intent i=new Intent(context,  ConsultForMeActivity.class);

               // context.startActivity(i);




            }


        }

        public interface ItemListener {
            void onItemClick(MenuCategoryModel.Src pName, int position);
        }

        private void setAnimation(View viewToAnimate, int position)
        {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition)
            {
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_from_right);
                viewToAnimate.startAnimation(animation);
                 lastPosition = position;
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

        private void alertCreate() {

            AlertDialog.Builder alert=new AlertDialog.Builder(context,R.style.CustomDialogTheme);
            alert.setTitle(Html.fromHtml("<font color='#dd0000'>Alert!</font>"));
            alert.setMessage(Html.fromHtml("<font color='#000000'>You must login to book an Appointment</font>"));
            alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i=new Intent(context,RegisterActivity.class);
                    context.startActivity(i);

                }
            });
            alert.setNegativeButton("Cancel",null);
           AlertDialog alrt= alert.create();
            alrt.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;

            alrt.show();
        }




    }








