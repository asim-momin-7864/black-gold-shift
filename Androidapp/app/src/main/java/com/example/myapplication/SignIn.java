package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

 Button btn1;
 TextInputEditText editText1,editText2;

 private FirebaseAuth firebaseAuth;
 private FirebaseAuth.AuthStateListener authStateListener;
 private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);

       btn1=findViewById(R.id.btn1);
       editText1=findViewById(R.id.TextInput1);
       editText2=findViewById(R.id.TextInput2);

       firebaseAuth=FirebaseAuth.getInstance();


       btn1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               LoginEmailPass(
                   editText1.getText().toString().trim(),
                   editText2.getText().toString().trim()
               );
           }
       });


    }
    private void LoginEmailPass(String email, String pass ){
        if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(pass)){

            firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Toast.makeText(SignIn.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SignIn.this, Dashboard.class);
                        startActivity(i);
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(SignIn.this, "Authentication Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


            }
        else{

            Toast.makeText(SignIn.this,"Please Provide both credentials",Toast.LENGTH_SHORT).show();
        }
     }

}