package com.julio.carwashtogo3.ui.cliente.catalogo;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.julio.carwashtogo3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleProductoFragment extends Fragment {


    public DetalleProductoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle_producto, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view, savedInstanceState );

        Button btnComprar = view.findViewById ( R.id.btnComprar );

        btnComprar.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                
            }
        } );
    }
}
