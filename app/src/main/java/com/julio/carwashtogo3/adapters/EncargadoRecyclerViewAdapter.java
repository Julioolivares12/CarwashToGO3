package com.julio.carwashtogo3.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.listeners.EncargadoOnItemClickListener;
import com.julio.carwashtogo3.model.EncargadoUsuarioViewModel;

import java.util.List;

public class EncargadoRecyclerViewAdapter extends RecyclerView.Adapter<EncargadoRecyclerViewAdapter.ViewHolder> {

    private final List<EncargadoUsuarioViewModel> mValues;
    private final EncargadoOnItemClickListener onItemClickListener;

    public EncargadoRecyclerViewAdapter(List<EncargadoUsuarioViewModel> items, EncargadoOnItemClickListener onItemClickListener) {
        this.mValues = items;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.fragment_encargado_item, parent, false );
        return new ViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get ( position );
        holder.mContentView.setText ( mValues.get ( position ).getUsuario ().getNombre () );

        holder.mView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (null != onItemClickListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    onItemClickListener.OnClick(holder.mItem);
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
        public final TextView mContentView;
        public EncargadoUsuarioViewModel mItem;

        public ViewHolder(View view) {
            super ( view );
            mView = view;
            mContentView = (TextView) view.findViewById ( R.id.content );
        }

        @Override
        public String toString() {
            return super.toString ();
        }
    }
}
