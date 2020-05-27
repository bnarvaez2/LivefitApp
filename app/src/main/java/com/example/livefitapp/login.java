package com.example.livefitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity {

    String email;
    Boolean x;
    private EditText editText;
    private Button button;

    public static final String EXTRA_EMAIL = "com.example.android.LivefitApp.extra.EMAIL";

    private FirebaseAuth mAuth;
    DatabaseReference user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseDatabase.getInstance().getReference();


        editText = findViewById(R.id.txtEmail);
        button = findViewById(R.id.btnINgresar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editText.getText().toString();
                ingresar();
            }
        });
    }

    private boolean registrar(){
        x = false;
        if(!email.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, email).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String id = mAuth.getCurrentUser().getUid();
                        user.child("register").child(id);
                        Toast.makeText(getApplicationContext(),"Ha sido registrado.",Toast.LENGTH_SHORT).show();
                        x = true;
                    }
                }
            });
        }
        return x;
    }

    private void ingresar(){
        if(registrar() == true || registrar() == false){
            if(!email.isEmpty()){
                mAuth.signInWithEmailAndPassword(email,email).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(login.this,MainActivity.class);
                            intent.putExtra(login.EXTRA_EMAIL, email + "");
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(this,MainActivity.class);
            email = mAuth.getCurrentUser().getEmail();
            intent.putExtra(EXTRA_EMAIL, email + "");
            startActivity(intent);
            finish();
        }
    }
}
