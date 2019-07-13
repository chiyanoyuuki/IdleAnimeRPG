package com.chiya.idleanimerpg;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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