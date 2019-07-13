package com.chiya.idleanimerpg;

import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TABLEMission
{
    private BDD base;

    public TABLEMission(BDD base)
    {
        this.base = base;
    }

    public void init(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE mission (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "rmonde INTEGER, " +
                "rpays INTEGER, " +
                "rpartie INTEGER, " +
                "animeid INTEGER, " +
                "partieid INTEGER, " +
                "image TEXTL, " +
                "nom TEXTL, " +
                "temps INTEGER, " +
                "reputmonde INTEGER, " +
                "reputpays INTEGER, " +
                "reputpartie INTEGER, " +
                " FOREIGN KEY (animeid)  REFERENCES anime(id)," +
                " FOREIGN KEY (partieid) REFERENCES partie(id));");
    }

    public void addMission(SQLiteDatabase db, int i, int j, String[] mission, Resources resources, String packageName)
    {
        String s = mission[1];
        while(s.contains("@"))
        {
            int deb = s.indexOf("@");
            String tmp = s.substring(deb+1);
            int fin = deb+tmp.indexOf("@")+1;
            String perso = s.substring(deb+1,fin);
            String newperso = resources.getStringArray(resources.getIdentifier(perso,"array",packageName))[0];
            s = s.substring(0,deb)+newperso+s.substring(fin+1);
        }

        String[] r = mission[0].split(",");
        String rmonde   = r[0].substring(1);
        String rpays    = r[1];
        String rpartie  = r[2].substring(0,r[2].length()-1);
        ContentValues content = new ContentValues();
        content.put("rmonde"        ,rmonde);
        content.put("rpays"         ,rpays);
        content.put("rpartie"       ,rpartie);
        content.put("nom"           ,s);
        content.put("temps"         ,mission[2]);
        content.put("image"         ,mission[3]);
        content.put("reputmonde"    ,mission[4]);
        content.put("reputpays"     ,mission[5]);
        content.put("reputpartie"   ,mission[6]);
        content.put("animeid"       ,i);
        content.put("partieid"      ,j);
        db.insert("mission",null,content);
    }

    public Cursor selectAllMissions(String animeid, String partieid)
    {
        String type = "niveaug";
        if(Integer.parseInt(partieid)==1)type="niveaum";
        SQLiteDatabase db = base.getReadableDatabase();
        return db.rawQuery("SELECT * FROM mission m WHERE animeid = ? AND partieid = ? AND " +
                        "(rmonde  = -1 OR rmonde  =(SELECT niveau FROM compte)) AND" +
                        "(rpays   = -1 OR rpays   =(SELECT niveau FROM reputpays        WHERE id=m.animeid)) AND" +
                        "(rpartie = -1 OR rpartie =(SELECT (CASE WHEN "+partieid+"=0 THEN NIVEAUG ELSE NIVEAUM END) FROM COMPTE))"
                ,new String[]{animeid, partieid});
    }
}
