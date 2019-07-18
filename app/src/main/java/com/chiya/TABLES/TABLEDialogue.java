package com.chiya.TABLES;

import android.content.ContentValues;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDDialogue;

import java.util.ArrayList;

public class TABLEDialogue
{
    private BDD base;
    private int iddialogue;

    public TABLEDialogue(BDD base)
    {
        this.base = base;
    }

    public void init(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE dialogue (" +
                "id INTEGER, " +
                "animeid INTEGER, " +
                "partieid INTEGER, " +
                "ordre INTEGER, " +
                "rmonde INTEGER, " +
                "rpays INTEGER, " +
                "rpartie INTEGER, " +
                "orientation INTEGER, " +
                "image TEXTL, " +
                "texte TEXTL, " +
                "lu INTEGER DEFAULT 0);");
    }

    public void newdialogue(SQLiteDatabase db, int pays, int partie, TypedArray dialogues, Resources resources, String packageName)
    {
        for(int i=0;i<dialogues.length();i++)
        {
            int id = dialogues.getResourceId(i,0);
            if(id>0)
            {
                int plus = 0;
                String[] dialogue = resources.getStringArray(id);
                for(int nb=0;nb<dialogue.length;nb++)
                {
                    String[] dial   = dialogue[nb].split("/");
                    if(dial[3].matches("^@@[0-9a-zA-Z_]+"))
                    {
                        String[] dialogue2  = resources.getStringArray(resources.getIdentifier(dial[3].substring(2),"array",packageName));
                        for(int nb2=0;nb2<dialogue2.length;nb2++)
                        {
                            String[] dial2      = dialogue2[nb2].split("/");
                            addDialogue(db,iddialogue,pays,partie,nb+plus++,dial2,resources,packageName);
                        }
                    }
                    else
                    {
                        addDialogue(db,iddialogue,pays,partie,nb+plus,dial,resources,packageName);
                    }
                }
            }
            iddialogue++;
        }
    }

    public void addDialogue(SQLiteDatabase db, int id, int i, int j, int ordre, String[] dialogue, Resources resources, String packageName)
    {
        String s = dialogue[3];
        while(s.contains("@"))
        {
            int deb = s.indexOf("@");
            String tmp = s.substring(deb+1);
            int fin = deb+tmp.indexOf("@")+1;
            String perso = s.substring(deb+1,fin);
            String newperso = resources.getStringArray(resources.getIdentifier(perso,"array",packageName))[0];
            s = s.substring(0,deb)+newperso+s.substring(fin+1);
        }

        String image = "bdd_perso_"+dialogue[2].toLowerCase();

        String[] r = dialogue[0].split(",");
        String rmonde   = r[0].substring(1);
        String rpays    = r[1];
        String rpartie  = r[2].substring(0,r[2].length()-1);
        ContentValues content = new ContentValues();
        content.put("id",id);
        content.put("animeid",i);
        content.put("partieid",j);
        content.put("ordre",ordre);
        content.put("rmonde",rmonde);
        content.put("rpays",rpays);
        content.put("rpartie",rpartie);
        content.put("orientation",dialogue[1]);
        content.put("image",image);
        content.put("texte",s);
        db.insert("dialogue",null,content);
    }

    public ArrayList<BDDDialogue> selectAllDialogues(String animeid, String partieid)
    {
        String type = "niveaug";
        if(Integer.parseInt(partieid)==1)type="niveaum";
        ArrayList<BDDDialogue> dialogues = new ArrayList<>();
        SQLiteDatabase db = base.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM dialogue d WHERE animeid = ? AND partieid = ? AND " +
                "(rmonde  = -1 OR rmonde  =(SELECT niveau   FROM compte)) AND" +
                "(rpays   = -1 OR rpays   =(SELECT niveau   FROM reputpays      WHERE id=d.animeid)) AND" +
                "(rpartie = -1 OR rpartie =(SELECT (CASE WHEN "+partieid+"=0 THEN NIVEAUG ELSE NIVEAUM END) FROM COMPTE)) AND " +
                " lu = 0 " +
                " ORDER BY id, ordre",new String[]{animeid, partieid});
        while(cursor.moveToNext())
        {
            dialogues.add(new BDDDialogue(cursor));
        }
        cursor.close();
        return dialogues;
    }

    public void readDialogue(String id)
    {
        SQLiteDatabase db = base.getReadableDatabase();
        db.execSQL("UPDATE DIALOGUE SET LU = 1 WHERE ID = "+id);
    }
}
