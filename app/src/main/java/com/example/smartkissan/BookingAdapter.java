package com.example.smartkissan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    private List<Booking> bookings;

    public BookingAdapter(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking booking = bookings.get(position);
        holder.textViewDate.setText(booking.getDate());
        holder.textViewService.setText(booking.getService());
        holder.textViewTime.setText(booking.getTime());
        holder.textViewActivity.setText(booking.getActivity());
        holder.textViewBill.setText(booking.getBill());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate;
        TextView textViewBill,textViewService,textViewActivity,textViewTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            // Initialize other TextViews
            textViewBill = itemView.findViewById(R.id.textViewBill);
            textViewActivity = itemView.findViewById(R.id.textViewActivity);
            textViewService = itemView.findViewById(R.id.textViewService);
            textViewTime = itemView.findViewById(R.id.textViewTime);
        }
    }
}

