package com.julio.carwashtogo3.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.julio.carwashtogo3.model.Empresa;

import java.util.ArrayList;
import java.util.List;

public class EncargadoSearchableSpinnerAdapter extends ArrayAdapter<Empresa> {

    private List<Empresa> empresas;
    private Context context;

    public EncargadoSearchableSpinnerAdapter(@NonNull Context context, int resource, List<Empresa> objects) {
        super ( context, resource, objects );
        this.empresas = objects;
        this.context = context;
    }

    public int getCount(){
        return this.empresas.size ();
    }

    public Empresa getItem(int position){
        return this.empresas.get (position );
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = new TextView(context);
        label.setText (empresas.get(position).getNombreEmpresa ());
        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getDropDownView ( position, convertView, parent );
        TextView label = new TextView(context);
        label.setText (empresas.get(position).getNombreEmpresa ());
        return label;
    }
}
