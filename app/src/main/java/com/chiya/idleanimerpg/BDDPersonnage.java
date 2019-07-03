package com.chiya.idleanimerpg;

import android.database.Cursor;

public class BDDPersonnage
{
    private long id;
    private String nom;
    private long animeid;
    private long partieid;
    private String image;
    private long got;

    public BDDPersonnage(Cursor c)
    {
        id      = c.getLong(0);
        nom     = c.getString(1);
        animeid = c.getLong(2);
        partieid= c.getLong(3);
        image   = c.getString(4);
        got     = c.getLong(5);
    }

    public long id()        {return id;}
    public String nom()     {return nom;}
    public long animeid()   {return animeid;}
    public long partieid()  {return partieid;}
    public String image()   {return image;}
    public boolean got()    {return got>0;}
}
