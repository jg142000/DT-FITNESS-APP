package com.app.dt_fitness_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import com.google.firebase.auth.FirebaseAuth;

public class CerrarSesion extends AppCompatActivity {

    private Button mosUser;
    private Button cerrarSesion;
    private FirebaseAuth fa;
    private AdView mAdView;
    private Button refrescar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerrar_sesion);
        cerrarSesion = findViewById(R.id.cerrarsesion);
        mosUser = findViewById(R.id.mostrarUserBT);
        refrescar = findViewById(R.id.refrescar);
        fa = FirebaseAuth.getInstance();
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fa.signOut();
                IniciarSesion.cambiarEstadomanSesion(CerrarSesion.this, false);
                startActivity(new Intent(CerrarSesion.this, IniciarSesion.class));
                finish();
            }
        });
        mosUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CerrarSesion.this, "Tu correo es " + IniciarSesion.imprimirUser(CerrarSesion.this), Toast.LENGTH_LONG).show();
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        refrescar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CerrarSesion.this, AdminPage.class));
            }
        });

    }
}
