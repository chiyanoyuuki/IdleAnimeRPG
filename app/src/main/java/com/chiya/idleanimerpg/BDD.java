package com.chiya.idleanimerpg;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.*;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class BDD extends SQLiteOpenHelper
{
    protected final static int VERSION = 1;
    protected final static String NOM = "IdleAnimeRPG.db";
    private boolean update;
    private Context context;
    private int iddialogue;

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
                "nom TEXTL UNIQUE, " +
                "animeid INTEGER, " +
                "partieid INTEGER, " +
                "image TEXTL, " +
                "visible INTEGER, " +
                " FOREIGN KEY (animeid)  REFERENCES anime(id)," +
                " FOREIGN KEY (partieid) REFERENCES partie(id));");

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
                "taillex INTEGER DEFAULT 1, " +
                "tailley INTEGER DEFAULT 1," +
                "lu INTEGER DEFAULT 0);");

        db.execSQL("CREATE TABLE reputslevels (" +
                "niveau INTEGER, " +
                "monde INTEGER, " +
                "pays INTEGER, " +
                "partie INTEGER);");

        db.execSQL("CREATE TABLE reputsnoms (" +
                "animeid INTEGER, " +
                "partieid INTEGER, " +
                "niveau INTEGER, " +
                "nom TEXTL, " +
                "FOREIGN KEY (animeid)  REFERENCES anime(id)," +
                "FOREIGN KEY (partieid) REFERENCES partie(id));");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        update = true;
        Log.w("UPGRADE","Upgrading Version of the Database. V" + oldVersion + " to V" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS anime");
        db.execSQL("DROP TABLE IF EXISTS partie");
        db.execSQL("DROP TABLE IF EXISTS personnage");
        db.execSQL("DROP TABLE IF EXISTS mission");
        db.execSQL("DROP TABLE IF EXISTS reputslevels");
        db.execSQL("DROP TABLE IF EXISTS reputsnoms");
        onCreate(db);
    }

    private void newdialogue(SQLiteDatabase db, int pays, int partie, TypedArray dialogues, Resources resources, String packageName)
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
                            addDialogue(db,iddialogue,pays,partie,nb+plus++,dial2,"bdd_perso_"+dial2[2].toLowerCase(),resources,packageName);
                        }
                    }
                    else
                    {
                        addDialogue(db,iddialogue,pays,partie,nb,dial,"bdd_perso_"+dial[2].toLowerCase(),resources,packageName);
                    }
                }
            }
            iddialogue++;
        }
    }

    private void newlevelsreputs(SQLiteDatabase db, String[] reputs)
    {
        for(int nb=0;nb<reputs.length;nb++)
        {
            String[] tmp = reputs[nb].split("/");
            ContentValues content = new ContentValues();
            content.put("niveau",   nb+1);
            content.put("monde",    tmp[0]);
            content.put("pays",     tmp[1]);
            content.put("partie",   tmp[2]);
            db.insert("reputslevels",null,content);
        }
    }

    private void newnomsreputs(SQLiteDatabase db, int i, int j, String[] noms)
    {
        for(int nb=0;nb<noms.length;nb++)
        {
            ContentValues content = new ContentValues();
            content.put("niveau",   nb+1);
            content.put("animeid",   i);
            content.put("partieid",   j);
            content.put("nom",    noms[0]);
            db.insert("reputsnoms",null,content);
        }
    }

    private void initBDD(SQLiteDatabase db)
    {
        iddialogue = 0;
        Resources resources = context.getResources();
        String packageName = context.getPackageName();

        TypedArray dialogues = resources.obtainTypedArray(R.array.dialogues_0);
        newdialogue(db,-1,-1,dialogues,resources,packageName);
        dialogues.recycle();

        String[] reputs = resources.getStringArray(R.array.level_reputs);
        newlevelsreputs(db,reputs);

        String[] noms = resources.getStringArray(R.array.level_reput);
        newnomsreputs(db,-1,-1,noms);

        TypedArray animes = resources.obtainTypedArray(R.array.animes);
        for(int i=0;i<animes.length();i++)
        {
            addReputPays(db,i);
            int id = animes.getResourceId(i,0);
            if(id>0)
            {
                String[] anime = resources.getStringArray(id);
                addAnime(db,i,anime);

                TypedArray dialoguespays = resources.obtainTypedArray(resources.getIdentifier("dialogues_pays_"+i,"array",packageName));
                newdialogue(db,i,-1,dialoguespays,resources,packageName);
                dialoguespays.recycle();

                String[] nomspays = resources.getStringArray(resources.getIdentifier("level_reput_"+i,"array",packageName));
                newnomsreputs(db,i,-1,nomspays);

                TypedArray parties = resources.obtainTypedArray(resources.getIdentifier("parties_"+i,"array",packageName));
                for(int j=0;j<parties.length();j++)
                {
                    addReputParties(db,i,j);
                    int id2 = parties.getResourceId(j,0);
                    if(id2>0){
                        String[] partie = resources.getStringArray(id2);
                        addPartie(db,i,j,partie);

                        TypedArray dialoguespartie = resources.obtainTypedArray(resources.getIdentifier("dialogues_partie_"+i+"_"+j,"array",packageName));
                        newdialogue(db,i,j,dialoguespartie,resources,packageName);
                        dialoguespartie.recycle();

                        String[] nomsparties = resources.getStringArray(resources.getIdentifier("level_reput_"+i+"_"+j,"array",packageName));
                        newnomsreputs(db,i,j,nomsparties);

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
        content.put("image"     ,"bdd_perso_"+personnage[0].toLowerCase());
        content.put("visible"   ,personnage[1]);
        db.insert("personnage",null,content);
    }

    public void addMission(SQLiteDatabase db, int i, int j, String[] mission)
    {
        String[] r = mission[0].split(",");
        String rmonde   = r[0].substring(1);
        String rpays    = r[1];
        String rpartie  = r[2].substring(0,r[2].length()-1);
        ContentValues content = new ContentValues();
        content.put("rmonde"        ,rmonde);
        content.put("rpays"         ,rpays);
        content.put("rpartie"       ,rpartie);
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

    public void addDialogue(SQLiteDatabase db, int id, int i, int j, int ordre, String[] dialogue, String image, Resources resources, String packageName)
    {
        String s = dialogue[3];
        while(s.contains("@"))
        {
            System.out.println(s);
            int deb = s.indexOf("@");
            String tmp = s.substring(deb+1);
            int fin = deb+tmp.indexOf("@")+1;
            String perso = s.substring(deb+1,fin);
            String newperso = resources.getStringArray(resources.getIdentifier(perso,"array",packageName))[0];
            s = s.substring(0,deb)+newperso+s.substring(fin+1);
        }

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
        if(dialogue.length>4)content.put("taillex",dialogue[4]);
        if(dialogue.length>5)content.put("tailley",dialogue[5]);
        db.insert("dialogue",null,content);
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
        return db.rawQuery("SELECT * FROM mission m WHERE animeid = ? AND partieid = ? AND " +
                "(rmonde  = -1 OR rmonde  =(SELECT niveau FROM compte        WHERE id=1)) AND" +
                "(rpays   = -1 OR rpays   =(SELECT niveau FROM reputpays     WHERE id=m.animeid)) AND" +
                "(rpartie = -1 OR rpartie =(SELECT niveau FROM reputparties  WHERE id=m.partieid AND reputpaysid=m.animeid))",new String[]{animeid, partieid});
    }

    public BDDPersonnage selectPersonnage(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT  * FROM personnage WHERE id=?",new String[]{id});
        cursor.moveToNext();
        return new BDDPersonnage(cursor);
    }

    public ArrayList<BDDDialogue> selectAllDialogues(String animeid, String partieid)
    {
        ArrayList<BDDDialogue> dialogues = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM dialogue d WHERE animeid = ? AND partieid = ? AND " +
                "(rmonde  = -1 OR rmonde  =(SELECT niveau FROM compte        WHERE id=1)) AND" +
                "(rpays   = -1 OR rpays   =(SELECT niveau FROM reputpays     WHERE id=d.animeid)) AND" +
                "(rpartie = -1 OR rpartie =(SELECT niveau FROM reputparties  WHERE id=d.partieid AND reputpaysid=d.animeid)) AND" +
                " lu = 0 " +
                " ORDER BY id, ordre",new String[]{animeid, partieid});
        while(cursor.moveToNext())
        {
            dialogues.add(new BDDDialogue(cursor));
        }
        cursor.close();
        return dialogues;
    }

    public BDDPersonnage selectPersonnage(String animeid, String partieid, String nom)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT  * FROM personnage WHERE animeid=? AND partieid=? AND nom=?",new String[]{animeid,partieid,nom});
        cursor.moveToNext();
        return new BDDPersonnage(cursor);
    }

    public BDDReputs selectReput(long i, long j)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        if(i!=-1&&j!=-1)cursor =  db.rawQuery("SELECT niveau, (SELECT nombre FROM reputparties where id=? and reputpaysid=?), partie FROM reputslevels WHERE NIVEAU = (SELECT NIVEAU FROM reputparties where id=? and reputpaysid=?)+1",new String[]{""+j,""+i,""+j,""+i});
        else if(i!=-1)  cursor =  db.rawQuery("SELECT niveau, (SELECT nombre from reputpays where id=?), pays FROM reputslevels WHERE NIVEAU = (SELECT NIVEAU FROM reputpays where id=?)+1",new String[]{""+i,""+i});
        else            cursor =  db.rawQuery("SELECT niveau, (SELECT nombre from compte) as nombre, monde FROM reputslevels WHERE NIVEAU = (SELECT NIVEAU FROM COMPTE)+1",new String[]{});

        cursor.moveToNext();
        //cursor.moveToNext();
        return new BDDReputs(cursor);
    }

    //////////////////////////////////////////////

    public void readDialogue(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues content = new ContentValues();
        content.put("lu", 1);
        db.update("dialogue", content, " id = ?", new String[] {id});
    }

    public void finishMission(BDDMission mission, BDDReputs monde, BDDReputs pays, BDDReputs partie)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        long nombre;

        ContentValues content = new ContentValues();
        nombre = monde.actual()+mission.rmonde();
        if(nombre>=monde.need())
        {
            nombre=nombre-monde.need();
            content.put("niveau", monde.niveau());
        }
        content.put("nombre", nombre);
        db.update("compte", content, " id = ?", new String[]{"1"});

        content = new ContentValues();
        nombre = pays.actual()+mission.rpays();
        if(nombre>=pays.need())
        {
            nombre=nombre-pays.need();
            content.put("niveau", pays.niveau());
        }
        content.put("nombre", nombre);
        db.update("reputpays", content, " id = ?", new String[]{"" + mission.animeid()});

        content = new ContentValues();
        nombre = partie.actual()+mission.rpartie();
        if(nombre>=partie.need())
        {
            nombre=nombre-partie.need();
            content.put("niveau", partie.niveau());
        }
        content.put("nombre", nombre);
        db.update("reputparties", content, " id = ? and reputpaysid = ?", new String[]{"" + mission.partieid(), "" + mission.animeid()});
    }

    public void changepseudo(String pseudo)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues content = new ContentValues();
        content.put("pseudo", pseudo);
        content.put("started",1);
        db.update("compte", content, " id = ?", new String[] {"1"});
    }
}