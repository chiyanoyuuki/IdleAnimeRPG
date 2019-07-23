package com.chiya.TABLES;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.chiya.BDD.BDD;

public class TABLEReputStats
{
    private BDD base;

    public TABLEReputStats(BDD base)
    {
        this.base = base;
    }

    public void init(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE reputsnoms (" +
                "animeid        INTEGER, " +
                "partieid       INTEGER, " +
                "niveau         INTEGER, " +
                "nom            TEXTL, " +
                "description    TEXTL, " +
                "bonus          TEXTL, " +
                "FOREIGN KEY (animeid)  REFERENCES anime(id)," +
                "FOREIGN KEY (partieid) REFERENCES partie(id));");
    }

    public void newnomsreputs(SQLiteDatabase db, int i, int j, String[] noms)
    {
        for(int nb=0;nb<noms.length;nb++)
        {
            String nom = noms[nb];
            String[] vals = nom.split(":");

            ContentValues content = new ContentValues();
            content.put("niveau",   nb+1);
            content.put("animeid",   i);
            content.put("partieid",   j);
            content.put("nom",    vals[0]);
            content.put("description",vals[1]);
            content.put("bonus",vals[2]);
            db.insert("reputsnoms",null,content);
        }
    }
}
