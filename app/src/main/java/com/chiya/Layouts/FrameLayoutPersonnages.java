package com.chiya.Layouts;

import android.database.Cursor;

import com.chiya.Activities.Master;
import com.chiya.Activities.NewActivity;
import com.chiya.BDD.BDDAnime;
import com.chiya.BDD.BDDPartie;

public class FrameLayoutPersonnages extends NewFrameLayoutMissions
{
    private ScrollViewPersonnages personnages;

    public FrameLayoutPersonnages(Master master, BDDAnime anime, BDDPartie partie)
    {
        super(master,anime,partie);
        init();
    }

    private void init()
    {
        Cursor cursor = db.personnage().selectAllPersonnages(""+partie.animeid(),""+partie.id());
        personnages = new ScrollViewPersonnages(master,cursor);
        this.addView(personnages);
    }
}