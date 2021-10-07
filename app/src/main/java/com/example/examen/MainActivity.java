package com.example.examen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private TextView clickregistrar;
    private Button logearse;

    private EditText correoreg;
    private EditText passwordreg;

    private String correoregistrarse="";
    private String passwordregistrarse="";

    private FirebaseAuth mAuth;
    private FirebaseDatabase mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickregistrar = (TextView) findViewById(R.id.clickcancelar);
        logearse = (Button) findViewById(R.id.btn_agregarpersona);
        correoreg = (EditText) findViewById(R.id.txtNombre);
        passwordreg = (EditText) findViewById(R.id.passwordregistrarse);

        mAuth = FirebaseAuth.getInstance();

        clickregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Usuario.class);
                startActivity(intent);
            }
        });

        logearse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                correoregistrarse = correoreg.getText().toString();
                passwordregistrarse = passwordreg.getText().toString();

                if (!correoregistrarse.isEmpty() && !passwordregistrarse.isEmpty()){
                    loginUser();
                }
                else {

                    Toast.makeText(MainActivity.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginUser(){

        mAuth.signInWithEmailAndPassword(correoregistrarse, passwordregistrarse).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Bienvenidos, se esta iniciando sesión...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, RegistrarPesona.class));
                    finish();
                }
            }
        });
    }
}