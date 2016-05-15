package com.educar.cryptoapp.actividades;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.educar.cryptoapp.adaptadores.MiFragmentPageAdapter;
import com.educar.cryptoapp.R;

public class PortadaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada);

        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Se encarga del efecto de desplazamiento entre las tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MiFragmentPageAdapter(
                getSupportFragmentManager()));

        /* Layout  al que asociamos el viewPager, y que hara que aparezcan
           correctamente el contenido de cada titulo correspondiente */
        TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //tabLayout.setTabMode(TabLayout.GRAVITY_CENTER);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.setIcon(R.drawable.nuevo);
        tab = tabLayout.getTabAt(1);
        tab.setIcon(R.drawable.lista);
        tab = tabLayout.getTabAt(2);
        tab.setIcon(R.drawable.copia);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PortadaActivity.this,"Volver", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_base, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
