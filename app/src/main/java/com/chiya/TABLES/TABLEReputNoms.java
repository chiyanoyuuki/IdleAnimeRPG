package com.chiya.TABLES;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.chiya.BDD.BDD;

public class TABLEReputNoms
{
    private BDD base;

    public TABLEReputNoms(BDD base)
    {
        this.base = base;
    }

    public void init(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE reputsnoms (" +
                "animeid INTEGER, " +
                "partieid INTEGER, " +
                "niveau INTEGER, " +
                "nom TEXTL, " +
                "FOREIGN KEY (animeid)  REFERENCES anime(id)," +
                "FOREIGN KEY (partieid) REFERENCES partie(id));");
    }

    public void newnomsreputs(SQLiteDatabase db, int i, int j, String[] noms)
    {
        for(int nb=0;nb<noms.length;nb++)
        {
            ContentValues content = new ContentValues();
            content.put("niveau",   nb+1);
            content.put("animeid",   i);
            content.put("partieid",   j);
            content.put("nom",    noms[0]);
            db.insert("reputsnoms",null,content);
        }
    }
}
