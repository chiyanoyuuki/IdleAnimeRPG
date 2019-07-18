package com.chiya.Fragments;

import android.os.Bundle;
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
import com.chiya.BDD.BDDPartie;

public class Missions extends NewFragment implements View.OnClickListener
{
    private BDDAnime anime;
    private BDDPartie partie;
    private FrameLayout topImage;
    private ImageView bg;
    private TextView retour, bmissions, bhabitants, bhistoire, bmissions2, brecrutement;
    private LinearLayout boutonsbot, mid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.missions, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
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
        partie = db.partie().select(master.getPartie());
        String state = master.getState();

        if(state.equals("histoire"))        {change(0,bmissions);change(1,bhistoire);}
        else if(state.equals("missions"))   {change(0,bmissions);change(1,bmissions2);}
        else if(state.equals("recrutement")){change(0,bmissions);change(1,brecrutement);}
        else if(state.equals("personnages")){change(0,bhabitants);}

        initbg(anime,partie);
        initBoutons();
    }

    private void initbg(BDDAnime anime, BDDPartie partie)
    {
        topImage.setBackgroundResource(getResources().getIdentifier(partie.image(),"drawable",getContext().getPackageName()));
        bg.setBackgroundResource(getResources().getIdentifier(anime.image(),"drawable",getContext().getPackageName()));
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
        else
        {
            master.changeEcran(anime.id(),partie.id(),tag);
        }
    }
}