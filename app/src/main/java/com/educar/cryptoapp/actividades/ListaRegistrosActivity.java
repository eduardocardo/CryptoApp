package com.educar.cryptoapp.actividades;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.View;

import com.educar.cryptoapp.Categoria;
import com.educar.cryptoapp.Cuenta;
import com.educar.cryptoapp.SQLite.CCuenta;
import com.educar.cryptoapp.adaptadores.CuentaAdapter;
import com.educar.cryptoapp.R;

import java.util.ArrayList;
import java.util.List;

public class ListaRegistrosActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private CuentaAdapter adapter;
    private RecyclerView.LayoutManager lManager;
    private List<Cuenta> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_registros);

        //se obtiene el objeto Categoria pasado por el intent
        Categoria categoria  = (Categoria)getIntent().getSerializableExtra("categoria");
        //A partir de la id de esa categoría se obtienen todas las cuentas que tengan esa categoría
        items = CCuenta.getAllCuentasByCategoria(String.valueOf(categoria.getIdCategoria()));

        // Obtener el Recycler
        recycler = (RecyclerView)findViewById(R.id.rv_listaCuentas);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new CuentaAdapter(items);
        //se implementa el metodo onclick al adapter

        adapter.setOnItemClickListener(new CuentaAdapter.IOnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClickListener(View view, int position, Object object) {
                Intent intent = new Intent(ListaRegistrosActivity.this,CuentaActivity.class);
                intent.putExtra("cuenta", items.get(position));
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Explode explode = new Explode();
                    explode.setDuration(2000); // Duración en milisegundos
                    getWindow().setExitTransition(explode);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(ListaRegistrosActivity.this).toBundle());

                }
                else
                {
                    startActivity(intent);
                }
            }
        });
        recycler.setAdapter(adapter);
    }
}
