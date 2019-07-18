package com.chiya.BDD;

import android.database.Cursor;

public class BDDPersonnage
{
    private long id;
    private String nom;
    private long animeid;
    private long partieid;
    private String image;
    private long niveau;
    private boolean viewable;

    public BDDPersonnage(Cursor c)
    {
        id      = c.getLong(0);
        nom     = c.getString(1);
        animeid = c.getLong(2);
        partieid= c.getLong(3);
        image   = c.getString(4);
        niveau  = c.getLong(5);
        viewable = c.getLong(6)==1;
    }

    public long id()        {return id;}
    public String nom()     {return nom;}
    public long animeid()   {return animeid;}
    public long partieid()  {return partieid;}
    public String image()   {return image;}
    public long niveau()    {return niveau;}
    public boolean viewable(){return viewable;}
}
