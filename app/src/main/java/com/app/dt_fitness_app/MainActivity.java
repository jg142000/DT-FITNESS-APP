package com.app.dt_fitness_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth fa = FirebaseAuth.getInstance();

    private static final String TAG = "MainActivity";
    private EditText editNombre;
    private EditText editDNI;
    private EditText editTelefono;
    private EditText editDireccion;
    private EditText editCorreo;
    private EditText editBono;
    private EditText editContraseña;

    private Button relogin;





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editBono = findViewById(R.id.Bono);
        editCorreo = findViewById(R.id.Correo);
        editDireccion = findViewById(R.id.Direccion);
        editDNI = findViewById(R.id.DNI);
        editNombre = findViewById(R.id.Nombre);
        editTelefono = findViewById(R.id.Telefono);
        editContraseña = findViewById(R.id.Contraseña);
        relogin = findViewById(R.id.relogin);



    }

    public void guardarCliente(View v) {
        String nombre = editNombre.getText().toString();
        String dni = editDNI.getText().toString();
        String telefono = editTelefono.getText().toString();
        String direccion = editDireccion.getText().toString();
        String bono = editBono.getText().toString();
        String correo = editCorreo.getText().toString();
        String contraseña = editContraseña.getText().toString();

        Cliente cliente = new Cliente(nombre, contraseña, telefono, dni, correo, direccion, bono);
        if (!nombre.isEmpty() && !dni.isEmpty() && !telefono.isEmpty() && !direccion.isEmpty() &&
                !bono.isEmpty() && !correo.isEmpty() && !contraseña.isEmpty()) {
            registrarCliente_(correo, contraseña);

            db.collection("Clientes").document(nombre).set(cliente)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this, "Añadido Cliente", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                    });

        } else {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
        }
    }


    private void registrarCliente_(String correo, String contraseña){
        fa.createUserWithEmailAndPassword(correo,contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if(task.isSuccessful()){

                  String id = fa.getCurrentUser().getUid();
                  startActivity(new Intent(MainActivity.this, IniciarSesion.class));
                  finish();
              }else{
                  Toast.makeText(MainActivity.this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
              }

            }
        });
    }

    public void reLogin(View v){
        Intent intent = new Intent(MainActivity.this, IniciarSesion.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
    }

}
