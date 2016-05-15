package com.educar.cryptoapp.adaptadores;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.educar.cryptoapp.FragmentCategorias;
import com.educar.cryptoapp.FragmentCopia;
import com.educar.cryptoapp.FragmentNuevo;

/**
 * Created by Eduardo on 24/04/2016.
 */
public class MiFragmentPageAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] =
            new String[]{"Nuevo", "Categorías","Copia"};

    public MiFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment f = null;

        switch (position) {
            case 0:
                f = FragmentNuevo.newInstance();
                break;
            case 1:
                f = FragmentCategorias.newInstance();
                break;
            case 2:
                f = FragmentCopia.newInstance();
        }
        return f;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
