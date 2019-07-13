package com.chiya.idleanimerpg;

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

public class Master extends Activity
{
    private BDD db;
    private String packageName;
    private FrameLayout background, top, bot;
    private FrameLayoutForeground foreground;
    private LinearLayout layout;
    private Dialogue dialogue;
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
        background = findViewById(R.id.mainlayout);
        addBackground();
        addForeground();
        background.addView(dialogue);
    }

    private void addBackground()
    {
        layout = new LinearLayout(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        bot = new FrameLayout(this);
        bot.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, foreground.getTopSize()));
        layout.addView(bot);

        layout.addView(new ScrollViewMonde(this));

        top = new FrameLayout(this);
        top.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, foreground.getBotSize()+110));
        layout.addView(top);

        background.addView(layout);
    }

    public void changeEcran(String ecran)
    {
        refresh(-1,-1);
        if(ecran.equals("accueil")){layout.removeViewAt(1);layout.addView(new ScrollViewMonde(this),1);}
    }

    public void changeEcran(BDDAnime anime)
    {
        refresh(anime.id(),-1);
        layout.removeViewAt(1);
        layout.addView(new LinearLayoutAnime(this,anime),1);
    }

    public void changeEcran(BDDAnime anime, BDDPartie partie)
    {
        refresh(anime.id(),partie.id());
        layout.removeViewAt(1);
        missions = new LinearLayoutMissions(this,anime,partie);
        layout.addView(missions,1);
    }

    public void refresh(long anime, long partie)
    {
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

    public void refreshAcc()
    {
        foreground.refreshAcc();
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
