package com.chiya.BDD;

import android.database.Cursor;

public class BDDEquipe
{
    private long id;
    private String pseudo;
    private long niveau;
    private long persoid;
    private String image;
    private boolean used;

    public BDDEquipe(Cursor cursor)
    {
        this.id         = cursor.getLong(0);
        this.pseudo     = cursor.getString(1);
        this.niveau     = cursor.getLong(2);
        this.persoid    = cursor.getLong(3);
        this.image      = cursor.getString(5);
        this.used       = cursor.getLong(6)==1;
    }

    public long id(){return id;}
    public String pseudo(){return pseudo;}
    public long niveau(){return niveau;}
    public long persoid(){return persoid;}
    public String image(){return image;}
    public boolean used(){return used;}

    public String toString()
    {
        return id+":"+pseudo+":"+persoid+":"+used;
    }
}
