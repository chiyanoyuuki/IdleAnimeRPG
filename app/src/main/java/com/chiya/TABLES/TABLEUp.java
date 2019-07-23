package com.chiya.TABLES;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDMission;

import java.util.ArrayList;

public class TABLEUp {

    private BDD base;

    public TABLEUp(BDD base)
    {
        this.base = base;
    }

    public void init(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE up (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "animeid INTEGER, " +
                "partieid INTEGER, " +
                "niveau INTEGER" +
                ");");
    }

    public void endMissionReputs(BDDMission mission)
    {
        SQLiteDatabase db = base.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " +
                "(SELECT MONDE  FROM REPUTSLEVELS WHERE NIVEAU = (SELECT NIVEAU+1 FROM COMPTE))-nombre AS MONDE, " +
                "(SELECT PAYS   FROM REPUTSLEVELS WHERE NIVEAU = (SELECT NIVEAU+1 FROM REPUTPAYS WHERE ID = "+mission.animeid()+"))-(SELECT NOMBRE FROM REPUTPAYS WHERE ID = "+mission.animeid()+") AS PAYS, " +
                "(SELECT PARTIE FROM REPUTSLEVELS WHERE NIVEAU = (SELECT NIVEAUG+1 FROM COMPTE)) -NOMBREG AS GENTIL, " +
                "(SELECT PARTIE FROM REPUTSLEVELS WHERE NIVEAU = (SELECT NIVEAUM+1 FROM COMPTE)) -NOMBREM AS MECHANT " +
                "FROM COMPTE ",new String[]{});

        cursor.moveToNext();

        long monde = mission.rmonde();
        long pays = mission.rpays();
        long partie = mission.rpartie();

        long needmonde      = cursor.getLong(0);
        long needpays       = cursor.getLong(1);
        long needpartie     = cursor.getLong(2);
        if(mission.partieid()==1)needpartie     = cursor.getLong(3);

        cursor.close();

        if(monde>=needmonde)
        {
            long now = monde - needmonde;
            db.execSQL("UPDATE COMPTE SET NIVEAU = NIVEAU + 1 AND NOMBRE = "+now);
            db.execSQL("INSERT INTO UP(animeid,partieid,niveau) VALUES(-1,-1,(SELECT NIVEAU FROM COMPTE))");
        }
        else
        {
            db.execSQL("UPDATE COMPTE SET NOMBRE = NOMBRE+"+monde);
        }

        if(pays>=needpays)
        {
            long now = pays - needpays;
            db.execSQL("UPDATE REPUTPAYS SET NIVEAU = NIVEAU + 1 AND NOMBRE = "+now+" WHERE ID = "+mission.animeid());
            db.execSQL("INSERT INTO UP(animeid,partieid,niveau) VALUES("+mission.animeid()+",-1,(SELECT NIVEAU FROM REPUTPAYS WHERE ID = "+mission.animeid()+"))");
        }
        else
        {
            db.execSQL("UPDATE REPUTPAYS SET NOMBRE = NOMBRE+"+pays+" WHERE ID = "+mission.animeid());
        }

        if(partie>=needpartie)
        {
            long now = partie - needpartie;
            if(mission.partieid()==0)
            {
                db.execSQL("UPDATE COMPTE SET NIVEAUG = NIVEAUG + 1 AND NOMBREG = "+now);
                db.execSQL("INSERT INTO UP(animeid,partieid,niveau) VALUES("+mission.animeid()+","+mission.partieid()+",(SELECT NIVEAUG FROM COMPTE))");
            }
            else if(mission.partieid()==1)
            {
                db.execSQL("UPDATE COMPTE SET NIVEAUM = NIVEAUM + 1 AND NOMBREM = "+now);
                db.execSQL("INSERT INTO UP(animeid,partieid,niveau) VALUES("+mission.animeid()+","+mission.partieid()+",(SELECT NIVEAUM FROM COMPTE))");
            }
        }
        else
        {
            if(mission.partieid()==0)       db.execSQL("UPDATE COMPTE SET NOMBREG = NOMBREG+"+partie);
            else if(mission.partieid()==1)  db.execSQL("UPDATE COMPTE SET NOMBREM = NOMBREM+"+partie);
        }
    }

    public ArrayList<String[]> select()
    {
        ArrayList<String[]> retour = new ArrayList<>();
        SQLiteDatabase db = base.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT R.* FROM UP U, REPUTSNOMS R WHERE U.ANIMEID = R.ANIMEID AND U.PARTIEID = R.PARTIEID AND U.NIVEAU = R.NIVEAU",new String[]{});
        while(cursor.moveToNext())
        {
            retour.add((cursor.getLong(0)+":"+cursor.getLong(1)+":"+cursor.getLong(2)+":"+cursor.getString(3)+":"+cursor.getString(4)+":"+cursor.getString(5)).split(":"));
        }
        return retour;
    }

    public void remove(String[] tmp)
    {
        SQLiteDatabase db = base.getWritableDatabase();
        db.execSQL("DELETE FROM UP WHERE ANIMEID="+tmp[0]+" AND PARTIEID="+tmp[1]+" AND NIVEAU="+tmp[2]);
    }
}
