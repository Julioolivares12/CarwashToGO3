package com.julio.carwashtogo3.ui.administrador.Pedidos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.adapters.PedidoRecyclerViewAdapter;
import com.julio.carwashtogo3.common.Constantes;
import com.julio.carwashtogo3.listeners.PedidoRouteOnClickListener;
import com.julio.carwashtogo3.model.CompraPaquete;
import com.julio.carwashtogo3.model.Paquete;
import com.julio.carwashtogo3.ui.maps.GoogleMapActivity;
import com.julio.carwashtogo3.ui.maps.MapRouteActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PedidoFragment extends Fragment {

    private static final int REQUEST_GET_MAP_ROUTE = 6;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance ();
    private DatabaseReference comprasRef;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PedidoFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PedidoFragment newInstance(int columnCount) {
        PedidoFragment fragment = new PedidoFragment ();
        Bundle args = new Bundle ();
        args.putInt ( ARG_COLUMN_COUNT, columnCount );
        fragment.setArguments ( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

        if (getArguments () != null) {
            mColumnCount = getArguments ().getInt ( ARG_COLUMN_COUNT );
        }
    }

    public void MostrarRuta(CompraPaquete cliente) {
        Intent route = new Intent ( getContext (), MapRouteActivity.class );
        route.putExtra ( "LAT",cliente.getLatitude () );
        route.putExtra ( "LONG",cliente.getLongitude () );
        startActivityForResult ( route, REQUEST_GET_MAP_ROUTE );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_pedido_list, container, false );
        comprasRef = firebaseDatabase.getReference ( Constantes.REF_COMPRAS );
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext ();
            final RecyclerView recyclerView = (RecyclerView) view;
            final List<CompraPaquete> compras_del_dia = new ArrayList<> ();
            comprasRef.addValueEventListener ( new ValueEventListener () {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists ()) {
                        compras_del_dia.clear ();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren ()) {
                            CompraPaquete compra = snapshot.getValue ( CompraPaquete.class );
                            compras_del_dia.add ( compra );
                        }
                        recyclerView.setAdapter ( new PedidoRecyclerViewAdapter ( compras_del_dia, mListener, new PedidoRouteOnClickListener () {
                            @Override
                            public void OnClick(CompraPaquete route) {
                                MostrarRuta(route);
                            }
                        } ) );
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText ( getContext (), "ocurrio un error" + databaseError.getMessage (), Toast.LENGTH_LONG ).show ();

                }
            } );

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager ( new LinearLayoutManager ( context ) );
            } else {
                recyclerView.setLayoutManager ( new GridLayoutManager ( context, mColumnCount ) );
            }

        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach ( context );
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException ( context.toString ()
                    + " must implement OnListFragmentInteractionListener" );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach ();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(CompraPaquete item);
    }
}
