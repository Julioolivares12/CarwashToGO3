package com.julio.carwashtogo3.ui.cliente.catalogo;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.support.design.widget.Snackbar;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.common.Constantes;
import com.julio.carwashtogo3.common.DatePickerFragment;
import com.julio.carwashtogo3.model.CompraPaquete;
import com.julio.carwashtogo3.model.Paquete;
import com.julio.carwashtogo3.model.User;

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
    private FirebaseAuth mAuth;

    private String urlImagen;

    private DatabaseReference refUsuarios;
    ProgressDialog progressDialog;
    public DetalleProductoFragment() {
        // Required empty public constructor
    }


    public void setUrlImagen(String urlImagen){
        this.urlImagen = urlImagen;
    }

    public String getUrlImagen(){
        return urlImagen;
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
        refUsuarios = firebaseDatabase.getReference ( Constantes.REF_USUARIOS );
        mAuth = FirebaseAuth.getInstance();
        if (getArguments()!= null){
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

                                setUrlImagen ( paquete.getUrlImagen () );
                                Glide.with(view).load(paquete.getUrlImagen()).centerCrop().into (iv_compra);
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


                progressDialog = new ProgressDialog (getContext ());
                progressDialog.setMessage ( "PROCESANDO COMPRA" );
                progressDialog.setIndeterminate ( true );
                progressDialog.setCancelable ( false );
                progressDialog.show ();
               final String key= reference.push ().getKey ();
               final String title = tv_titulo_compra.getText ().toString ();
               final String descripcion = tv_descripcion_compra.getText ().toString ();
               final String fecha = fechaCompra.getText ().toString ();

                FirebaseUser usuarioActual = mAuth.getCurrentUser ();

                refUsuarios.child ( usuarioActual.getUid () ).addValueEventListener ( new ValueEventListener () {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists ()){
                               User user = dataSnapshot.getValue (User.class);
                               CompraPaquete compraPaquete = new CompraPaquete ();
                               compraPaquete.setUID(key);
                               compraPaquete.setTitulo(title);
                               compraPaquete.setDescripcion(descripcion);
                               compraPaquete.setFechaReserva(fecha);
                               compraPaquete.setImagen (getUrlImagen ());
                               compraPaquete.setNombreCliente (user.getNombre ());
                               comPrar(compraPaquete);
                               progressDialog.dismiss ();
                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        progressDialog.dismiss ();
                    }
                } );
            }
        } );
    }

    private void comPrar(CompraPaquete compraPaquete){
        reference.child (compraPaquete.getUID ()).setValue ( compraPaquete ).addOnSuccessListener ( new OnSuccessListener<Void> () {
            @Override
            public void onSuccess(Void aVoid) {
                View view = getView ();
                assert view != null;
                Snackbar.make ( view,"Compra realizada con exito",Snackbar.LENGTH_LONG ).show ();
            }
        } );

        refUsuarios.child(mAuth.getUid()).child ("compras").push().setValue(compraPaquete);

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
