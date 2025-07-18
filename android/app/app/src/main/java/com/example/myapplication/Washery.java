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

public class Washery extends AppCompatActivity {
    Button btn;
    TextInputEditText TextinputEdittext, TextinputEdittext1,
            TextinputEdittext2, TextinputEdittext3,TextinputEdittext4;
    private CollectionReference date;
    private CollectionReference ShiftLog;
    private FirebaseFirestore db;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_washery);

        String collection = getIntent().getStringExtra("collection");
         mode =getIntent().getStringExtra("Mode");
        String docname = "Washery";
        db = FirebaseFirestore.getInstance();
        date = db.collection(collection);
        ShiftLog = db.collection("ShiftLog");

        TextinputEdittext = findViewById(R.id.TextinputEdittext);
        TextinputEdittext1 = findViewById(R.id.TextinputEdittext1);
        TextinputEdittext2 = findViewById(R.id.TextinputEdittext2);
        TextinputEdittext3 = findViewById(R.id.TextinputEdittext3);
        TextinputEdittext4 = findViewById(R.id.TextinputEdittext4);

        btn = findViewById(R.id.btn);

        if(mode.compareTo("Edit")==0) {
            date.document(docname).get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    WasheryTemp coaldisdata = documentSnapshot.toObject(WasheryTemp.class);

                    String Raw = coaldisdata.getWashery_Rawcoal();
                    TextinputEdittext.setText(Raw);
                    String Clean = coaldisdata.getWashery_Cleancoal();
                    TextinputEdittext1.setText(Clean);
                    String Midding = coaldisdata.getWashery_Midding();
                    TextinputEdittext2.setText(Midding);
                    String Slurry = coaldisdata.getWashery_Slurry();
                    TextinputEdittext3.setText(Slurry);
                    String Rejected = coaldisdata.getWashery_Rejected();
                    TextinputEdittext4.setText(Rejected);


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

        String Rawcoal = TextinputEdittext.getText().toString();
        String Cleancoal = TextinputEdittext1.getText().toString();
        String Midding = TextinputEdittext2.getText().toString();
        String Slurry= TextinputEdittext3.getText().toString();
        String Rejected= TextinputEdittext3.getText().toString();



        DocumentReference washery = date.document(docname);
        DocumentReference washerylog = ShiftLog.document(collection);

        WasheryTemp washerytemp=new WasheryTemp(Rawcoal,Cleancoal,Midding,Slurry,Rejected);

        washerylog.set(washerytemp, SetOptions.merge());

        washery.set(washerytemp).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(Washery.this, "Data Sent Successful.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Washery.this, CreateRepo.class);
                    i.putExtra("collection",collection);
                    i.putExtra("Mode",mode);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(Washery.this, "Data Sending Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}