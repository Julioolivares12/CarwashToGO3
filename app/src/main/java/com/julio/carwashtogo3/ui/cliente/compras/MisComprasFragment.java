package com.julio.carwashtogo3.ui.cliente.compras;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.adapters.AdapterMisCompras;
import com.julio.carwashtogo3.common.Constantes;
import com.julio.carwashtogo3.listeners.MisComprasOnItemClickListener;
import com.julio.carwashtogo3.model.CompraPaquete;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MisComprasFragment extends Fragment {


    private boolean isTwoPane = false;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference refUsuario;
    private final List<CompraPaquete> misCompras = new ArrayList<>();
    public MisComprasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate ( R.layout.fragment_mis_compras, container, false );
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view, savedInstanceState );
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth =FirebaseAuth.getInstance ();
        refUsuario = firebaseDatabase.getReference ( Constantes.REF_USUARIOS);
        if (view.findViewById(R.id.detalle_compra) != null) {
            isTwoPane = true;
        }

        final RecyclerView recyclerViewCompras = view.findViewById(R.id.rv_miscompras);
        FirebaseUser user = mAuth.getCurrentUser();

        refUsuario.child(user.getUid()).child("compras").addListenerForSingleValueEvent ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    misCompras.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        CompraPaquete compra = snapshot.getValue(CompraPaquete.class);
                        misCompras.add(compra);
                    }
                    recyclerViewCompras.setAdapter(new AdapterMisCompras ( misCompras, new MisComprasOnItemClickListener () {
                        @Override
                        public void onClick(CompraPaquete compraPaquete) {
                            if (isTwoPane){

                                Bundle datos = new Bundle();
                                datos.putString("","");

                                DetalleMisComprasFragment misComprasFragment = new DetalleMisComprasFragment();
                                misComprasFragment.setArguments (datos);

                                getChildFragmentManager().beginTransaction()
                                        .replace(R.id.detalle_compra,
                                                misComprasFragment)
                                        .commit();

                            }else {
                                Bundle datos = new Bundle();
                                datos.putString ("","");
                                Navigation.findNavController (view).navigate (R.id.action_navigation_compras_to_detalleMisComprasFragment,datos);
                            }
                        }
                    } ));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

    }
}
