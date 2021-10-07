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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Usuario extends AppCompatActivity {

    private TextView clickcacelar;
    private Button registrarusuario;

    private EditText correocaja;
    private EditText passwordcaja;

    private String correo="";
    private String password="";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        clickcacelar = (TextView) findViewById(R.id.clickcancelar);
        registrarusuario = (Button) findViewById(R.id.btn_agregarpersona);

        correocaja = (EditText) findViewById(R.id.txtNombre);
        passwordcaja = (EditText) findViewById(R.id.passwordregistrarse);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        clickcacelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Usuario.this, "Se cancelo el registro...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        registrarusuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                correo = correocaja.getText().toString();
                password = passwordcaja.getText().toString();

                if (!correo.isEmpty() && !password.isEmpty()){
                    if (password.length() >= 6){
                        registrar();
                    }
                    else{
                        Toast.makeText(Usuario.this, "La contraseña debe tener por lo menos 6 caracteres...", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Usuario.this, "Debe Completar los campos requeridos...", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registrar(){

        mAuth.createUserWithEmailAndPassword(correo, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("correo", correo);
                    map.put("password", password);

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {

                            if (task2.isSuccessful()){

                                Toast.makeText(Usuario.this, "Se registro tu usuario...", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Usuario.this, MainActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(Usuario.this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(Usuario.this, "No se pudieron registrar los datos correctamente...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}