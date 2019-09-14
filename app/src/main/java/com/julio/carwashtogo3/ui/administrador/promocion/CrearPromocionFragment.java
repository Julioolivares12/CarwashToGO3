package com.julio.carwashtogo3.ui.administrador.promocion;


import android.content.Intent;
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
import com.julio.carwashtogo3.model.Promocion;

import static android.app.Activity.RESULT_OK;

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

    private Promocion promocion = new Promocion();


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

        btnCrearPromo.setEnabled(false);

        btnSubirImagenPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrigaleria = new Intent();
                abrigaleria.setType("image/*");
                abrigaleria.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(abrigaleria,"selecciona una imagen"),REQUEST_GALERIA3);
            }
        });
        btnCrearPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = edtNombrePromo.getText().toString();
                double precio = Double.valueOf(edtPrecio.getText().toString().trim());
                String fechainicio = edtFechaInicio.getText().toString().trim();
                String fechaFinal = edtFechaFin.getText().toString().trim();
                String descripcionPromo = edtDescripcionPromo.getText().toString();

                promocion.setNombre(nombre);
                promocion.setPrecio(precio);
                promocion.setFechaIncio(fechainicio);
                promocion.setFechaFinal(fechaFinal);
                promocion.setDescripcionPromo(descripcionPromo);

                crearPromocion(promocion);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALERIA3){
            if (resultCode == RESULT_OK){
                urlImagenSeleccionada = data.getData();
                btnCrearPromo.setEnabled(true);
            }
        }
    }
    private void crearPromocion(Promocion promocion){
        
    }

}
