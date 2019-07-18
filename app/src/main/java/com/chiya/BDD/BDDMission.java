package com.chiya.BDD;

import android.database.Cursor;

public class BDDMission
{
    private long id;
    private long animeid;
    private long partieid;
    private String image;
    private String nom;
    private long temps;
    private long rmonde;
    private long rpays;
    private long rpartie;
    private boolean started;

    public BDDMission(Cursor c)
    {
        id      = c.getLong(0);
        animeid = c.getLong(4);
        partieid= c.getLong(5);
        image   = c.getString(6);
        nom     = c.getString(7);
        temps   = c.getLong(8)*1000;
        rmonde  = c.getLong(9);
        rpays   = c.getLong(10);
        rpartie = c.getLong(11);
        started = c.getLong(12)==1;
    }

    public long id()        {return id;}
    public String nom()     {return nom;}
    public long animeid()   {return animeid;}
    public long partieid()  {return partieid;}
    public String image()   {return image;}
    public long temps()     {return temps;}
    public long rmonde()    {return rmonde;}
    public long rpays()     {return rpays;}
    public long rpartie()   {return rpartie;}
    public boolean started(){return started;}
}