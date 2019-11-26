package com.julio.carwashtogo3.adapters;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.listeners.PedidoRouteOnClickListener;
import com.julio.carwashtogo3.model.CompraPaquete;
import com.julio.carwashtogo3.ui.administrador.Pedidos.PedidoFragment.OnListFragmentInteractionListener;

import java.util.List;


public class PedidoRecyclerViewAdapter extends RecyclerView.Adapter<PedidoRecyclerViewAdapter.ViewHolder> {

    private final List<CompraPaquete> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final PedidoRouteOnClickListener onRoutelistener;

    public PedidoRecyclerViewAdapter(List<CompraPaquete> items, OnListFragmentInteractionListener listener, PedidoRouteOnClickListener onRoutelistener) {
        mValues = items;
        mListener = listener;
        this.onRoutelistener =onRoutelistener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( parent.getContext () )
                .inflate ( R.layout.fragment_pedido_item, parent, false );
        return new ViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get ( position );
        holder.mIdView.setText ( mValues.get ( position ).getTitulo () );
        holder.mContentView.setText ( mValues.get ( position ).getNombreCliente () );
        holder.tvdireccion.setText ( mValues.get ( position ).getDireecion () );
        holder.routeimg.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                onRoutelistener.OnClick ( holder.mItem );
            }
        } );
        holder.mView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction ( holder.mItem );
                }
            }
        } );
    }

    @Override
    public int getItemCount() {
        return mValues.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView tvdireccion;
        public final ImageButton routeimg;
        public CompraPaquete mItem;


        public ViewHolder(View view) {
            super ( view );
            mView = view;
            mIdView = (TextView) view.findViewById ( R.id.item_number );
            mContentView = (TextView) view.findViewById ( R.id.content );
            tvdireccion = (TextView) view.findViewById ( R.id.tvdireccionpedido );
            routeimg = (ImageButton) view.findViewById ( R.id.btnroute );
        }

        @Override
        public String toString() {
            return super.toString () + " '" + mContentView.getText () + "'";
        }
    }
}
