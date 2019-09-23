package com.julio.carwashtogo3.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.listeners.EmpresaOnItemClickListener;
import com.julio.carwashtogo3.model.Empresa;

import java.util.List;

public class EmpresaRecyclerViewAdapter extends RecyclerView.Adapter<EmpresaRecyclerViewAdapter.EmpresaHolder> {

    List<Empresa> empresaList;
    private final EmpresaOnItemClickListener onItemClickListener;

    public EmpresaRecyclerViewAdapter(List<Empresa> empresaList,EmpresaOnItemClickListener onItemClickListener){
        this.empresaList = empresaList;
        this.onItemClickListener= onItemClickListener;
    }

    @NonNull
    @Override
    public EmpresaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_empresa,viewGroup,false);

        return new EmpresaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpresaHolder empresaHolder, int i) {
        Empresa empresa = empresaList.get(i);
        empresaHolder.bind(empresa,onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return empresaList.size();
    }

    class EmpresaHolder extends RecyclerView.ViewHolder{

        private ImageView logoEmpresa;
        public EmpresaHolder(@NonNull View itemView) {
            super(itemView);
           logoEmpresa = itemView.findViewById(R.id.logoEmpresaList);
        }
        private void bind(final Empresa empresa,final EmpresaOnItemClickListener onItemClickListener){
            Glide.with(itemView.getContext()).load(empresa.getUrlImagen()).centerCrop().into(logoEmpresa);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnClick(empresa);
                }
            });
        }

    }
}
