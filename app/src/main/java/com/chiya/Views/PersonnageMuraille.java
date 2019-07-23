package com.chiya.Views;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chiya.Activities.R;
import com.chiya.Activities.TestActivityFragment;
import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDEquipe;

import java.util.Arrays;

public class PersonnageMuraille implements Animation.AnimationListener, View.OnClickListener
{
    private long startTime, time;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            long millis = time-(System.currentTimeMillis() - startTime);
            int seconds = 0;int minutes = 0;int heures = 0;
            if(millis<0)
            {
                timing.setTextColor(Color.parseColor("#2020aa"));
                handler.removeCallbacks(runnable);
            }
            else
            {
                seconds = (int) (millis / 1000);
                minutes = seconds / 60;
                heures = minutes / 60;
                seconds = seconds % 60;
                minutes = minutes % 60;
            }
            timing.setText(String.format("%02d:%02d:%02d", heures, minutes, seconds));
            handler.postDelayed(this, 1000);
        }
    };

    private BDD db;
    private boolean end;
    private int delta;
    private BDDEquipe personnage;
    private boolean available;
    private LinearLayout layout;
    private TextView pseudo, timing;
    private ImageView imgperso, imgpseudo;
    private TestActivityFragment master;

    public PersonnageMuraille(TestActivityFragment master, BDDEquipe personnage)
    {
        this.master = master;
        layout      = (LinearLayout) LayoutInflater.from(master).inflate(R.layout.personnagemuraille,null);
        imgperso    = layout.findViewById(R.id.persomur_perso);
        imgpseudo   = layout.findViewById(R.id.persomur_fondpseudo);
        pseudo      = layout.findViewById(R.id.persomur_pseudo);
        timing      = layout.findViewById(R.id.persomur_timing);

        layout.setLayoutParams(new FrameLayout.LayoutParams(250, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setTranslationX((int)(Math.random()*1000));
        layout.setAnimation(newanim());
        layout.setOnClickListener(this);
        
        refresh(personnage);
    }

    //REFRESH==============================================

    public void refresh(BDDEquipe personnage)
    {
        handler.removeCallbacks(runnable);
        timing.setTextColor(Color.parseColor("#aa2020"));
        this.personnage = personnage;
        if(personnage.used())setUnavailable();
        else setAvailable();
        System.out.println(personnage.pseudo());
        pseudo.setText(personnage.pseudo());
        imgperso.setImageResource(master.getResources().getIdentifier(personnage.image(),"drawable",master.getPackageName()));
        imgpseudo.setBackgroundResource(master.getResources().getIdentifier("lvl_"+personnage.niveau(),"drawable",master.getPackageName()));
    }

    public void setAvailable()
    {
        available = true;
        timing.setText("");
        pseudo.setTextColor(Color.parseColor("#ffffff"));
        imgperso.setAlpha(1f);
    }

    public void setUnavailable()
    {
        db = master.getDb();
        available = false;
        imgperso.setAlpha(0.5f);
        String times = db.equipe().getTimes(personnage.id());
        startTime = Long.parseLong(times.substring(0,times.indexOf(":")));
        time = Long.parseLong(times.substring(times.indexOf(":")+1));
        handler.postDelayed(runnable, 0);
    }

    //ANIMATION============================================

    private Animation newanim()
    {
        delta = (int)(Math.random()*500)-250;
        layout.clearAnimation();
        if(layout.getX()+delta<0||layout.getX()+delta>1000)delta=delta*-1;
        if(delta<0)imgperso.setScaleX(1);
        else imgperso.setScaleX(-1);

        Animation animation = new TranslateAnimation(0, delta,0,0);
        animation.setDuration(2000);
        animation.setAnimationListener(this);
        return animation;
    }

    //LAYOUTS==============================================

    public LinearLayout getLayout(){return layout;}

    @Override
    public void onAnimationStart(Animation animation) {}
    @Override
    public void onAnimationRepeat(Animation animation){}
    @Override
    public void onAnimationEnd(Animation animation) {
        layout.setX(layout.getX()+delta);
        layout.clearAnimation();
        if(!end)layout.setAnimation(newanim());
    }

    @Override
    public void onClick(View view)
    {
        if(personnage.used())
        {
            String[] tmp = db.missionsEnCours().from(personnage).split(":");
            System.out.println(Arrays.toString(tmp));
            master.changeEcran(Long.parseLong(tmp[0]),Long.parseLong(tmp[1]),tmp[2]);
        }
        else
        {
            master.showPerso(personnage);
        }
    }

    public BDDEquipe getPerso(){return personnage;}
}
