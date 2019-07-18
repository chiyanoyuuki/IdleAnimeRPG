package com.chiya.TABLES;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDMission;
import com.chiya.BDD.BDDReputs;

public class TABLEReputs
{
    private BDD base;

    public TABLEReputs(BDD base)
    {
        this.base = base;
    }

    public void init(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE reputpays (" +
                "id INTEGER PRIMARY KEY, " +
                "niveau INTEGER DEFAULT 0, " +
                "nombre INTEGER DEFAULT 0);");
    }

    public void addReputPays(SQLiteDatabase db, int i)
    {
        ContentValues content = new ContentValues();
        content.put("id",i);
        db.insert("reputpays",null,content);
    }

    public BDDReputs selectReput(long i, long j)
    {
        String type = "niveaug";
        if(j==1)type="niveaum";
        SQLiteDatabase db = base.getReadableDatabase();
        Cursor cursor;

        if(i!=-1&&j!=-1)cursor =  db.rawQuery("SELECT niveau, (SELECT (CASE WHEN "+j+"=0 THEN NOMBREG ELSE NOMBREM END) FROM COMPTE), partie FROM reputslevels WHERE NIVEAU = (SELECT (CASE WHEN "+j+"=0 THEN NIVEAUG ELSE NIVEAUM END) FROM COMPTE)+1",new String[]{});
        else if(i!=-1)  cursor =  db.rawQuery("SELECT niveau, (SELECT nombre from reputpays where id=?), pays FROM reputslevels WHERE NIVEAU = (SELECT NIVEAU FROM reputpays where id=?)+1",new String[]{""+i,""+i});
        else            cursor =  db.rawQuery("SELECT niveau, (SELECT nombre from compte) as nombre, monde FROM reputslevels WHERE NIVEAU = (SELECT NIVEAU FROM COMPTE)+1",new String[]{});

        cursor.moveToNext();
        return new BDDReputs(cursor);
    }

    public void endMissionReputs(BDDMission mission)
    {
        String niveaup = "niveaug";
        String nombrep = "nombreg";
        if(mission.partieid()==1){niveaup = "niveaum";nombrep = "nombrem";}

        updateReput("NIVEAU", "NOMBRE","COMPTE",   "MONDE",    mission.rmonde(),   "COMPTE.NIVEAU",                "");
        updateReput("NIVEAU", "NOMBRE","REPUTPAYS","PAYS",     mission.rpays(),    "(SELECT NIVEAU FROM REPUTPAYS","WHERE ID="+mission.animeid()+")");
        updateReput(     niveaup,        nombrep,"COMPTE",   "PARTIE",   mission.rpartie(),  "(SELECT "+niveaup+" FROM COMPTE)","");
    }

    public void updateReput(String niv, String nom, String table, String type, long nb, String niveau, String cond)
    {
        SQLiteDatabase db = base.getWritableDatabase();
        String SQL = "(SELECT "+type+" FROM REPUTSLEVELS WHERE NIVEAU = "+niveau+" "+cond+" +1)";
        String exec = "UPDATE "+table+" SET "+
                niv+" = (CASE WHEN "+nom+" + "+nb+" >= "+SQL+" THEN "+niv+"+1 ELSE "+niv+" END), "+
                nom+" = (CASE WHEN "+nom+" + "+nb+" >= "+SQL+" THEN "+nom+"+"+nb+"-"+SQL+" ELSE "+nom+"+"+nb+" END) "+
                (cond.equals("")?cond:cond.substring(0,cond.length()-1));
        db.execSQL(exec);
    }
}
