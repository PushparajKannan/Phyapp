package com.phyapp.root.physiotherapy.Adapters;

//public class ImageAdapter {
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.phyapp.root.physiotherapy.ModelClass.ImageModel;
import com.phyapp.root.physiotherapy.ProductsDescriptionActivity;
import com.phyapp.root.physiotherapy.R;

import java.util.ArrayList;


   // public class ImageAdapter extends RecyclerView.Adapter {

        public class ImageAdapter  extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
            private ItemListener mListener;
            //   private static final String LOG_TAG = com.example.root.a5aab.Adapters.ClasicRecyclerAdapter.class.getSimpleName();
            private ArrayList<ImageModel> mItems;

            int animID= R.anim.layout_animation_from_right;
            Context context;
            public static int position;
            private int expandedPosition = -1;
            private int mExpandedPosition = -1;
            private int lastPosition = -1;


            public ImageAdapter(Activity context, ArrayList<ImageModel> program, ImageAdapter.ItemListener listener) {

                this.context = context;
                mItems = program;
                mListener = listener;



            }

          /*  public ImageAdapter(Response.Listener<String> listener, ArrayList<ImageModel> arrayList, Response.Listener<String> listener1) {
            }*/


            public void setOnItemClickListener(ImageAdapter.ItemListener listener) {

                mListener = listener;
            }

            @Override
            public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
            {
                View v = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.products_list, parent, false);
                context = parent.getContext();
                return new ImageAdapter.ViewHolder(v);
            }

            @Override
            public void onBindViewHolder(final ImageAdapter.ViewHolder holder, final int position)
            {

                holder.setData(mItems.get(position));

                //setAnimation(holder.itemView, position);

            }


            @Override
            public int getItemCount() {
                if (mItems != null) {
                    return mItems.size();
                }
                return 0;
            }

            public interface ItemListener {
                void onItemClick(ImageModel pName, int position);


                void onItemClick(ImageModel item);
            }

            public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

                public CardView cardView;
                public ImageModel pName;
                ImageView imgview;
                TextView name,historydate,historydrop,historypickup,historytime,historykm;
                View textContainer;


                public ViewHolder(View itemView) {
                    super(itemView);

                    itemView.setOnClickListener(this);
                    cardView = (CardView) itemView.findViewById(R.id.cardView);
                    name = (TextView) itemView.findViewById(R.id.textViewpro);
                    imgview=(ImageView) itemView.findViewById(R.id.imageViewpro);
                   // historydate=(TextView) itemView.findViewById(R.id.histroy_date);
                   // historydrop=(TextView) itemView.findViewById(R.id.history_drop);
                   // historypickup=(TextView) itemView.findViewById(R.id.history_pickup);
                   // historytime=(TextView) itemView.findViewById(R.id.history_time);
                    //historykm=(TextView) itemView.findViewById(R.id.history_km);

                }

                @Override
                public void onClick(View v) {

                    if (mListener != null) {


                        mListener.onItemClick(pName, getAdapterPosition());
                    }

                   // Toast.makeText(context, pName.getName(), Toast.LENGTH_SHORT).show();

                    Log.i("id",pName.getId());

                    Intent i=new Intent(context, ProductsDescriptionActivity.class);

                    i.putExtra("id",pName.getId());
                    context.startActivity(i);


                }

                public void setData(ImageModel pName) {

                    this.pName = pName;


                    String proimage;

                    name.setText(pName.getName());

                    proimage=pName.getImage();


                    Glide.with(context)
                            .load(proimage)
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.noimage).into(imgview);
                           /* .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
                                                       boolean isFromMemoryCache, boolean isFirstResource) {


                           // load.dismiss();
                            return false;
                        }
                    })*/
                           // .transform(new CircleTransform(getApplicationContext()))




                   /* historydate.setText(pName.getDate());
                    historydrop.setText(pName.getTo());
                    historytime.setText(pName.getTime());
                    historypickup.setText(pName.getFrom());
                    historykm.setText(pName.getKm()+" "+"km");
*/

                }
            }
        }

          /*  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
                //  private final LinearLayout llExpandArea;

                //TextView Itemone,Itemtwo,Appointment,tilldate;
                Button Appoinment;

                public ViewHolder(View itemView) {

                    super(itemView);
                    itemView.setOnClickListener(this);
                    cardView = (CardView) itemView.findViewById(R.id.history_card);
                    name = (TextView) itemView.findViewById(R.id.history_name);
                    historydate=(TextView) itemView.findViewById(R.id.histroy_date);
                    historydrop=(TextView) itemView.findViewById(R.id.history_drop);
                    historypickup=(TextView) itemView.findViewById(R.id.history_pickup);
                    historytime=(TextView) itemView.findViewById(R.id.history_time);
                    historykm=(TextView) itemView.findViewById(R.id.history_km);


                }

               *//* public void setData(RideList pName) {

                }*//*

                @Override
                public void onClick(View v) {


                    if (mListener != null) {


                        mListener.onItemClick(pName, getAdapterPosition());
                    }

                    Toast.makeText(context, pName.getName(), Toast.LENGTH_SHORT).show();




                }



           *//* public interface ItemListener {
                void onItemClick(RideList pName, int position);
            }*//*

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

   // }

*/