package com.example.myapplication;

import android.content.Intent;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;


public class ShovelCoal extends AppCompatActivity {
    Button btn;
    TextInputEditText TextinputEdittext, TextinputEdittext1,
            TextinputEdittext2, TextinputEdittext3;

    private CollectionReference shovel;
    private FirebaseFirestore db;
    private CollectionReference ShiftLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shovel_coal);

        String collection = getIntent().getStringExtra("collection");
        String docname = "CoalMined";
        db = FirebaseFirestore.getInstance();
        shovel = db.collection(collection).document(docname).collection("Shovel");
        ShiftLog=db.collection("ShiftLog");


        TextinputEdittext = findViewById(R.id.TextinputEdittext);
        TextinputEdittext1 = findViewById(R.id.TextinputEdittext1);
        TextinputEdittext2 = findViewById(R.id.TextinputEdittext2);
        TextinputEdittext3 = findViewById(R.id.TextinputEdittext3);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AddData(collection);

            }
        });

    }

    public void AddData(String collection) {

        String Serial = TextinputEdittext.getText().toString();
        String Shovelno = TextinputEdittext1.getText().toString();
        String Benchno = TextinputEdittext2.getText().toString();
        String Coalquantity = TextinputEdittext3.getText().toString();


        DocumentReference subshovelcoal = shovel.document(Shovelno);
        DocumentReference subshovelcoallog = ShiftLog.document(collection);


        ShovelCoalTemp shovelcoaltemp = new ShovelCoalTemp(Serial, Shovelno, Benchno, Coalquantity);

        subshovelcoallog.set(shovelcoaltemp, SetOptions.merge());

        subshovelcoal.set(shovelcoaltemp).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(ShovelCoal.this, "Data Sent Successful.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ShovelCoal.this, CoalMined.class);
                    i.putExtra("collection", collection);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(ShovelCoal.this, "Data Sending Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}