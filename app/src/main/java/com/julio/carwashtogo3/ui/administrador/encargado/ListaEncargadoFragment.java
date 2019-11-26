package com.julio.carwashtogo3.ui.administrador.encargado;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.adapters.EncargadoRecyclerViewAdapter;
import com.julio.carwashtogo3.common.Constantes;
import com.julio.carwashtogo3.listeners.EncargadoOnItemClickListener;
import com.julio.carwashtogo3.model.Encargado;
import com.julio.carwashtogo3.model.EncargadoUsuarioViewModel;
import com.julio.carwashtogo3.model.User;
import com.julio.carwashtogo3.ui.administrador.empresa.NuevaEmpresaFragment;

import java.util.ArrayList;
import java.util.List;


public class ListaEncargadoFragment extends Fragment {

    private boolean isTwoPane=false;
    private final List<EncargadoUsuarioViewModel> encargadoList = new ArrayList<> ();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference encargadoRef;
    private DatabaseReference usuarioRef;
    //private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListaEncargadoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_encargado_list, container, false );
        encargadoRef = firebaseDatabase.getReference( Constantes.REF_ENCARGADO);
        usuarioRef = firebaseDatabase.getReference( Constantes.REF_USUARIOS);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext ();
            final RecyclerView recyclerView = (RecyclerView) view;
            final EncargadoRecyclerViewAdapter adarpter = new EncargadoRecyclerViewAdapter (encargadoList, new EncargadoOnItemClickListener() {
                @Override
                public void OnClick(EncargadoUsuarioViewModel encargado) {
                    Bundle argumets = new Bundle();
                    argumets.putString("IDENCARGADO",encargado.getEncargado ().getEncargadoId ());
                    argumets.putString("IDUSUARIO",encargado.getEncargado ().getUid ());
                    argumets.putString("EMPRESAID",encargado.getEncargado ().getEmpresaid ());
                    NuevaEmpresaFragment editarEmpresaFragment = new NuevaEmpresaFragment();
                    editarEmpresaFragment.setArguments(argumets);
                    View view = getView();
                    assert view != null;
                    Navigation.findNavController(view).navigate(R.id.navigation_nuevo_encargado,argumets);
                }
            });

            recyclerView.setAdapter(adarpter);
            encargadoRef.addValueEventListener(new ValueEventListener () {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        encargadoList.clear();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            final Encargado encargado = snapshot.getValue(Encargado.class);
                            usuarioRef.child(encargado.getUid()).addValueEventListener ( new ValueEventListener () {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){
                                        User usuario = dataSnapshot.getValue (User.class);
                                        EncargadoUsuarioViewModel item = new EncargadoUsuarioViewModel();
                                        item.setUsuario ( usuario );
                                        item.setEncargado ( encargado );
                                        encargadoList.add(item);
                                        adarpter.notifyDataSetChanged ();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }

                            } );
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), "ocurrio un error "+databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }

            });
        }
        return view;
    }
}
