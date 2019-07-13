package com.chiya.idleanimerpg;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class TABLEAnime
{
    private BDD base;

    public TABLEAnime(BDD base)
    {
        this.base = base;
    }

    public void init(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE anime      (" +
                "id INTEGER PRIMARY KEY, " +
                "nom TEXTL, " +
                "image TEXTL, " +
                "isup INTEGER);");
    }

    public void addAnime(SQLiteDatabase db, int i, String[] anime)
    {
        ContentValues content = new ContentValues();
        content.put("id"        ,i);
        content.put("nom"       ,anime[0]);
        content.put("image"     ,anime[1]);
        content.put("isup"      ,anime[2]);
        db.insert("anime",null,content);
    }
}
