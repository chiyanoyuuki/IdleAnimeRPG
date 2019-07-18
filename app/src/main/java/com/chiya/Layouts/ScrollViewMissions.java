package com.chiya.Layouts;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chiya.Activities.Master;
import com.chiya.Activities.NewActivity;
import com.chiya.BDD.BDDAnime;
import com.chiya.BDD.BDDMission;
import com.chiya.BDD.BDDPartie;
import com.chiya.idleanimerpg.Mission;

public class ScrollViewMissions extends NewScrollView
{
    private BDDAnime anime;
    private BDDPartie partie;
    private LinearLayout layout;

    public ScrollViewMissions(Master master, BDDAnime anime, BDDPartie partie)
    {
        super(master);
        this.anime = anime;
        this.partie = partie;
        init();
    }

    private void init()
    {
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.setPadding(50,150,50,150);

        layout = new LinearLayout(this.getContext());
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        Cursor cursor = db.mission().selectAllMissions(""+anime.id(),""+partie.id());
        while (cursor.moveToNext())
        {
            BDDMission mission = new BDDMission(cursor);
            Mission m = new Mission(this.getContext(),mission,master,anime,partie);
            layout.addView(m);
        }
        cursor.close();

        this.addView(layout);
    }

    public void refresh()
    {
        layout.removeAllViews();
        Cursor cursor = db.mission().selectAllMissions(""+anime.id(),""+partie.id());
        while (cursor.moveToNext())
        {
            BDDMission mission = new BDDMission(cursor);
            Mission m = new Mission(this.getContext(),mission,master,anime,partie);
            layout.addView(m);
        }
        cursor.close();
    }
}