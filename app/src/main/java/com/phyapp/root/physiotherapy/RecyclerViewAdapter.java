package com.phyapp.root.physiotherapy;

/**
 * Created by root on 12/3/18.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phyapp.root.physiotherapy.ModelClass.MenuSubListModel;
import com.phyapp.root.physiotherapy.Utils.SessionManager;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String LOG_TAG = RecyclerViewAdapter.class.getSimpleName();
    private ArrayList<MenuSubListModel.Src> mItems;
    ItemListener mListener;
    Context context;
    public static int position;
    private int expandedPosition = -1;
    private int mExpandedPosition = -1;
    private int lastPosition = -1;

    SessionManager session;
    String UserID;
    String data;
    String mainID;
    String submenuid;


  /*  public RecyclerViewAdapter(Activity context, ArrayList<RecyclerViewModel> program, ItemListener listener) {

        this.context = context;
        mItems = program;
        mListener = listener;
    }*/

    public RecyclerViewAdapter(ConsultForMeActivity context, ArrayList<MenuSubListModel.Src> program, Object listener,String userID,String sid) {

        this.context = context;
        mItems = program;
        mListener = (ItemListener) listener;

        UserID=userID;
       // mainID=mainId;
        submenuid=sid;

       // HashMap<String,String> user=session.getUserDetails();

       // Language=user.get(session.KEY_USERS_Language);

       // UserID=user.get(session.KEY_USERID);

        data="user";

        if(UserID==null||UserID.isEmpty())
        {
          data="nouser";
        }

    }

    public void setOnItemClickListener(ItemListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_items, parent, false);
        context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setData(mItems.get(position));

        setAnimation(holder.itemView, position);
/*

        if (position == expandedPosition) {
            holder.llExpandArea.setVisibility(View.VISIBLE);
        } else {
            holder.llExpandArea.setVisibility(View.GONE);
        }

*/
        final String name = holder.pName.getTitle().toString();

       // holder.name.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_keyboard_arrow_up,0);




        final boolean isExpanded = position==mExpandedPosition;
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
              //  Toast.makeText(context, ""+holder.pName.getTitle(), Toast.LENGTH_SHORT).show();

               // holder.name.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_keyboard_arrow_up,0);
            }
        });


        holder.Appoinment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(data.equals("nouser")){

                    alertCreate();

                }
                else
                    {

                    Intent i = new Intent(context, AppointmentActivity.class);
                    i.putExtra("nameoftheropy", name);
                    i.putExtra("subid",submenuid);
                    i.putExtra("iid",holder.pName.getIid());
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    //  Toast.makeText(context, "Appoinment", Toast.LENGTH_SHORT).show();
                }
            }
        });






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
       // alert.create();

        AlertDialog alrt= alert.create();
        alrt.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        alrt.show();
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
        private final LinearLayout llExpandArea;
        public CardView cardView;
        public MenuSubListModel.Src pName;
        TextView name;
        View textContainer;
        //TextView Itemone,Itemtwo,Appointment,tilldate;
        TextView Appoinment;

        public ViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);
            cardView = (CardView)itemView.findViewById(R.id.cvItem);
            name = (TextView) itemView.findViewById(R.id.list_texts);
            textContainer = itemView.findViewById(R.id.text_container);
            llExpandArea = (LinearLayout) itemView.findViewById(R.id.llExpandArea);
           /* Itemone=(TextView) itemView.findViewById(R.id.itone);
            Itemtwo=(TextView) itemView.findViewById(R.id.ittwo);
            Appointment=(TextView) itemView.findViewById(R.id.ittwo1);
            tilldate=(TextView) itemView.findViewById(R.id.itone1);*/

           Appoinment=(TextView) itemView.findViewById(R.id.appoinment);



        }

        public void setData(MenuSubListModel.Src pName) {
            this.pName = pName;

            name.setText(pName.getTitle());
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

         //   Toast.makeText(context,pName.getTitle(),Toast.LENGTH_SHORT).show();

        }

    }

    public interface ItemListener {
        void onItemClick(MenuSubListModel.Src pName, int position);
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


}