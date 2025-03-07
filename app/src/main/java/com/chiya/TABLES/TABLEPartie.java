package com.chiya.TABLES;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDPartie;

public class TABLEPartie
{
    private BDD base;

    public TABLEPartie(BDD base)
    {
        this.base = base;
    }

    public void init(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE partie     (" +
                "id INTEGER, " +
                "nom TEXTL, " +
                "animeid INTEGER, " +
                "image TEXTL, " +
                "PRIMARY KEY (id,animeid),  " +
                "FOREIGN KEY (animeid)  REFERENCES anime(id));");
    }

    public void addPartie(SQLiteDatabase db, int i, int j, String[] partie)
    {
        ContentValues content = new ContentValues();
        content.put("id"        ,j);
        content.put("nom"       ,partie[0]);
        content.put("animeid"   ,i);
        content.put("image"     ,partie[1]);
        db.insert("partie",null,content);
    }

    public Cursor selectAllParties(String animeid)
    {
        SQLiteDatabase db = base.getReadableDatabase();
        return db.rawQuery("SELECT * FROM partie WHERE animeid = ?",new String[]{animeid});
    }

    public BDDPartie select(long anime, long partie)
    {
        SQLiteDatabase db = base.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PARTIE WHERE ID="+partie+" AND ANIMEID="+anime,new String[]{});
        cursor.moveToNext();
        return new BDDPartie(cursor);
    }
}
