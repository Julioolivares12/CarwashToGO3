package com.julio.carwashtogo3.ui.administrador.promocion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.common.Constantes;

public class ListarPromociones extends Fragment {
    private OnFragmentInteractionListener mListener;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    //database = FirebaseDatabase.getInstance();
    DatabaseReference refPromocion = database.getReference(Constantes.REF_EMPRESA);;
    //refPromocion
    private StorageReference storageReference;
    private RecyclerView miRecyclerView;

    public ListarPromociones() {
    }

    public static ListarPromociones newInstance(String param1, String param2) {
        ListarPromociones fragment = new ListarPromociones();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar_promociones, container, false);
        miRecyclerView = (RecyclerView)view.findViewById(R.id.rb_promociones_list);
        miRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String todo;

        return view;
    }

    private void getPromocionesFromFirebase(){
        refPromocion.child("Empresas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists()){
                   for (DataSnapshot ds: dataSnapshot.getChildren()){
                       String titulo = ds.child("encargado").getValue().toString();
                   }
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
