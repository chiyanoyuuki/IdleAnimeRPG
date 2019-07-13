package com.chiya.idleanimerpg;

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

import androidx.core.content.ContextCompat;

public class FrameLayoutAnimeChoice extends FrameLayout
{
    private Master master;
    private BDD db;
    private Context context;
    private String packageName;
    private BDDAnime anime;
    private Shader shader;

    public FrameLayoutAnimeChoice(Master master, BDDAnime anime, String couleur)
    {
        super(master);
        this.master         = master;
        this.db             = master.getDb();
        this.context        = master;
        this.packageName    = master.getPackageName();
        this.anime = anime;
        this.shader = new LinearGradient(250,0,0,80, Color.parseColor(couleur),Color.parseColor("#ffffff"),Shader.TileMode.CLAMP);
        init();
    }

    private void init()
    {
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,500));
        this.setBackgroundResource(R.drawable.testbg);
        this.setClickable(true);
        this.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                master.changeEcran(anime);
            }
        });
        addImage();
        addText();
        if(!anime.isup())addCache();
    }

    private void addImage()
    {
        ImageView image = new ImageView(context);
        image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        image.setImageResource(super.getResources().getIdentifier(anime.image(),"drawable",packageName));
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        this.addView(image);
    }

    private void addText()
    {
        FrameLayout fondpseudo = new FrameLayout(context);
        LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM;
        fondpseudo.setLayoutParams(lp);
        fondpseudo.setPadding(50,0,0,50);

        ImageView fond = new ImageView(context);
        fond.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fond.setBackgroundColor(Color.parseColor("#202020"));
        fond.setAlpha(0.5f);

        TextView texte = new TextView(context);
        texte.setText(anime.nom());
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
        ImageView img = new ImageView(context);
        img.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        img.setBackgroundResource(R.drawable.testbg);
        img.setAlpha(0.9f);
        img.setClickable(true);

        TextView prochainement = new TextView(context);
        prochainement.setText(anime.nom()+" bientôt...");
        prochainement.setTextColor(Color.parseColor("#ffffff"));
        prochainement.getPaint().setShader(shader);
        prochainement.setPadding(50,50,0,0);
        prochainement.setTextSize(20);
        prochainement.setTypeface(prochainement.getTypeface(),Typeface.BOLD_ITALIC);

        this.addView(img);
        this.addView(prochainement);
    }
}