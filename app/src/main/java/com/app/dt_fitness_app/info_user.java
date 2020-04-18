package com.app.dt_fitness_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


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
    private String firebaseAuth = FirebaseAuth.getInstance().getCurrentUser().getEmail();

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
        findViewById(R.id.edit_nombre).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyDialog("Introduzca su nuevo nombre", "nombre");
            }
        });

        findViewById(R.id.edit_direccion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyDialog("Introduzca la nueva dirección", "direccion");
            }
        });

        findViewById(R.id.edit_contraseña).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyDialog("Introduzca la nueva contraseña", "contraseña");
            }
        });

        findViewById(R.id.edit_correo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyDialog("Introduzca el nuevo correo", "correo");
            }
        });

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
                infoBono.setText(bono);
            }
        });
   }



    private void showMyDialog(String main, final String field){
        AlertDialog.Builder builder = new AlertDialog.Builder(info_user.this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.edit_dialog_view, null);

        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();

        TextView titulo = view.findViewById(R.id.main_message);
        titulo.setText(main);

        final EditText newField = view.findViewById(R.id.new_field);

        Button cancelar = view.findViewById(R.id.cancel);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        
        Button aplicar = view.findViewById(R.id.aplicar_cambios);
        aplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(field.equals("contraseña")) {
                        nuevaContraseña(newField.getText().toString());
                    }
                    if(field.equals("correo")){
                        db.collection("Clientes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot document : task.getResult()){
                                        String nombre = document.get("nombre").toString();
                                        String contraseña = document.get("contraseña").toString();
                                        String telefono = document.get("telefono").toString();
                                        String dni = document.get("dni").toString();
                                        String correo = infoContraseña.getText().toString();
                                        String direccion = document.get("direccion").toString();
                                        String bono = document.get("bono").toString();
                                        Cliente c = new Cliente(nombre,contraseña,telefono,dni,correo,direccion,bono);
                                        db.collection("Clientes").document(infoCorreo.getText().toString()).set(c);
                                        nuevoCorreo(newField.getText().toString());

                                    }
                                }
                            }
                        });



                    }
                cambiarCampoDB(field, newField.getText().toString());
                    if(field.equals("correo")){
                        startActivity(new Intent(info_user.this, IniciarSesion.class));
                        finish();
                    } else {
                        dialog.dismiss();
                        try {
                            Thread.sleep(95);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(firebaseAuth.equals("dtfitnessmadrid@gmail.com")){
                            finish();
                            startActivity(new Intent(info_user.this, AdminPage.class));
                        }
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
            }
        });
    }


    private void cambiarCampoDB(String field, String nuevoValor){
        col.document(IniciarSesion.imprimirUser(info_user.this)).update(field, nuevoValor).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(info_user.this, "Dato actualizado correctamente", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void nuevaContraseña(String nuevaContraseña){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(nuevaContraseña);
    }

    private void nuevoCorreo(String nuevoCorreo){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updateEmail(nuevoCorreo);
    }


}
