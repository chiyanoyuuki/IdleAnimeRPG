package com.chiya.Layouts;

import android.database.Cursor;
import android.widget.FrameLayout;

import com.chiya.Activities.R;
import com.chiya.Activities.TestActivityFragment;
import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDEquipe;
import com.chiya.Views.PersonnageMuraille;

import java.util.ArrayList;

public class LayoutMuraille
{
    private FrameLayout muraille;
    private ArrayList<PersonnageMuraille> personnages;

    public LayoutMuraille(TestActivityFragment master)
    {
        personnages = new ArrayList<>();
        muraille = master.findViewById(R.id.accueil_personnages);
        BDD db = master.getDb();
        Cursor cursor = db.equipe().selectEquipe();
        while(cursor.moveToNext())
        {
            BDDEquipe personnage = new BDDEquipe(cursor);
            PersonnageMuraille tmp = new PersonnageMuraille(master,personnage);
            personnages.add(tmp);
            muraille.addView(tmp.getLayout());
        }
        cursor.close();
    }
}
