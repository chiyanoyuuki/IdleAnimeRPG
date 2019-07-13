package com.chiya.idleanimerpg;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ScrollViewMissions extends NewScrollView
{
    private BDDAnime anime;
    private BDDPartie partie;

    public ScrollViewMissions(Master master, Cursor cursor, BDDAnime anime, BDDPartie partie)
    {
        super(master);
        this.anime = anime;
        this.partie = partie;
        init(cursor);
    }

    private void init(Cursor cursor)
    {
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.setPadding(50,150,50,150);

        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        while (cursor.moveToNext())
        {
            BDDMission mission = new BDDMission(cursor);
            Mission m = new Mission(context,mission,master,anime,partie);
            layout.addView(m);
        }
        cursor.close();

        this.addView(layout);
    }
}