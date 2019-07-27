package com.chiya.Activities;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDEquipe;
import com.chiya.BDD.BDDMission;
import com.chiya.Fragments.Anime;
import com.chiya.Fragments.Missions;
import com.chiya.Fragments.Monde;
import com.chiya.Layouts.Equipe;
import com.chiya.Layouts.LayoutDialogue;
import com.chiya.Layouts.LayoutForeground;
import com.chiya.Layouts.Stats;
import com.chiya.Layouts.ViewPerso;
import com.chiya.idleanimerpg.Up;

public class TestActivityFragment extends FragmentActivity
{
    private Stats stats;
    private Equipe equipe;
    private ViewPerso viewperso;
    private Up up;
    private String tag, packageName;
    private BDD db;
    private LayoutForeground foreground;
    private Fragment fragment;
    private long anime, partie;
    private FrameLayout mid, ecran, front;
    private LayoutDialogue dialogue;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.accueil);

        init();
    }

    private void init()
    {
        anime=-1;partie=-1;
        db = new BDD(this);
        viewperso = new ViewPerso(this);
        equipe = new Equipe(this);
        stats = new Stats(this);
        foreground = new LayoutForeground(this);
        this.packageName = getPackageName();
        mid = findViewById(R.id.accueil_fragm);
        ecran = findViewById(R.id.accueil_mainlayout);
        front = findViewById(R.id.accueil_front);
        dialogue = new LayoutDialogue(this);
        dialogue.set(db.dialogue().selectAllDialogues(""+-1,""+-1));

        fragment = new Monde();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.accueil_fragm, fragment);
        ft.commit();


        up = new Up(this);
        up.refresh();

        //ecran.addView(viewperso.layout());
        front.addView(stats.layout());
        front.addView(equipe.layout());
        front.addView(viewperso.layout());
        ecran.addView(dialogue.layout());
        ecran.addView(up.layout());
    }

    public void changeEcran(String s)
    {
         if(s.equals("accueil")&&(anime!=-1||viewperso.isVisible()||equipe.isVisible()||stats.isVisible()))
         {
             remove();
             fragment = new Monde();
             change(-1,-1,"");
             viewperso.invis();
             stats.invis();
             equipe.invis();
         }
         else if(s.equals("equipe")&&!equipe.isVisible())
         {
             stats.invis();
             viewperso.invis();
             equipe.init();
         }
         else if(s.equals("stats")&&!stats.isVisible())
         {
             equipe.invis();
             viewperso.invis();
             stats.init();
         }
    }

    public void changeEcran(long anime, long partie)
    {
        if(this.anime!=anime||this.partie!=partie)
        {
            if(anime!=-1&&partie==-1)
            {
                remove();
                fragment = new Anime();
                change(anime,partie,"");
            }
            else if(anime!=-1&&partie!=-1)
            {
                remove();
                fragment = new Missions();
                change(anime,partie,"histoire");
            }
        }
    }

    public void changeEcran(long anime, long partie, String tag)
    {
        if(this.anime!=anime||this.partie!=partie||!tag.equals(this.tag))
        {
            if(anime!=-1&&partie!=-1)
            {
                remove();
                fragment = new Missions();
                change(anime,partie,tag);
            }
        }
    }

    private void remove()
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(fragment).commit();
    }

    private void change(long anime, long partie, String tag)
    {
        this.anime = anime;
        this.partie = partie;
        this.tag = tag;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.accueil_fragm, fragment).commit();
        refresh(anime,partie);
    }

    private void refresh(long anime, long partie)
    {
        foreground.refreshBarres();
        dialogue.set(db.dialogue().selectAllDialogues(""+anime,""+partie));
    }

    private void recompense()
    {
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

            mid.addView(tmp);
            Animation animation = new TranslateAnimation(0, -tmp.getX() - 40, 0, -tmp.getY() - 100 + h);
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
                    mid.removeView(tmp);
                }
            });
            animation.setFillAfter(true);
            tmp.setAnimation(animation);
        }
    }

    public void showPerso(BDDEquipe perso)
    {
        viewperso.view(perso);
    }
    public void showPerso(String s)
    {
        viewperso.view(s);
    }

    public void finishMission(BDDMission bddmission)
    {
        recompense();
        db.up().endMissionReputs(bddmission);
        up.refresh();
        refresh(anime,partie);
    }

    public void changePersoPseudo(long id, String pseudo)
    {
        db.equipe().changePseudo(id,pseudo);
        foreground.refreshMuraille();
    }

    public BDD getDb()              {return db;}
    public long getAnime()          {return anime;}
    public long getPartie()         {return partie;}
    public String getState()        {return tag;}
    public void refreshMuraille()   {foreground.refreshMuraille();}
    public BDDEquipe getFirstPerso(){return foreground.getFirstPerso();}
    public void refreshAcc()        {foreground.refreshAcc();}

    @Override
    public void onBackPressed(){}
}