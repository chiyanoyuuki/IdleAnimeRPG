package com.chiya.Layouts;

import android.database.Cursor;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chiya.Activities.R;
import com.chiya.Activities.TestActivityFragment;
import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDEquipe;
import com.chiya.Views.PersonnageMuraille;

import java.util.ArrayList;

public class LayoutMuraille
{
    private TestActivityFragment master;
    private BDD db;
    private FrameLayout muraille;
    private ArrayList<PersonnageMuraille> personnages;

    public LayoutMuraille(TestActivityFragment master)
    {
        this.master = master;
        personnages = new ArrayList<>();
        muraille = master.findViewById(R.id.accueil_personnages);
        db = master.getDb();
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

    public BDDEquipe getFirstPerso()
    {
        for(PersonnageMuraille personnage:personnages)
        {
            BDDEquipe perso = personnage.getPerso();
            if(!perso.used()){db.equipe().setUsed(perso);personnage.refresh(perso);return perso;}
        }
        return null;
    }

    public void refreshPersos()
    {
        Cursor cursor = db.equipe().selectEquipe();
        int cpt = 0;
        while(cursor.moveToNext())
        {
            BDDEquipe personnage = new BDDEquipe(cursor);
            if(cpt>=personnages.size())
            {
                PersonnageMuraille tmp = new PersonnageMuraille(master,personnage);
                personnages.add(tmp);
                muraille.addView(tmp.getLayout());
            }
            else personnages.get(cpt++).refresh(personnage);
        }
        cursor.close();
    }
}
