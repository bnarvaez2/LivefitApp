package com.example.livefitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    TextView nombreTV;
    TextView emailTV;
    TextView passwordTV;
    Button button;
    String nombre, correo, password;
    Usuario usuario;

    public static final String EXTRA_EMAIL = "com.example.android.LivefitApp.extra.EMAIL";
    public static final String EXTRA_NOMBRE = "com.example.android.LivefitApp.extra.NOMBRE";

    private FirebaseAuth mAuth;
    DatabaseReference user;

    private ImageButton imageButtonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Intent intent = getIntent();

        nombreTV =findViewById(R.id.txtNombre);
        emailTV =findViewById(R.id.txtEmail);
        passwordTV =findViewById(R.id.txtPassword);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseDatabase.getInstance().getReference();

        button = findViewById(R.id.btnSignUp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarse();
            }
        });

        imageButtonBack = findViewById(R.id.btnBack);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void registrarse(){
        nombre = nombreTV.getText().toString();
        correo = emailTV.getText().toString();
        password = passwordTV.getText().toString();
        if(!correo.isEmpty() && !password.isEmpty() && !nombre.isEmpty()) {
            usuario = new Usuario(nombre,correo,password);
            mAuth.createUserWithEmailAndPassword(correo, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String id = mAuth.getCurrentUser().getUid();
                        user.child("register").child(usuario.getCorreo().replace('.','|')).setValue(usuario);
                        Intent intent;
                        intent = new Intent(Signup.this, MainActivity.class);
                        intent.putExtra(EXTRA_EMAIL, correo);
                        intent.putExtra(EXTRA_NOMBRE,correo);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"Registro exitoso",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        if(correo.contains(" ")){
                            Toast.makeText(getApplicationContext(),"No se permiten espacios en el correo.",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Este correo no está disponible.",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }else{
            Toast.makeText(this, "Asegurese de llenar todos los campos.", Toast.LENGTH_SHORT).show();
        }
    }
}
