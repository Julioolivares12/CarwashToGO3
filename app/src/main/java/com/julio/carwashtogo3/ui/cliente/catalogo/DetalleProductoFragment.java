package com.julio.carwashtogo3.ui.cliente.catalogo;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.common.Constantes;
import com.julio.carwashtogo3.common.DatePickerFragment;
import com.julio.carwashtogo3.model.Paquete;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleProductoFragment extends Fragment {


    //private final TextView tv_titulo_compra,tv_descripcion_compra;
    //private ImageView iv_compra;

    private ImageButton btnAbrirCalendario;

    private EditText fechaCompra;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private DatabaseReference paqueteRef;

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
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view, savedInstanceState );

        Button btnComprar = view.findViewById ( R.id.btnComprar );
        btnAbrirCalendario= view.findViewById ( R.id.btnAbrirCalendario );
      final TextView tv_titulo_compra= view.findViewById ( R.id.tv_titulo_compra );
      final TextView tv_descripcion_compra = view.findViewById ( R.id.tv_descripcion_compra );
      final ImageView iv_compra =view.findViewById(R.id.img_compra);
        fechaCompra = view.findViewById ( R.id.fechaCompra );

        firebaseDatabase = FirebaseDatabase.getInstance ();
        reference = firebaseDatabase.getReference(Constantes.REF_COMPRAS);
        paqueteRef= firebaseDatabase.getReference ( Constantes.REF_PAQUETES );
        if (getArguments ()!= null){
           final String UID_PRODUCTO = getArguments ().getString (Constantes.UID_PAQUETE);
            paqueteRef.child (UID_PRODUCTO).addValueEventListener ( new ValueEventListener () {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists ()){
                       final Paquete paquete = dataSnapshot.getValue (Paquete.class);

                       Log.i ( "Paquete",paquete.getTitulo () );
                        if (paquete!= null){
                            tv_titulo_compra.setText ( paquete.getTitulo () );
                            tv_descripcion_compra.setText ( paquete.getDescripcion () );
                            if (paquete.getUrlImagen () != null && !TextUtils.isEmpty(paquete.getUrlImagen())){
                                View view1 = getView ();
                                assert view1!= null;
                                Glide.with (view1).load(paquete.getUrlImagen()).centerCrop().into (iv_compra );
                            }

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.i ( "Detalle Compra",databaseError.getMessage());
                }
            } );

        }



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
