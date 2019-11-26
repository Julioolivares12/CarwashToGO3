package com.julio.carwashtogo3.ui.administrador.paquetes;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.julio.carwashtogo3.R;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class NuevoPaqueteFragment extends Fragment {


    private EditText edtdescripcion, edtprecio, edttitulo;
    private Button btnGuardarPaquete, btnsubirfotopaquete;
    private ImageView imgPaquete;
    public static final int REQUEST_GALERIA2 = 10;
    private Uri imagenSeleccionada;
    //firebase
    //----------------------------------------
    private FirebaseDatabase database = FirebaseDatabase.getInstance ();
    DatabaseReference refPaquetes = database.getReference ( Constantes.REF_PAQUETES );
    DatabaseReference refPaquetes2 = database.getReference ( Constantes.REF_PAQUETES );
    DatabaseReference refPaquetes3 = database.getReference ( Constantes.REF_PAQUETES );
    private StorageReference storageReference;
    Paquete editedPackage;
    //-----------------------------------------
    Paquete paq = new Paquete ();

    public NuevoPaqueteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_nuevo_paquete, container, false );
        edtdescripcion = view.findViewById ( R.id.edtdescripcionPaquete );
        edtprecio = view.findViewById ( R.id.edt_precio_paquete );
        edttitulo = view.findViewById ( R.id.edt_titulo_paqquete );
        imgPaquete = view.findViewById ( R.id.imgPaquete );
        btnGuardarPaquete = view.findViewById ( R.id.btnguardarpaquete );
        btnsubirfotopaquete = view.findViewById ( R.id.btnagregarfotopaquete );
        storageReference = FirebaseStorage.getInstance ().getReference ();
        btnsubirfotopaquete.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent abrigaleria = new Intent ();
                abrigaleria.setType ( "image/*" );
                abrigaleria.setAction ( Intent.ACTION_GET_CONTENT );
                startActivityForResult ( Intent.createChooser ( abrigaleria, "selecciona una imagen" ), REQUEST_GALERIA2 );
            }
        } );

        if (getArguments () != null) {
            String paquete_id = getArguments ().getString ( Constantes.UID_PAQUETE );
            refPaquetes.child ( paquete_id ).addValueEventListener ( new ValueEventListener () {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists ()) {
                        Paquete paq = dataSnapshot.getValue ( Paquete.class );
                        editedPackage = paq;
                        edtdescripcion.setText ( paq.getDescripcion () );
                        edtprecio.setText ( paq.getPrecio ().toString () );
                        edttitulo.setText ( paq.getTitulo () );
                        Glide.with ( imgPaquete.getContext () ).load ( paq.getUrlImagen () ).centerCrop ().into ( imgPaquete );
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            } );
            btnGuardarPaquete.setText ( "ACTUALIZAR" );
        }

        btnGuardarPaquete.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String id = UUID.randomUUID ().toString ();
                String descripcion = edtdescripcion.getText ().toString ();
                String titulo = edttitulo.getText ().toString ();
                Double precio = Double.parseDouble ( edtprecio.getText ().toString () );
                if (getArguments () != null) {
                    final String paquete_id = getArguments ().getString ( Constantes.UID_PAQUETE );
                    editedPackage.setTitulo ( edttitulo.getText ().toString () );
                    editedPackage.setDescripcion ( edtdescripcion.getText ().toString () );
                    editedPackage.setPrecio ( Double.parseDouble ( edtprecio.getText ().toString () ) );
                    actualizarPaquete ( paquete_id, editedPackage );
                } else {

                    //empresa.setUid(id);
                    paq.setPrecio ( precio );
                    paq.setDescripcion ( descripcion );
                    paq.setTitulo ( titulo );
                    crearPaquete ( paq );
                }

            }
        } );

        return view;
    }

    public void RegresarLista(){
        View view = getView ();
        Navigation.findNavController ( view ).navigate ( R.id.navigation_listarPaquetes,new Bundle() );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view, savedInstanceState );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
        if (requestCode == REQUEST_GALERIA2) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                imagenSeleccionada = data.getData ();
                Glide.with ( imgPaquete.getContext () ).load ( imagenSeleccionada ).centerCrop ().into ( imgPaquete );
            }
        }
    }

    private void crearPaquete(Paquete paq) {
        final Paquete paquete = paq;
        final String key = refPaquetes.push ().getKey ();
        assert key != null;
        paquete.setUid ( key );
        refPaquetes.child ( key ).setValue ( paquete ).addOnSuccessListener ( new OnSuccessListener<Void> () {
            @Override
            public void onSuccess(Void aVoid) {
                if(imagenSeleccionada != null){
                    subirFoto ( key, imagenSeleccionada, paquete );
                }else{
                    RegresarLista ();
                }

            }
        } ).addOnFailureListener ( new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText ( getContext (), "ocuurio un error al guardar los datos" + e.getMessage (), Toast.LENGTH_SHORT ).show ();
            }
        } );
    }

    private void actualizarPaquete(final String key, final Paquete paquete) {
        refPaquetes.child ( key ).setValue ( paquete ).addOnSuccessListener ( new OnSuccessListener<Void> () {
            @Override
            public void onSuccess(Void aVoid) {
                if (imagenSeleccionada != null) {
                    subirFoto ( key, imagenSeleccionada, paquete );
                }else{
                    RegresarLista();
                }
            }
        } );
    }

    public void subirFoto(final String k, Uri uri, Paquete em) {
        try {
            final Paquete paquete = em;
            final StorageReference storageR = storageReference.child ( Constantes.LOGOS_PAQUETES + k + "." + getFileExtension ( uri ) );
            storageR.putFile ( uri ).addOnSuccessListener ( new OnSuccessListener<UploadTask.TaskSnapshot> () {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageR.getDownloadUrl ().addOnSuccessListener ( new OnSuccessListener<Uri> () {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imagen = uri.toString ();
                            paquete.setUrlImagen ( imagen );
                            refPaquetes3.child ( k ).setValue ( paquete ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    RegresarLista();
                                }
                            } );

                            //Snackbar.make(view,"Paquete creado",Snackbar.LENGTH_SHORT).show();
                        }
                    } );
                }
            } );
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public void ActualizarFoto(final String k, Uri uri, Paquete em) {
        try {
            final Paquete paquete = em;
            final StorageReference storageR = storageReference.child ( Constantes.LOGOS_PAQUETES + k + "." + getFileExtension ( uri ) );

        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public String getFileExtension(Uri uri) {
        FragmentActivity activity = getActivity ();
        assert activity != null;
        ContentResolver cR = activity.getContentResolver ();
        MimeTypeMap mime = MimeTypeMap.getSingleton ();
        return mime.getExtensionFromMimeType ( cR.getType ( uri ) );
    }

}
