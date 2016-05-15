package com.educar.cryptoapp.adaptadores;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.educar.cryptoapp.Categoria;
import com.educar.cryptoapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Eduardo on 10/04/2016.
 */
public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaHolder> {

    public IOnItemClickListener iOnItemClickListener;

    public interface IOnItemClickListener {
        void onItemClickListener(View view, int position, Object object);
    }

    public void setOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }

    private List<Categoria> items;



    public class CategoriaHolder extends RecyclerView.ViewHolder
    {
        public Context context;

        public View view;
        private TextView titulo;
        private ImageView image;

        public CategoriaHolder(View v)
        {
            super(v);
            view = v;
            titulo =(TextView)v.findViewById(R.id.tv_titu);
            image =(ImageView)v.findViewById(R.id.iv_ico);
            context = v.getContext();
        }

    }

    /**
     * Constructos de la clase CategoriaAdapter
     * @param cate
     */
    public CategoriaAdapter(List<Categoria> cate)
    {
        items = cate;
    }

    @Override
    public CategoriaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_elemento_categoria, parent, false);
        return new CategoriaHolder(v);

    }

    @Override
    public void onBindViewHolder(final CategoriaHolder holder, final int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (iOnItemClickListener != null) {
                    iOnItemClickListener.onItemClickListener(holder.view, position, items.get(position));
                }
            }
        });
        holder.image.setImageResource(items.get(position).getIcono());
        holder.titulo.setText(items.get(position).getNombre());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }



}
