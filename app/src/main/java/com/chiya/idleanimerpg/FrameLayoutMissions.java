package com.chiya.idleanimerpg;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FrameLayoutMissions extends NewFrameLayoutMissions
{
    private TextView histoire, missions, recrutements;

    public FrameLayoutMissions(Master master, BDDAnime anime, BDDPartie partie)
    {
        super(master,anime,partie);
        init();
    }

    private void init()
    {
        addMissions();
        addSousMenus();
    }

    private void addMissions()
    {
        Cursor cursor = db.mission().selectAllMissions(""+anime.id(),""+partie.id());
        this.addView(new ScrollViewMissions(master,cursor,anime,partie));
    }

    private void addSousMenus()
    {
        LinearLayout sousMenus = new LinearLayout(context);
        sousMenus.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,130));
        sousMenus.setPadding(50,0,0,0);
        sousMenus.setOrientation(LinearLayout.HORIZONTAL);
        histoire        = ssmenu("Histoire",    0);
        missions        = ssmenu("Missions",    1);
        recrutements    = ssmenu("Recrutement", 2);
        sousMenus.addView(histoire);
        sousMenus.addView(missions);
        sousMenus.addView(recrutements);
        this.addView(sousMenus);
    }

    private TextView ssmenu(String menu, int x)
    {
        final int i = x;
        TextView tmp = new TextView(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
        lp.setMargins(0,0,50,0);
        tmp.setLayoutParams(lp);
        tmp.setText(menu);
        tmp.setPadding(0,30,0,0);
        tmp.setBackgroundResource(R.drawable.drapeau);
        if(menu.equals("Histoire")) tmp.setBackgroundResource(R.drawable.drapeauclicked);
        tmp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tmp.setTextColor(Color.parseColor("#ffffff"));
        tmp.setTextSize(9);
        tmp.setTypeface(tmp.getTypeface(), Typeface.BOLD);
        tmp.setClickable(true);
        tmp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                change(i);
            }
        });
        return tmp;
    }

    private void change(int i)
    {
        if(i==0)
        {
            histoire.setBackgroundResource(R.drawable.drapeauclicked);
            missions.setBackgroundResource(R.drawable.drapeau);
            recrutements.setBackgroundResource(R.drawable.drapeau);
        }
        else if(i==1)
        {
            histoire.setBackgroundResource(R.drawable.drapeau);
            missions.setBackgroundResource(R.drawable.drapeauclicked);
            recrutements.setBackgroundResource(R.drawable.drapeau);
        }
        else if(i==2)
        {
            histoire.setBackgroundResource(R.drawable.drapeau);
            missions.setBackgroundResource(R.drawable.drapeau);
            recrutements.setBackgroundResource(R.drawable.drapeauclicked);
        }
    }

    public void refresh()
    {

    }
}