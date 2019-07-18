package com.chiya.BDD;

import android.database.Cursor;

public class BDDPartie
{
    private long id;
    private String nom;
    private long animeid;
    private String image;

    public BDDPartie(Cursor c)
    {
        id      = c.getLong(0);
        nom     = c.getString(1);
        animeid = c.getLong(2);
        image   = c.getString(3);
    }

    public long id()        {return id;}
    public String nom()     {return nom;}
    public long animeid()   {return animeid;}
    public String image()   {return image;}
}
