package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class CreateRepo extends AppCompatActivity {



    CardView overburden,coal,washery,dispatched;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_repo);
        Intent intent=getIntent();
        String collection=intent.getStringExtra("collection");
        String mode=intent.getStringExtra("Mode");

        overburden=findViewById(R.id.cardviweover);
        coal=findViewById(R.id.cardviwecoal);
        washery=findViewById(R.id.cardviwewash);
        dispatched=findViewById(R.id.cardviwedis);
        textView=findViewById(R.id.Textchange);

        if(mode.compareTo("Edit")==0){

            textView.setText("Edit Report");

        }

        overburden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CreateRepo.this, Overburden.class);
                i.putExtra("collection",collection);
                i.putExtra("Mode",mode);
                startActivity(i);
            }
        });
        coal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CreateRepo.this, CoalMined.class);
                i.putExtra("collection",collection);
                i.putExtra("Mode",mode);
                startActivity(i);
            }
        });
        washery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CreateRepo.this, Washery.class);
                i.putExtra("collection",collection);
                i.putExtra("Mode",mode);
                startActivity(i);
            }
        });
        dispatched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CreateRepo.this, CoalDispatched.class);
                i.putExtra("collection",collection);
                i.putExtra("Mode",mode);
                startActivity(i);
            }
        });

    }
}