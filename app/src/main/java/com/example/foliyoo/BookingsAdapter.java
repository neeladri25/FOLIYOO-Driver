package com.example.foliyoo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.MyViewHolder> {

    private List<Bookings> bookingsList;

    public BookingsAdapter(Context mCtx, List<Bookings> bookingsList){
        this.bookingsList=bookingsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.completed, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Bookings booking = bookingsList.get(position);
        holder.from.setText(booking.getFrom());
        holder.to.setText(booking.getTo());
        holder.bid.setText(booking.getBid());
        holder.date.setText(booking.getDate());

    }

    @Override
    public int getItemCount() {
        return bookingsList.size();
    }

    public BookingsAdapter(List<Bookings> bookingsList) {
        this.bookingsList = bookingsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView from, to, bid,date;

        public MyViewHolder(@NonNull View view) {
            super(view);
            from = (TextView) view.findViewById(R.id.from);
            to = (TextView) view.findViewById(R.id.to);
            bid = (TextView) view.findViewById(R.id.bid);
            date=(TextView) view.findViewById(R.id.datetime);
        }
    }


}
