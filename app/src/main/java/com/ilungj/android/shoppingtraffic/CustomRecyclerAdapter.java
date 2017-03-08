package com.ilungj.android.shoppingtraffic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ilungj.android.shoppingtraffic.com.example.ilung.myapplication.fragment.SubFragment;
import com.ilungj.android.shoppingtraffic.com.example.ilung.myapplication.service.Stores;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Il Ung on 1/15/2017.
 */

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.CustomViewHolder> {

    Context context;
    List<Stores> storesList;
    ProgressBar progressBar;

    public CustomRecyclerAdapter(Context context, List<Stores> storesList, ProgressBar progressBar) {
        this.context = context;
        this.storesList = storesList;
        this.progressBar = progressBar;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Log.d("TEST", "ONBINDVIEWHOLDER");
        Picasso.with(context).load(storesList.get(position).getImgUrl()).into(holder.imageView);
        holder.textView.setText("Latitude: " + storesList.get(position).getLatitude());
        holder.textView2.setText("Longitude: " + storesList.get(position).getLongitude());
        holder.textView3.setText("Number of people: " + storesList.get(position).getNum());

        if(position == getItemCount() - 1) {
            Log.d("TEST", "GETITEMCOUNT");
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return storesList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView, textView2, textView3;

        public CustomViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            textView = (TextView) view.findViewById(R.id.textView);
            textView2 = (TextView) view.findViewById(R.id.textView2);
            textView3 = (TextView) view.findViewById(R.id.textView3);
        }
    }
}
