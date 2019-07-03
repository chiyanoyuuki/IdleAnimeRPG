package com.chiya.idleanimerpg;

import android.database.Cursor;

public class BDDMission
{
    private long id;
    private long animeid;
    private long partieid;
    private String image;
    private String nom;
    private long reput;
    private long temps;

    public BDDMission(Cursor c)
    {
        id      = c.getLong(0);
        animeid = c.getLong(1);
        partieid= c.getLong(2);
        image   = c.getString(3);
        nom     = c.getString(4);
        reput   = c.getLong(5);
        temps   = c.getLong(6)*60;
    }

    public long id()        {return id;}
    public String nom()     {return nom;}
    public long animeid()   {return animeid;}
    public long partieid()  {return partieid;}
    public String image()   {return image;}
    public long reput()     {return reput;}
    public long temps()     {return temps;}
}