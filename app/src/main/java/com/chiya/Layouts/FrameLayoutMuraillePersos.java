package com.chiya.Layouts;

import android.database.Cursor;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chiya.Activities.Master;
import com.chiya.Activities.NewActivity;
import com.chiya.BDD.BDDEquipe;
import com.chiya.Activities.R;

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
            this.addView(perso);
        }
    }

    public void refreshPersos()
    {
        Cursor cursor = db.equipe().selectEquipe();
        int cpt = 0;
        while(cursor.moveToNext())
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

        ImageView tmp = new ImageView(this.getContext());
        tmp.setImageResource(R.drawable.muraille);
        if(i==1)tmp.setImageResource(R.drawable.muraille2);
        tmp.setLayoutParams(fp);
        tmp.setScaleType(ImageView.ScaleType.FIT_END);
        tmp.setTranslationX(x);
        tmp.setTranslationY(y);
        tmp.setScaleX(scale);

        return tmp;
    }

    public BDDEquipe getFirstPerso()
    {
        Cursor cursor = db.equipe().selectEquipe();
        int cpt=0;
        while(cursor.moveToNext())
        {
            BDDEquipe personnage = new BDDEquipe(cursor);
            if(!personnage.used()) {db.equipe().setUsed(personnage);return personnage;}
            cpt++;
        }
        cursor.close();
        return null;
    }

    public void stop()
    {
        for(int i=0;i<personnages.size();i++)
        {
            personnages.get(i).clearAnimation();
            personnages.get(i).stop();
        }
    }
}