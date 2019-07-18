package com.chiya.BDD;

import android.database.Cursor;

public class BDDCompte
{
    private long id;
    private String pseudo;
    private long persoid;
    private long ressources;
    private boolean started;
    private long teamsize;

    public BDDCompte(Cursor c)
    {
        id          = c.getLong(0);
        pseudo      = c.getString(1);
        persoid     = c.getLong(2);
        ressources  = c.getLong(3);
        started     = c.getLong(4)!=0;
        teamsize    = c.getLong(5);
    }

    public long id()        {return id;}
    public String pseudo()  {return pseudo;}
    public long persoid()   {return persoid;}
    public long ressources(){return ressources;}
    public boolean started(){return started;}
    public long teamsize(){return teamsize;}
}
