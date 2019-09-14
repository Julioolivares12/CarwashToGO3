package com.julio.carwashtogo3.ui.administrador.empresa;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.common.Constantes;
import com.julio.carwashtogo3.model.Empresa;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class NuevaEmpresaFragment extends Fragment {


    private EditText edtNombreEmpresa,edtEncagado,edtUbicacion,edtTelefono;
    private Spinner spnNivel;
    private Button btnSubirlogo,btnGuardar;
    public static final int REQUEST_GALERIA2=10;
    private Uri imagenSeleccionada;

    //firebase
    //----------------------------------------
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refEmpresas = database.getReference("empresas");
    private StorageReference storageReference;
    //-----------------------------------------
    private String nivel;

    Empresa empresa = new Empresa();
    public NuevaEmpresaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nueva_empresa, container, false);

        edtNombreEmpresa = view.findViewById(R.id.edtNombreEmpresaActualizar);
        edtEncagado = view.findViewById(R.id.edtEncargadoActualizar);
        edtUbicacion = view.findViewById(R.id.edtUbicacionActualizar);
        edtTelefono = view.findViewById(R.id.edtTelefonoActualizar);

        spnNivel = view.findViewById(R.id.spnNivelActualizar);
        btnSubirlogo = view.findViewById(R.id.btnActualizarEmpresalogo);
        btnGuardar= view.findViewById(R.id.btnActualizarEmpresa);

        storageReference = FirebaseStorage.getInstance().getReference();

        btnGuardar.setEnabled(false);
        final String [] niveles = {
                "nivel 1","nivel 2","nivel 3"
        };
        fillSpinner(niveles);
        spnNivel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nivel = niveles[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSubirlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"selecciona una foto"),REQUEST_GALERIA2);
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = UUID.randomUUID().toString();
                String nombreEmpresa = edtNombreEmpresa.getText().toString();
                String encargado = edtEncagado.getText().toString();
                String ubicacion = edtUbicacion.getText().toString();
                String telefono = edtTelefono.getText().toString();

                //empresa.setUid(id);
                empresa.setNombreEmpresa(nombreEmpresa);
                empresa.setEncargado(encargado);
                empresa.setUbicacion(ubicacion);
                empresa.setNivel(nivel);
                empresa.setTelefono(telefono);
            }
        });

        return view;
    }

    private void fillSpinner(String[] value){
        Context context =getContext();
        if (context != null){
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                    context.getApplicationContext()
                    ,android.R.layout.simple_list_item_1
                    ,value);
            spnNivel.setAdapter(arrayAdapter);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALERIA2){
            if (resultCode == RESULT_OK){
                assert data != null;
                imagenSeleccionada = data.getData();
                btnGuardar.setEnabled(true);
                crearEmpresa();
            }
        }
    }

    private void crearEmpresa(){
        String key =refEmpresas.push().getKey();
        assert key != null;
        empresa.setUid(key);
        refEmpresas.child(key).setValue(empresa);
        subirFoto(key,imagenSeleccionada);
    }

    public void subirFoto(final String k, Uri uri){
        try {
            final StorageReference storageR =storageReference.child(Constantes.LOGOS_EMPRESAS +k+"."+ getFileExtension(uri));
            storageR.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageR.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imagen = uri.toString();
                            empresa.setUrlImagen(imagen);
                            refEmpresas.child(k).setValue(empresa);
                            View view = getView();
                            assert view != null;
                            Snackbar.make(view,"Empresa creada",Snackbar.LENGTH_SHORT).show();
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
