package com.chiya.idleanimerpg;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TABLEEquipe
{
    private BDD base;

    public TABLEEquipe(BDD base)
    {
        this.base = base;
    }

    public void init(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE equipe     (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pseudo TEXTL, " +
                "niveau INTEGER, " +
                "persoid INTEGER," +
                "inteam INTEGER, " +
                "image TEXTL, " +
                " FOREIGN KEY (persoid)  REFERENCES personnage(id));");
    }

    public Cursor selectEquipe()
    {
        SQLiteDatabase db = base.getReadableDatabase();
        return db.rawQuery("SELECT  * FROM equipe WHERE inteam=1",new String[]{});
    }

    public void addequipe(String nom)
    {
        SQLiteDatabase db = base.getWritableDatabase();
        db.execSQL("INSERT INTO equipe(pseudo,niveau,persoid,inteam,image) VALUES(" +
                "\""+nom+"\"," +
                "1," +
                "(SELECT id FROM personnage WHERE nom=\""+nom+"\")," +
                "(CASE WHEN (SELECT COUNT(*) FROM equipe WHERE inteam=1)<(SELECT teamsize FROM COMPTE WHERE id=1) THEN 1 ELSE 0 END)," +
                "(SELECT image FROM personnage WHERE nom=\""+nom+"\"))");
    }

    public boolean gotPerso(String s)
    {
        SQLiteDatabase db = base.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM EQUIPE E,PERSONNAGE P WHERE E.PERSOID=P.ID AND P.NOM=?",new String[]{s});
        boolean b = cursor.moveToNext();
        cursor.close();
        return b;
    }
}
