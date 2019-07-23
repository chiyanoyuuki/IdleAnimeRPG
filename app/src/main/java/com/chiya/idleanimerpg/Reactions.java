package com.chiya.idleanimerpg;

import android.content.res.Resources;

import com.chiya.Activities.TestActivityFragment;
import com.chiya.BDD.BDD;

import java.util.ArrayList;

public class Reactions
{
    private BDD db;
    private TestActivityFragment master;
    private ArrayList<String> reactions;

    public Reactions(TestActivityFragment master)
    {
        this.master = master;
        this.db = master.getDb();
        this.reactions = new ArrayList<>();
    }

    public void add(String s)
    {
        String[] reacs = s.substring(s.indexOf("[")+1,s.indexOf("]")).split(",");
        for(String reac:reacs){reactions.add(reac);}
    }

    public void doReac(String s)
    {
        if(s.equals         ("COMPTE_START"))   {db.compte().start();}
        else if(s.equals    ("ADD_TEAMSIZE"))   {db.compte().addTeamSize();}
        else if(s.startsWith("ADD_EQUIPE_"))    {db.equipe().addequipe(perso(s.substring(s.lastIndexOf("_")+1)));master.refreshMuraille();}
        else if(s.startsWith("VIEW_"))          {db.viewed().view(perso(s.substring(s.lastIndexOf("_")+1)));}
    }

    public String perso(String s)
    {
        Resources resources = master.getResources();
        return master.getResources().getStringArray(resources.getIdentifier(s,"array",master.getPackageName()))[0];
    }

    public void set()
    {
        for(String s : reactions)
        {
            doReac(s);
        }
        this.reactions = new ArrayList<>();
    }
}
