package com.example.myapplication;

import android.content.Intent;
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


public class DrillCoal extends AppCompatActivity {
    Button btn;
    TextInputEditText TextinputEdittext, TextinputEdittext1,
            TextinputEdittext2,TextinputEdittext3,
            TextinputEdittext4,TextinputEdittext5;
    private CollectionReference drill;
    private FirebaseFirestore db;
    private CollectionReference ShiftLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drill);

        String collection=getIntent().getStringExtra("collection");
        String docname="CoalMined";
        db= FirebaseFirestore.getInstance();
        drill =db.collection(collection).document(docname).collection("Drill");
        ShiftLog=db.collection("ShiftLog");


        TextinputEdittext=findViewById(R.id.TextinputEdittext);
        TextinputEdittext1=findViewById(R.id.TextinputEdittext1);
        TextinputEdittext2=findViewById(R.id.TextinputEdittext2);
        TextinputEdittext3=findViewById(R.id.TextinputEdittext3);
        TextinputEdittext4=findViewById(R.id.TextinputEdittext4);
        TextinputEdittext5=findViewById(R.id.TextinputEdittext5);
        btn=findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddData(collection);

            }
        });

    }
    public void AddData(String collection){

        String Serialno=TextinputEdittext.getText().toString();
        String Drillno=TextinputEdittext1.getText().toString();
        String Benchno=TextinputEdittext2.getText().toString();
        String Noofshotholesdrilled=TextinputEdittext3.getText().toString();
        String drillingmeter=TextinputEdittext4.getText().toString();
        String total=TextinputEdittext5.getText().toString();


        DocumentReference subdrill = drill.document(Drillno);
        DocumentReference subdrilllog = ShiftLog.document(collection);


        DrillCoalTemp drilltemp=new DrillCoalTemp(Serialno,Drillno,Benchno,Noofshotholesdrilled,drillingmeter,total);

        subdrilllog.set(drilltemp, SetOptions.merge());

        subdrill.set(drilltemp).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(DrillCoal.this, "Data Sent Successful.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(DrillCoal.this, CoalMined.class);
                    i.putExtra("collection",collection);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(DrillCoal.this, "Data Sending Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}