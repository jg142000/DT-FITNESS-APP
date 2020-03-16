package com.app.dt_fitness_app;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class info_user extends AppCompatActivity {


    private TextView infoNombre;
    private TextView infoCorreo;
    private TextView infoDNI;
    private TextView infoDireccion;
    private TextView infoContraseña;
    private TextView infoTelefono;
    private TextView infoBono;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference col = db.collection("Clientes");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);
        infoNombre = findViewById(R.id.mi_info_nombre);
        infoCorreo = findViewById(R.id.mi_info_correo);
        infoDNI = findViewById(R.id.mi_info_dni);
        infoDireccion = findViewById(R.id.mi_info_direccion);
        infoContraseña = findViewById(R.id.mi_info_contraseña);
        infoTelefono= findViewById(R.id.mi_info_telefono);
        infoBono = findViewById(R.id.mi_info_bono);

        if(IniciarSesion.imprimirUser(info_user.this).equals(("dtfitnessmadrid@gmail.com"))){
            showDataRoot();
        } else {
            showData();
        }

    }

    public void showData(){

        col.document(IniciarSesion.imprimirUser(info_user.this)).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
           @Override
           public void onSuccess(DocumentSnapshot documentSnapshot) {
           String nombre = documentSnapshot.getString("nombre");
           String correo = documentSnapshot.getString("correo");
           String dni = documentSnapshot.getString("dni");
           String direccion = documentSnapshot.getString("direccion");
           String contraseña = documentSnapshot.getString("contraseña");
           String telefono = documentSnapshot.getString("telefono");
           String bono = documentSnapshot.getString("bono");
            infoNombre.setText(nombre);
            infoCorreo.setText(correo);
            infoDNI.setText(dni);
            infoDireccion.setText(direccion);
            infoContraseña.setText(contraseña);
            infoTelefono.setText(telefono);
            infoBono.setText("Tipo de bono: "+bono);
            }
        });
   }

   public void showDataRoot(){

        col.document(AdminPage.imprimirUser(info_user.this)).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nombre = documentSnapshot.getString("nombre");
                String correo = documentSnapshot.getString("correo");
                String dni = documentSnapshot.getString("dni");
                String direccion = documentSnapshot.getString("direccion");
                String contraseña = documentSnapshot.getString("contraseña");
                String telefono = documentSnapshot.getString("telefono");
                String bono = documentSnapshot.getString("bono");
                infoNombre.setText(nombre);
                infoCorreo.setText(correo);
                infoDNI.setText(dni);
                infoDireccion.setText(direccion);
                infoContraseña.setText(contraseña);
                infoTelefono.setText(telefono);
                infoBono.setText("Tipo de bono: "+bono);
            }
        });
   }

   // private String obtenerNombredB(String correo){

   // }



}
