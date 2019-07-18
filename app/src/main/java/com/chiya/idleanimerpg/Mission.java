package com.chiya.idleanimerpg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chiya.Activities.Master;
import com.chiya.BDD.BDDAnime;
import com.chiya.BDD.BDDMission;
import com.chiya.BDD.BDDPartie;
import com.chiya.Activities.NewActivity;
import com.chiya.Layouts.NewFrameLayout;
import com.chiya.Activities.R;

public class Mission extends NewFrameLayout
{
    private BDDAnime anime;
    private BDDPartie partie;
    private long time;
    private Context context;
    private ImageView barreprogress, barre2;
    private TextView barre, start;
    private long startTime;
    private LinearLayout mission;
    private BDDMission bddmission;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            long millis = time-(System.currentTimeMillis() - startTime);

            int nb = (int)(((millis*1.0)/(time*1.0))*100.0);
            barreprogress.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,100-nb));
            barre2.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,nb));

            int seconds = 0;
            int minutes = 0;
            int heures = 0;
            if(millis<0)
            {
                handler.removeCallbacks(runnable);
                start.setText("Finir");
                start.setBackground(ContextCompat.getDrawable(context,R.drawable.bouton2over));
                barreprogress.setImageResource(R.drawable.progressb);
            }
            else
            {
                seconds = (int) (millis / 1000);
                minutes = seconds / 60;
                heures = minutes / 60;
                seconds = seconds % 60;
                minutes = minutes % 60;
            }
            barre.setText(String.format("%02d:%02d:%02d", heures, minutes, seconds));
            handler.postDelayed(this, 1000);
        }
    };

    public Mission(Context context, BDDMission bddmission, Master master, BDDAnime anime, BDDPartie partie)
    {
        super(master);
        this.anime = anime;
        this.partie = partie;
        this.bddmission = bddmission;
        this.master = master;
        time = bddmission.temps();
        this.context = context;
        init();
        verifstarted();
    }

    private void init()
    {
        Constructor c = new Constructor(master);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,250);
        lp.setMargins(0,0,0,20);
        this.setLayoutParams(lp);

        mission                 = c.mission();
        barre2                  = c.barre2();
        barreprogress           = c.barreprogress();
        start                   = c.start();
        barre                   = c.barre(bddmission.temps());
        ImageView fondmission   = c.fondMission();
        FrameLayout progression = c.progression();
        LinearLayout apercuhaut = c.apercuhaut();
        LinearLayout nomreput   = c.nomreput();
        LinearLayout reputs     = c.reputs();
        LinearLayout apercubas  = c.apercubas();
        LinearLayout linear     = c.linear();
        ImageView fleche        = c.fleche();
        ImageView reputmonde    = c.reput(R.drawable.icone_reputmonde);
        ImageView reputpays     = c.reput(R.drawable.icone_reputpays);
        ImageView reputpartie;
        if(partie.id()==0) reputpartie = c.reput(R.drawable.icone_reputgentil);
        else reputpartie = c.reput(R.drawable.icone_reputmechant);
        TextView nbmonde        = c.nombre("+"+bddmission.rmonde());
        TextView nbpays         = c.nombre("+"+bddmission.rpays());
        TextView nbpartie       = c.nombre("+"+bddmission.rpartie());
        ImageView icone         = c.icone();
        ImageView tmp           = c.tmp();
        TextView nommission     = c.nommission(bddmission.nom());

        addClickStart();

        reputs.addView(reputpartie);reputs.addView(nbpartie);reputs.addView(reputpays);reputs.addView(nbpays);reputs.addView(reputmonde);reputs.addView(nbmonde);
        nomreput.addView(nommission);nomreput.addView(reputs);
        linear.addView(barreprogress);linear.addView(barre2);
        progression.addView(tmp);progression.addView(linear);progression.addView(barre);
        apercuhaut.addView(icone);apercuhaut.addView(nomreput);apercuhaut.addView(fleche);
        apercubas.addView(progression);apercubas.addView(start);
        mission.addView(apercuhaut);mission.addView(apercubas);
        this.addView(fondmission);this.addView(mission);
    }

    private void addClickStart()
    {
        /*start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TextView tmp = (TextView) v;
                if(tmp.getText().equals("Lancer"))
                {
                    BDDEquipe personnage = master.getFirstPerso();
                    if(personnage!=null)
                    {
                        startTime = System.currentTimeMillis();
                        db.missionsEnCours().add(bddmission,""+startTime,personnage);
                        handler.postDelayed(runnable, 0);
                        start.setText("Annuler");
                        start.setBackground(ContextCompat.getDrawable(context,R.drawable.bouton2disabled));
                        barreprogress.setImageResource(R.drawable.progressv);
                        master.refreshMuraille();
                    }
                }
                else if(tmp.getText().equals("Annuler"))
                {
                    annuler();
                }
                else if(tmp.getText().equals("Finir"))
                {
                    db.missionsEnCours().remove(bddmission);
                    master.refreshMuraille();
                    master.finishMission(bddmission,anime.id(),partie.id());
                }
            }
        });*/
    }

    private void annuler()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(super.getResources().getIdentifier(bddmission.image(), "drawable", packageName));
        builder.setTitle("Annuler mission ?");

        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                db.missionsEnCours().remove(bddmission);
                handler.removeCallbacks(runnable);
                start.setText("Lancer");
                start.setBackground(ContextCompat.getDrawable(context,R.drawable.bouton2));
                barreprogress.setImageResource(R.drawable.progressr);
                master.refreshMuraille();
            }
        });

        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i){}
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void verifstarted()
    {
        if(bddmission.started())
        {
            this.startTime = db.missionsEnCours().select(bddmission.id()).start();
            handler.postDelayed(runnable, 0);
            start.setText("Annuler");
            start.setBackgroundResource(R.drawable.bouton2disabled);
            barreprogress.setImageResource(R.drawable.progressv);
        }
    }
}
