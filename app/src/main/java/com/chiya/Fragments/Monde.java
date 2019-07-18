package com.chiya.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chiya.Activities.R;
import com.chiya.Activities.TestActivityFragment;
import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDAnime;
import com.chiya.Views.Choice;

import java.util.ArrayList;

public class Monde extends NewFragment
{
    private LinearLayout layout;
    private ArrayList<Choice> choices;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.monde, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        choices = new ArrayList<>();
        layout = view.findViewById(R.id.monde_linear0);

        TestActivityFragment master = (TestActivityFragment)getActivity();
        BDD db = master.getDb();
        Cursor cursor = db.selectAll("anime");
        while (cursor.moveToNext()) {
            BDDAnime anime = new BDDAnime(cursor);
            Choice tmp = new Choice(this, "#ffaaaa",!anime.isup(),anime.image(),anime.nom(),anime.id(),-1);
            choices.add(tmp);
            layout.addView(tmp.getLayout());
        }
        cursor.close();
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        for(Choice c:choices){c.destroy();}
        choices = null;
        layout = null;
    }
}
