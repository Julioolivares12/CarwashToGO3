package com.julio.carwashtogo3.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.model.Promocion;

import java.util.List;

public class PromocionesRecyclerViewAdapter extends RecyclerView.Adapter<PromocionesRecyclerViewAdapter.ViewHolder> {
    List<Promocion> promocions;
    private int resource;

    public PromocionesRecyclerViewAdapter(List<Promocion>promocions, int resource) {
        this.promocions = promocions;
        this.resource = resource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Promocion promocion = promocions.get(i);
        viewHolder.txtTitulo.setText(promocion.getNombre());
        viewHolder.txtCreado.setText(promocion.getEncargado());
        viewHolder.txtFechaI.setText(promocion.getFechaIncio());
        viewHolder.txtFechaF.setText(promocion.getFechaFinal());
        viewHolder.txtPrecio.setText(promocion.getPrecio());
    }

    @Override
    public int getItemCount() {
        return promocions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTitulo;
        private TextView txtCreado;
        private TextView txtFechaI;
        private TextView txtFechaF;
        private TextView txtPrecio;
        private ImageView imagenPromo;
        public View view;


        public ViewHolder(View view){
            super(view);

            this.view = view;
            this.txtTitulo = (TextView)view.findViewById(R.id.tvTituloPromo);
            this.txtCreado = (TextView)view.findViewById(R.id.tvCreado);
            this.txtFechaI = (TextView)view.findViewById(R.id.tvFechaI);
            this.txtFechaF = (TextView)view.findViewById(R.id.tvFechaF);
            this.txtPrecio = (TextView)view.findViewById(R.id.tvPrecio);
            this.imagenPromo = (ImageView)view.findViewById(R.id.logoPromocionList);


        }
    }

}
