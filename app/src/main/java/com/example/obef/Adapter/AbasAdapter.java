package com.example.obef.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.obef.Fragments.FragmentPontos;
import com.example.obef.Fragments.FragmentRanking;

public class AbasAdapter extends FragmentPagerAdapter {

    private String[] mTabTitles;

    public AbasAdapter(FragmentManager fm, String[] tituloAba) {
        super(fm);
        this.mTabTitles = tituloAba;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentRanking();
            case 1:
                return new FragmentPontos();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.mTabTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }
}
