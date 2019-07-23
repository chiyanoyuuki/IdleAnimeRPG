package com.chiya.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chiya.Activities.R;
import com.chiya.Activities.TestActivityFragment;
import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDPartie;
import com.chiya.Views.Choice;

import java.util.ArrayList;

public class Anime extends NewFragment
{
    private FrameLayout animeview;
    private ImageView retouraccueil;
    private LinearLayout layout;
    private ArrayList<Choice> choices;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.anime, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        choices = new ArrayList<>();
        layout = view.findViewById(R.id.anime_layoutanime);
        animeview = view.findViewById(R.id.anime_animeview);
        retouraccueil = view.findViewById(R.id.anime_retouraccueil);

        init();
    }

    private void init()
    {
        TestActivityFragment master = (TestActivityFragment)getActivity();
        BDD db = master.getDb();
        long anime = master.getAnime();

        animeview.setBackgroundResource(getResources().getIdentifier(db.anime().select(anime).image(),"drawable",getContext().getPackageName()));
        Cursor cursor = db.partie().selectAllParties(""+anime);
        while (cursor.moveToNext()) {
            BDDPartie partie = new BDDPartie(cursor);
            Choice tmp = new Choice(this, "#aaaaff",false,partie.image(),partie.nom(),partie.animeid(),partie.id());
            choices.add(tmp);
            layout.addView(tmp.getLayout());
        }
        cursor.close();

        retouraccueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestActivityFragment master = (TestActivityFragment)getActivity();
                master.changeEcran("accueil");
            }
        });
    }

    public void click(long anime, long partie)
    {
        TestActivityFragment master = (TestActivityFragment)getActivity();
        master.changeEcran(anime,partie);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        for(Choice c:choices){c.destroy();}
        choices = null;
        layout = null;
        animeview = null;
        retouraccueil = null;
    }
}
