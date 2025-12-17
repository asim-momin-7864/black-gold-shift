package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class TripperCoal extends AppCompatActivity {
    Button btn;
    TextInputEditText TextinputEdittext, TextinputEdittext1,
            TextinputEdittext2,TextinputEdittext3,
            TextinputEdittext4,TextinputEdittext5,TextinputEdittext6,TextinputEdittext7;
    private CollectionReference tripper;
    private FirebaseFirestore db;
    private CollectionReference ShiftLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tripper_coal);

        String collection=getIntent().getStringExtra("collection");
        String docname="CoalMined";
        db= FirebaseFirestore.getInstance();
        tripper=db.collection(collection).document(docname).collection("Tripper");
        ShiftLog=db.collection("ShiftLog");


        TextinputEdittext=findViewById(R.id.TextinputEdittext);
        TextinputEdittext1=findViewById(R.id.TextinputEdittext1);
        TextinputEdittext2=findViewById(R.id.TextinputEdittext2);
        TextinputEdittext3=findViewById(R.id.TextinputEdittext3);
        TextinputEdittext4=findViewById(R.id.TextinputEdittext4);
        TextinputEdittext5=findViewById(R.id.TextinputEdittext5);
        TextinputEdittext6=findViewById(R.id.TextinputEdittext6);
        TextinputEdittext7=findViewById(R.id.TextinputEdittext7);
        btn=findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AddData(collection);

            }
        });
    }


public void AddData(String collection){

    String Serial=TextinputEdittext.getText().toString();
    String Tripperno=TextinputEdittext1.getText().toString();
    String Benchno=TextinputEdittext2.getText().toString();
    String Dumperfac=TextinputEdittext3.getText().toString();
    String Tripsnowithout=TextinputEdittext4.getText().toString();
    String Coalquanwithout=TextinputEdittext5.getText().toString();
    String Tripsnowith=TextinputEdittext6.getText().toString();
    String Coalquanwith =TextinputEdittext7.getText().toString();

    DocumentReference subtrippercoal = tripper.document(Tripperno);
    DocumentReference subtrippercoallog = ShiftLog.document(collection);


    TripperCoalTemp trippercoaltemp=new TripperCoalTemp(Serial,Tripperno,Benchno,Dumperfac,Tripsnowithout,Coalquanwithout,Tripsnowith,Coalquanwith);
    subtrippercoallog.set(trippercoaltemp, SetOptions.merge());

    subtrippercoal.set(trippercoaltemp).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {

            if(task.isSuccessful()){
                Toast.makeText(TripperCoal.this, "Data Sent Successful.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(TripperCoal.this, CoalMined.class);
                i.putExtra("collection",collection);
                startActivity(i);
                finish();
            }
            else{
                Toast.makeText(TripperCoal.this, "Data Sending Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    });

}

}