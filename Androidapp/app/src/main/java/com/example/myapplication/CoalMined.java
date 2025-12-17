package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;



public class CoalMined extends AppCompatActivity {
    CardView tripper,Shovel,drill,miner,expo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_coal_mined);

        Intent intent = getIntent();
        String collection=intent.getStringExtra("collection");

        tripper=findViewById(R.id.tripper);
        Shovel=findViewById(R.id.shovel);
        drill=findViewById(R.id.drill);
        miner=findViewById(R.id.miner);
        expo=findViewById(R.id.expo);

        tripper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CoalMined.this, TripperCoal.class);
                i.putExtra("collection",collection);
                startActivity(i);
                finish();
            }
        });
        Shovel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CoalMined.this, ShovelCoal.class);
                i.putExtra("collection",collection);
                startActivity(i);
                finish();
            }
        });
        drill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CoalMined.this, DrillCoal.class);
                i.putExtra("collection",collection);
                startActivity(i);
                finish();
            }
        });
        expo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CoalMined.this, ExplosivesCoal.class);
                i.putExtra("collection",collection);
                startActivity(i);
                finish();
            }
        });
        miner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CoalMined.this, SurfaceMiner.class);
                i.putExtra("collection",collection);
                startActivity(i);
                finish();
            }
        });


    }
}