package com.julio.carwashtogo3.ui.administrador.promocion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.adapters.EmpresaRecyclerViewAdapter;
import com.julio.carwashtogo3.adapters.PromocionesRecyclerViewAdapter;
import com.julio.carwashtogo3.common.Constantes;
import com.julio.carwashtogo3.listeners.EmpresaOnItemClickListener;
import com.julio.carwashtogo3.listeners.PromocionOnItemClickListener;
import com.julio.carwashtogo3.model.Empresa;
import com.julio.carwashtogo3.model.Promocion;
import com.julio.carwashtogo3.ui.administrador.empresa.EditarEmpresaFragment;

import java.util.ArrayList;
import java.util.List;

public class ListarPromociones extends Fragment {
    private boolean isTwoPane=false;
    private final List<Promocion> promocionList = new ArrayList<>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference refPromocion;
    private StorageReference storageReference;
    private RecyclerView miRecyclerView;

    public ListarPromociones() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_listar_promociones, container, false);
        refPromocion = database.getReference(Constantes.REF_PROMOCIONES);
        final    RecyclerView recyclerView =view.findViewById(R.id.rb_promociones_list);

        //getListaEmpresas();
        //setRecyclerView(recyclerView);
        refPromocion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Promocion promo = snapshot.getValue(Promocion.class);
                        promocionList.add(promo);
                    }
                    Log.d("titulo",promocionList.get(0).getNombre());
                    Log.d("creado",promocionList.get(1).getEncargado());
                    Log.d("fechai",promocionList.get(2).getFechaIncio());
                    Log.d("fechaf",promocionList.get(3).getFechaFinal());
                    Log.d("precio",promocionList.get(4).getPrecio());
                    Log.d("url",promocionList.get(5).getUrlImagen());


                    recyclerView.setAdapter(new PromocionesRecyclerViewAdapter(promocionList, new PromocionOnItemClickListener() {
                       // @Override

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

   /*
    private void getPromocionesFromFirebase(){
        refPromocion.child("promociones").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists()){
                   for (DataSnapshot ds: dataSnapshot.getChildren()){
                   //    List<titulo>  = ds.child("encargado").getValue();
                   }
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
*/


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
   //     if (mListener != null) {
     //       mListener.onFragmentInteraction(uri);
     //   }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
     //       mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
       // mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
