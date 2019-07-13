package com.chiya.idleanimerpg;

import android.database.Cursor;

public class BDDAnime
{
    private long id;
    private String nom;
    private String image;
    private boolean isup;

    public BDDAnime(Cursor c)
    {
        id      = c.getLong(0);
        nom     = c.getString(1);
        image   = c.getString(2);
        isup    = c.getLong(3)==1;
    }

    public long id()        {return id;}
    public String nom()     {return nom;}
    public String image()   {return image;}
    public boolean isup()   {return isup;}
}
