package com.educar.cryptoapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.educar.cryptoapp.SQLite.CCategoria;
import com.educar.cryptoapp.SQLite.DatabaseManager;
import com.educar.cryptoapp.actividades.ListaRegistrosActivity;
import com.educar.cryptoapp.adaptadores.CategoriaAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentCategorias extends Fragment {


    private RecyclerView recycler;
    private CategoriaAdapter adapter;
    private RecyclerView.LayoutManager lManager;
    private ArrayList<Categoria> items;


    public static FragmentCategorias newInstance() {
        FragmentCategorias fragment = new FragmentCategorias();
        return fragment;
    }

    public FragmentCategorias(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_categorias, container, false);

        //se obtiene todas las categorias
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {


                return null;
            }
        }.execute();

        items = CCategoria.getAllCategorias() ;

        // Obtener el Recycler
        recycler = (RecyclerView)view.findViewById(R.id.rv_listaCategorias);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new CategoriaAdapter(items);
        adapter.setOnItemClickListener(new CategoriaAdapter.IOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position, Object object) {
                int pos = position;
                Intent intent = new Intent(getActivity().getApplicationContext(), ListaRegistrosActivity.class);
                Categoria categoria = (Categoria)object;
               intent.putExtra("categoria",categoria);
                //intent.putExtra("categoria",items.get(position));
                startActivity(intent);
            }
        });
        recycler.setAdapter(adapter);
        return view;
    }
}
