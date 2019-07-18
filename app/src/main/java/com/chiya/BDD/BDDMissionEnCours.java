package com.chiya.BDD;

import android.database.Cursor;

public class BDDMissionEnCours
{
    private long id;
    private long start;
    private long equipeid;
    private String bonus;

    public BDDMissionEnCours(Cursor cursor)
    {
        this.id = cursor.getLong(0);
        this.start = Long.parseLong(cursor.getString(1));
        this.equipeid = cursor.getLong(2);
        this.bonus = cursor.getString(3);
    }

    public long id(){return id;}
    public long start(){return start;}
    public long equipeid(){return equipeid;}
    public String bonus(){return bonus;}
}
