package com.app.dt_fitness_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class Clientes_adapter extends FirestoreRecyclerAdapter<Cliente, Clientes_adapter.ClienteHolder> {


    private OnItemClickListener listener;
    private static ArrayList<Cliente> lista_clientes;


    public Clientes_adapter(@NonNull FirestoreRecyclerOptions<Cliente> options,ArrayList<Cliente> lista_clientes) {

        super(options);
        this.lista_clientes = lista_clientes;
    }

    @Override
    protected void onBindViewHolder(@NonNull ClienteHolder holder, int position, @NonNull Cliente model) {
        holder.nombre_cliente.setText(model.getNombre());
        lista_clientes.add(model);
    }


    @NonNull
    @Override
    public ClienteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cliente_item,
                parent,false);
        return new ClienteHolder(v);
    }


    class ClienteHolder extends RecyclerView.ViewHolder{

        TextView nombre_cliente;
        public ClienteHolder(@NonNull View itemView) {
            super(itemView);
            nombre_cliente = itemView.findViewById(R.id.id_cliente);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position));
                    }
                }
            });
        }
    }



    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot posicion);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public static ArrayList<Cliente> getLista_clientes(){
        return lista_clientes;
    }

   public void setFilter(ArrayList<Cliente> lista_clientes){
        this.lista_clientes = new ArrayList<>();
        lista_clientes.addAll(lista_clientes);
        notifyDataSetChanged();
    }
}
