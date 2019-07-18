package com.chiya.Layouts;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chiya.Activities.Master;
import com.chiya.Activities.R;
import com.chiya.BDD.BDD;

public class FrameChoice extends FrameLayout implements View.OnClickListener
{
    private Master master;
    private BDD db;
    private String packageName;
    private Shader shader;
    private boolean isup;
    private String image, nom;
    private long anime, partie;

    private ImageView imagev;
    private FrameLayout fondpseudo;

    public FrameChoice(Master master, String couleur, boolean isup, String image, String nom, long anime, long partie)
    {
        super(master);
        this.master         = master;
        this.db             = master.getDb();
        this.packageName    = master.getPackageName();
        this.shader = new LinearGradient(250,0,0,80, Color.parseColor(couleur),Color.parseColor("#ffffff"),Shader.TileMode.CLAMP);
        this.isup = isup;
        this.image = image;
        this.nom = nom;
        this.anime = anime;
        this.partie = partie;
        init();
    }

    private void init()
    {
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,500));
        this.setBackgroundResource(R.drawable.testbg);
        this.setClickable(true);
        this.setOnClickListener(this);
        addImage();
        addText();
        if(isup)addCache();
    }

    private void addImage()
    {
        imagev = new ImageView(this.getContext());
        imagev.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        imagev.setImageResource(super.getResources().getIdentifier(this.image,"drawable",packageName));
        imagev.setScaleType(ImageView.ScaleType.FIT_XY);
        this.addView(imagev);
    }

    private void addText()
    {
        FrameLayout fondpseudo = new FrameLayout(this.getContext());
        LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM;
        fondpseudo.setLayoutParams(lp);
        fondpseudo.setPadding(50,0,0,50);

        ImageView fond = new ImageView(this.getContext());
        fond.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fond.setBackgroundColor(Color.parseColor("#202020"));
        fond.setAlpha(0.5f);

        TextView texte = new TextView(this.getContext());
        texte.setText(nom);
        texte.setTextColor(Color.parseColor("#ffffff"));
        texte.setTypeface(texte.getTypeface(), Typeface.BOLD_ITALIC);
        texte.getPaint().setShader(shader);
        texte.setTextSize(20);

        fondpseudo.addView(fond);
        fondpseudo.addView(texte);

        this.addView(fondpseudo);
    }

    private void addCache()
    {
        ImageView img = new ImageView(this.getContext());
        img.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        img.setBackgroundResource(R.drawable.testbg);
        img.setAlpha(0.9f);
        img.setClickable(true);

        TextView prochainement = new TextView(this.getContext());
        prochainement.setText(nom+" bientôt...");
        prochainement.setTextColor(Color.parseColor("#ffffff"));
        prochainement.getPaint().setShader(shader);
        prochainement.setPadding(50,50,0,0);
        prochainement.setTextSize(20);
        prochainement.setTypeface(prochainement.getTypeface(),Typeface.BOLD_ITALIC);

        this.addView(img);
        this.addView(prochainement);
    }


    @Override
    public void onClick(View view)
    {
        master.changeEcran(anime,partie);
    }

    public void wipe()
    {
        imagev = null;
    }
}
