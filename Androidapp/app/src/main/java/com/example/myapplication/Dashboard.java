package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class Dashboard extends AppCompatActivity {
    CardView createrepo,pdfview,editrepo,security;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);

        createrepo=findViewById(R.id.cardView);
        pdfview=findViewById(R.id.cardView2);
        editrepo=findViewById(R.id.cardView3);
        security=findViewById(R.id.cardView4);

        createrepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Dashboard.this, DateTime.class);
                i.putExtra("Mode","Create");
                startActivity(i);
            }
        });

        security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Dashboard.this, SecurityInst.class);
                startActivity(i);
            }
        });

        pdfview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Dashboard.this, Datepdf.class);
                startActivity(i);
            }
        });

        editrepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Dashboard.this, DateTime.class);
                i.putExtra("Mode","Edit");
                startActivity(i);
            }
        });


    }
}