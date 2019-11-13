package com.julio.carwashtogo3.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.listeners.MisComprasOnItemClickListener;
import com.julio.carwashtogo3.model.CompraPaquete;

import java.util.List;

public class AdapterMisCompras extends RecyclerView.Adapter<AdapterMisCompras.MisComprasViewHolder> {


    private List<CompraPaquete> misCompras;
    private final MisComprasOnItemClickListener _misComprasOnItemClickListener;

    public AdapterMisCompras(List<CompraPaquete> misCompras,MisComprasOnItemClickListener misComprasOnItemClickListener) {
        this._misComprasOnItemClickListener = misComprasOnItemClickListener;
        this.misCompras = misCompras;
    }

    @NonNull
    @Override
    public MisComprasViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from (viewGroup.getContext ()).inflate ( R.layout.item_mi_compras,viewGroup,false);
         return new MisComprasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MisComprasViewHolder misComprasViewHolder, int position) {
        CompraPaquete compraPaquete = misCompras.get(position);
        misComprasViewHolder.bind(compraPaquete,_misComprasOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return misCompras.size ();
    }

    class MisComprasViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        public MisComprasViewHolder(@NonNull View itemView) {
            super ( itemView );
            title = itemView.findViewById ( R.id.title_mis_compras);
        }
        public void bind(final CompraPaquete compraPaquete, final MisComprasOnItemClickListener misComprasOnItemClickListener){
            title.setText (compraPaquete.getTitulo ());
            itemView.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    misComprasOnItemClickListener.onClick(compraPaquete);
                }
            } );

        }
    }
}
