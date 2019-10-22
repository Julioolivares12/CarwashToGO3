package com.julio.carwashtogo3.ui.cliente.catalogo;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.adapters.PaquetesRecyclerViewAdapater;
import com.julio.carwashtogo3.common.Constantes;
import com.julio.carwashtogo3.listeners.PaquetesOnItemClickListener;
import com.julio.carwashtogo3.model.Paquete;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogoDeProductosFragment extends Fragment {


    private List<Paquete> paqueteList = new ArrayList<>();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance ();
    DatabaseReference paqueref;
    public CatalogoDeProductosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalogo_de_productos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view, savedInstanceState );
        paqueref =firebaseDatabase.getReference (Constantes.REF_PAQUETES);

        final RecyclerView recyclerView = view.findViewById ( R.id.rb_catalogo_productos);
        paqueref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    paqueteList.clear();
                    for (DataSnapshot d : dataSnapshot.getChildren ()){
                        Paquete paquete = d.getValue (Paquete.class);
                       // Log.i ( "catalogo individual",paquete.getTitulo());
                        paqueteList.add(paquete);
                    }
                    Log.i ("Catalogo",paqueteList.toString ());

                    recyclerView.setAdapter ( new PaquetesRecyclerViewAdapater ( paqueteList, new PaquetesOnItemClickListener () {
                        @Override
                        public void OnClick(Paquete paquete) {
                            View view1 = getView ();

                            assert view1 != null;
                            Bundle datos = new Bundle ();
                            datos.putString (Constantes.UID_PAQUETE,paquete.getUid ());
                            Log.i ( "UID PAQUETE",paquete.getUid () );
                            Navigation.findNavController(view1).navigate (R.id.action_catalogoDeProductosFragment_to_detalleProductoFragment,datos);
                        }
                    } ) );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i ( "Error catalogo",databaseError.getMessage () );
            }
        });
    }
}
