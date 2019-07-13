package com.chiya.idleanimerpg;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

public class Partie extends Master
{
    private FrameLayoutMuraillePersos muraille;

    private BDDAnime anime;
    private BDDPartie partie;
    private FrameLayout top, hautimg;
    private LinearLayout missions, personnages, stats, centerlayout, hautmissions;
    private TextView btn01, btn02, btn03;
    private int state;
    private FrameLayout persos;
    private ArrayList<ImageView> persosmuraille, listepersonnages;

    /*@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        listepersonnages = new ArrayList<>();
        persosmuraille = new ArrayList<>();
        String animeid = getIntent().getStringExtra("animeid");
        String partieid = getIntent().getStringExtra("partieid");
        anime = db.selectAnime(animeid);
        partie = db.selectPartie(animeid,partieid);
        addViews();
        //addForeground();
        verifStart();
    }

    public void addViews()
    {
        mainlayout.addView(addHeader());
        mainlayout.addView(addReputs(partie.animeid(),partie.id()));
        centerlayout = new LinearLayout(this);
        centerlayout.setOrientation(LinearLayout.VERTICAL);
        centerlayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1));
        addTop();
        addMid();
        addBot();
        centerlayout.addView(missions);
        mainlayout.addView(centerlayout);
        mainlayout.addView(addFooter(false));
        background.addView(mainlayout);
        refreshbarres(anime.id(),partie.id());
    }

    public void verifStart()
    {
        ArrayList<BDDDialogue> dialogues = db.selectAllDialogues(""+partie.animeid(),""+partie.id());
        dialogue = new Dialogue(this, db, dialogues,this);
        background.addView(dialogue.getView());
    }

    private void addTop()
    {
        top = new FrameLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,400);
        top.setLayoutParams(lp);
        top.setBackground(ContextCompat.getDrawable(this,R.drawable.testbg));

        ImageView topimage = new ImageView(this);
        topimage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        topimage.setImageResource(super.getResources().getIdentifier(partie.image(),"drawable",packageName));
        topimage.setScaleType(ImageView.ScaleType.FIT_XY);

        ImageView tmp = new ImageView(this);
        tmp.setImageResource(R.drawable.retour);
        FrameLayout.LayoutParams fp = new FrameLayout.LayoutParams(150,150);
        //fp.gravity = Gravity.
        tmp.setLayoutParams(fp);
        tmp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Partie.this, Anime.class);
                intent.putExtra("animeid",""+partie.animeid());
                closeDb();
                startActivity(intent);
            }
        });

        top.addView(topimage);
        top.addView(tmp);
        centerlayout.addView(top);
    }

    private void change(int i)
    {
        if(state!=i)
        {
            if(state==0)        {centerlayout.removeView(missions);     btn01.setBackground(ContextCompat.getDrawable(this,R.drawable.bouton));}
            else if(state==1)   {centerlayout.removeView(persos);       btn02.setBackground(ContextCompat.getDrawable(this,R.drawable.bouton));}
            else if(state==2)   {centerlayout.removeView(stats);        btn03.setBackground(ContextCompat.getDrawable(this,R.drawable.bouton));}

            if(i==0)            {centerlayout.addView(missions);        btn01.setBackground(ContextCompat.getDrawable(this,R.drawable.clicked));refreshMuraille();}
            else if(i==1)       {centerlayout.addView(persos);          btn02.setBackground(ContextCompat.getDrawable(this,R.drawable.clicked));}
            else if(i==2)       {centerlayout.addView(stats);           btn03.setBackground(ContextCompat.getDrawable(this,R.drawable.clicked));}

            state = i;
        }
    }

    private void addMid()
    {
        LinearLayout mid = new LinearLayout(this);
        mid.setGravity(Gravity.BOTTOM);
        mid.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        mid.setOrientation(LinearLayout.HORIZONTAL);

        btn01 = new TextView(this);
        fmtBtn(btn01,"Missions");
        btn01.setBackground(ContextCompat.getDrawable(this,R.drawable.clicked));
        btn01.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                change(0);
            }
        });

        btn02 = new TextView(this);
        fmtBtn(btn02,"Habitants");
        btn02.setBackground(ContextCompat.getDrawable(this,R.drawable.bouton));
        btn02.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                change(1);
            }
        });

        btn03 = new TextView(this);
        fmtBtn(btn03,"Stats");
        btn03.setBackground(ContextCompat.getDrawable(this,R.drawable.bouton));
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
        top.addView(mid);
    }

    private void fmtBtn(TextView tv, String s)
    {
        tv.setLayoutParams(new LinearLayout.LayoutParams(0,150,1));
        tv.setTextColor(Color.parseColor("#ffffff"));
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        tv.setGravity(Gravity.CENTER);
        tv.setText(s);
    }

    private void addBot()
    {
        createMissions();
        createPersonnages();
        createStats();
    }

    private void createMissions()
    {
        missions = new LinearLayout(this);
        missions.addView(new FrameLayoutMissions(this,anime,partie));
    }

    private TextView ssmenu(String menu)
    {
        TextView tmp = new TextView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(230, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.setMargins(0,0,50,0);
        tmp.setLayoutParams(lp);
        tmp.setText(menu);
        tmp.setPadding(0,30,0,0);
        tmp.setBackgroundResource(R.drawable.drapeau);
        if(menu.equals("Histoire")) tmp.setBackgroundResource(R.drawable.drapeauclicked);
        tmp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tmp.setTextColor(Color.parseColor("#ffffff"));
        tmp.setTextSize(9);
        tmp.setTypeface(tmp.getTypeface(),Typeface.BOLD);
        return tmp;
    }

    public void refreshMuraille()
    {
        muraille.refreshPersos();
    }

    private void refreshMissions()
    {
        hautmissions.removeAllViews();
        Cursor cursor = db.selectAllMissions(""+anime.id(),""+partie.id());

        while (cursor.moveToNext())
        {
            BDDMission mission = new BDDMission(cursor);
            Mission m = new Mission(this,this,mission);
            hautmissions.addView(m.layout());
        }
        cursor.close();
    }

    private void addLayout(ArrayList<LinearLayout> layouts)
    {
        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams lp0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,200);
        lp0.setMargins(20,20,20,0);
        layout.setLayoutParams(lp0);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        layouts.add(layout);
    }

    private void createPersonnages()
    {
        personnages = new LinearLayout(this);
        personnages.setOrientation(LinearLayout.VERTICAL);
        personnages.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1));

        persos = new FrameLayout(this);
        persos.setBackgroundColor(Color.parseColor("#505050"));
        persos.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1));
        //persos.setPadding(16,20,16,0);

        ImageView fondx = new ImageView(this);
        fondx.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fondx.setBackgroundResource(super.getResources().getIdentifier(anime.image(),"drawable",packageName));
        fondx.setAlpha(0.4f);

        ScrollView haut = new ScrollView(this);
        haut.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ArrayList<LinearLayout> layouts = new ArrayList<LinearLayout>();
        addLayout(layouts);
        Cursor cursor = db.selectAllPersonnages(""+partie.animeid(),""+partie.id());
        int cpt=0;
        while (cursor.moveToNext())
        {
            BDDPersonnage personnage = new BDDPersonnage(cursor);

            FrameLayout fond = new FrameLayout(this);
            ImageView img = new ImageView(this);
            fond.setBackgroundResource(super.getResources().getIdentifier("lvl_"+personnage.niveau(),"drawable",packageName));
            img.setImageResource(super.getResources().getIdentifier(personnage.image(),"drawable",packageName));
            if(!personnage.viewable())img.setColorFilter(Color.parseColor("#000000"));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(200,200);
            lp.setMargins(20,20,20,20);
            fond.setLayoutParams(lp);
            img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            img.setPadding(500,0,500,0);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            fond.addView(img);
            listepersonnages.add(img);
            layouts.get(layouts.size()-1).addView(fond);
            cpt++;
            if(cpt%4==0)addLayout(layouts);
        }
        cursor.close();
        for(LinearLayout layout:layouts)personnages.addView(layout);

        haut.addView(personnages);

        persos.addView(fondx);
        persos.addView(haut);
    }

    private void createStats()
    {
        stats = new LinearLayout(this);
        stats.setBackground(ContextCompat.getDrawable(this,R.drawable.testbg));
        stats.setPadding(16,20,16,0);
        stats.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1));
        TextView text = new TextView(this);
        text.setTextColor(Color.parseColor("#000000"));
        text.setTextSize(16);
        text.setGravity(Gravity.CENTER);
        text.setText("Stats");
        stats.addView(text);
    }

    public void finishMission(BDDMission mission)
    {
        recompense();
        db.finishMission(mission,rmonde,rpays,rpartie);
        refreshMissions();
        ArrayList<BDDDialogue> dialogues = db.selectAllDialogues(""+partie.animeid(),""+partie.id());
        dialogue.reinit(dialogues);
        refreshbarres(anime.id(),partie.id());
    }

    private void recompense()
    {
        int h = 20;
        String image = "icone_reputpartie";
        for(int i=0;i<100;i++)
        {
            if(i==59){image = "icone_reputpays";h=10;}
            if(i==89){image = "icone_reputmonde";h=0;}
            final ImageView tmp = new ImageView(this);
            tmp.setImageResource(super.getResources().getIdentifier(image,"drawable",packageName));
            tmp.setLayoutParams(new LinearLayout.LayoutParams(40,40));
            tmp.setX(0+(int)(Math.random()*1000));
            tmp.setY(600+(int)(Math.random()*800));

            background.addView(tmp);
            Animation animation = new TranslateAnimation(0,-tmp.getX()-40,0,-tmp.getY()+135+h);
            animation.setDuration(1500);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}
                @Override
                public void onAnimationRepeat(Animation animation) {}
                @Override
                public void onAnimationEnd(Animation animation) {
                    background.removeView(tmp);
                }
            });
            animation.setFillAfter(true);
            tmp.setAnimation(animation);
        }
    }

    public void refreshPersos()
    {
        int cpt = 0;
        Cursor cursor = db.selectAllPersonnages(""+partie.animeid(),""+partie.id());
        while(cursor.moveToNext())
        {
            BDDPersonnage tmp = new BDDPersonnage(cursor);
            if(tmp.viewable())listepersonnages.get(cpt).clearColorFilter();
            cpt++;
        }
        cursor.close();
    }*/
}
