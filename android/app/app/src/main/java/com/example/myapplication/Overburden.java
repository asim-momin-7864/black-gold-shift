package com.example.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;

public class Overburden extends AppCompatActivity {
    CardView trip,shovel,drill,expo;
    Dialog dialog;
    TextInputEditText no;
    Button btn;

    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overburden);

        Intent intent = getIntent();

        String collection=intent.getStringExtra("collection");
         mode =intent.getStringExtra("Mode");

        trip=findViewById(R.id.tripper);
        shovel=findViewById(R.id.shovel);
        drill=findViewById(R.id.drill);
        expo=findViewById(R.id.expo);

        dialog=new Dialog(Overburden.this);
        dialog.setContentView(R.layout.custom_dialog_box);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_bg);

        btn=dialog.findViewById(R.id.btnr);
        no=dialog.findViewById(R.id.TextinputEdittext);



        trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.compareTo("Edit")==0){
                    dialog.show();
                    dialogboxfun(Tripper.class,collection);

                }
                else{
                Intent i =new Intent(Overburden.this, Tripper.class);
                i.putExtra("collection",collection);
                i.putExtra("Mode",mode);
                startActivity(i);
                finish();
                }
            }
        });
        shovel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.compareTo("Edit")==0){
                    dialog.show();
                    dialogboxfun(Shovel.class,collection);
                }
                else{
                Intent i =new Intent(Overburden.this, Shovel.class);
                i.putExtra("collection",collection);
                i.putExtra("Mode",mode);
                startActivity(i);
                finish();}
            }
        });
        drill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.compareTo("Edit")==0){
                    dialog.show();
                    dialogboxfun(Drill.class,collection);

                }
                else{
                Intent i =new Intent(Overburden.this, Drill.class);
                i.putExtra("collection",collection);
                i.putExtra("Mode",mode);
                startActivity(i);
                finish();}
            }
        });
        expo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.compareTo("Edit")==0){
                    dialog.show();
                    dialogboxfun(Explosives.class,collection);

                }
                else {
                    Intent i = new Intent(Overburden.this, Explosives.class);
                    i.putExtra("collection", collection);
                    i.putExtra("Mode",mode);
                    startActivity(i);
                    finish();
                }
            }
        });





    }
    public void dialogboxfun(Class<?> next, String collection){

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number=no.getText().toString();
                Intent i =new Intent(Overburden.this, next);
                i.putExtra("collection",collection);
                i.putExtra("Number",number);
                i.putExtra("Mode",mode);

                startActivity(i);
                finish();

            }
        });


    }

}