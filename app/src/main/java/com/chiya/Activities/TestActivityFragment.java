package com.chiya.Activities;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.chiya.BDD.BDD;
import com.chiya.Fragments.Anime;
import com.chiya.Fragments.Missions;
import com.chiya.Fragments.Monde;
import com.chiya.Layouts.LayoutForeground;

public class TestActivityFragment extends FragmentActivity
{
    private String tag;
    private BDD db;
    private LayoutForeground foreground;
    private Fragment fragment;
    private long anime, partie;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.accueil);

        init();
    }

    private void init()
    {
        db = new BDD(this);
        foreground = new LayoutForeground(this);

        fragment = new Monde();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.accueil_fragm, fragment);
        ft.commit();
    }

    public void changeEcran(long anime, long partie)
    {
        if(anime==-1&&partie==-1)
        {
            remove();
            this.anime = anime;
            this.partie = partie;
            fragment = new Monde();
            change();
        }
        else if(anime!=-1&&partie==-1)
        {
            remove();
            this.anime = anime;
            this.partie = partie;
            fragment = new Anime();
            change();
        }
        else if(anime!=-1&&partie!=-1)
        {
            tag = "histoire";
            remove();
            this.anime = anime;
            this.partie = partie;
            fragment = new Missions();
            change();
        }
    }

    public void changeEcran(long anime, long partie, String tag)
    {
        if(anime!=-1&&partie!=-1)
        {
            this.tag = tag;
            remove();
            this.anime = anime;
            this.partie = partie;
            fragment = new Missions();
            change();
        }
    }

    private void remove()
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(fragment).commit();
    }

    private void change()
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.accueil_fragm, fragment).commit();
    }

    public BDD getDb() {return db;}
    public long getAnime(){return anime;}
    public long getPartie(){return partie;}
    public String getState(){return tag;}

    @Override
    public void onBackPressed(){}
}