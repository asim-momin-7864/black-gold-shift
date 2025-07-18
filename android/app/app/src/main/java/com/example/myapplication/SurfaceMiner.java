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


public class SurfaceMiner extends AppCompatActivity {
    Button btn;
    TextInputEditText TextinputEdittext, TextinputEdittext1,
            TextinputEdittext2, TextinputEdittext3;
    private CollectionReference surfaceminer;
    private CollectionReference ShiftLog;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_surface_miner);

        String collection = getIntent().getStringExtra("collection");
        String docname = "CoalMined";
        db = FirebaseFirestore.getInstance();
        surfaceminer = db.collection(collection).document(docname).collection("SurfaceMiner");
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

        String Serialno = TextinputEdittext.getText().toString();
        String Surfaceminerno = TextinputEdittext1.getText().toString();
        String Benchno = TextinputEdittext2.getText().toString();
        String Coalquantity = TextinputEdittext3.getText().toString();

        DocumentReference subsurfacecoal = surfaceminer.document(Surfaceminerno);
        DocumentReference subsurfacecoallog = ShiftLog.document(collection);

        SufacemineCoalTemp sufacemineCoalTemp = new SufacemineCoalTemp(Serialno, Surfaceminerno, Benchno, Coalquantity);
        subsurfacecoallog.set(sufacemineCoalTemp, SetOptions.merge());

        subsurfacecoal.set(sufacemineCoalTemp).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(SurfaceMiner.this, "Data Sent Successful.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SurfaceMiner.this, CoalMined.class);
                    i.putExtra("collection", collection);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(SurfaceMiner.this, "Data Sending Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}