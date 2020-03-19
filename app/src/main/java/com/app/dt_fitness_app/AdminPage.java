package com.app.dt_fitness_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminPage extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference clienteReferencia = db.collection("Clientes");
    private Clientes_adapter adapter;
    private static final String TAG = "DocSnippets";
    private static final String STRING_PREFERENCE =  "com.app.dt_fitness_app";
    private static final String CARD_USER = "user";  // Usuario al que accedemos
    private static String correo_card;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setUpRecylerView();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    private void setUpRecylerView() {
        Query query = clienteReferencia.orderBy("nombre",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Cliente> options = new FirestoreRecyclerOptions.Builder<Cliente>()
                .setQuery(query,Cliente.class)
                .build();
        adapter = new Clientes_adapter(options,Clientes_adapter.getLista_clientes());
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new Clientes_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot) {

                SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCE, MODE_PRIVATE);
                preferences.edit().putString(CARD_USER,documentSnapshot.getString("correo")).apply();
                correo_card = documentSnapshot.getString("correo");
                startActivity(new Intent (AdminPage.this,info_user.class));

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_dani,menu);
        MenuItem item = menu.findItem(R.id.action_buscador);
        SearchView searchView = (SearchView) item.getActionView();

       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    ArrayList<Cliente> listaFiltrada = filter(Clientes_adapter.getLista_clientes(),newText);
                    adapter.setFilter(listaFiltrada);
                }
                catch (Exception e){
                  e.printStackTrace();
                }
                return false;
            }
        });
       item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
           @Override
           public boolean onMenuItemActionExpand(MenuItem item) {
               return true;
           }

           @Override
           public boolean onMenuItemActionCollapse(MenuItem item) {
               adapter.setFilter(Clientes_adapter.getLista_clientes());
               return true;
           }
       });

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int res_id = item.getItemId();
        switch (res_id) {
            case R.id.action_settings:
                startActivity(new Intent(AdminPage.this,CerrarSesion.class));
                return true;

            case R.id.action_buscador:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ArrayList<Cliente> filter(ArrayList<Cliente> lista_clientes,String texto) {

        ArrayList<Cliente> lisctaFiltrada = new ArrayList<>();
        try {
            texto = texto.toLowerCase();
            for(Cliente c : adapter.getLista_clientes()){
                String c2 = c.getNombre().toLowerCase();
                if(c2.contains(texto)){
                    lisctaFiltrada.add(c);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return lisctaFiltrada;
    }




    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
    }


    public static String imprimirUser(Context c){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCE, MODE_PRIVATE);
        return preferences.getString(CARD_USER, correo_card);
    }





}