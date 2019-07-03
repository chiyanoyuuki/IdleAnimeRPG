package com.chiya.idleanimerpg;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.*;
import android.util.Log;
import java.util.Arrays;

public class BDD extends SQLiteOpenHelper
{
    protected final static int VERSION = 1;
    protected final static String NOM = "IdleAnimeRPG.db";
    private boolean update;
    private Context context;

    public BDD(Context context)
    {
        super(context, NOM, null, VERSION);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db)
    {
        Log.w("CREATING","Creating Database");

        if(!update)
        {
            initBDDCompte(db);
        }

        db.execSQL("DROP TABLE IF EXISTS anime");
        db.execSQL("DROP TABLE IF EXISTS partie");
        db.execSQL("DROP TABLE IF EXISTS personnage");
        db.execSQL("DROP TABLE IF EXISTS mission");

        initBDDInfos(db);
        initBDD(db);
    }

    private void initBDDCompte(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE compte     (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pseudo TEXTL, " +
                "persoid INTEGER    DEFAULT 0, " +
                "ressources INTEGER DEFAULT 10, " +
                "started INTEGER    DEFAULT 0," +
                "niveau INTEGER     DEFAULT 0, " +
                "nombre INTEGER     DEFAULT 0, " +
                " FOREIGN KEY (persoid) REFERENCES personnage(id));");

        db.execSQL("CREATE TABLE equipe     (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pseudo TEXTL, " +
                "niveau INTEGER, " +
                "persoid INTEGER," +
                " FOREIGN KEY (persoid)  REFERENCES personnage(id));");

        db.execSQL("CREATE TABLE reputpays (" +
                "id INTEGER PRIMARY KEY, " +
                "niveau INTEGER DEFAULT 0, " +
                "nombre INTEGER DEFAULT 0);");

        db.execSQL("CREATE TABLE reputparties (" +
                "id INTEGER, " +
                "reputpaysid INTEGER, " +
                "niveau INTEGER DEFAULT 0, " +
                "nombre INTEGER DEFAULT 0, " +
                "PRIMARY KEY (id,reputpaysid), " +
                "FOREIGN KEY (reputpaysid) REFERENCES reputpays(id));");
    }

    private void initBDDInfos(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE anime      (" +
                "id INTEGER PRIMARY KEY, " +
                "nom TEXTL, " +
                "image TEXTL, " +
                "isup INTEGER, " +
                "partie TEXTL, " +
                "type TEXTL);");

        db.execSQL("CREATE TABLE partie     (" +
                "id INTEGER, " +
                "nom TEXTL, " +
                "animeid INTEGER, " +
                "image TEXTL, " +
                "PRIMARY KEY (id,animeid),  " +
                "FOREIGN KEY (animeid)  REFERENCES anime(id));");

        db.execSQL("CREATE TABLE personnage (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nom TEXTL, " +
                "animeid INTEGER, " +
                "partieid INTEGER, " +
                "image TEXTL, " +
                "visible INTEGER, " +
                " FOREIGN KEY (animeid)  REFERENCES anime(id)," +
                " FOREIGN KEY (partieid) REFERENCES partie(id));");

        db.execSQL("CREATE TABLE mission (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "animeid INTEGER, " +
                "partieid INTEGER, " +
                "image TEXTL, " +
                "nom TEXTL, " +
                "reput INTEGER, " +
                "temps INTEGER, " +
                "reputmonde INTEGER, " +
                "reputpays INTEGER, " +
                "reputpartie INTEGER, " +
                " FOREIGN KEY (animeid)  REFERENCES anime(id)," +
                " FOREIGN KEY (partieid) REFERENCES partie(id));");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        update = true;
        Log.w("UPGRADE","Upgrading Version of the Database. V" + oldVersion + " to V" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS anime");
        db.execSQL("DROP TABLE IF EXISTS partie");
        db.execSQL("DROP TABLE IF EXISTS personnage");
        db.execSQL("DROP TABLE IF EXISTS mission");
        onCreate(db);
    }

    private void initBDD(SQLiteDatabase db)
    {
        Resources resources = context.getResources();
        String packageName = context.getPackageName();

        TypedArray animes = resources.obtainTypedArray(R.array.animes);
        for(int i=0;i<animes.length();i++)
        {
            addReputPays(db,i);
            int id = animes.getResourceId(i,0);
            if(id>0)
            {
                String[] anime = resources.getStringArray(id);
                addAnime(db,i,anime);

                TypedArray parties = resources.obtainTypedArray(resources.getIdentifier("parties_"+i,"array",packageName));
                for(int j=0;j<parties.length();j++)
                {
                    addReputParties(db,i,j);
                    int id2 = parties.getResourceId(j,0);
                    if(id2>0){
                        String[] partie = resources.getStringArray(id2);
                        addPartie(db,i,j,partie);

                        TypedArray personnages = resources.obtainTypedArray(resources.getIdentifier("personnages_"+i+"_"+j,"array",packageName));
                        for(int k=0;k<personnages.length();k++)
                        {
                            int id3 = personnages.getResourceId(k,0);
                            if(id3>0){
                               String[] personnage = resources.getStringArray(id3);
                               addPersonnage(db,i,j,personnage);
                            }
                        }
                        personnages.recycle();

                        String[] missions = resources.getStringArray(resources.getIdentifier("missions_"+i+"_"+j,"array",packageName));

                        for(String mission:missions)
                        {
                            addMission(db,i,j,mission.split("/"));
                        }
                    }
                }
                parties.recycle();
            }
        }
        animes.recycle();

        if(!update) {
            ContentValues content = new ContentValues();
            content.put("pseudo"    ,"Nouveau joueur");
            content.put("persoid"   ,0);
            content.put("ressources",10);
            content.put("started"   ,0);
            db.insert("compte",null,content);

            ContentValues equipe = new ContentValues();
            equipe.put("niveau",1);
            equipe.put("persoid",0);
            db.insert("equipe",null,equipe);
        }
    }

    public void addAnime(SQLiteDatabase db, int i, String[] anime)
    {
        //System.out.println(Arrays.toString(anime));
        ContentValues content = new ContentValues();
        content.put("id"        ,i);
        content.put("nom"       ,anime[0]);
        content.put("image"     ,anime[1]);
        content.put("isup"      ,anime[2]);
        content.put("partie"    ,anime[3]);
        content.put("type"      ,anime[4]);
        db.insert("anime",null,content);
    }

    public void addPartie(SQLiteDatabase db, int i, int j, String[] partie)
    {
        //System.out.println(Arrays.toString(partie));
        ContentValues content = new ContentValues();
        content.put("id"        ,j);
        content.put("nom"       ,partie[0]);
        content.put("animeid"   ,i);
        content.put("image"     ,partie[1]);
        db.insert("partie",null,content);
    }

    public void addPersonnage(SQLiteDatabase db, int i, int j, String[] personnage)
    {
        //System.out.println(Arrays.toString(personnage));
        ContentValues content = new ContentValues();
        if(i==0&&j==1&&personnage[0].equals("Okori"))content.put("id",0);
        content.put("nom"       ,personnage[0]);
        content.put("animeid"   ,i);
        content.put("partieid"  ,j);
        content.put("image"     ,"bdd_perso_"+i+"_"+j+"_"+personnage[0].toLowerCase());
        content.put("visible"   ,personnage[1]);
        db.insert("personnage",null,content);
    }

    public void addMission(SQLiteDatabase db, int i, int j, String[] mission)
    {
        System.out.println(Arrays.toString(mission));
        ContentValues content = new ContentValues();
        content.put("reput"         ,mission[0]);
        content.put("nom"           ,mission[1]);
        content.put("temps"         ,mission[2]);
        content.put("image"         ,mission[3]);
        content.put("reputmonde"    ,mission[4]);
        content.put("reputpays"     ,mission[5]);
        content.put("reputpartie"   ,mission[6]);
        content.put("animeid"       ,i);
        content.put("partieid"      ,j);
        db.insert("mission",null,content);
    }

    public void addReputPays(SQLiteDatabase db, int i)
    {
        ContentValues content = new ContentValues();
        content.put("id",i);
        db.insert("reputpays",null,content);
    }

    public void addReputParties(SQLiteDatabase db, int i, int j)
    {
        ContentValues content = new ContentValues();
        content.put("id",j);
        content.put("reputpaysid",i);
        db.insert("reputparties",null,content);
    }

    public Cursor selectAll(String table)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + table,new String[]{});
    }

    public BDDAnime selectAnime(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM anime WHERE id = ?",new String[]{id});
        cursor.moveToNext();
        return new BDDAnime(cursor);
    }

    public Cursor selectAllParties(String animeid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM partie WHERE animeid = ?",new String[]{animeid});
    }

    public BDDPartie selectPartie(String animeid, String partieid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM partie WHERE animeid = ? AND id = ?",new String[]{animeid,partieid});
        cursor.moveToNext();
        return new BDDPartie(cursor);
    }

    public BDDCompte selectCompte()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM compte",new String[]{});
        cursor.moveToNext();
        return new BDDCompte(cursor);
    }

    //PERSONNAGES

    public Cursor selectAllPersonnages(String animeid, String partieid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT  * FROM personnage WHERE animeid=? AND partieid=?",new String[]{animeid,partieid});
    }

    public Cursor selectAllMissions(String animeid, String partieid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT  * FROM mission m, reputparties r WHERE m.animeid=? AND m.partieid=? AND r.niveau=m.reput",new String[]{animeid,partieid});
    }

    public BDDPersonnage selectPersonnage(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT  * FROM personnage WHERE id=?",new String[]{id});
        cursor.moveToNext();
        return new BDDPersonnage(cursor);
    }

    public BDDPersonnage selectPersonnage(String animeid, String partieid, String nom)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT  * FROM personnage WHERE animeid=? AND partieid=? AND nom=?",new String[]{animeid,partieid,nom});
        cursor.moveToNext();
        return new BDDPersonnage(cursor);
    }


}