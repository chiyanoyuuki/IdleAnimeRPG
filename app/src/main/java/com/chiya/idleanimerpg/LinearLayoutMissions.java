package com.chiya.idleanimerpg;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class LinearLayoutMissions extends NewLinearLayout
{
    private BDDAnime anime;
    private BDDPartie partie;
    private TextView btn01, btn02, btn03;
    private FrameLayoutMissions missions;
    private FrameLayoutPersonnages personnages;

    public LinearLayoutMissions(Master master, BDDAnime anime, BDDPartie partie)
    {
        super(master);
        this.anime = anime;
        this.partie = partie;
        init();
    }

    private void init()
    {
        this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,0,1));
        this.setOrientation(VERTICAL);

        missions = new FrameLayoutMissions(master,anime,partie);
        personnages = new FrameLayoutPersonnages(master,anime,partie);

        addTop();
        addLiaison();
        addMid();
    }

    private void addMid()
    {
        this.addView(missions);
    }

    private void addLiaison()
    {
        ImageView liaison = new ImageView(context);
        liaison.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,20));
        liaison.setImageResource(R.drawable.fond);
        liaison.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.addView(liaison);
    }

    private void addTop()
    {
        FrameLayout top = new FrameLayout(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,400);
        top.setLayoutParams(lp);
        top.setBackground(ContextCompat.getDrawable(context,R.drawable.midbg));

        ImageView topimage = new ImageView(context);
        topimage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        topimage.setImageResource(super.getResources().getIdentifier(partie.image(),"drawable",packageName));
        topimage.setScaleType(ImageView.ScaleType.FIT_XY);

        ImageView tmp = new ImageView(context);
        tmp.setImageResource(R.drawable.retour);
        FrameLayout.LayoutParams fp = new FrameLayout.LayoutParams(150,150);
        //fp.gravity = Gravity.
        tmp.setLayoutParams(fp);
        tmp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                master.changeEcran(anime);
            }
        });

        top.addView(topimage);
        top.addView(tmp);
        top.addView(boutons());

        this.addView(top);
    }

    private LinearLayout boutons()
    {
        LinearLayout mid = new LinearLayout(context);
        mid.setGravity(Gravity.BOTTOM);
        mid.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mid.setOrientation(LinearLayout.HORIZONTAL);

        btn01 = new TextView(context);
        fmtBtn(btn01,"Missions");
        btn01.setBackgroundResource(R.drawable.clicked);
        btn01.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                change(0);
            }
        });

        btn02 = new TextView(context);
        fmtBtn(btn02,"Habitants");
        btn02.setBackgroundResource(R.drawable.bouton);
        btn02.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                change(1);
            }
        });

        btn03 = new TextView(context);
        fmtBtn(btn03,"Stats");
        btn03.setBackgroundResource(R.drawable.bouton);
        btn03.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                change(2);
            }
        });

        mid.addView(btn01);
        mid.addView(btn02);
        mid.addView(btn03);

        return mid;
    }

    private void fmtBtn(TextView tv, String s)
    {
        tv.setLayoutParams(new LinearLayout.LayoutParams(0,150,1));
        tv.setTextColor(Color.parseColor("#ffffff"));
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        tv.setGravity(Gravity.CENTER);
        tv.setText(s);
    }

    private void change(int i)
    {
        if(i==0)
        {
            btn01.setBackgroundResource(R.drawable.clicked);
            btn02.setBackgroundResource(R.drawable.bouton);
            btn03.setBackgroundResource(R.drawable.bouton);

            this.removeViewAt(2);
            this.addView(missions,2);
        }
        else if(i==1)
        {
            btn01.setBackgroundResource(R.drawable.bouton);
            btn02.setBackgroundResource(R.drawable.clicked);
            btn03.setBackgroundResource(R.drawable.bouton);

            this.removeViewAt(2);
            this.addView(personnages,2);
        }
        else if(i==2)
        {
            btn01.setBackgroundResource(R.drawable.bouton);
            btn02.setBackgroundResource(R.drawable.bouton);
            btn03.setBackgroundResource(R.drawable.clicked);
        }
    }

    public void refresh()
    {
        missions.refresh();
    }
}