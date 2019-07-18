package com.chiya.Layouts;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chiya.Activities.Master;
import com.chiya.Activities.NewActivity;
import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDAnime;
import com.chiya.BDD.BDDPartie;

public abstract class NewFrameLayoutMissions extends FrameLayout
{
    protected Master master;
    protected BDD db;
    protected String packageName;
    protected BDDAnime anime;
    protected BDDPartie partie;

    public NewFrameLayoutMissions(Master master, BDDAnime anime, BDDPartie partie)
    {
        super(master);
        this.master         = master;
        this.db             = master.getDb();
        this.packageName    = master.getPackageName();

        this.anime = anime;
        this.partie = partie;

        //this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1));
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addBackground();
    }

    private void addBackground()
    {
        ImageView background = new ImageView(this.getContext());
        background.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        background.setImageResource(super.getResources().getIdentifier(anime.image(),"drawable",packageName));
        background.setAlpha(0.6f);
        background.setScaleType(ImageView.ScaleType.FIT_XY);
        this.addView(background);
    }
}
