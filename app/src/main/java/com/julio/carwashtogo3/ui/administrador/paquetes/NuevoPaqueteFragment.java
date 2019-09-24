package com.julio.carwashtogo3.ui.administrador.paquetes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.julio.carwashtogo3.common.Constantes;
import com.julio.carwashtogo3.model.Paquete;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.julio.carwashtogo3.R;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class NuevoPaqueteFragment extends Fragment {


    private EditText edtdescripcion,edtprecio,edttitulo;
    private Button btnGuardarPaquete;
    public static final int REQUEST_GALERIA2=10;
    private Uri imagenSeleccionada;

    //firebase
    //----------------------------------------
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refPaquetes = database.getReference(Constantes.REF_PAQUETES);
    private StorageReference storageReference;
    //-----------------------------------------
    Paquete paq = new Paquete();
    public NuevoPaqueteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nuevo_paquete, container, false);
        edtdescripcion = view.findViewById(R.id.edtdescripcionPaquete);
        edtprecio = view.findViewById(R.id.edt_precio_paquete);
        edttitulo = view.findViewById(R.id.edt_titulo_paqquete);
        btnGuardarPaquete= view.findViewById(R.id.btnguardarpaquete);

        storageReference = FirebaseStorage.getInstance().getReference();
        btnGuardarPaquete.setEnabled(true);
        btnGuardarPaquete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = UUID.randomUUID().toString();
                String descripcion = edtdescripcion.getText().toString();
                String titulo = edttitulo.getText().toString();
                Double precio = Double.parseDouble(edtprecio.getText().toString());

                //empresa.setUid(id);
                paq.setPrecio(precio);
                paq.setDescripcion(descripcion);
                paq.setTitulo(titulo);
                crearPaquete(paq);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALERIA2){
            if (resultCode == RESULT_OK){
                assert data != null;
                imagenSeleccionada = data.getData();
                btnGuardarPaquete.setEnabled(true);
                //crearPaquete();
            }
        }
    }

    private void crearPaquete(Paquete paq) {
        final  Paquete paquete = paq;
        final  String key =refPaquetes.push().getKey();
        assert key != null;
        paquete.setUid(key);
        refPaquetes.child(key).setValue(paquete).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //subirFoto(key,imagenSeleccionada,empresa);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "ocuurio un error al guardar los datos" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void subirFoto(final String k, Uri uri,Paquete em){
        try {
            final Paquete paquete = em;
            final StorageReference storageR =storageReference.child(Constantes.LOGOS_EMPRESAS +k+"."+ getFileExtension(uri));
            storageR.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageR.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imagen = uri.toString();
                            paquete.setUrlImagen(imagen);
                            refPaquetes.child(k).setValue(paquete);
                            View view = getView();
                            assert view != null;
                            Snackbar.make(view,"Paquete creado",Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getFileExtension(Uri uri) {
        FragmentActivity activity = getActivity();
        assert activity != null;
        ContentResolver cR = activity.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}
