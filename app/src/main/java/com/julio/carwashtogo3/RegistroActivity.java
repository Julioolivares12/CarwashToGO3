package com.julio.carwashtogo3;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.julio.carwashtogo3.common.Constantes;
import com.julio.carwashtogo3.model.User;

public class RegistroActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    String role;
    private StorageReference mStorageRef;

    private Uri imagenSeleccionada;

    Toolbar toolbar;
    Button btnCrear,btnAbrirGaleria;
    EditText edtNombre,edtCodEmpleado,edtDui,edtFechaNac,edtCargoEmpresa,edtCorreo,edtPassword;
    User user = new User();
    public static final int REQUEST_GALERIA= 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        toolbar = findViewById(R.id.tb_registro);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Registro");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        mAuth = FirebaseAuth.getInstance();
        myRef = database.getReference(Constantes.REF_USUARIOS);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        toolbar = findViewById(R.id.tb_registro);
        setSupportActionBar(toolbar);

        edtNombre = findViewById(R.id.edtNombre);
        edtCodEmpleado = findViewById(R.id.edt_codEmpleado);
        edtDui = findViewById(R.id.edt_dui);
        edtFechaNac = findViewById(R.id.edt_fechaNac);
        edtCargoEmpresa = findViewById(R.id.edt_cargo);
        edtCorreo = findViewById(R.id.edt_correoR);
        edtPassword = findViewById(R.id.edt_passwordR);

        btnCrear = findViewById(R.id.btnCrear);
        btnAbrirGaleria = findViewById(R.id.btnAbrirGaleria);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = edtNombre.getText().toString();
                String codEmpleado = edtCodEmpleado.getText().toString();
                String dui = edtDui.getText().toString();
                String fechaNac= edtFechaNac.getText().toString();
                String cargo = edtCargoEmpresa.getText().toString();
                String correo = edtCorreo.getText().toString();
                String password = edtPassword.getText().toString();

                //validaciones

                //asignacion de valores para el objeto user se guardara en bd
                user.setNombre(nombre);
                user.setCodEmpleado(codEmpleado);
                user.setDui(dui);
                user.setFechaNac(fechaNac);
                user.setCargo(cargo);
                user.setCorreo(correo);
                user.setPassword(password);
                user.setRol("cliente");
                registrarUsuario(user);
            }
        });

        btnAbrirGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirCamara = new Intent();
                abrirCamara.setType("image/*");
                abrirCamara.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(abrirCamara,"selecciona una foto"),REQUEST_GALERIA);

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALERIA){
            if (resultCode == RESULT_OK){
                assert data != null;
                imagenSeleccionada= data.getData();
            }
        }
    }

    private void registrarUsuario(final User user){
        mAuth.createUserWithEmailAndPassword(user.getCorreo(),user.getPassword()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser usuarioActual = mAuth.getCurrentUser();
                    if (usuarioActual != null){
                        crearUsuario(usuarioActual,user);
                        actualizarUI(usuarioActual);
                    }else{
                        Toast.makeText(RegistroActivity.this, "occurrio un error", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(RegistroActivity.this, "ocurrio un error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void actualizarUI(FirebaseUser usuarioActual) {
        myRef.child(usuarioActual.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    role = dataSnapshot.child("rol").toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RegistroActivity.this, "ocurrio un error "+databaseError.getDetails(), Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra(Constantes.ROL_USER,role);
        startActivity(intent);
        finish();
    }

    //crea un usuario
    private void crearUsuario(FirebaseUser firebaseUser,User user){
        String key=myRef.push().getKey();
        assert key != null;
        String keyUser= firebaseUser.getUid();
        myRef.child(keyUser).setValue(user);
        subirFoto(keyUser,imagenSeleccionada);
    }

    //sube una foto al storage de firebase
    private void subirFoto(final String k, Uri uri){
        try{
            final StorageReference storageReference=mStorageRef.child("fotos-usuarios/"+k+"."+getFileExtension(uri));
            storageReference.putFile(imagenSeleccionada).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imagen_user = uri.toString();
                            user.setUrl_imagen(imagen_user);
                            myRef.child(k).setValue(user);
                        }
                    });
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //obtiene la extension de la foto
    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}
