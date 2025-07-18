package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class Datepdf extends AppCompatActivity {
    Button btndate;
    DatePicker datepicker;
    String[] items ={"Morning","Evening","Night"};
    AutoCompleteTextView autoCompleteTextView3;
    ArrayAdapter<String> arrayitems;
    private String shift;
    private FirebaseStorage storage = FirebaseStorage.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_date_time);
        btndate=findViewById(R.id.btndate);
        datepicker=findViewById(R.id.datepick);


        autoCompleteTextView3=findViewById(R.id.autoCompleteTextView3);
        arrayitems=new ArrayAdapter<String>(this,R.layout.drop_down,items);
        autoCompleteTextView3.setAdapter(arrayitems);


        autoCompleteTextView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                shift= (String)parent.getItemAtPosition(position);
                Toast.makeText(Datepdf.this, shift, Toast.LENGTH_SHORT).show();
                if (shift.compareTo("Morning")==0){
                    String initi = "M";
                    startact(initi);


                } else if (shift.compareTo("Evening")==0){
                    String initi="E";
                    startact(initi);

                }
                else if(shift.compareTo("Nignt")==0){
                    String initi="N";
                    startact(initi);

                }

            }
        });


    }
    private void startact(String initi){

        btndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Day,Month,Year;
                Day=String.valueOf(datepicker.getDayOfMonth());
                Month=String.valueOf(datepicker.getMonth()+1);
                Year=String.valueOf(datepicker.getYear());
                String date=(Day+Month+Year);
                String coll=date+initi;
                String pdfur="pdfs/"+coll+".pdf";
                StorageReference pdfRef = storage.getReference().child(pdfur);
                pdfRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String pdfUrl = uri.toString();
                    openPdfInBrowser(pdfUrl);
                }).addOnFailureListener(e -> {

                    Toast.makeText(Datepdf.this, "Error while opening Pdf", Toast.LENGTH_SHORT).show();

                });



            }
        });

    }
    private void openPdfInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(url), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(Intent.createChooser(intent, "Open PDF with"));
    }

}