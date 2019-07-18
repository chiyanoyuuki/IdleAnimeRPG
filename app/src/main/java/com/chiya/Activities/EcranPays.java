package com.chiya.Activities;

import com.chiya.BDD.BDDAnime;
import com.chiya.Layouts.LinearLayoutAnime;

public class EcranPays extends NewActivity
{
    private LinearLayoutAnime animes;

    public EcranPays()
    {
        super();
    }

    public void addMid()
    {
        long animeid = getIntent().getLongExtra("animeid",1);
        BDDAnime anime = db.anime().select(animeid);
        //animes = new LinearLayoutAnime(this,anime);
        mid.addView(animes);
    }
}
