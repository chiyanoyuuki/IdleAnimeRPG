package com.chiya.TABLES;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDEquipe;
import com.chiya.BDD.BDDMission;
import com.chiya.BDD.BDDMissionEnCours;

public class TABLEMissionsEnCours
{
    private BDD base;

    public TABLEMissionsEnCours(BDD base)
    {
        this.base = base;
    }

    public void init(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE missionsencours      (" +
                "id INTEGER PRIMARY KEY, " +
                "start TEXTL, " +
                "equipeid INTEGER, " +
                "bonus TEXTL, " +
                "jumpanime INTEGER, " +
                "jumppartie INTEGER, " +
                "jumpstate TEXTL );");
    }

    public BDDMissionEnCours select(long id)
    {
        SQLiteDatabase db = base.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM MISSIONSENCOURS WHERE ID="+id,new String[]{});
        cursor.moveToNext();
        BDDMissionEnCours mission = new BDDMissionEnCours(cursor);
        cursor.close();
        return mission;
    }

    public void add(BDDMission mission, String start, BDDEquipe perso, long anime, long partie,String state)
    {
        SQLiteDatabase db = base.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("id",mission.id());
        content.put("start",start);
        content.put("equipeid",perso.id());
        content.put("bonus","");
        content.put("jumpanime",anime);
        content.put("jumppartie",partie);
        content.put("jumpstate",state);
        db.insert("missionsencours",null,content);
    }

    public void remove(BDDMission mission)
    {
        SQLiteDatabase db = base.getWritableDatabase();
        db.execSQL("UPDATE EQUIPE SET USED = 0 WHERE ID = (SELECT EQUIPEID FROM MISSIONSENCOURS WHERE ID="+mission.id()+")");
        db.execSQL("DELETE FROM MISSIONSENCOURS WHERE ID="+mission.id());
    }

    public String from(BDDEquipe perso)
    {
        SQLiteDatabase db = base.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT JUMPANIME, JUMPPARTIE, JUMPSTATE FROM MISSIONSENCOURS WHERE EQUIPEID="+perso.id(),new String[]{});
        cursor.moveToNext();
        return cursor.getLong(0)+":"+cursor.getLong(1)+":"+cursor.getString(2);
    }
}
