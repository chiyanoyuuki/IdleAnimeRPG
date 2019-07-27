package com.chiya.Fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chiya.Activities.R;
import com.chiya.Activities.TestActivityFragment;
import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDAnime;
import com.chiya.BDD.BDDMission;
import com.chiya.BDD.BDDPartie;
import com.chiya.BDD.BDDPersonnage;
import com.chiya.Views.Mission;

import java.util.ArrayList;

public class Missions extends NewFragment implements View.OnClickListener
{
    private String state, packageName;
    private BDDAnime anime;
    private BDDPartie partie;
    private FrameLayout topImage;
    private ImageView bg;
    private TextView retour, bmissions, bhabitants, bhistoire, bmissions2, brecrutement;
    private LinearLayout boutonsbot, mid;
    private ArrayList<Mission> missions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.missions, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        missions    = new ArrayList<>();
        packageName = getContext().getPackageName();
        topImage    = view.findViewById(R.id.mhistoire_partieview);
        retour      = view.findViewById(R.id.mhistoire_retouraccueil2);
        bmissions   = view.findViewById(R.id.mhistoire_boutonmissions);
        bhabitants  = view.findViewById(R.id.mhistoire_boutonhabitants);
        bhistoire   = view.findViewById(R.id.mhistoire_boutonhistoire);
        bmissions2  = view.findViewById(R.id.mhistoire_boutonsbasiques);
        brecrutement= view.findViewById(R.id.mhistoire_boutonrecrutement);
        boutonsbot  = view.findViewById(R.id.mhistoire_boutonsbot);
        mid         = view.findViewById(R.id.mhistoire_layoutmissions);
        bg          = view.findViewById(R.id.mhistoire_bg);

        init();
    }

    private void init()
    {
        TestActivityFragment master = (TestActivityFragment) getActivity();
        BDD db = master.getDb();
        anime = db.anime().select(master.getAnime());
        partie = db.partie().select(master.getAnime(),master.getPartie());
        state = master.getState();
        initbg(anime,partie);
        initBoutons();

        if(state.equals("histoire"))        {change(0,bmissions);change(1,bhistoire);}
        else if(state.equals("missions"))   {change(0,bmissions);change(1,bmissions2);}
        else if(state.equals("recrutement")){change(0,bmissions);change(1,brecrutement);}
        else if(state.equals("personnages")){change(0,bhabitants);}

        initPage(db);
    }

    private void initbg(BDDAnime anime, BDDPartie partie)
    {
        topImage.setBackgroundResource(getResources().getIdentifier(partie.image(),"drawable",packageName));
        bg.setBackgroundResource(getResources().getIdentifier(anime.image(),"drawable",packageName));
    }

    private void initBoutons()
    {
        addBtn(retour,"retour");
        addBtn(bmissions,"histoire");
        addBtn(bhabitants,"personnages");
        addBtn(bhistoire,"histoire");
        addBtn(bmissions2,"missions");
        addBtn(brecrutement,"recrutement");
    }

    private void addBtn(TextView tv, String tag)
    {
        tv.setOnClickListener(this);
        tv.setTag(tag);
    }

    private void change(int i, TextView tv)
    {
        if(tv==bhabitants) boutonsbot.setVisibility(View.INVISIBLE);

        tv.setClickable(false);
        tv.setFocusable(false);
        if(i==0)tv.setBackgroundResource(R.drawable.clicked);
        else tv.setBackgroundResource(R.drawable.drapeauclicked);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        topImage    = null;
        retour      = null;
        bmissions   = null;
        bhabitants  = null;
        bhistoire   = null;
        bmissions2  = null;
        brecrutement= null;
        boutonsbot  = null;
        mid         = null;
        bg          = null;
        for(Mission m:missions)
        {
            m.destroy();
        }
        missions = null;
    }

    @Override
    public void onClick(View view)
    {
        TestActivityFragment master = (TestActivityFragment)getActivity();
        String tag = view.getTag().toString();
        if(tag.equals("retour"))
        {
            master.changeEcran(anime.id(),-1);
        }
        else if(tag.matches("[0-9]+"))
        {
            master.showPerso(tag);
        }
        else
        {
            master.changeEcran(anime.id(),partie.id(),tag);
        }
    }

    private void initPage(BDD db)
    {
        if(state.equals("personnages"))
        {
            mid.setPadding(0,50,0,100);
            long level = -1;
            int cpt = 0;
            LinearLayout layout = newLayout();
            Cursor cursor = db.personnage().selectAllPersonnages(""+partie.animeid(),""+partie.id());
            while(cursor.moveToNext())
            {
                BDDPersonnage perso = new BDDPersonnage(cursor);
                if(level==-1)
                {
                    level = perso.niveau();
                }
                else if(level!=perso.niveau())
                {
                    level = perso.niveau();
                    mid.addView(layout);
                    layout = newLayout();
                    cpt = 0;
                }
                if(cpt%4==0&&cpt!=0)
                {
                    mid.addView(layout);
                    layout = newLayout();
                }
                layout.addView(createImg(perso));
                cpt++;
            }
            cursor.close();
            mid.addView(layout);
        }
        else
        {
            mid.setPadding(50,200,50,200);
            createMissions();
        }
    }

    private void createMissions()
    {
        TestActivityFragment master = (TestActivityFragment)getActivity();
        BDD db = master.getDb();

        for(Mission m:missions)
        {
            mid.removeView(m.layout());
            m.destroy();
        }
        missions = new ArrayList<>();


        int type = 1;
        if(state.equals("histoire"))type=0;
        else if(state.equals("recrutement"))type=2;

        Cursor cursor = db.mission().selectAllMissions(""+anime.id(),""+partie.id(),""+type);
        while(cursor.moveToNext())
        {
            BDDMission tmp = new BDDMission(cursor);
            Mission mission = new Mission(this,tmp,anime,partie);
            missions.add(mission);
            mid.addView(mission.layout());
        }
        cursor.close();
    }

    public void refresh()
    {
        createMissions();
    }

    private LinearLayout newLayout()
    {
        LinearLayout tmp = new LinearLayout(this.getContext());
        tmp.setOrientation(LinearLayout.HORIZONTAL);
        tmp.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tmp.setGravity(Gravity.CENTER);
        tmp.setPadding(0,20,0,20);
        return tmp;
    }

    private ImageView createImg(BDDPersonnage personnage)
    {
        ImageView perso = new ImageView(this.getContext());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(170,170);
        lp.setMargins(10,0,10,0);
        perso.setLayoutParams(lp);
        perso.setBackgroundResource(super.getResources().getIdentifier("lvl_"+personnage.niveau(),"drawable",packageName));
        perso.setImageResource(this.getResources().getIdentifier(personnage.image(),"drawable",packageName));
        if(!personnage.viewable())perso.setColorFilter(Color.parseColor("#000000"));
        perso.setScaleType(ImageView.ScaleType.CENTER_CROP);
        perso.setPadding(200,0,200,0);
        perso.setTag(""+personnage.id());
        perso.setOnClickListener(this);
        return perso;
    }
}