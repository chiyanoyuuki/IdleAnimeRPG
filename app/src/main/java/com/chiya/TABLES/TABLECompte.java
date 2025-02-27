package com.chiya.TABLES;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDCompte;

public class TABLECompte
{
    private BDD base;

    public TABLECompte(BDD base)
    {
        this.base = base;
    }

    public void init(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE compte     (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pseudo TEXTL, " +
                "persoid INTEGER    DEFAULT 0, " +
                "ressources INTEGER DEFAULT 10, " +
                "started INTEGER    DEFAULT 0," +
                "teamsize INTEGER   DEFAULT 0, " +
                "niveau INTEGER     DEFAULT 0, " +
                "nombre INTEGER     DEFAULT 0, " +
                "niveaug INTEGER    DEFAULT 0, " +
                "nombreg INTEGER    DEFAULT 0,"  +
                "niveaum INTEGER    DEFAULT 0,"  +
                "nombrem INTEGER    DEFAULT 0,"  +
                " FOREIGN KEY (persoid) REFERENCES personnage(id));");
    }

    public void create(SQLiteDatabase db)
    {
        ContentValues content = new ContentValues();
        content.put("pseudo"    ,"Nouveau joueur");
        content.put("persoid"   ,0);
        content.put("ressources",10);
        content.put("started"   ,0);
        db.insert("compte",null,content);
    }

    public void start()
    {
        SQLiteDatabase db = base.getWritableDatabase();
        db.execSQL("UPDATE COMPTE SET STARTED = 1");
    }

    public void changepseudo(String pseudo)
    {
        SQLiteDatabase db = base.getWritableDatabase();
        db.execSQL("UPDATE COMPTE SET PSEUDO = \""+pseudo+"\"");
    }

    public void addTeamSize()
    {
        SQLiteDatabase db = base.getWritableDatabase();
        db.execSQL("UPDATE COMPTE SET TEAMSIZE=TEAMSIZE+1");
        db.execSQL("UPDATE EQUIPE SET INTEAM=1 WHERE ID = (SELECT MAX(ID) FROM EQUIPE WHERE INTEAM=0)");
    }

    public BDDCompte selectCompte()
    {
        SQLiteDatabase db = base.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM COMPTE",new String[]{});
        cursor.moveToNext();
        return new BDDCompte(cursor);
    }

    public String levels(long anime)
    {
        SQLiteDatabase db = base.getReadableDatabase();
        Cursor cursor;
        if(anime==-1)   cursor = db.rawQuery("SELECT NIVEAU,-1,NIVEAUG,NIVEAUM FROM COMPTE",new String[]{});
        else            cursor = db.rawQuery("SELECT NIVEAU, (SELECT NIVEAU FROM REPUTPAYS WHERE ID="+anime+"),NIVEAUG,NIVEAUM FROM COMPTE",new String[]{});
        cursor.moveToNext();
        return cursor.getLong(0)+":"+cursor.getLong(1)+":"+cursor.getLong(2)+":"+cursor.getLong(3);
    }
}
