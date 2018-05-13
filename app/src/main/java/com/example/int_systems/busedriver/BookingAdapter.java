package com.example.int_systems.busedriver;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class BookingAdapter  extends RecyclerView.Adapter <BookingAdapter.ViewHolder> {

    public BookingAdapter(Context mCtx, List<BookingResponse> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<BookingResponse> productList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.requestes, null);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(BookingAdapter.ViewHolder holder, int position) {
        BookingResponse rider = productList.get(position);

        //binding the data with the viewholder views
        //binding the data with the viewholder views
        holder.requesterName.setText(rider.Ridername);
        holder.passNum.setText(rider.no_pass);
        holder.destination.setText(rider.destination);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView requesterName, passNum, destination, imageurl;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            requesterName =(TextView) itemView.findViewById(R.id.requestName);
            passNum = (TextView) itemView.findViewById(R.id.textnumpass);
            destination = (TextView)itemView.findViewById(R.id.destination);
            //  imageView =(ImageView)itemView.findViewById(R.id.imageView);



        }
    }
}
