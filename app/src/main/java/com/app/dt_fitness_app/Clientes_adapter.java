package com.app.dt_fitness_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class Clientes_adapter extends FirestoreRecyclerAdapter<Cliente, Clientes_adapter.ClienteHolder> implements Filterable {
    private List<String> lista_clientes;

    private OnItemClickListener listener;
    private List<String> lista_clientesFull;

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
    Clientes_adapter(@NonNull FirestoreRecyclerOptions<Cliente> options, List<String> lista_clientes) {
        super(options);
        this.lista_clientes = lista_clientes;
    }

    @Override
    protected void onBindViewHolder(@NonNull ClienteHolder holder, int position, @NonNull Cliente model) {
        holder.nombre_cliente.setText(model.getNombre());

    }


    @NonNull
    @Override
    public ClienteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cliente_item,
                parent,false);
        return new ClienteHolder(v);
    }
    public int getItemCount() {
        return lista_clientes.size();
    }


    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<String> results = new ArrayList<>();
                if (lista_clientesFull == null)
                    lista_clientesFull  = lista_clientes;
                if (constraint != null){
                    if(lista_clientesFull !=null & lista_clientesFull.size()>0 ){
                        for ( final String g :lista_clientesFull) {
                            if (g.toLowerCase().contains(constraint.toString()))results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                lista_clientes = (ArrayList<String>)results.values;
                notifyDataSetChanged();

            }
        };
    };





    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot posicion);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}