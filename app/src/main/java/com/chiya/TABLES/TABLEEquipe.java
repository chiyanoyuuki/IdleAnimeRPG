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
                "place INTEGER, " +
                " FOREIGN KEY (persoid)  REFERENCES personnage(id));");
    }

    public Cursor selectEquipe()
    {
        SQLiteDatabase db = base.getReadableDatabase();
        return db.rawQuery("SELECT  * FROM equipe WHERE inteam=1 ORDER BY place",new String[]{});
    }

    public Cursor selectAll()
    {
        SQLiteDatabase db = base.getReadableDatabase();
        return db.rawQuery("SELECT  * FROM equipe ORDER BY NIVEAU DESC",new String[]{});
    }

    public BDDEquipe select(String id)
    {
        SQLiteDatabase db = base.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM equipe WHERE ID="+id,new String[]{});
        cursor.moveToNext();
        return new BDDEquipe(cursor);
    }

    public void addequipe(String nom)
    {
        SQLiteDatabase db = base.getWritableDatabase();
        db.execSQL("INSERT INTO equipe(pseudo,niveau,persoid,inteam,image,used,place) VALUES(" +
                "\""+nom+"\"," +
                "(SELECT NIVEAU FROM PERSONNAGE WHERE PRENOM=\""+nom+"\")," +
                "(SELECT id FROM personnage WHERE PRENOM=\""+nom+"\")," +
                "(CASE WHEN (SELECT COUNT(*) FROM equipe WHERE inteam=1)<(SELECT teamsize FROM COMPTE) THEN 1 ELSE 0 END)," +
                "(SELECT image FROM personnage WHERE PRENOM=\""+nom+"\")," +
                "0," +
                "(CASE WHEN (SELECT COUNT(*) FROM equipe WHERE inteam=1)<(SELECT teamsize FROM COMPTE) THEN (SELECT TEAMSIZE FROM COMPTE) ELSE 0 END))");
    }

    public boolean gotPerso(String s)
    {
        SQLiteDatabase db = base.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM EQUIPE E,PERSONNAGE P WHERE E.PERSOID=P.ID AND P.PRENOM=?",new String[]{s});
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

    public void changePseudo(long id, String pseudo)
    {
        SQLiteDatabase db = base.getWritableDatabase();
        db.execSQL("UPDATE EQUIPE SET PSEUDO = \""+pseudo+"\" WHERE ID = "+id);
    }

    public void switchteam(String s1, String s2)
    {
        System.out.println("SWITCH:"+s1+" <=> "+s2);
        SQLiteDatabase db = base.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT PLACE, (SELECT PLACE FROM EQUIPE WHERE ID="+s2+") FROM EQUIPE WHERE ID="+s1,new String[]{});
        cursor.moveToNext();

        long i = cursor.getLong(0);
        long j = cursor.getLong(1);

        db.execSQL("UPDATE EQUIPE SET PLACE = (CASE " +
                " WHEN ID="+s1+" THEN "+j +
                " WHEN ID="+s2+" THEN "+i +
                " ELSE PLACE " +
                " END)");
    }
    public boolean switchnew(String s1, String s2)
    {
        SQLiteDatabase db = base.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT INTEAM FROM EQUIPE WHERE ID="+s2,new String[]{});
        cursor.moveToNext();

        if(cursor.getLong(0)==1)return false;

        db.execSQL("UPDATE EQUIPE SET INTEAM=1, PLACE=(SELECT PLACE FROM EQUIPE WHERE ID="+s1+") WHERE ID="+s2);
        db.execSQL("UPDATE EQUIPE SET INTEAM=0 WHERE ID="+s1);

        return true;
    }
}

