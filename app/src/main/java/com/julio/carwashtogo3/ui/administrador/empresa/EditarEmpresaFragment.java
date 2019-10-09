package com.julio.carwashtogo3.ui.administrador.empresa;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.common.Constantes;
import com.julio.carwashtogo3.model.Empresa;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditarEmpresaFragment extends Fragment {


    private TextView txtNombreEmpresaDetalle,txtEncargadoEmpresaDetalle,txtUbicacionEmpresaDetalle,txtTelefonoEmpresaDetalle,txtNivelEmpresaDetalle;
    private ImageView imageView;
    private String UIdEmpresa;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public EditarEmpresaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_empresa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Constantes.REF_EMPRESA);

        txtNombreEmpresaDetalle = view.findViewById(R.id.txtNombreEmpresaDetalle);
        txtEncargadoEmpresaDetalle = view.findViewById(R.id.txtEncargadoEmpresaDetalle);
        txtNivelEmpresaDetalle = view.findViewById(R.id.txtNivelEmpresaDetalle);
        txtUbicacionEmpresaDetalle = view.findViewById(R.id.txtUbicacionEmpresaDetalle);
        txtTelefonoEmpresaDetalle = view.findViewById(R.id.txtTelefonoEmpresaDetalle);
        imageView = view.findViewById(R.id.iv_imagen_EmpresaDetalle);
        if (getArguments() != null){
            UIdEmpresa = getArguments().getString(Constantes.UID_EMPRESA);
            assert UIdEmpresa != null;
            databaseReference.child(UIdEmpresa).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        Empresa empresa = dataSnapshot.getValue(Empresa.class);
                        if (empresa != null){
                            txtNombreEmpresaDetalle.setText(empresa.getNombreEmpresa());
                            txtEncargadoEmpresaDetalle.setText(empresa.getEncargado());
                            txtNivelEmpresaDetalle.setText(empresa.getNivel());
                            txtUbicacionEmpresaDetalle.setText(empresa.getUbicacion());
                            txtTelefonoEmpresaDetalle.setText(empresa.getTelefono());

                            Glide.with(view)
                                    .load(empresa.getUrlImagen())
                                    .centerCrop()
                                    .into(imageView);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), "ocuurio un error"+databaseError.getDetails(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
