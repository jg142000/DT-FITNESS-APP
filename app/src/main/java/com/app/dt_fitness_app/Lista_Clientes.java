package com.app.dt_fitness_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Lista_Clientes extends AppCompatActivity {

    RecyclerView rv;
    List<Cliente> clienteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        rv = (RecyclerView) findViewById(R.id.recycler);

        rv.setLayoutManager(new LinearLayoutManager(this));

        clienteList = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();



    }
}
