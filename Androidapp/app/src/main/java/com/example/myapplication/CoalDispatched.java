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


public class CoalDispatched extends AppCompatActivity {
    Button btn;
    TextInputEditText TextinputEdittext, TextinputEdittext1,
            TextinputEdittext2, TextinputEdittext3,TextinputEdittext4, TextinputEdittext5;
    private CollectionReference date;
    private CollectionReference ShiftLog;
    private FirebaseFirestore db;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_coal_dispatched);

        String collection = getIntent().getStringExtra("collection");
        mode =getIntent().getStringExtra("Mode");
        String docname = "CoalDispatched";
        db = FirebaseFirestore.getInstance();
        date = db.collection(collection);
        ShiftLog = db.collection("ShiftLog");

        TextinputEdittext = findViewById(R.id.TextinputEdittext);
        TextinputEdittext1 = findViewById(R.id.TextinputEdittext1);
        TextinputEdittext2 = findViewById(R.id.TextinputEdittext2);
        TextinputEdittext3 = findViewById(R.id.TextinputEdittext3);
        TextinputEdittext4 = findViewById(R.id.TextinputEdittext4);
        TextinputEdittext5 = findViewById(R.id.TextinputEdittext5);
        btn = findViewById(R.id.btn);

        if(mode.compareTo("Edit")==0){
            date.document(docname).get().addOnSuccessListener(documentSnapshot -> {
                if(documentSnapshot.exists()){
                   CoalDispatchedTemp coaldisdata=documentSnapshot.toObject(CoalDispatchedTemp.class);

                   String Dispatch_Rail_Target=coaldisdata.getDispatch_Rail_Target();
                   TextinputEdittext.setText(Dispatch_Rail_Target);
                   String Dispatch_Rail_Actual=coaldisdata.getDispatch_Rail_Actual();
                   TextinputEdittext1.setText(Dispatch_Rail_Actual);
                   String Dispatch_Rail_Grade=coaldisdata.getDispatch_Rail_Grade();
                   TextinputEdittext2.setText(Dispatch_Rail_Grade);
                   String Dispatch_Road_Target=coaldisdata.getDispatch_Road_Target();
                   TextinputEdittext3.setText(Dispatch_Road_Target);
                   String Dispatch_Road_Actual= coaldisdata.getDispatch_Road_Actual();
                   TextinputEdittext4.setText(Dispatch_Road_Actual);
                   String Dispatch_Road_Grade= coaldisdata.getDispatch_Road_Grade();
                   TextinputEdittext5.setText(Dispatch_Road_Grade);

                }

            }).addOnFailureListener(e -> {
                finish();


            });


        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AddData(collection,docname);

            }
        });

    }

    public void AddData(String collection,String docname) {

         String Dispatch_Rail_Target=TextinputEdittext.getText().toString();
         String Dispatch_Rail_Actual= TextinputEdittext1.getText().toString();
         String Dispatch_Rail_Grade= TextinputEdittext2.getText().toString();
         String Dispatch_Road_Target= TextinputEdittext3.getText().toString();
         String Dispatch_Road_Actual= TextinputEdittext4.getText().toString();
         String Dispatch_Road_Grade = TextinputEdittext5.getText().toString();

         DocumentReference dispatched = date.document(docname);
        DocumentReference dispatchedlog = ShiftLog.document(collection);

        CoalDispatchedTemp coalDispatchedTemp=new CoalDispatchedTemp(Dispatch_Rail_Target,Dispatch_Rail_Actual,Dispatch_Rail_Grade,Dispatch_Road_Target,Dispatch_Road_Actual,Dispatch_Road_Grade);

        dispatchedlog.set(coalDispatchedTemp, SetOptions.merge());

        dispatched.set(coalDispatchedTemp).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(CoalDispatched.this, "Data Sent Successful.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CoalDispatched.this, CreateRepo.class);
                    i.putExtra("collection",collection);
                   i.putExtra("Mode",mode);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(CoalDispatched.this, "Data Sending Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });





    }



}