package com.chiya.idleanimerpg;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Dialogue
{
    private FrameLayout derriere;
    private TextView texte;
    private ImageView image, bulle, image0;
    private Context context;
    private BDD db;
    private ArrayList<String> personnages;

    private String[] dialogues;
    private String[] dialogue;
    int idx;

    public Dialogue(Context context, BDD db, String[] persos, String[] dialogues)
    {
        this.db = db;
        this.context = context;
        this.personnages = new ArrayList<String>();
        initPersonnages(persos);
        initLayout();
        this.dialogues = dialogues;
        this.dialogue = dialogues[0].split("/");
        idx = 0;
        refresh();
    }

    private void initPersonnages(String[] persos)
    {
        for(String perso:persos)
        {
            String[] tmp = perso.split("/");
            personnages.add(db.selectPersonnage(tmp[0],tmp[1],tmp[2]).image());
        }
    }

    private void initLayout()
    {
        derriere                = new FrameLayout(context);
        ImageView img           = new ImageView(context);
        LinearLayout all        = new LinearLayout(context);
        FrameLayout haut        = new FrameLayout(context);
        bulle                   = new ImageView(context);
        texte                   = new TextView(context);
        LinearLayout bas        = new LinearLayout(context);
        image0                  = new ImageView(context);
        image                   = new ImageView(context);

        all.setOrientation(LinearLayout.VERTICAL);
        bas.setOrientation(LinearLayout.HORIZONTAL);

        texte.setLayoutParams   (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        all.setLayoutParams     (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        bulle.setLayoutParams   (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        haut.setLayoutParams    (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1));
        bas.setLayoutParams     (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1));
        image0.setLayoutParams  (new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1));
        image.setLayoutParams   (new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1));

        haut.setPadding(0,270,0,0);
        bulle.setImageResource(R.drawable.bulle);

        img.setBackgroundColor(Color.parseColor("#505050"));
        img.setAlpha(0.9f);

        texte.setPadding(150,0,150,80);
        texte.setTypeface(texte.getTypeface(), Typeface.BOLD);
        texte.setGravity(Gravity.CENTER);
        texte.setTextSize(15);

        haut.addView(bulle);
        haut.addView(texte);
        bas.addView(image0);
        bas.addView(image);
        all.addView(haut);
        all.addView(bas);

        derriere.setFocusable(true);
        derriere.setClickable(true);
        derriere.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changeText();
            }
        });

        derriere.addView(img);
        derriere.addView(all);
    }

    private void refresh()
    {
        int orientation = Integer.parseInt(dialogue[0]);
        String perso = personnages.get(Integer.parseInt(dialogue[1]));
        texte.setText(dialogue[2]);
        int scaleX = 1;
        int scaleY = 1;
        if(dialogue.length>3)scaleX = Integer.parseInt(dialogue[3]);
        if(dialogue.length>4)scaleY = Integer.parseInt(dialogue[4]);
       if(orientation==0)
       {
           image0.setImageResource(context.getResources().getIdentifier(perso,"drawable",context.getPackageName()));
           image0.setScaleX(-1*scaleX);
           image0.setScaleY(scaleX);
           bulle.setScaleX(-1);
           image.setImageResource(0);
       }
       else
       {
           image.setImageResource(context.getResources().getIdentifier(perso,"drawable",context.getPackageName()));
           image.setScaleX(scaleX);
           image.setScaleY(scaleY);
           image0.setScaleX(1);
           image0.setImageResource(0);
       }
    }

    public FrameLayout getView()
    {
        return derriere;
    }

    private void changeText()
    {
        idx++;
        if(idx<dialogues.length)
        {
            dialogue = dialogues[idx].split("/");
            refresh();
        }
        else {
            derriere.setVisibility(View.INVISIBLE);
        }
    }
}
