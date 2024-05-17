package com.example.smartkissan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItemList;

    public CartAdapter(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart_item, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        holder.itemName.setText(cartItem.getName());
        holder.itemPrice.setText("Rs." + cartItem.getPrice());
        holder.itemQuantity.setText("Quantity: " + cartItem.getQuantity());
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName, itemPrice, itemQuantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.text_item_name);
            itemPrice = itemView.findViewById(R.id.text_item_price);
            itemQuantity = itemView.findViewById(R.id.text_item_quantity);
        }
    }
}

