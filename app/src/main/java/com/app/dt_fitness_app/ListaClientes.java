package com.app.dt_fitness_app;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ListaClientes extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colRef = db.collection("Clientes");

    RecyclerView mFirestoreList;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        RecyclerView mFirestoreList = findViewById(R.id.recycler_view);

        Query query = db.collection("Clientes");

        FirestoreRecyclerOptions<Cliente> options = new FirestoreRecyclerOptions.Builder<Cliente>()
                .setQuery(query, Cliente.class).build();

         adapter = new FirestoreRecyclerAdapter<Cliente, ClienteHolder>(options) {

            @NonNull
            @Override
            public ClienteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.clientes_item, parent, false);
                return new ClienteHolder(v);
            }

            @Override
            protected void onBindViewHolder(ClienteHolder clienteHolder, int i, Cliente cliente) {

                clienteHolder.textViewNombre.setText(cliente.getNombre());
                clienteHolder.textViewCorreo.setText(cliente.getCorreo());
                clienteHolder.textViewPriority.setText(cliente.getBono());
            }
        };
         mFirestoreList.setHasFixedSize(true);
         mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
         mFirestoreList.setAdapter(adapter);
    }

    private class ClienteHolder extends RecyclerView.ViewHolder {

        private TextView textViewNombre;
        private TextView textViewCorreo;
        private TextView textViewPriority;

        public ClienteHolder(@NonNull View itemView) {
            super(itemView);

            textViewNombre = itemView.findViewById(R.id.text_title);
            textViewCorreo = itemView.findViewById(R.id.text_description);
            textViewPriority = itemView.findViewById(R.id.text_priority);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}