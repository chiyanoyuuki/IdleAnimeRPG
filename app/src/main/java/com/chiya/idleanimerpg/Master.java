package com.chiya.idleanimerpg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
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
    protected Shader shader     = new LinearGradient(250,0,0,80,Color.parseColor("#ffaaaa"),Color.parseColor("#ffffff"),Shader.TileMode.CLAMP);
    protected Shader shader2    = new LinearGradient(250,0,0,80,Color.parseColor("#aaaaff"),Color.parseColor("#ffffff"),Shader.TileMode.CLAMP);

    protected TextView pseudo;
    protected BDD db;
    protected BDDCompte compte;
    protected FrameLayout background;
    protected LinearLayout mainlayout;
    protected String packageName;
    protected Dialogue dialogue;
    protected BDDReputs rmonde, rpays, rpartie;
    protected ImageView[][] barres;
    protected int indicebar;
    protected ImageView cachebas, cachehaut;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_main);

        barres = new ImageView[3][2];
        db = new BDD(this);
        compte = db.selectCompte();
        background = findViewById(R.id.mainlayout);
        mainlayout = new LinearLayout(this);
        mainlayout.setOrientation(LinearLayout.VERTICAL);
        packageName = getPackageName();
    }

    abstract void addViews();
    abstract void verifStart();

    protected void addForeground()
    {
        LinearLayout foreg = new LinearLayout(this);
        foreg.setOrientation(LinearLayout.VERTICAL);
        foreg.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ImageView top = new ImageView(this);
        top.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150));
        top.setImageResource(R.drawable.fond);
        top.setScaleType(ImageView.ScaleType.FIT_XY);

        ImageView mid = new ImageView(this);
        mid.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0,1));

        ImageView bot = new ImageView(this);
        bot.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150));
        bot.setImageResource(R.drawable.fond);
        bot.setScaleType(ImageView.ScaleType.FIT_XY);

        foreg.addView(top);
        foreg.addView(mid);
        foreg.addView(bot);
        background.addView(foreg);
    }

    protected FrameLayout addHeader()
    {
        BDDCompte compte = db.selectCompte();
        BDDPersonnage personnage = db.selectPersonnage(""+compte.persoid());

        FrameLayout derriere = new FrameLayout(this);
        derriere.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,150));

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setPadding(50,0,100,10);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        layout.setBackground(ContextCompat.getDrawable(this,R.drawable.fondtop));


        FrameLayout fond = new FrameLayout(this);
        ImageView image = new ImageView(this);
        fond.setBackground(ContextCompat.getDrawable(this,R.drawable.testbg));
        fond.setLayoutParams(new LinearLayout.LayoutParams(100,100));
        image.setLayoutParams(new LinearLayout.LayoutParams(170,170));
        image.setImageResource(this.getResources().getIdentifier(personnage.image(),"drawable",packageName));
        image.setScaleType(ImageView.ScaleType.FIT_START);
        fond.addView(image);

        pseudo = new TextView(this);
        pseudo.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1));
        pseudo.setPadding(10,0,0,0);
        pseudo.setText(compte.pseudo());
        pseudo.setTextColor(Color.parseColor("#ffffff"));
        pseudo.setTypeface(pseudo.getTypeface(), Typeface.BOLD);

        TextView ressource = new TextView(this);
        ressource.setLayoutParams(new LinearLayout.LayoutParams(100,60));
        ressource.setPadding(0,0,20,0);
        ressource.setText(""+compte.ressources());
        ressource.setBackground(ContextCompat.getDrawable(this,R.drawable.testbg));
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
            cachehaut = new ImageView(this);
            cachehaut.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            cachehaut.setBackgroundColor(Color.parseColor("#505050"));
            cachehaut.setAlpha(0.9f);
            cachehaut.setClickable(true);
            cachehaut.setFocusable(true);
            derriere.addView(cachehaut);
        }

        return derriere;
    }

    protected void refreshbarres(long i, long j)
    {
        rmonde = db.selectReput(-1,-1);
        rpays = db.selectReput(i,-1);
        rpartie = db.selectReput(i,j);
        setbar(0,rmonde);
        setbar(1,rpays);
        setbar(2,rpartie);
    }

    protected LinearLayout addReputs(long i, long j)
    {
        int nb = 1;
        if(i!=-1) nb = 2;
        if(j!=-1) nb = 3;

        LinearLayout reputs = new LinearLayout(this);
        reputs.setOrientation(LinearLayout.VERTICAL);
        reputs.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 15*nb));

        rmonde = db.selectReput(-1,-1);
        reputs.addView(addReput("progressr",rmonde));
        if(i!=-1)
        {
            rpays = db.selectReput(i,-1);
            reputs.addView(addReput("progressb",rpays));
        }
        if(j!=-1)
        {
            rpartie = db.selectReput(i,j);
            reputs.addView(addReput("progressv",rpartie));
        }

        return reputs;
    }

    protected void setbar(int i, BDDReputs reput)
    {
        long actual = reput.actual();
        long need = reput.need();

        int pct = (int)((actual*1.0)/(need*1.0)*100.0);

        barres[i][0].setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,pct));
        barres[i][1].setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,100-pct));
    }

    protected FrameLayout addReput(String couleur, BDDReputs reput)
    {
        FrameLayout layout = new FrameLayout(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0,1));
        layout.setBackground(ContextCompat.getDrawable(this,R.drawable.fullbg2));

        LinearLayout bars = new LinearLayout(this);
        bars.setOrientation(LinearLayout.HORIZONTAL);
        bars.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        long actual = reput.actual();
        long need = reput.need();

        int pct = (int)((actual*1.0)/(need*1.0)*100.0);

        ImageView b1 = new ImageView(this);
        b1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,pct));
        b1.setImageResource(this.getResources().getIdentifier(couleur,"drawable",packageName));
        b1.setScaleType(ImageView.ScaleType.FIT_XY);

        ImageView b2 = new ImageView(this);
        b2.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,100-pct));
        b2.setImageResource(this.getResources().getIdentifier(couleur,"drawable",packageName));
        b2.setScaleType(ImageView.ScaleType.FIT_XY);
        b2.setAlpha(0.5f);

        barres[indicebar][0] = b1;
        barres[indicebar++][1] = b2;

        bars.addView(b1);
        bars.addView(b2);

        layout.addView(bars);

        return layout;
    }

    protected FrameLayout addFooter(boolean alreadyaccueil)
    {
        final Context fin = this;
        BDDCompte compte = db.selectCompte();

        FrameLayout derriere = new FrameLayout(this);
        derriere.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,150));

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setBackground(ContextCompat.getDrawable(this,R.drawable.fondbas));
        layout.setGravity(Gravity.CENTER_VERTICAL);
        layout.setPadding(30,0,30,0);

        ImageView accueil = new ImageView(this);
        accueil.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1));
        accueil.setPadding(0,30,0,30);
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
        personnages.setImageResource(R.drawable.personnages);
        personnages.setPadding(0,20,0,30);

        ImageView stats = new ImageView(this);
        stats.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1));
        stats.setPadding(10,40,0,30);
        stats.setImageResource(R.drawable.stats);

        layout.addView(accueil);
        layout.addView(personnages);
        layout.addView(stats);
        derriere.addView(layout);

        if(!compte.started())
        {
            cachebas = new ImageView(this);
            cachebas.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            cachebas.setBackgroundColor(Color.parseColor("#505050"));
            cachebas.setAlpha(0.9f);
            cachebas.setClickable(true);
            cachebas.setFocusable(true);
            derriere.addView(cachebas);
        }

        return derriere;
    }

    public void refreshAcc()
    {
        compte = db.selectCompte();
        if(compte.started())
        {
            pseudo.setText(compte.pseudo());
            cachehaut.setVisibility(View.INVISIBLE);
            cachebas.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed(){}
    public void closeDb(){db.close();}
}
