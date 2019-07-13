package com.chiya.idleanimerpg;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FrameLayoutPersonnageMuraille extends NewLinearLayout
{
    private FrameLayout fondpseudo;
    private TextView pseudo;
    private ImageView perso;

    public FrameLayoutPersonnageMuraille(Master master)
    {
        super(master);
        init();
    }

    private void init()
    {
        FrameLayout.LayoutParams fp2 = new FrameLayout.LayoutParams(200, 300);
        fp2.gravity = Gravity.BOTTOM;
        this.setLayoutParams(fp2);
        this.setTranslationY(20);
        this.setOrientation(VERTICAL);
        this.setTranslationX((int)(Math.random()*1000));
        addPseudo();
        addPerso();
    }

    private void addPseudo()
    {
        fondpseudo = new FrameLayout(context);
        fondpseudo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,2));

        pseudo = new TextView(context);
        pseudo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        pseudo.setTextColor(Color.parseColor("#ffffff"));
        pseudo.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        pseudo.setTypeface(pseudo.getTypeface(), Typeface.BOLD);

        ImageView fond = new ImageView(context);
        fond.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fond.setBackgroundColor(Color.parseColor("#202020"));
        fond.setAlpha(0.5f);

        fondpseudo.addView(fond);
        fondpseudo.addView(pseudo);
    }

    private void addPerso()
    {
        perso = new ImageView(context);
        perso.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,8));
        perso.setPadding(200, 0, 200, 0);
        perso.setScaleType(ImageView.ScaleType.CENTER_CROP);

        this.addView(perso);
    }

    public void setScale(int i)
    {
        this.perso.setScaleX(i);
    }

    public void refresh(BDDEquipe personnage)
    {
        this.removeAllViews();
        this.addView(fondpseudo);
        this.addView(perso);
        pseudo.setText(personnage.pseudo());
        perso.setImageResource(this.getResources().getIdentifier(personnage.image(),"drawable",packageName));
    }
}