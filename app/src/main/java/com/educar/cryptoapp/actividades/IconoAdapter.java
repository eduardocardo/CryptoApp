package com.educar.cryptoapp.actividades;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.educar.cryptoapp.Cuenta;
import com.educar.cryptoapp.Logo;
import com.educar.cryptoapp.R;

import java.util.List;

/**
 * Created by Eduardo on 06/05/2016.
 */
public class IconoAdapter extends RecyclerView.Adapter<IconoAdapter.IconoHolder> {

    public IOnItemClickListener iOnItemClickListener;

    public interface IOnItemClickListener {
        void onItemClickListener(View view, int position, Object object);
    }

    public void setOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }

    private List<Logo> items;



    public class IconoHolder extends RecyclerView.ViewHolder
    {
        public Context context;

        public View view;
        private TextView nombre;
        private ImageView image;

        public IconoHolder(View v)
        {
            super(v);
            view = v;
            nombre =(TextView)v.findViewById(R.id.tv_nombreIcono);
            image =(ImageView)v.findViewById(R.id.iv_iconoSeleccionable);
        }

    }

    /**
     * Constructos de la clase IconoAdapter
     * @param lo
     */
    public IconoAdapter(List<Logo> lo)
    {
        items = lo;
    }

    @Override
    public IconoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_row_icono, parent, false);
        return new IconoHolder(v);

    }

    @Override
    public void onBindViewHolder(final IconoHolder holder, final int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (iOnItemClickListener != null) {
                    iOnItemClickListener.onItemClickListener(holder.view, position, items.get(position));
                }
            }
        });
        holder.image.setImageResource(items.get(position).getImagen());
        holder.nombre.setText(items.get(position).getNombre());
        holder.nombre.setTextColor(ContextCompat.getColor(holder.view.getContext(),R.color.ColorText_Icon));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
