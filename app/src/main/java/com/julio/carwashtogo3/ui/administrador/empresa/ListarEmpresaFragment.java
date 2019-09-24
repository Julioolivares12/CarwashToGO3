package com.julio.carwashtogo3.ui.administrador.empresa;


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
import com.julio.carwashtogo3.adapters.EmpresaRecyclerViewAdapter;
import com.julio.carwashtogo3.common.Constantes;
import com.julio.carwashtogo3.listeners.EmpresaOnItemClickListener;
import com.julio.carwashtogo3.model.Empresa;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListarEmpresaFragment extends Fragment {


    private boolean isTwoPane=false;
    private final List<Empresa> empresaList = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference empresasRef;

    public ListarEmpresaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_empresa, container, false);
        empresasRef = firebaseDatabase.getReference(Constantes.REF_EMPRESA);
        if (view.findViewById(R.id.detalle_empresa)!= null){
            isTwoPane = true;
        }
     final RecyclerView recyclerView =view.findViewById(R.id.rb_empresas_list);

        //getListaEmpresas();
        //setRecyclerView(recyclerView);
        empresasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    empresaList.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Empresa empresa = snapshot.getValue(Empresa.class);
                        empresaList.add(empresa);
                    }
                    Log.d("empresa list",empresaList.get(0).getNombreEmpresa());

                    recyclerView.setAdapter(new EmpresaRecyclerViewAdapter(empresaList, new EmpresaOnItemClickListener() {
                        @Override
                        public void OnClick(Empresa empresa) {
                            if (isTwoPane){
                                Bundle arguments = new Bundle();
                                arguments.putString(Constantes.UID_EMPRESA,empresa.getUid());
                                EditarEmpresaFragment editarEmpresaFragment = new EditarEmpresaFragment();
                                editarEmpresaFragment.setArguments(arguments);

                                getChildFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.detalle_empresa,editarEmpresaFragment)
                                        .commit();
                            }else {
                                Bundle argumets = new Bundle();
                                argumets.putString(Constantes.UID_EMPRESA,empresa.getUid());
                                EditarEmpresaFragment editarEmpresaFragment = new EditarEmpresaFragment();
                                editarEmpresaFragment.setArguments(argumets);
                                View view = getView();
                                assert view != null;
                                Navigation.findNavController(view).navigate(R.id.action_listarEmpresaFragment_to_editarEmpresaFragment,argumets);
                            }
                        }
                    }));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "ocurrio un error "+databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



        return view;
    }

    private void setRecyclerView(final RecyclerView recyclerView){
        recyclerView.setAdapter(new EmpresaRecyclerViewAdapter(empresaList, new EmpresaOnItemClickListener() {
            @Override
            public void OnClick(Empresa empresa) {
                if (isTwoPane){
                    Bundle arguments = new Bundle();
                    arguments.putString(Constantes.UID_EMPRESA,empresa.getUid());
                    EditarEmpresaFragment editarEmpresaFragment = new EditarEmpresaFragment();
                    editarEmpresaFragment.setArguments(arguments);

                    getChildFragmentManager()
                            .beginTransaction()
                            .replace(R.id.detalle_empresa,editarEmpresaFragment)
                            .commit();
                }else {
                    Bundle argumets = new Bundle();
                    argumets.putString(Constantes.UID_EMPRESA,empresa.getUid());
                    EditarEmpresaFragment editarEmpresaFragment = new EditarEmpresaFragment();
                    editarEmpresaFragment.setArguments(argumets);
                    View view = getView();
                    assert view != null;
                    Navigation.findNavController(view).navigate(R.id.action_listarEmpresaFragment_to_editarEmpresaFragment,argumets);
                }
            }
        }));
    }

    private void getListaEmpresas(){
        //para leer datos
        empresasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Empresa empresa = snapshot.getValue(Empresa.class);
                        empresaList.add(empresa);
                    }
                    Log.d("empresa list",empresaList.get(0).getNombreEmpresa());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "ocurrio un error "+databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
