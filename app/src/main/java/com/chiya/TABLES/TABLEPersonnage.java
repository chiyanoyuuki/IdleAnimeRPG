package com.chiya.TABLES;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDPersonnage;

public class TABLEPersonnage
{
    private BDD base;

    public TABLEPersonnage(BDD base)
    {
        this.base = base;
    }

    public void init(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE personnage (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "prenom TEXTL UNIQUE, " +
                "animeid INTEGER, " +
                "partieid INTEGER, " +
                "image TEXTL, " +
                "niveau INTEGER, " +
                "nom TEXTL, " +
                "sexe TEXTL, " +
                "age TEXTL, " +
                "taille TEXTL, " +
                "traduction TEXTL, " +
                "description TEXTL, " +
                " FOREIGN KEY (animeid)  REFERENCES anime(id)," +
                " FOREIGN KEY (partieid) REFERENCES partie(id));");
    }

    public void addPersonnage(SQLiteDatabase db, int i, int j, String[] personnage)
    {
        ContentValues content = new ContentValues();
        content.put("prenom"       ,personnage[0]);
        content.put("animeid"   ,i);
        content.put("partieid"  ,j);
        content.put("image"     ,"bdd_perso_"+personnage[2]);
        content.put("niveau"    ,personnage[3]);
        content.put("nom",personnage[4]);
        content.put("sexe",personnage[5]);
        content.put("age",personnage[6]);
        content.put("taille",personnage[7]);
        content.put("traduction",personnage[8]);
        content.put("description",personnage[9]);

        db.insert("personnage",null,content);

        db.execSQL("INSERT INTO viewed(id,viewable) VALUES (" +
                "(SELECT id FROM personnage WHERE prenom=\""+personnage[0]+"\")," +
                personnage[1] +
                ")");
    }

    public Cursor selectAllPersonnages(String animeid, String partieid)
    {
        SQLiteDatabase db = base.getReadableDatabase();
        return db.rawQuery("SELECT p.*, (SELECT viewable FROM viewed WHERE id=p.id) as viewable FROM personnage p WHERE p.animeid=? AND p.partieid=? ORDER BY p.niveau DESC",new String[]{animeid,partieid});
    }

    public BDDPersonnage selectPersonnage(String nom)
    {
        SQLiteDatabase db = base.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT p.*, (SELECT viewable FROM viewed WHERE id=p.id) as viewable FROM personnage p WHERE p.prenom=?",new String[]{nom});
        cursor.moveToNext();
        return new BDDPersonnage(cursor);
    }

    public BDDPersonnage selectPersonnage(long id)
    {
        SQLiteDatabase db = base.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT p.*, (SELECT viewable FROM viewed WHERE id=p.id) as viewable FROM personnage p WHERE p.id=?",new String[]{""+id});
        cursor.moveToNext();
        return new BDDPersonnage(cursor);
    }
}
