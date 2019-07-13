package com.chiya.idleanimerpg;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public abstract class NewFrameLayoutMissions extends FrameLayout
{
    protected Master master;
    protected BDD db;
    protected Context context;
    protected String packageName;
    protected BDDAnime anime;
    protected BDDPartie partie;

    public NewFrameLayoutMissions(Master master, BDDAnime anime, BDDPartie partie)
    {
        super(master);
        this.master         = master;
        this.db             = master.getDb();
        this.context        = master;
        this.packageName    = master.getPackageName();

        this.anime = anime;
        this.partie = partie;

        //this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1));
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addBackground();
    }

    private void addBackground()
    {
        ImageView background = new ImageView(context);
        background.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        background.setImageResource(super.getResources().getIdentifier(anime.image(),"drawable",packageName));
        background.setAlpha(0.6f);
        background.setScaleType(ImageView.ScaleType.FIT_XY);
        this.addView(background);
    }
}
