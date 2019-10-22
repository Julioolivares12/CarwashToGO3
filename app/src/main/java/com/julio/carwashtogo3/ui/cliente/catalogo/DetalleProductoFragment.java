package com.julio.carwashtogo3.ui.cliente.catalogo;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.common.DatePickerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleProductoFragment extends Fragment {


    private TextView tv_titulo_compra,tv_descripcion_compra;
    private ImageView iv_compra;

    private ImageButton btnAbrirCalendario;

    private EditText fechaCompra;

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
        btnAbrirCalendario= view.findViewById ( R.id.btnAbrirCalendario );
        tv_titulo_compra= view.findViewById ( R.id.tv_titulo_compra );
        tv_descripcion_compra = view.findViewById ( R.id.tv_descripcion_compra );
        fechaCompra = view.findViewById ( R.id.fechaCompra );

        btnAbrirCalendario.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        } );
        btnComprar.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                
            }
        } );
    }

    private void showDateDialog() {
        DatePickerFragment datePickerFragment = DatePickerFragment.newInstance ( new DatePickerDialog.OnDateSetListener () {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final  String selectedDate = dayOfMonth+"/"+(month+1)+"/"+year;
                fechaCompra.setText (selectedDate);
            }
        } );

        datePickerFragment.show ( getActivity ().getSupportFragmentManager (),"datePicker" );
    }
}
