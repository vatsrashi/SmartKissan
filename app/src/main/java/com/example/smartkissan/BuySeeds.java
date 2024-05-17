package com.example.smartkissan;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BuySeeds extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SeedAdapter seedAdapter;
    ImageView back;

    Button buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_seeds);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_view_seeds);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        back = findViewById(R.id.back);

        // Create sample seed data
        List<Seed> seedList = new ArrayList<>();
        seedList.add(new Seed("Tomato seeds", "High-quality tomato seeds for your field.", 1450, R.drawable.tomato));
        seedList.add(new Seed("Maize seeds", "Fresh maize seeds to grow your own salads.", 999, R.drawable.maize));
        seedList.add(new Seed("Chilly seeds", "Sizzling hot chilly seeds.", 999, R.drawable.chilly));
        seedList.add(new Seed("Onion seeds", "Pure fresh onion seeds.", 999, R.drawable.onion));
        seedList.add(new Seed("Paddy seeds", "Basamati rice paddy seeds.", 999, R.drawable.paddy));
        seedList.add(new Seed("Muskmelon seeds", "Yellow juicy muskmelon seeds.", 999, R.drawable.muskmelon));
        seedList.add(new Seed("Bio fertilizer", "Organic bio fertilizer.", 999, R.drawable.bioferti));
        seedList.add(new Seed("Pea Seeds", "Fresh green pea seeds.", 999, R.drawable.pea));
        seedList.add(new Seed("Cutter", "Best quality cutter.", 11999, R.drawable.cutter));
        seedList.add(new Seed("Tiller", "Best quality tiller.", 12699, R.drawable.tiller));
        seedList.add(new Seed("Driller", "Best quality driller.", 12399, R.drawable.driller));
        seedList.add(new Seed("Sprayer", "Best quality sprayer.", 5000, R.drawable.tool));
        seedList.add(new Seed("Dripper", "Best quality dripper.", 15000, R.drawable.drip));
        seedList.add(new Seed("Sugarcane cutter", "Best quality cutter.", 2500, R.drawable.sugarcane));
        seedList.add(new Seed("Bio pesticide", "Best quality bio pesticide.", 999, R.drawable.biopesti));
        seedList.add(new Seed("Crop cover", "Best quality crop cover.", 999, R.drawable.cover));
        seedList.add(new Seed("Cattle feed", "Best quality cattle feed.", 799, R.drawable.cattle));
        seedList.add(new Seed("NPK fertilizer", "Best quality npk fertilizer.", 899, R.drawable.npk));



        // Initialize and set the adapter
        seedAdapter = new SeedAdapter(seedList);
        recyclerView.setAdapter(seedAdapter);

        seedAdapter.setOnItemClickListener(new SeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Seed seed = seedList.get(position);
                // Handle the onClick event for the "Buy Now" button
                // For example, you can add the item to the cart or navigate to a payment activity
                Intent logout = new Intent(BuySeeds.this, individual_product.class);
                startActivity(logout);


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(BuySeeds.this, Dashboard.class);
                startActivity(back);
            }
        });
    }
}
