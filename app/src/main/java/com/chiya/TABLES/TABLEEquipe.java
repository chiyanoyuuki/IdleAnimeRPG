package com.chiya.TABLES;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDEquipe;

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
                "used INTEGER, " +
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
                "(SELECT NIVEAU FROM PERSONNAGE WHERE NOM=\""+nom+"\")," +
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

    public void setUsed(BDDEquipe personnage)
    {
        SQLiteDatabase db = base.getWritableDatabase();
        db.execSQL("UPDATE EQUIPE SET USED = 1 WHERE ID = "+personnage.id());
    }

    public String getTimes(long id)
    {
        SQLiteDatabase db = base.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT START,(SELECT TEMPS*1000 FROM MISSION WHERE ID=M.ID ) FROM MISSIONSENCOURS M WHERE EQUIPEID = "+id,new String[]{});
        cursor.moveToNext();
        return cursor.getString(0)+":"+cursor.getLong(1);
    }
}
