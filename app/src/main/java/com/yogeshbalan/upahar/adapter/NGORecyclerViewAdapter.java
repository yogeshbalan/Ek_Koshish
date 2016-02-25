package com.yogeshbalan.upahar.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.yogeshbalan.upahar.R;
import com.yogeshbalan.upahar.Utils;
import com.yogeshbalan.upahar.activity.NGO_DetailActivity;
import com.yogeshbalan.upahar.model.NGO;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yogesh on 11/2/16.
 */
public class NGORecyclerViewAdapter extends RecyclerView.Adapter<NGORecyclerViewAdapter.ViewHolder> {


    List<NGO> list;
    Context context;
    View myView;

    public NGORecyclerViewAdapter(List<NGO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ngo_list_item, null);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.renderView(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, desc, slogan;
        ImageView banner;
        CircleImageView logo;
        CardView cardView;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            myView = itemView;
            cardView = (CardView) itemView.findViewById(R.id.mycard);
            title = (TextView) itemView.findViewById(R.id.tvNGO_Name);
            desc = (TextView) itemView.findViewById(R.id.tvEventDesc);
            slogan = (TextView) itemView.findViewById(R.id.tvNGOSlogan);
            banner = (ImageView) itemView.findViewById(R.id.ivEventImage);
            logo = (CircleImageView) itemView.findViewById(R.id.ngo_logo);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layout_card);
        }

        public void renderView(final NGO ngo) {
            title.setText(ngo.getName());
            slogan.setText(ngo.getSlogan());
            desc.setText(ngo.getDescription().substring(0, 30) + "...");
            Picasso.with(context).load(ngo.getLogoUrl()).placeholder(R.drawable.image_ngo).into(logo, new Callback() {
                @Override
                public void onSuccess() {
                    cardView.setCardBackgroundColor(Utils.getVibrantColor(logo, context));
                    linearLayout.setBackgroundColor(Utils.getVibrantColor(logo, context));
                }

                @Override
                public void onError() {

                }
            });
            if (ngo.getImageUrls().get(0) != "none"){
                Picasso.with(context).load(ngo.getImageUrls().get(0)).placeholder(R.drawable.placeholder).into(banner);
            }
            //Log.v("test", "memberID = " + String.valueOf(myPrefs.getString("favcy_member", "")));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NGO_DetailActivity.class);
                    intent.putExtra("ngo", ngo);
                    context.startActivity(intent);
                    Toast.makeText(context, "added 50 points", Toast.LENGTH_SHORT);
                }
            });
        }

    }

}
