package com.chiya.Views;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chiya.Activities.TestActivityFragment;
import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDEquipe;

public class PersonnageMuraille implements Animation.AnimationListener
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

    private boolean end;
    private int delta;
    private BDDEquipe personnage;
    private boolean available;
    private LinearLayout layout;
    private TextView pseudo, timing;
    private FrameLayout perso, fondpseudo;
    private ImageView imgperso, imgpseudo;

    public PersonnageMuraille(TestActivityFragment master, BDDEquipe personnage)
    {
        layout      = new LinearLayout(master);
        fondpseudo  = new FrameLayout(master);
        perso       = new FrameLayout(master);
        imgperso    = new ImageView(master);
        imgpseudo   = new ImageView(master);
        pseudo      = new TextView(master);
        timing      = new TextView(master);

        initLayout();
        initPseudo();
        initPerso();
        initTiming();
        add();
        refresh(master,personnage);
    }

    //REFRESH==============================================

    public void refresh(TestActivityFragment master, BDDEquipe personnage)
    {
        handler.removeCallbacks(runnable);
        timing.setTextColor(Color.parseColor("#aa2020"));
        this.personnage = personnage;
        if(personnage.used())setUnavailable(master);
        else setAvailable();
        pseudo.setText(personnage.pseudo());
        imgperso.setImageResource(master.getResources().getIdentifier(personnage.image(),"drawable",master.getPackageName()));
    }

    public void setAvailable()
    {
        available = true;
        timing.setText("");
        pseudo.setTextColor(Color.parseColor("#ffffff"));
        imgperso.setAlpha(1f);
    }

    public void setUnavailable(TestActivityFragment master)
    {
        BDD db = master.getDb();
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

    private void initLayout()
    {
        layout.setLayoutParams(new FrameLayout.LayoutParams(200, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setTranslationX((int)(Math.random()*1000));
        layout.setAnimation(newanim());
    }

    private void initPseudo()
    {
        fondpseudo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,2));

        pseudo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        pseudo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        pseudo.setTextColor(Color.parseColor("#ffffff"));
        pseudo.setTypeface(pseudo.getTypeface(), Typeface.BOLD);

        imgpseudo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imgpseudo.setBackgroundColor(Color.parseColor("#202020"));
        imgpseudo.setAlpha(0.5f);

        fondpseudo.addView(imgpseudo);
        fondpseudo.addView(pseudo);
    }

    private void initPerso()
    {
        perso.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,8));

        imgperso.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imgperso.setPadding(200, 0, 200, 0);
        imgperso.setScaleType(ImageView.ScaleType.CENTER_CROP);

        perso.addView(imgperso);
    }

    private void initTiming()
    {
        timing.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        timing.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        timing.setTypeface(timing.getTypeface(), Typeface.BOLD);
        timing.setTranslationY(-10);

        perso.addView(timing);
    }

    private void add()
    {
        layout.addView(fondpseudo);
        layout.addView(perso);
    }

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
}
