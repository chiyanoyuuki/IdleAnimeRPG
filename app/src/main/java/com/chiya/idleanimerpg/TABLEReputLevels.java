package com.chiya.idleanimerpg;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class TABLEReputLevels
{
    private BDD base;

    public TABLEReputLevels(BDD base)
    {
        this.base = base;
    }

    public void init(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE reputslevels (" +
                "niveau INTEGER, " +
                "monde INTEGER, " +
                "pays INTEGER, " +
                "partie INTEGER);");
    }

    public void newlevelsreputs(SQLiteDatabase db, String[] reputs)
    {
        for(int nb=0;nb<reputs.length;nb++)
        {
            String[] tmp = reputs[nb].split("/");
            ContentValues content = new ContentValues();
            content.put("niveau",   nb+1);
            content.put("monde",    tmp[0]);
            content.put("pays",     tmp[1]);
            content.put("partie",   tmp[2]);
            db.insert("reputslevels",null,content);
        }
    }
}
