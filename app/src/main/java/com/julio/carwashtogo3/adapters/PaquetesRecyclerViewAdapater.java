package com.julio.carwashtogo3.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.listeners.PaquetesOnItemClickListener;
import com.julio.carwashtogo3.model.Paquete;

import java.util.List;

public class PaquetesRecyclerViewAdapater extends RecyclerView.Adapter<PaquetesRecyclerViewAdapater.PaquetesHolder> {


    List<Paquete> paqueteList;

    private final PaquetesOnItemClickListener onItemClickListener;


    public PaquetesRecyclerViewAdapater(List<Paquete> paqueteList, PaquetesOnItemClickListener onItemClickListener){
        this.paqueteList = paqueteList;
        this.onItemClickListener = onItemClickListener;

    }

    @NonNull
    public  PaquetesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view = LayoutInflater.from ( viewGroup.getContext ()).inflate ( R.layout.item_paquetes,viewGroup,false );

        return  new PaquetesHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaquetesHolder paquetesHolder, int i){
        Paquete paquete = paqueteList.get ( i );
        paquetesHolder.bind (paquete, onItemClickListener);
    }

    @Override
    public  int getItemCount(){
        return paqueteList.size ();
    }


    class PaquetesHolder extends RecyclerView.ViewHolder{

           private ImageView logoPaquetes;
           public PaquetesHolder(@NonNull View itemView) {
               super(itemView);
               logoPaquetes = itemView.findViewById(R.id.logoPaqueteList);
        }
        private void bind(final Paquete paquete,final PaquetesOnItemClickListener onItemClickListener){
              Glide.with(itemView.getContext()).load(paquete.getUrlImagen()).centerCrop().into(logoPaquetes);
    //
              itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                  public void onClick(View v) {
                        onItemClickListener.OnClick(paquete);
                    }
                });
            }
    }
}
