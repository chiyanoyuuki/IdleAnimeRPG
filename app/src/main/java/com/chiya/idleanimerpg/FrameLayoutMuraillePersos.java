package com.chiya.idleanimerpg;

import android.database.Cursor;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class FrameLayoutMuraillePersos extends NewFrameLayout
{
    private ArrayList<FrameLayoutPersonnageMuraille> personnages;

    public FrameLayoutMuraillePersos(Master master)
    {
        super(master);
        init();
    }

    private void init()
    {
        personnages = new ArrayList<>();
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0,1));
        this.addView(muraille(100,-15,1.2f,1));
        this.createPersos();
        this.addView(muraille(0,50,1.3f,2));
        this.refreshPersos();
    }

    private void createPersos()
    {
        for(int i=0;i<10;i++)
        {
            FrameLayoutPersonnageMuraille perso = new FrameLayoutPersonnageMuraille(master);
            personnages.add(perso);
            personnages.get(i).setAnimation(newanim(i));
            this.addView(perso);
        }
    }

    private Animation newanim(int i)
    {
        final int x = i;

        int tmpi = (int)(Math.random()*500)-250;
        FrameLayoutPersonnageMuraille tmp = personnages.get(i);
        tmp.clearAnimation();
        if(tmp.getX()+tmpi<0||tmp.getX()+tmpi>1000)tmpi=tmpi*-1;
        final int delta = tmpi;
        if(delta<0)tmp.setScale(1);
        else tmp.setScale(-1);

        Animation animation = new TranslateAnimation(0, delta,0,0);
        animation.setDuration(2000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                LinearLayout tmp = personnages.get(x);
                tmp.setX(tmp.getX()+delta);
                tmp.setAnimation(newanim(x));
            }
        });
        return animation;
    }

    public void refreshPersos()
    {
        long nb = db.compte().selectCompte().teamsize();
        Cursor cursor = db.equipe().selectEquipe();
        int cpt = 0;
        while(cursor.moveToNext()&&cpt<nb)
        {
            BDDEquipe personnage = new BDDEquipe(cursor);
            personnages.get(cpt++).refresh(personnage);
        }
        cursor.close();
    }

    private ImageView muraille(int x, int y, float scale, int i)
    {
        FrameLayout.LayoutParams fp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        fp.gravity = Gravity.BOTTOM;

        ImageView tmp = new ImageView(context);
        tmp.setImageResource(R.drawable.muraille);
        if(i==1)tmp.setImageResource(R.drawable.muraille2);
        tmp.setLayoutParams(fp);
        tmp.setScaleType(ImageView.ScaleType.FIT_END);
        tmp.setTranslationX(x);
        tmp.setTranslationY(y);
        tmp.setScaleX(scale);

        return tmp;
    }
}