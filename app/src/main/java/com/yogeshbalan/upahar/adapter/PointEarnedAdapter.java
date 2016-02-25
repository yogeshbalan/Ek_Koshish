package com.yogeshbalan.upahar.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.yogeshbalan.upahar.R;
import com.yogeshbalan.upahar.Utils;
import com.yogeshbalan.upahar.model.UserLB;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yogesh on 13/2/16.
 */
public class PointEarnedAdapter extends RecyclerView.Adapter<PointEarnedAdapter.ViewHolder> {


    Context context;
    List<UserLB> list;
    View myView;

    public PointEarnedAdapter(List<UserLB> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.point_earned_list_item, null);
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
        public TextView ngoName;
        CircleImageView logo;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            myView = itemView;
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            ngoName = (TextView) itemView.findViewById(R.id.ngo_name);
            logo = (CircleImageView) itemView.findViewById(R.id.imageView);
        }

        public void renderView(final UserLB ngo) {
            ngoName.setText(ngo.getNgo());
            Picasso.with(context).load(ngo.getLogo_url()).placeholder(R.drawable.image_ngo).into(logo, new Callback() {
                @Override
                public void onSuccess() {
                    ngoName.setTextColor(Utils.getVibrantColor(logo, context));
                }

                @Override
                public void onError() {

                }
            });

            /*//Log.v("test", "memberID = " + String.valueOf(myPrefs.getString("favcy_member", "")));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NGO_DetailActivity.class);
                    intent.putExtra("ngo", ngo);
                    context.startActivity(intent);
                    Toast.makeText(context, "added 50 points", Toast.LENGTH_SHORT);
                }
            });*/
        }

    }


}
