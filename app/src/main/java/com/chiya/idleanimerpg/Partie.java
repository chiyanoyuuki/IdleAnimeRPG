package com.chiya.idleanimerpg;

import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

public class Partie extends Master
{
    private BDDPartie partie;
    private FrameLayout top;
    private LinearLayout missions, personnages, stats, centerlayout;
    private TextView btn01, btn02, btn03;
    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        String animeid = getIntent().getStringExtra("animeid");
        String partieid = getIntent().getStringExtra("partieid");
        partie = db.selectPartie(animeid,partieid);
        addViews();
        verifStart();
    }

    public void addViews()
    {
        mainlayout.addView(addHeader());
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
    }

    public void verifStart()
    {
        if(!compte.started())
        {
            Resources resources = getResources();
            String[] persos     = resources.getStringArray(getResources().getIdentifier("intro_persos_"     +partie.animeid()+"_"+partie.id(),"array",packageName));
            String[] dialogues  = resources.getStringArray(getResources().getIdentifier("intro_dialogues_"  +partie.animeid()+"_"+partie.id(),"array",packageName));
            Dialogue textes = new Dialogue(this, db, persos,dialogues);
            background.addView(textes.getView());
        }
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

        top.addView(topimage);
        centerlayout.addView(top);
    }

    private void change(int i)
    {
        if(state!=i)
        {
            if(state==0)        {centerlayout.removeView(missions);     btn01.setBackground(ContextCompat.getDrawable(this,R.drawable.bouton));}
            else if(state==1)   {centerlayout.removeView(personnages);  btn02.setBackground(ContextCompat.getDrawable(this,R.drawable.bouton));}
            else if(state==2)   {centerlayout.removeView(stats);        btn03.setBackground(ContextCompat.getDrawable(this,R.drawable.bouton));}

            if(i==0)            {centerlayout.addView(missions);        btn01.setBackground(ContextCompat.getDrawable(this,R.drawable.clicked));}
            else if(i==1)       {centerlayout.addView(personnages);     btn02.setBackground(ContextCompat.getDrawable(this,R.drawable.clicked));}
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
        fmtBtn(btn02,"Personnages");
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
        missions.setOrientation(LinearLayout.VERTICAL);
        missions.setBackground(ContextCompat.getDrawable(this,R.drawable.testbg));
        missions.setPadding(50,50,50,0);
        missions.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1));

        Cursor cursor = db.selectAllMissions(""+partie.animeid(),""+partie.id());

        while (cursor.moveToNext())
        {
            Mission m = new Mission(this);
            missions.addView(m.layout());
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
        personnages.setBackground(ContextCompat.getDrawable(this,R.drawable.testbg));
        personnages.setPadding(16,20,16,0);
        personnages.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1));

        ArrayList<LinearLayout> layouts = new ArrayList<LinearLayout>();
        addLayout(layouts);
        Cursor cursor = db.selectAllPersonnages(""+partie.animeid(),""+partie.id());
        int cpt=0;
        while (cursor.moveToNext())
        {
            BDDPersonnage personnage = new BDDPersonnage(cursor);

            FrameLayout fond = new FrameLayout(this);
            ImageView img = new ImageView(this);
            fond.setBackground(ContextCompat.getDrawable(this,R.drawable.fullbg2));
            img.setImageResource(super.getResources().getIdentifier(personnage.image(),"drawable",packageName));
            if(!personnage.got())img.setColorFilter(Color.parseColor("#000000"));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(200,200);
            lp.setMargins(20,20,20,20);
            fond.setLayoutParams(lp);
            img.setLayoutParams(new LinearLayout.LayoutParams(350,350));
            img.setScaleType(ImageView.ScaleType.FIT_START);
            fond.addView(img);
            layouts.get(layouts.size()-1).addView(fond);

            cpt++;
            if(cpt%4==0)addLayout(layouts);
        }
        cursor.close();
        for(LinearLayout layout:layouts)personnages.addView(layout);
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
}
