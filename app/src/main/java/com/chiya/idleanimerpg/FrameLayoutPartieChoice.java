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

public class FrameLayoutPartieChoice extends FrameLayout
{
    private Master master;
    private BDD db;
    private Context context;
    private String packageName;
    private BDDAnime anime;
    private BDDPartie partie;
    private Shader shader;

    public FrameLayoutPartieChoice(Master master, BDDAnime anime, BDDPartie partie, String couleur)
    {
        super(master);
        this.master         = master;
        this.db             = master.getDb();
        this.context        = master;
        this.packageName    = master.getPackageName();
        this.anime          = anime;
        this.partie         = partie;
        this.shader         = new LinearGradient(250,0,0,80, Color.parseColor(couleur),Color.parseColor("#ffffff"),Shader.TileMode.CLAMP);
        init();
    }

    private void init()
    {
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,500));
        this.setBackground(ContextCompat.getDrawable(context,R.drawable.testbg));
        this.setClickable(true);
        this.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                master.changeEcran(anime,partie);
            }
        });
        addImage();
        addText();
    }

    private void addImage()
    {
        ImageView image = new ImageView(context);
        image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        image.setImageResource(super.getResources().getIdentifier(partie.image(),"drawable",packageName));
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
        texte.setText(partie.nom());
        texte.setTextColor(Color.parseColor("#ffffff"));
        texte.setTypeface(texte.getTypeface(), Typeface.BOLD_ITALIC);
        texte.getPaint().setShader(shader);
        texte.setTextSize(20);

        fondpseudo.addView(fond);
        fondpseudo.addView(texte);

        this.addView(fondpseudo);
    }
}