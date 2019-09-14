package com.julio.carwashtogo3.ui.administrador.promocion;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.common.Constantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class CrearPromocionFragment extends Fragment {

    private EditText edtNombrePromo,edtPrecio,edtFechaInicio,edtFechaFin,edtDescripcionPromo;
    private CheckBox chAceite,chRinso,chAspirado,chAmbientador,chEspuma,chCera;
    private Button btnSubirImagenPromo,btnCrearPromo;
    private Uri urlImagenSeleccionada;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref_promociones = firebaseDatabase.getReference(Constantes.REF_PROMOCIONES);
    private StorageReference storageReference;

    public static int REQUEST_GALERIA3 = 13;

    public CrearPromocionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_crear_promocion, container, false);
        storageReference = FirebaseStorage.getInstance().getReference();

        edtNombrePromo = view.findViewById(R.id.edtNombrePromocion);
        edtPrecio = view.findViewById(R.id.edtPrecio);
        edtFechaInicio = view.findViewById(R.id.edtFechaInicio);
        edtFechaFin = view.findViewById(R.id.edtFechaFin);
        edtDescripcionPromo = view.findViewById(R.id.edtDescripcionPromo);

        chAceite = view.findViewById(R.id.chAceite);
        chRinso = view.findViewById(R.id.chRinso);
        chAspirado = view.findViewById(R.id.chAspirado);
        chAmbientador = view.findViewById(R.id.chAmbientador);
        chEspuma = view.findViewById(R.id.chEspuma);
        chCera = view.findViewById(R.id.chCera);

        btnSubirImagenPromo = view.findViewById(R.id.btnSubirImagenPromo);
        btnCrearPromo = view.findViewById(R.id.btnCrearPromo);


        return view;
    }

}
