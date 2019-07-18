package com.chiya.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDAnime;
import com.chiya.BDD.BDDEquipe;
import com.chiya.BDD.BDDMission;
import com.chiya.BDD.BDDPartie;
import com.chiya.Layouts.FrameLayoutForeground;
import com.chiya.Layouts.LinearLayoutAnime;
import com.chiya.Layouts.LinearLayoutMissions;
import com.chiya.Layouts.ScrollViewMonde;
import com.chiya.idleanimerpg.Dialogue;

public class Master extends Activity
{
    private long anime = -1, partie = -1;

    private BDD db;
    private String packageName;
    private FrameLayout background, top, bot;
    private FrameLayoutForeground foreground;
    private LinearLayout layout;
    private Dialogue dialogue;
    private String actual;

    private FrameLayout mid;

    private ScrollViewMonde svm;
    private LinearLayoutAnime lla;
    private LinearLayoutMissions missions;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_main);
        init();
    }

    private void init()
    {
        db = new BDD(this);
        foreground = new FrameLayoutForeground(this);
        dialogue = new Dialogue(this);
        dialogue.reinit(db.dialogue().selectAllDialogues(""+-1,""+-1));
        packageName = this.getPackageName();
        background = findViewById(R.id.accueil_topecran);
        addBackground();
        addForeground();
        background.addView(dialogue);
    }

    private void addBackground()
    {
        layout = new LinearLayout(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        top = new FrameLayout(this);
        //top.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, foreground.getTopSize()));
        layout.addView(top);


        mid = new FrameLayout(this);
        mid.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1));

        svm = new ScrollViewMonde(this);
        actual = "monde";

        mid.addView(svm);
        layout.addView(mid);

        bot = new FrameLayout(this);
        bot.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, foreground.getBotSize()+110));
        layout.addView(bot);

        background.addView(layout);


    }

    public void changeEcran(String ecran)
    {
        if(ecran.equals("accueil"))
        {
            refresh(-1,-1);
            svm = new ScrollViewMonde(this);
            mid.removeAllViews();
            System.gc();
            mid.addView(svm);
        }
    }

    public void changeEcran(long anime, long partie)
    {
        refresh(anime,partie);
        if(anime==-1&&partie==-1)
        {
            wipe();
            svm = new ScrollViewMonde(this);
            changeView("monde");
        }
        else if(anime!=-1&&partie==-1)
        {
            wipe();
            BDDAnime bddanime = db.anime().select(anime);
            lla = new LinearLayoutAnime(this,bddanime);
            changeView("anime");
        }
        else if(anime!=-1&&partie!=-1)
        {
            wipe();
            BDDAnime bddanime = db.anime().select(anime);
            BDDPartie bddpartie = db.partie().select(partie);
            missions = new LinearLayoutMissions(this,bddanime,bddpartie);
            changeView("missions");
        }
    }

    private void wipe()
    {
        if(actual.equals("monde"))           {svm.wipe();svm=null;}
        else if(actual.equals("anime"))      {lla.wipe();lla=null;}
        else if(actual.equals("missions"))   {missions.removeAllViews();missions=null;}
    }

    private void changeView(String s)
    {
        mid.removeAllViews();
        if(s.equals("monde"))           mid.addView(svm);
        else if(s.equals("anime"))      mid.addView(lla);
        else if(s.equals("missions"))   mid.addView(missions);
        actual = s;
    }

    public void refresh(long anime, long partie)
    {
        this.anime = anime;
        this.partie = partie;
        foreground.refreshBarres(anime,partie);
        dialogue.reinit(db.dialogue().selectAllDialogues(""+anime,""+partie));
    }

    public void finishMission(BDDMission bddmission, long anime, long partie)
    {
        recompense(anime,partie);
        db.reputs().endMissionReputs(bddmission);
        refresh(anime,partie);
        missions.refresh();
    }

    public void refreshMuraille()
    {
        foreground.refreshMuraille();
    }

    public BDDEquipe getFirstPerso()
    {
        return foreground.getFirstPerso();
    }

    public void refreshAcc()
    {
       // foreground.refreshAcc();
    }

    private void recompense(long anime, long partie) {
        int h = 20;
        String image = "icone_reputgentil";
        if(partie==1) image = "icone_reputmechant";
        for (int i = 0; i < 100; i++) {
            if (i == 59) {
                image = "icone_reputpays";
                h = 10;
            }
            if (i == 89) {
                image = "icone_reputmonde";
                h = 0;
            }
            final ImageView tmp = new ImageView(this);
            tmp.setImageResource(super.getResources().getIdentifier(image, "drawable", packageName));
            tmp.setLayoutParams(new LinearLayout.LayoutParams(40, 40));
            tmp.setX(0 + (int) (Math.random() * 1000));
            tmp.setY(600 + (int) (Math.random() * 800));

            background.addView(tmp);
            Animation animation = new TranslateAnimation(0, -tmp.getX() - 40, 0, -tmp.getY() + 135 + h);
            animation.setDuration(1500);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    background.removeView(tmp);
                }
            });
            animation.setFillAfter(true);
            tmp.setAnimation(animation);
        }
    }

    private void addForeground()
    {
        background.addView(foreground);
    }
    public BDD getDb(){return db;}
    @Override
    public void onBackPressed(){}
}
