package com.chiya.idleanimerpg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;

public abstract class Master extends Activity
{
    protected BDD db;
    protected BDDCompte compte;
    protected FrameLayout background;
    protected LinearLayout mainlayout;
    protected String packageName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_main);

        db = new BDD(this);
        compte = db.selectCompte();
        background = findViewById(R.id.mainlayout);
        mainlayout = new LinearLayout(this);
        mainlayout.setOrientation(LinearLayout.VERTICAL);
        packageName = getPackageName();
    }

    abstract void addViews();
    abstract void verifStart();

    public FrameLayout addHeader()
    {
        BDDCompte compte = db.selectCompte();
        BDDPersonnage personnage = db.selectPersonnage(""+compte.persoid());

        FrameLayout derriere = new FrameLayout(this);
        derriere.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,150));

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setPadding(30,0,100,0);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        layout.setBackground(ContextCompat.getDrawable(this,R.drawable.midbg2));


        FrameLayout fond = new FrameLayout(this);
        ImageView image = new ImageView(this);
        fond.setBackground(ContextCompat.getDrawable(this,R.drawable.bouton));
        fond.setLayoutParams(new LinearLayout.LayoutParams(120,120));
        fond.setPadding(10,15,15,15);
        image.setLayoutParams(new LinearLayout.LayoutParams(170,170));
        image.setImageResource(this.getResources().getIdentifier(personnage.image(),"drawable",packageName));
        image.setScaleType(ImageView.ScaleType.FIT_START);
        fond.addView(image);



        TextView pseudo = new TextView(this);
        pseudo.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1));
        pseudo.setPadding(10,0,0,0);
        pseudo.setText(compte.pseudo());
        pseudo.setTextColor(Color.parseColor("#ffffff"));
        pseudo.setTypeface(pseudo.getTypeface(), Typeface.BOLD);

        TextView ressource = new TextView(this);
        ressource.setLayoutParams(new LinearLayout.LayoutParams(100,60));
        ressource.setPadding(0,0,20,0);
        ressource.setText(""+compte.ressources());
        ressource.setBackground(ContextCompat.getDrawable(this,R.drawable.fullbg2));
        ressource.setTextColor(Color.parseColor("#ffffff"));
        ressource.setTypeface(pseudo.getTypeface(),Typeface.BOLD);
        ressource.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        ImageView imgressource = new ImageView(this);
        imgressource.setLayoutParams(new LinearLayout.LayoutParams(100,100));
        imgressource.setImageResource(R.drawable.ressource);

        layout.addView(fond);
        layout.addView(pseudo);
        layout.addView(ressource);
        layout.addView(imgressource);

        derriere.addView(layout);

        if(!compte.started())
        {
            ImageView img = new ImageView(this);
            img.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            img.setBackgroundColor(Color.parseColor("#505050"));
            img.setAlpha(0.9f);
            img.setClickable(true);
            img.setFocusable(true);
            derriere.addView(img);
        }

        return derriere;
    }

    public FrameLayout addFooter(boolean alreadyaccueil)
    {
        final Context fin = this;
        BDDCompte compte = db.selectCompte();

        FrameLayout derriere = new FrameLayout(this);
        derriere.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,150));

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setGravity(Gravity.CENTER_VERTICAL);

        ImageView accueil = new ImageView(this);
        accueil.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1));
        accueil.setBackground(ContextCompat.getDrawable(this,R.drawable.firstbg2));
        accueil.setPadding(0,10,0,10);
        accueil.setImageResource(R.drawable.accueil);
        if(!alreadyaccueil)accueil.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(fin, MainActivity.class);
                fin.startActivity(intent);
                ((Master) fin).closeDb();
            }
        });

        ImageView personnages = new ImageView(this);
        personnages.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1));
        personnages.setBackground(ContextCompat.getDrawable(this,R.drawable.midbg2));
        personnages.setImageResource(R.drawable.personnages);

        ImageView stats = new ImageView(this);
        stats.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1));
        stats.setBackground(ContextCompat.getDrawable(this,R.drawable.lastbg2));
        stats.setPadding(0,10,0,10);
        stats.setImageResource(R.drawable.stats);

        layout.addView(accueil);
        layout.addView(personnages);
        layout.addView(stats);
        derriere.addView(layout);

        if(!compte.started())
        {
            ImageView img = new ImageView(this);
            img.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            img.setBackgroundColor(Color.parseColor("#505050"));
            img.setAlpha(0.9f);
            img.setClickable(true);
            img.setFocusable(true);
            derriere.addView(img);
        }

        return derriere;
    }

    /*@Override
    public void onBackPressed(){

    }*/

    public void closeDb(){db.close();}
}
