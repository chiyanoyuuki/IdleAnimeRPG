package com.chiya.TABLES;

import android.database.sqlite.SQLiteDatabase;

import com.chiya.BDD.BDD;

public class TABLEViewed
{
    private BDD base;

    public TABLEViewed(BDD base)
    {
        this.base = base;
    }

    public void init(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE viewed (" +
                "id INTEGER NOT NULL," +
                "viewable INTEGER NOT NULL DEFAULT 0," +
                "FOREIGN KEY(id) REFERENCES personnage(id)," +
                "PRIMARY KEY(id)\n" +
                ");");
    }

    public void view(String perso)
    {
        SQLiteDatabase db = base.getWritableDatabase();
        db.execSQL("UPDATE viewed SET viewable=1 WHERE ID=(SELECT id FROM personnage WHERE nom=\""+perso+"\")");
    }
}
