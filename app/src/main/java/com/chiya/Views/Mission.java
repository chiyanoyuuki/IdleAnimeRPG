package com.chiya.Views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chiya.Activities.R;
import com.chiya.Activities.TestActivityFragment;
import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDAnime;
import com.chiya.BDD.BDDEquipe;
import com.chiya.BDD.BDDMission;
import com.chiya.BDD.BDDPartie;
import com.chiya.Fragments.Missions;

public class Mission implements View.OnClickListener
{
    private long startTime;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            long millis = mission.temps()-(System.currentTimeMillis() - startTime);

            int nb = (int)(((millis*1.0)/(mission.temps()*1.0))*100.0);
            progress.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,100-nb));
            progressbis.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,nb));

            int seconds = 0;
            int minutes = 0;
            int heures = 0;
            if(millis<0)
            {
                handler.removeCallbacks(runnable);
                bouton.setText("Finir");
                bouton.setBackgroundResource(R.drawable.bouton2over);
                progress.setBackgroundResource(R.drawable.progressb);
                time.setText(String.format("%02d:%02d:%02d", heures, minutes, seconds));
            }
            else
            {
                temps(millis);
            }
            handler.postDelayed(this, 1000);
        }
    };
    private Missions master;
    private ImageView infos, icone, iconepartie, progress, progressbis;
    private TextView nom, bouton, nbmonde, nbpays, nbpartie, time;
    private FrameLayout background;
    private BDDMission mission;
    private BDDAnime anime;
    private BDDPartie partie;
    private String packageName;

    public Mission(Missions master, BDDMission mission, BDDAnime anime, BDDPartie partie)
    {
        this.mission = mission;
        this.anime = anime;
        this.partie = partie;
        this.master = master;
        Context context = master.getContext();
        this.packageName = context.getPackageName();

        background      = (FrameLayout) LayoutInflater.from(master.getContext()).inflate(R.layout.mission,null);
        icone           = background.findViewById(R.id.mission_icone);
        nom             = background.findViewById(R.id.mission_nom);
        iconepartie     = background.findViewById(R.id.mission_gentilmech);
        nbmonde         = background.findViewById(R.id.mission_nbmonde);
        nbpays          = background.findViewById(R.id.mission_nbpays);
        nbpartie        = background.findViewById(R.id.mission_nbgentil);
        progress        = background.findViewById(R.id.mission_progress);
        progressbis     = background.findViewById(R.id.mission_progressbis);
        time            = background.findViewById(R.id.mission_timing);
        bouton          = background.findViewById(R.id.mission_bouton);

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 250);
        lp.setMargins(0,0,0,20);
        background.setLayoutParams(lp);

        icone.setImageResource(master.getResources().getIdentifier(mission.image(),"drawable",packageName));
        nom.setText(mission.nom());
        if(partie.id()==1)iconepartie.setBackgroundResource(R.drawable.icone_reputmechant);
        nbpartie.setText("+"+mission.rpartie());
        nbpays.setText("+"+mission.rpays());
        nbmonde.setText("+"+mission.rmonde());
        bouton.setOnClickListener(this);
        temps(mission.temps());

        verifstarted();
    }

    private void temps(long temps)
    {
        int seconds = (int) (temps / 1000);
        int minutes = seconds / 60;
        int heures = minutes / 60;
        seconds = seconds % 60;
        minutes = minutes % 60;
        time.setText(String.format("%02d:%02d:%02d", heures, minutes, seconds));
    }

    private void format(TextView tv)
    {
        tv.setTextColor(Color.parseColor("#ffffff"));
        tv.setTypeface(tv.getTypeface(),Typeface.BOLD);
        tv.setGravity(Gravity.CENTER);
    }

    public FrameLayout layout(){return background;}

    private void verifstarted()
    {
        if(mission.started())
        {
            TestActivityFragment tmp = (TestActivityFragment)master.getActivity();
            BDD db = tmp.getDb();
            this.startTime = db.missionsEnCours().select(mission.id()).start();
            handler.postDelayed(runnable, 0);
            bouton.setText("Annuler");
            bouton.setBackgroundResource(R.drawable.bouton2disabled);
            progress.setBackgroundResource(R.drawable.progressv);
        }
    }

    @Override
    public void onClick(View view)
    {
        TestActivityFragment tmp = (TestActivityFragment)master.getActivity();
        BDD db = tmp.getDb();
        if(bouton.getText().equals("Lancer"))
        {
            BDDEquipe personnage = tmp.getFirstPerso();
            if(personnage!=null)
            {
                startTime = System.currentTimeMillis();
                db.missionsEnCours().add(mission,""+startTime,personnage,tmp.getAnime(),tmp.getPartie(),tmp.getState());
                handler.postDelayed(runnable, 0);
                bouton.setText("Annuler");
                bouton.setBackgroundResource(R.drawable.bouton2disabled);
                progress.setBackgroundResource(R.drawable.progressv);
                tmp.refreshMuraille();
            }
        }
        else if(bouton.getText().equals("Annuler"))
        {
            annuler();
        }
        else if(bouton.getText().equals("Finir"))
        {
            db.missionsEnCours().remove(mission);
            tmp.refreshMuraille();
            tmp.finishMission(mission);
            master.refresh();
        }
    }

    private void positive()
    {
        TestActivityFragment tmp = (TestActivityFragment)master.getActivity();
        BDD db = tmp.getDb();
        db.missionsEnCours().remove(mission);
        handler.removeCallbacks(runnable);
        bouton.setText("Lancer");
        bouton.setBackgroundResource(R.drawable.bouton2);
        progress.setBackgroundResource(R.drawable.progressr);
        tmp.refreshMuraille();
    }

    private void annuler()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(master.getContext());
        builder.setIcon(master.getResources().getIdentifier(mission.image(), "drawable", packageName));
        builder.setTitle("Annuler mission ?");

        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i){positive();}
        });

        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i){}
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    
    public void destroy()
    {
        handler.removeCallbacks(runnable);
        handler = null;
        runnable = null;

        master          = null;
        infos           = null;
        icone           = null;
        iconepartie     = null;
        progress        = null;
        progressbis     = null;
        nom             = null;
        bouton          = null;
        nbmonde         = null;
        nbpays          = null;
        nbpartie        = null;
        time            = null;
        background      = null;
    }
}
