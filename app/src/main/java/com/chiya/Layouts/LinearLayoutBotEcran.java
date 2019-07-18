package com.chiya.Layouts;

import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chiya.Activities.Master;
import com.chiya.Activities.NewActivity;
import com.chiya.Activities.R;

public class LinearLayoutBotEcran extends NewLinearLayout
{
    private boolean alreadyaccueil;
    private final int size = 150;

    public LinearLayoutBotEcran(Master master)
    {
        super(master);
        init();
    }

    private void init()
    {
        this.alreadyaccueil = true;

        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, size));
        this.setBackgroundResource(R.drawable.fondbas);
        this.setGravity(Gravity.CENTER_VERTICAL);
        this.setPadding(30,0,30,0);

        addAcuueil();
        addEquipe();
        addStats();
    }

    private void addAcuueil()
    {
        ImageView accueil = new ImageView(this.getContext());
        accueil.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1));
        accueil.setPadding(0,30,0,30);
        accueil.setImageResource(R.drawable.accueil);
        /*accueil.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                master.changeEcran("accueil");
            }
        });*/
        this.addView(accueil);
    }

    private void addEquipe()
    {
        ImageView equipe = new ImageView(this.getContext());
        equipe.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1));
        equipe.setImageResource(R.drawable.personnages);
        equipe.setPadding(0,20,0,30);
        /*equipe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                master.changeEcran("equipe");
            }
        });*/
        this.addView(equipe);
    }

    private void addStats()
    {
        ImageView stats = new ImageView(this.getContext());
        stats.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1));
        stats.setPadding(10,40,0,30);
        stats.setImageResource(R.drawable.stats);
        /*stats.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                master.changeEcran("stats");
            }
        });*/
        this.addView(stats);
    }

    public int getSize(){return size;}
}