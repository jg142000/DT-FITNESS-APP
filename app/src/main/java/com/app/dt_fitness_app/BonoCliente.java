package com.app.dt_fitness_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BonoCliente extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bono_cliente);

    }
    public void cerrarSesion(View v){
        startActivity(new Intent(BonoCliente.this,CerrarSesion.class));
    }
    //prueba
    
}
