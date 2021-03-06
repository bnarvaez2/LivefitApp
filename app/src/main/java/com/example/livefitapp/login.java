package com.example.livefitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
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

import java.util.Locale;

public class login extends AppCompatActivity {

    String email;
    String password;
    Boolean x;

    private EditText txtEmail;
    private EditText txtPassword;
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


        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        button = findViewById(R.id.btnINgresar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = txtEmail.getText().toString();
                password = txtPassword.getText().toString();
                ingresar();
            }
        });
    }

    private void ingresar() {
        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(login.this, MainActivity.class);
                        intent.putExtra(login.EXTRA_EMAIL, email + "");
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.correo_o_contrasenia_incorrecta), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.campos_vacios), Toast.LENGTH_SHORT).show();
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

    public void ir_a_signup(View view) {
        Intent intent = new Intent(this,Signup.class);
        startActivity(intent);
    }
}
