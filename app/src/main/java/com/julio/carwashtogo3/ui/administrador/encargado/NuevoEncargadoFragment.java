package com.julio.carwashtogo3.ui.administrador.encargado;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;

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
import com.google.firebase.storage.StorageReference;
import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.adapters.EncargadoSearchableSpinnerAdapter;
import com.julio.carwashtogo3.common.Constantes;
import com.julio.carwashtogo3.model.Empresa;
import com.julio.carwashtogo3.model.Encargado;
import com.julio.carwashtogo3.model.User;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NuevoEncargadoFragment extends Fragment {

    SearchableSpinner searchablespinner;
    private final List<Empresa> empresaList = new ArrayList<> ();
    //firebase
    //----------------------------------------
    private FirebaseDatabase database = FirebaseDatabase.getInstance ();
    DatabaseReference refEmpresas;
    DatabaseReference refEncargado;
    private DatabaseReference refUsuarios;
    private StorageReference storageReference;
    Button btnguardarencargado;
    Bundle bundle;
    TextView tituloformencargado;
    EditText edtnombreencargado, edtdui, edtnit, edtcorreo, edtpassword, edtfechanac, edtcodigoempleado;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    public NuevoEncargadoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_nuevo_encargado, container, false );
        searchablespinner = (SearchableSpinner) view.findViewById ( R.id.spinnerempresa );
        refEmpresas = database.getReference ( Constantes.REF_EMPRESA );
        refEncargado = database.getReference ( Constantes.REF_ENCARGADO );
        refUsuarios = database.getReference ( Constantes.REF_USUARIOS );
        auth = FirebaseAuth.getInstance ();
        Empresa defaultempresa = new Empresa ();
        defaultempresa.setNombreEmpresa ( "-- Seleccione Empresa --" );
        empresaList.add ( defaultempresa );
        refEmpresas.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists ()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren ()) {
                        Empresa empresa = snapshot.getValue ( Empresa.class );
                        empresaList.add ( empresa );
                    }

                    EncargadoSearchableSpinnerAdapter adapter = new EncargadoSearchableSpinnerAdapter ( getContext (), android.R.layout.simple_spinner_item, empresaList );
                    adapter.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
                    //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext (), android.R.layout.simple_list_item_1,empresaname);
                    searchablespinner.setAdapter ( adapter );
                    searchablespinner.setTitle ( "Empresa" );
                    if (getArguments () != null) {
                        String empresaId = bundle.getString ( "EMPRESAID" );
                        for (int i = 0; i < adapter.getCount (); i++) {
                            if (empresaId.trim ().equals ( adapter.getItem ( i ).getUid () )) {
                                searchablespinner.setSelection ( i );
                                break;
                            }
                        }

                    } else {
                        searchablespinner.setSelection ( 0 );
                    }
                    searchablespinner.setPositiveButton ( "Seleccionar" );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText ( getContext (), "ocurrio un error " + databaseError.getMessage (), Toast.LENGTH_LONG ).show ();
            }
        } );
        btnguardarencargado = view.findViewById ( R.id.btnguardarencargado );
        edtnombreencargado = view.findViewById ( R.id.edtnombreencargado );
        edtdui = view.findViewById ( R.id.edtduiencargado );
        edtnit = view.findViewById ( R.id.edtnitencargado );
        edtcorreo = view.findViewById ( R.id.edtcorreoencargado );
        edtpassword = view.findViewById ( R.id.edtpasswordencargado );
        edtfechanac = view.findViewById ( R.id.edtfechanacencargado );
        edtcodigoempleado = view.findViewById ( R.id.edtcodencargado );
        tituloformencargado = view.findViewById ( R.id.tvtituloaddencargado );
        bundle = this.getArguments ();
        if (bundle != null) {
            String encargadoId = bundle.getString ( "IDENCARGADO" );
            String UserId = bundle.getString ( "IDUSUARIO" );
            refUsuarios.child ( UserId ).addValueEventListener ( new ValueEventListener () {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists ()) {
                        User usuario_existente = dataSnapshot.getValue ( User.class );
                        edtcodigoempleado.setText ( usuario_existente.getCodEmpleado () );
                        edtcorreo.setText ( usuario_existente.getCorreo () );
                        edtdui.setText ( usuario_existente.getDui () );
                        edtfechanac.setText ( usuario_existente.getFechaNac () );
                        edtnombreencargado.setText ( usuario_existente.getNombre () );
                        edtpassword.setText ( usuario_existente.getPassword () );
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            } );
            refEncargado.child ( encargadoId ).addValueEventListener ( new ValueEventListener () {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists ()) {
                        Encargado encargado_existente = dataSnapshot.getValue ( Encargado.class );
                        edtnit.setText ( encargado_existente.getNit () );

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            } );
            btnguardarencargado.setText ( "ACTUALIZAR" );
            edtcorreo.setEnabled ( false );
            edtpassword.setEnabled ( false );
            tituloformencargado.setText ( "Editar Encargado" );
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated ( view, savedInstanceState );
        btnguardarencargado.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                final Empresa selected_empresa = (Empresa) searchablespinner.getSelectedItem ();
                final String keyencargado = refEncargado.push ().getKey ();
                final String keyusuario = refUsuarios.push ().getKey ();

                String nombre = edtnombreencargado.getText ().toString ();
                String dui = edtdui.getText ().toString ();
                String nit = edtnit.getText ().toString ();
                String codigo_emp = edtcodigoempleado.getText ().toString ();
                String correo_encargado = edtcorreo.getText ().toString ();
                String password_encargado = edtpassword.getText ().toString ();
                String fechanac = edtfechanac.getText ().toString ();

                if (TextUtils.isEmpty ( nit )) {
                    edtnit.setError ( "El dui es requerido" );
                    edtnit.requestFocus ();
                } else if (TextUtils.isEmpty ( dui )) {
                    edtdui.setError ( "El dui es requerido" );
                    edtdui.requestFocus ();
                } else if (selected_empresa == null || TextUtils.isEmpty ( selected_empresa.getUid () )) {
                    TextView errortext = (TextView) searchablespinner.getSelectedView ();
                    errortext.setError ( "La empresa es requerida" );
                    errortext.requestFocus ();
                } else if (TextUtils.isEmpty ( nombre )) {
                    edtnombreencargado.setError ( "El nombre es requerido" );
                    edtnombreencargado.requestFocus ();
                } else if (TextUtils.isEmpty ( codigo_emp )) {
                    edtcodigoempleado.setError ( "El codigo es requerido" );
                    edtcodigoempleado.requestFocus ();
                } else if (TextUtils.isEmpty ( correo_encargado )) {
                    edtcorreo.setError ( "El correo es requerido" );
                    edtcorreo.requestFocus ();
                } else if (TextUtils.isEmpty ( password_encargado )) {
                    edtpassword.setError ( "el password es requerido" );
                    edtpassword.requestFocus ();
                } else if (TextUtils.isEmpty ( fechanac )) {
                    edtfechanac.setError ( "la fecha de nacimiento es requerida" );
                    edtfechanac.requestFocus ();
                } else {
                    //SE VALIDO EL FORMULARIO Y ESTA BIEN
                    if (getArguments () != null) {
                        final String encargado_existente_Id = bundle.getString ( "IDENCARGADO" );
                        final String User_existente_Id = bundle.getString ( "IDUSUARIO" );
                        refUsuarios.child ( User_existente_Id ).addValueEventListener ( new ValueEventListener () {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists ()) {
                                    User _existente = dataSnapshot.getValue ( User.class );
                                    _existente.setCorreo ( edtcorreo.getText ().toString () );
                                    _existente.setFechaNac ( edtfechanac.getText ().toString () );
                                    _existente.setDui ( edtdui.getText ().toString () );
                                    _existente.setPassword ( edtpassword.getText ().toString () );
                                    _existente.setRol ( "cliente" );
                                    _existente.setCodEmpleado ( edtcodigoempleado.getText ().toString () );
                                    _existente.setCargo ( "Encargado" );
                                    _existente.setNombre ( edtnombreencargado.getText ().toString () );


                                    refUsuarios.child ( User_existente_Id ).setValue ( _existente ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            refEncargado.child ( encargado_existente_Id ).addValueEventListener ( new ValueEventListener () {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.exists ()) {
                                                        Encargado _encargado_existente = dataSnapshot.getValue (Encargado.class);
                                                        _encargado_existente.setNit ( edtnit.getText ().toString () );
                                                        _encargado_existente.setEmpresaid ( selected_empresa.getUid () );
                                                        refEncargado.child (encargado_existente_Id ).setValue ( _encargado_existente ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                NavigateToList();
                                                            }
                                                        } );
                                                    }
                                                }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            } );
                                        }
                                    } );
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        } );


                    } else {
                        //CREANDO LA ENTIDAD USUARIO
                        final User usuario_empleado = new User ();
                        usuario_empleado.setCorreo ( correo_encargado );
                        usuario_empleado.setFechaNac ( fechanac );
                        usuario_empleado.setDui ( dui );
                        usuario_empleado.setPassword ( password_encargado );
                        usuario_empleado.setRol ( "cliente" );
                        usuario_empleado.setCodEmpleado ( codigo_emp );
                        usuario_empleado.setCargo ( "Encargado" );
                        usuario_empleado.setNombre ( nombre );

                        final Encargado nuevo_encargado = new Encargado ();
                        nuevo_encargado.setEmpresaid ( selected_empresa.getUid () );
                        nuevo_encargado.setNit ( nit );
                        nuevo_encargado.setUid ( keyusuario );
                        nuevo_encargado.setEncargadoId ( keyencargado );
                        auth.createUserWithEmailAndPassword ( usuario_empleado.getCorreo (), usuario_empleado.getPassword () ).addOnCompleteListener ( new OnCompleteListener<AuthResult> () {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText ( getContext (), "createUserWithEmail:onComplete:" + task.isSuccessful (), Toast.LENGTH_SHORT ).show ();
                                FirebaseUser user = auth.getCurrentUser ();

                                //progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful ()) {
                                    Toast.makeText ( getContext (), "Authentication failed." + task.getException (),
                                            Toast.LENGTH_SHORT ).show ();
                                } else {
                                    refUsuarios.child ( user.getUid () ).setValue ( usuario_empleado ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            refEncargado.child ( keyencargado ).setValue ( nuevo_encargado ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    NavigateToList();
                                                }
                                            } );
                                        }
                                    } );
                                }
                            }
                        } );
                    }
                }
            }
        } );
    }

    public void NavigateToList(){
        View view = getView();
        assert view != null;
        Navigation.findNavController(view).navigate(R.id.navigation_lista_encargados,new Bundle (  ));
    }
}
