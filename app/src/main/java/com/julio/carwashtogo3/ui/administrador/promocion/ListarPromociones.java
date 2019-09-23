package com.julio.carwashtogo3.ui.administrador.promocion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.common.Constantes;

public class ListarPromociones extends Fragment {
   
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refPromocion = database.getReference(Constantes.REF_PROMOCIONES);
    private StorageReference storageReference;
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
        return view;
    }

}
