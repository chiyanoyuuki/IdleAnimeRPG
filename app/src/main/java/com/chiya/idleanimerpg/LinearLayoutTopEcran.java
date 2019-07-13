package com.chiya.idleanimerpg;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LinearLayoutTopEcran extends NewLinearLayout
{
    private BDDCompte compte;

    private ImageView perso;
    private TextView pseudo;
    private TextView ressources;
    private ImageView iconeRessources;

    private final int size = 150;

    public LinearLayoutTopEcran(Master master)
    {
        super(master);
        init();
    }

    private void init()
    {
        this.compte = db.compte().selectCompte();
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, size));
        this.setPadding(50,0,100,10);
        this.setGravity(Gravity.CENTER_VERTICAL);
        this.setBackgroundResource(R.drawable.fondtop);

        addPerso();
        addPseudo();
        addRessources();
        addIconeRessources();
    }

    private void addPerso()
    {
        BDDPersonnage personnage = db.personnage().selectPersonnage("Okori");

        FrameLayout fond = new FrameLayout(context);
        fond.setLayoutParams(new LinearLayout.LayoutParams(100,100));
        fond.setBackgroundResource(R.drawable.testbg);
        fond.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                master.changeEcran("icone");
            }
        });

        perso = new ImageView(context);
        perso.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        perso.setImageResource(this.getResources().getIdentifier(personnage.image(),"drawable",packageName));
        perso.setScaleType(ImageView.ScaleType.CENTER_CROP);
        perso.setPadding(300,20,300,0);
        perso.setScaleX(2);
        perso.setScaleY(2);

        fond.addView(perso);
        this.addView(fond);
    }

    private void addPseudo()
    {
        pseudo = new TextView(context);
        pseudo.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1));
        pseudo.setPadding(10,0,0,0);
        pseudo.setText(compte.pseudo());
        pseudo.setTextColor(Color.parseColor("#ffffff"));
        pseudo.setTypeface(pseudo.getTypeface(), Typeface.BOLD);
        this.addView(pseudo);
    }

    private void addRessources()
    {
        ressources = new TextView(context);
        ressources.setLayoutParams(new LinearLayout.LayoutParams(100,60));
        ressources.setPadding(0,0,20,0);
        ressources.setText(""+compte.ressources());
        ressources.setBackgroundResource(R.drawable.testbg);
        ressources.setTextColor(Color.parseColor("#ffffff"));
        ressources.setTypeface(pseudo.getTypeface(),Typeface.BOLD);
        ressources.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        this.addView(ressources);
    }

    private void addIconeRessources()
    {
        iconeRessources = new ImageView(context);
        iconeRessources.setLayoutParams(new LinearLayout.LayoutParams(100,100));
        iconeRessources.setImageResource(R.drawable.ressource);
        this.addView(iconeRessources);
    }

    public int getSize(){return size;}

    public void refresh()
    {
        compte = db.compte().selectCompte();
        pseudo.setText(compte.pseudo());
    }
}
