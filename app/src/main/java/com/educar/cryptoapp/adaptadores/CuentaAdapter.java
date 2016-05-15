package com.educar.cryptoapp.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.educar.cryptoapp.Cuenta;
import com.educar.cryptoapp.R;
import com.educar.cryptoapp.SQLite.CLogo;

import java.util.List;

/**
 * Created by Eduardo on 02/05/2016.
 */
public class CuentaAdapter extends RecyclerView.Adapter<CuentaAdapter.CuentaHolder> {

    public IOnItemClickListener iOnItemClickListener;

    public interface IOnItemClickListener {
        void onItemClickListener(View view, int position, Object object);
    }

    public void setOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }

    private List<Cuenta> items;



    public class CuentaHolder extends RecyclerView.ViewHolder
    {
        public Context context;

        public View view;
        private TextView titulo;
        private ImageView image;

        public CuentaHolder(View v)
        {
            super(v);
            view = v;
            titulo =(TextView)v.findViewById(R.id.tv_tituloRegistro);
            image =(ImageView)v.findViewById(R.id.iv_iconoRegistro);
        }

    }

    /**
     * Constructos de la clase CategoriaAdapter
     * @param cuen
     */
    public CuentaAdapter(List<Cuenta> cuen)
    {
        items = cuen;
    }

    @Override
    public CuentaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_registro, parent, false);
        return new CuentaHolder(v);

    }

    @Override
    public void onBindViewHolder(final CuentaHolder holder, final int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (iOnItemClickListener != null) {
                    iOnItemClickListener.onItemClickListener(holder.view, position, items.get(position));
                }
            }
        });
        holder.image.setImageResource(CLogo.getLogoById(String.valueOf(items.get(position).getIcono())).getImagen());
        holder.titulo.setText(items.get(position).getRegistro());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
