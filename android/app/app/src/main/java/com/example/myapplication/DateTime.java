package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class DateTime extends AppCompatActivity {
    Button btndate;
    DatePicker datepicker;
    String[] items ={"Morning","Evening","Night"};
    AutoCompleteTextView autoCompleteTextView3;
    ArrayAdapter<String> arrayitems;
    private String shift;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);

        String mode=getIntent().getStringExtra("Mode");
        btndate=findViewById(R.id.btndate);
        datepicker=findViewById(R.id.datepick);


        autoCompleteTextView3=findViewById(R.id.autoCompleteTextView3);
        arrayitems=new ArrayAdapter<String>(this,R.layout.drop_down,items);
         autoCompleteTextView3.setAdapter(arrayitems);


         autoCompleteTextView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   shift= (String)parent.getItemAtPosition(position);
                 Toast.makeText(DateTime.this, shift, Toast.LENGTH_SHORT).show();
                 if (shift.compareTo("Morning")==0){
                     String initi = "M";
                     startact(initi,mode);


                 } else if (shift.compareTo("Evening")==0){
                     String initi="E";
                     startact(initi,mode);

                 }
                 else if(shift.compareTo("Nignt")==0){
                     String initi="N";
                     startact(initi,mode);

                 }

             }
         });

    }

   private void startact(String initi, String mode){

    btndate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String Day,Month,Year;
            Day=String.valueOf(datepicker.getDayOfMonth());
            Month=String.valueOf(datepicker.getMonth()+1);
            Year=String.valueOf(datepicker.getYear());
            String date=(Day+Month+Year);
            String coll=date+initi;


            Intent i=new Intent(DateTime.this,CreateRepo.class);
            i.putExtra("collection",coll);
            i.putExtra("Mode",mode);

            startActivity(i);
        }
    });

    }




}