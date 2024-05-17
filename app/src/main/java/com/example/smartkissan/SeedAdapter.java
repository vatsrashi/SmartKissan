package com.example.smartkissan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SeedAdapter extends RecyclerView.Adapter<SeedAdapter.SeedViewHolder> {

    private List<Seed> seedList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public SeedAdapter(List<Seed> seedList) {
        this.seedList = seedList;
    }

    @NonNull
    @Override
    public SeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_seed_card, parent, false);
        return new SeedViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SeedViewHolder holder, int position) {
        Seed seed = seedList.get(position);
        holder.seedName.setText(seed.getName());
        holder.seedDescription.setText(seed.getDescription());
        holder.seedPrice.setText("Price: Rs." + String.valueOf(seed.getPrice()));
        holder.seedImage.setImageResource(seed.getImageResource());
    }

    @Override
    public int getItemCount() {
        return seedList.size();
    }

    public static class SeedViewHolder extends RecyclerView.ViewHolder {
        public TextView seedName, seedDescription, seedPrice;
        public ImageView seedImage;
        public Button buyButton;

        public SeedViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            seedName = itemView.findViewById(R.id.text_seed_name);
            seedDescription = itemView.findViewById(R.id.text_seed_description);
            seedPrice = itemView.findViewById(R.id.text_seed_price);
            seedImage = itemView.findViewById(R.id.image_seed);
            buyButton = itemView.findViewById(R.id.btn_buy_seed);

            buyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(v, position);
                        }
                    }
                }
            });
        }
    }
}
