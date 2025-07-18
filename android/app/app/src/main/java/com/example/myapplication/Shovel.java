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

public class Shovel extends AppCompatActivity {

    Button btn;
    TextInputEditText TextinputEdittext, TextinputEdittext1,
            TextinputEdittext2,TextinputEdittext3,
            TextinputEdittext4,TextinputEdittext5;

    private CollectionReference shovel;
    private CollectionReference ShiftLog;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shovel2);

        String collection=getIntent().getStringExtra("collection");
        String docname="Overburden";
        db= FirebaseFirestore.getInstance();
        shovel=db.collection(collection).document(docname).collection("Shovel");
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

        String Serial=TextinputEdittext.getText().toString();
        String Shovelno=TextinputEdittext1.getText().toString();
        String Benchno=TextinputEdittext2.getText().toString();
        String SolidQuan=TextinputEdittext3.getText().toString();
        String RehandalingQuan=TextinputEdittext4.getText().toString();
        String Total=TextinputEdittext5.getText().toString();


        DocumentReference subshovel = shovel.document(Shovelno);
        DocumentReference subshovellog = ShiftLog.document(collection);


        ShovelTemp shoveltemp=new ShovelTemp(Serial,Shovelno,Benchno,SolidQuan,RehandalingQuan,Total);

        subshovellog.set(shoveltemp, SetOptions.merge());

        subshovel.set(shoveltemp).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(Shovel.this, "Data Sent Successful.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Shovel.this, Overburden.class);
                    i.putExtra("collection",collection);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(Shovel.this, "Data Sending Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}