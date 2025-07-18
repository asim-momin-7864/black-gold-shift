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

public class Tripper extends AppCompatActivity {
    Button btn;
    TextInputEditText TextinputEdittext, TextinputEdittext1,
            TextinputEdittext2,TextinputEdittext3,
            TextinputEdittext4,TextinputEdittext5,TextinputEdittext6,TextinputEdittext7;

    private CollectionReference tripper;
    private CollectionReference ShiftLog;
    private FirebaseFirestore db;
    private String mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tripper2);

        String collection=getIntent().getStringExtra("collection");
        mode=getIntent().getStringExtra("Mode");
        String number=getIntent().getStringExtra("Number");
        String docname="Overburden";
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

        if(mode.compareTo("Edit")==0){
            tripper.document(number).get().addOnSuccessListener(documentSnapshot -> {
                if(documentSnapshot.exists()){
                    TripperTemp tripperdata=documentSnapshot.toObject(TripperTemp.class);

                    String serial=tripperdata.getOverburden_Tripper_SIno();
                    TextinputEdittext.setText(serial);
                    String tripperno=tripperdata.getOverburden_Tripper_Tripperno();
                    TextinputEdittext1.setText(tripperno);
                    String dumper=tripperdata.getOverburden_Tripper_Dumperfac();
                    TextinputEdittext2.setText(dumper);
                    String bench=tripperdata.getOverburden_Tripper_Benchno();
                    TextinputEdittext3.setText(bench);
                    String tripsno= tripperdata.getOverburden_Tripper_Tripsno();
                    TextinputEdittext4.setText(tripsno);
                    String solid= tripperdata.getOverburden_Tripper_Soilquantity();
                    TextinputEdittext5.setText(solid);
                    String rehan= tripperdata.getOverburden_Tripper_Rehanquantity();
                    TextinputEdittext6.setText(rehan);
                    String total= tripperdata.getOverburden_Tripper_Totalquantity();
                    TextinputEdittext7.setText(total);


                }

            }).addOnFailureListener(e -> {
                finish();


            });


        }

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
        String Dumperfac=TextinputEdittext2.getText().toString();
        String Benchno=TextinputEdittext3.getText().toString();
        String Tripsno=TextinputEdittext4.getText().toString();
        String Solidno=TextinputEdittext5.getText().toString();
        String rehand=TextinputEdittext6.getText().toString();
        String total=TextinputEdittext7.getText().toString();

        DocumentReference subtripper = tripper.document(Tripperno);
        DocumentReference subtripperlog = ShiftLog.document(collection);



        TripperTemp trippertemp=new TripperTemp(Serial,Tripperno,Dumperfac,Benchno,Tripsno,Solidno,rehand,total);

        subtripperlog.set(trippertemp, SetOptions.merge());

        subtripper.set(trippertemp).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(Tripper.this, "Data Sent Successful.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Tripper.this, Overburden.class);
                    i.putExtra("collection",collection);
                    i.putExtra("Mode",mode);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(Tripper.this, "Data Sending Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });





    }
}