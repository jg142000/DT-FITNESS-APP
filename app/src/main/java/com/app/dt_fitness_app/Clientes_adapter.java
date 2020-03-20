package com.app.dt_fitness_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class Clientes_adapter extends FirestoreRecyclerAdapter<Cliente, Clientes_adapter.ClienteHolder> {
    //private List<Cliente> lista_clientes;
   // private List<Cliente> lista_clientesFull;

    private OnItemClickListener listener;



    public Clientes_adapter(@NonNull FirestoreRecyclerOptions<Cliente> options) {
        super(options);
        //this.lista_clientes = lista_clientes;
        //lista_clientesFull = new ArrayList<>(lista_clientes);
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

    /*public int getItemCount(){
        return lista_clientes.size();
    }
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Cliente> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(lista_clientesFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Cliente cliente : lista_clientesFull){
                    if(cliente.getNombre().toLowerCase().contains(filterPattern)){
                        filteredList.add(cliente);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            lista_clientes.clear();
            lista_clientes.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
*/
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



   /*public void setFilter(ArrayList<Cliente> lista_clientes){
        this.lista_clientes = new ArrayList<>();
        lista_clientes.addAll(lista_clientes);
        notifyDataSetChanged();
    }*/
}
