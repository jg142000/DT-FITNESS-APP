package com.app.dt_fitness_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IniciarSesion extends AppCompatActivity  {

    private static EditText mcorreo;
    private EditText mcontraseña;

    private String correoAdmin = "daniel@gmail.com";

    private Button iniciar;
    private Button botonRegistro;

    private FirebaseAuth fa;

    private RadioButton manSesion;
    private boolean isActivatedmanSesion;
    private static final String STRING_PREFERENCE =  "com.app.dt_fitness_app";
    private static final String PREFERENCE_MAN_SESION = "estado.man.sesion";
    private static final String PREFERENCE_USER = "user";

    private static final String TAG = "EmailPassword";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(obtenerEstadomanSesion() && obtenerUsuario()!=null && !obtenerUsuario().equals(correoAdmin)){ //falta guardar si dani esta iniciado sesion para ir a su pantalla
            startActivity(new Intent(IniciarSesion.this,CerrarSesion.class));
            finish();
        } else if(obtenerEstadomanSesion() && obtenerUsuario().equals(correoAdmin)){
            startActivity(new Intent(IniciarSesion.this,AdminPage.class));
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        fa = FirebaseAuth.getInstance();
        mcontraseña = findViewById(R.id.contraseña_sign);
        mcorreo = findViewById(R.id.correo_sign);
        manSesion = findViewById(R.id.mantener_sesion);
        isActivatedmanSesion = manSesion.isChecked(); //desactivado
        manSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isActivatedmanSesion){
                    manSesion.setChecked(false);
                }
                isActivatedmanSesion = manSesion.isChecked();
            }
        });
    }
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = fa.getCurrentUser();

    }


    public void guardarEstadomanSesion(){
        String correo = mcorreo.getText().toString();
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCE, MODE_PRIVATE);
        preferences.edit().putBoolean(PREFERENCE_MAN_SESION, manSesion.isChecked()).apply();
        preferences.edit().putString(PREFERENCE_USER,correo).apply();
    }

    public String obtenerUsuario(){
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCE, MODE_PRIVATE);
        return preferences.getString(PREFERENCE_USER, "correo");
    }

    public static String imprimirUser(Context c){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCE, MODE_PRIVATE);
        return preferences.getString(PREFERENCE_USER, mcorreo.getText().toString());
    }

    public boolean obtenerEstadomanSesion(){
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCE, MODE_PRIVATE);
        return preferences.getBoolean(PREFERENCE_MAN_SESION, false);
    }

    public static void cambiarEstadomanSesion(Context c, boolean estado){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCE, MODE_PRIVATE);
        preferences.edit().putBoolean(PREFERENCE_MAN_SESION, estado).apply();
    }

    public void iniciarSesion(View v){
        String correo = mcorreo.getText().toString();
        String contraseña = mcontraseña.getText().toString();
        Button b = findViewById(R.id.iniciarSesionButton);
        b.setEnabled(false);

        if(!correo.isEmpty() && !contraseña.isEmpty()) {
            fa.signInWithEmailAndPassword(correo, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    String correo2 = mcorreo.getText().toString();
                    if (task.isSuccessful() && !isDani(correo2)){
                        Log.d(TAG, "Inicio sesión: correcto ");
                        startActivity(new Intent(IniciarSesion.this,CerrarSesion.class));
                        guardarEstadomanSesion();
                        Toast.makeText(IniciarSesion.this, "Has iniciado sesión como "+ imprimirUser(IniciarSesion.this), Toast.LENGTH_LONG).show(); // Pruebaaaa!!!
                        finish();

                    }
                    else if (task.isSuccessful() && isDani(correo2)){
                        Log.d(TAG, "Has entrado en modo administrador");
                        startActivity(new Intent(IniciarSesion.this,AdminPage.class));
                        guardarEstadomanSesion();
                        finish();

                    }
                    else {

                        Log.w(TAG, "Inicio sesión: incorrecto",task.getException() );
                        Toast.makeText(IniciarSesion.this,
                                "Error al iniciar sesión,compruebe que todos los campos están correctos"
                                , Toast.LENGTH_LONG).show();
                        Button b = findViewById(R.id.iniciarSesionButton);
                        b.setEnabled(true);

                    }
                }
            });
        }
        else {
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_LONG).show();
            b.setEnabled(true);
        }
    }

    public void goToRegistro(View v){
        Intent intent = new Intent(IniciarSesion.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    private boolean isDani(String correo){
        return correo.equals(correoAdmin);
    }
}
