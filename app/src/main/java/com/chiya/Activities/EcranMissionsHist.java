package com.chiya.Activities;

import com.chiya.BDD.BDDAnime;
import com.chiya.BDD.BDDPartie;
import com.chiya.Layouts.LinearLayoutAnime;
import com.chiya.Layouts.LinearLayoutMissions;

public class EcranMissionsHist extends NewActivity
{
    private LinearLayoutMissions missions;

    public EcranMissionsHist()
    {
        super();
    }

    public void addMid()
    {
        mid.removeAllViews();

        long animeid = getIntent().getLongExtra("animeid",1);
        long partieid = getIntent().getLongExtra("partieid",1);

        BDDAnime anime = db.anime().select(animeid);
        BDDPartie partie = db.partie().select(partieid);

       // missions = new LinearLayoutMissions(this,anime,partie);

        mid.addView(missions);
    }
}
