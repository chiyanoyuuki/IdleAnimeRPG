package com.chiya.BDD;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.*;
import android.util.Log;

import com.chiya.Activities.R;
import com.chiya.TABLES.TABLEAnime;
import com.chiya.TABLES.TABLECompte;
import com.chiya.TABLES.TABLEDialogue;
import com.chiya.TABLES.TABLEEquipe;
import com.chiya.TABLES.TABLEMission;
import com.chiya.TABLES.TABLEMissionsEnCours;
import com.chiya.TABLES.TABLEPartie;
import com.chiya.TABLES.TABLEPersonnage;
import com.chiya.TABLES.TABLEReputLevels;
import com.chiya.TABLES.TABLEReputNoms;
import com.chiya.TABLES.TABLEReputs;
import com.chiya.TABLES.TABLEViewed;

public class BDD extends SQLiteOpenHelper
{
    protected final static int VERSION = 1;
    protected final static String NOM = "IdleAnimeRPG.db";
    private boolean update;
    private Context context;

    private TABLECompte compte;
    private TABLEEquipe equipe;
    private TABLEReputs reputs;
    private TABLEViewed viewed;
    private TABLEMissionsEnCours missionsEnCours;

    private TABLEAnime anime;
    private TABLEPartie partie;
    private TABLEPersonnage personnage;
    private TABLEMission mission;
    private TABLEDialogue dialogue;
    private TABLEReputLevels reputlevels;
    private TABLEReputNoms reputnoms;

    public BDD(Context context)
    {
        super(context, NOM, null, VERSION);
        this.context = context;
        init();
    }

    private void init()
    {
        compte      = new TABLECompte       (this);
        equipe      = new TABLEEquipe       (this);
        reputs      = new TABLEReputs       (this);
        viewed      = new TABLEViewed       (this);
        missionsEnCours = new TABLEMissionsEnCours(this);

        anime       = new TABLEAnime        (this);
        partie      = new TABLEPartie       (this);
        personnage  = new TABLEPersonnage   (this);
        mission     = new TABLEMission      (this);
        dialogue    = new TABLEDialogue     (this);
        reputlevels = new TABLEReputLevels  (this);
        reputnoms   = new TABLEReputNoms    (this);
    }

    public void onCreate(SQLiteDatabase db)
    {
        Log.w("CREATING","Creating Database");
        if(!update){initBDDCompte(db);}
        initBDDInfos(db);
        initBDD(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        update = true;
        Log.w("UPGRADE","Upgrading Version of the Database. V" + oldVersion + " to V" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS anime");
        db.execSQL("DROP TABLE IF EXISTS partie");
        db.execSQL("DROP TABLE IF EXISTS personnage");
        db.execSQL("DROP TABLE IF EXISTS mission");
        db.execSQL("DROP TABLE IF EXISTS dialogue");
        db.execSQL("DROP TABLE IF EXISTS reputslevels");
        db.execSQL("DROP TABLE IF EXISTS reputsnoms");
        onCreate(db);
    }

    private void initBDDCompte(SQLiteDatabase db)
    {
        compte.init(db);
        equipe.init(db);
        reputs.init(db);
        viewed.init(db);
        missionsEnCours.init(db);
    }

    private void initBDDInfos(SQLiteDatabase db)
    {
        anime.init(db);
        partie.init(db);
        personnage.init(db);
        mission.init(db);
        dialogue.init(db);
        reputlevels.init(db);
        reputnoms.init(db);
    }

    private void initBDD(SQLiteDatabase db)
    {
        Resources resources = context.getResources();
        String packageName = context.getPackageName();

        TypedArray dialogues = resources.obtainTypedArray(R.array.dialogues_0);
        dialogue.newdialogue(db,-1,-1,dialogues,resources,packageName);
        dialogues.recycle();

        String[] reputs = resources.getStringArray(R.array.level_reputs);
        reputlevels.newlevelsreputs(db,reputs);

        String[] noms = resources.getStringArray(R.array.level_reput);
        reputnoms.newnomsreputs(db,-1,-1,noms);

        TypedArray animes = resources.obtainTypedArray(R.array.animes);
        for(int i=0;i<animes.length();i++)
        {
            this.reputs.addReputPays(db,i);
            int id = animes.getResourceId(i,0);
            if(id>0)
            {
                String[] anime = resources.getStringArray(id);
                this.anime.addAnime(db,i,anime);

                TypedArray dialoguespays = resources.obtainTypedArray(resources.getIdentifier("dialogues_pays_"+i,"array",packageName));
                dialogue.newdialogue(db,i,-1,dialoguespays,resources,packageName);
                dialoguespays.recycle();

                String[] nomspays = resources.getStringArray(resources.getIdentifier("level_reput_"+i,"array",packageName));
                reputnoms.newnomsreputs(db,i,-1,nomspays);

                TypedArray parties = resources.obtainTypedArray(resources.getIdentifier("parties_"+i,"array",packageName));
                for(int j=0;j<parties.length();j++)
                {
                    int id2 = parties.getResourceId(j,0);
                    if(id2>0){
                        String[] partie = resources.getStringArray(id2);
                        this.partie.addPartie(db,i,j,partie);

                        TypedArray dialoguespartie = resources.obtainTypedArray(resources.getIdentifier("dialogues_partie_"+i+"_"+j,"array",packageName));
                        dialogue.newdialogue(db,i,j,dialoguespartie,resources,packageName);
                        dialoguespartie.recycle();

                        String[] nomsparties = resources.getStringArray(resources.getIdentifier("level_reput_"+i+"_"+j,"array",packageName));
                        reputnoms.newnomsreputs(db,i,j,nomsparties);

                        TypedArray personnages = resources.obtainTypedArray(resources.getIdentifier("personnages_"+i+"_"+j,"array",packageName));
                        for(int k=0;k<personnages.length();k++)
                        {
                            int id3 = personnages.getResourceId(k,0);
                            if(id3>0){
                               String[] personnage = resources.getStringArray(id3);
                               this.personnage.addPersonnage(db,i,j,personnage);
                            }
                        }
                        personnages.recycle();

                        String[] missions = resources.getStringArray(resources.getIdentifier("missions_"+i+"_"+j,"array",packageName));

                        for(String mission:missions)
                        {
                            this.mission.addMission(db,i,j,mission.split("/"),resources,packageName);
                        }
                    }
                }
                parties.recycle();
            }
        }
        animes.recycle();

        if(!update) {compte.create(db);}
    }

    public Cursor selectAll(String table)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + table,new String[]{});
    }

    public TABLECompte compte()             {return compte;}
    public TABLEEquipe equipe()             {return equipe;}
    public TABLEReputs reputs()             {return reputs;}
    public TABLEViewed viewed()             {return viewed;}
    public TABLEMissionsEnCours missionsEnCours(){return missionsEnCours;}

    public TABLEAnime anime()               {return anime;}
    public TABLEPartie partie()             {return partie;}
    public TABLEPersonnage personnage()     {return personnage;}
    public TABLEMission mission()           {return mission;}
    public TABLEDialogue dialogue()         {return dialogue;}
    public TABLEReputLevels reputlevels()   {return reputlevels;}
    public TABLEReputNoms reputnoms()       {return reputnoms;}
}