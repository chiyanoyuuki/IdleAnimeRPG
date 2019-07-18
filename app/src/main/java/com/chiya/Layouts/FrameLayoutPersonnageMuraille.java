package com.chiya.Layouts;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chiya.Activities.Master;
import com.chiya.Activities.NewActivity;
import com.chiya.BDD.BDDEquipe;

public class FrameLayoutPersonnageMuraille extends NewLinearLayout implements Animation.AnimationListener
{
    private boolean end;
    private int delta;
    private boolean available;
    private FrameLayout fondpseudo;
    private TextView pseudo;
    private TextView timing;
    private FrameLayout perso;
    private ImageView imgperso;
    private BDDEquipe personnage;
    private long time, startTime;
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

    public FrameLayoutPersonnageMuraille(Master master)
    {
        super(master);
        init();
        this.setAnimation(newanim());
    }

    private Animation newanim()
    {
        delta = (int)(Math.random()*500)-250;
        this.clearAnimation();
        if(this.getX()+delta<0||this.getX()+delta>1000)delta=delta*-1;
        if(delta<0)this.setScale(1);
        else this.setScale(-1);

        Animation animation = new TranslateAnimation(0, delta,0,0);
        animation.setDuration(2000);
        animation.setAnimationListener(this);
        return animation;
    }

    private void init()
    {
        FrameLayout.LayoutParams fp2 = new FrameLayout.LayoutParams(200, 300);
        fp2.gravity = Gravity.BOTTOM;
        this.setLayoutParams(fp2);
        this.setTranslationY(20);
        this.setOrientation(VERTICAL);
        this.setTranslationX((int)(Math.random()*1000));
        this.setFocusable(false);
        this.setClickable(false);
        addPseudo();
        addTiming();
        addPerso();
    }

    private void addPseudo()
    {
        fondpseudo = new FrameLayout(this.getContext());
        fondpseudo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,2));

        pseudo = new TextView(this.getContext());
        pseudo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        pseudo.setTextColor(Color.parseColor("#ffffff"));
        pseudo.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        pseudo.setTypeface(pseudo.getTypeface(), Typeface.BOLD);

        ImageView fond = new ImageView(this.getContext());
        fond.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fond.setBackgroundColor(Color.parseColor("#202020"));
        fond.setAlpha(0.5f);

        fondpseudo.addView(fond);
        fondpseudo.addView(pseudo);
    }

    private void addTiming()
    {
        timing = new TextView(this.getContext());
        timing.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        timing.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        timing.setTypeface(timing.getTypeface(), Typeface.BOLD);
        //timing.setGravity(Gravity.CENTER);
        timing.setTranslationY(-10);
    }

    private void addPerso()
    {
        perso = new FrameLayout(this.getContext());
        perso.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,8));

        imgperso = new ImageView(this.getContext());
        imgperso.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imgperso.setPadding(200, 0, 200, 0);
        imgperso.setScaleType(ImageView.ScaleType.CENTER_CROP);

        perso.addView(imgperso);
        perso.addView(timing);

        this.addView(perso);
    }

    public void setScale(int i)
    {
        this.imgperso.setScaleX(i);
    }

    public void refresh(BDDEquipe personnage)
    {
        handler.removeCallbacks(runnable);
        timing.setTextColor(Color.parseColor("#aa2020"));
        this.personnage = personnage;
        if(personnage.used())setUnavailable();
        else setAvailable();
        this.removeAllViews();
        this.addView(fondpseudo);
        this.addView(perso);
        pseudo.setText(personnage.pseudo());
        imgperso.setImageResource(this.getResources().getIdentifier(personnage.image(),"drawable",packageName));

        setClick();
    }

    private void setClick()
    {
        /*this.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(available)
                {
                    master.changeEcran("equipe");
                }
                else
                {
                    String from = db.missionsEnCours().from(personnage);
                    long anime = Long.parseLong(from.substring(0,from.indexOf(":")));
                    long partie = Long.parseLong(from.substring(from.indexOf(":")+1));
                    master.changeEcran(anime,partie);
                }
            }
        });*/
        this.setFocusable(true);
        this.setClickable(true);
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
        available = false;
        imgperso.setAlpha(0.5f);
        String times = db.equipe().getTimes(personnage.id());
        startTime = Long.parseLong(times.substring(0,times.indexOf(":")));
        time = Long.parseLong(times.substring(times.indexOf(":")+1));
        handler.postDelayed(runnable, 0);
    }

    public void stop()
    {
        handler.removeCallbacks(runnable);
        runnable = null;
        end = true;
    }

    @Override
    public void onAnimationStart(Animation animation) {}
    @Override
    public void onAnimationRepeat(Animation animation){}
    @Override
    public void onAnimationEnd(Animation animation) {
        this.setX(this.getX()+delta);
        this.clearAnimation();
        if(!end)this.setAnimation(newanim());
    }
}