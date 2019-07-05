package com.chiya.idleanimerpg;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class Mission
{
    private long time;
    private ImageView barreprogress, barre2;
    private TextView barre, start;
    private long startTime;
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
            seconds = seconds % 60;
            minutes = minutes % 60;
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
            handler.postDelayed(this, 500);
        }
    };
    private Partie partie;
    private LinearLayout mission;
    private Context context;
    private BDDMission bddmission;
    private FrameLayout background;

    public Mission(Partie partie, Context context, BDDMission bddmission)
    {
        this.partie = partie;
        this.bddmission = bddmission;
        time = bddmission.temps();
        this.context = context;
        init();
    }

    private void init()
    {
        background = new FrameLayout(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,250);
        lp.setMargins(0,0,0,20);
        background.setLayoutParams(lp);

        ImageView fondmission = new ImageView(context);
        fondmission.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fondmission.setBackground(ContextCompat.getDrawable(context,R.drawable.fullbg2));
        fondmission.setAlpha(0.7f);

        mission = new LinearLayout(context);
        mission.setPadding(50,20,30,20);
        mission.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mission.setOrientation(LinearLayout.VERTICAL);

        FrameLayout progression = new FrameLayout(context);
        LinearLayout apercuhaut = new LinearLayout(context);
        LinearLayout nomreput   = new LinearLayout(context);
        LinearLayout reputs     = new LinearLayout(context);
        LinearLayout apercubas  = new LinearLayout(context);
        LinearLayout linear     = new LinearLayout(context);
        ImageView fleche        = new ImageView(context);
        ImageView reputmonde    = new ImageView(context);
        ImageView reputpays     = new ImageView(context);
        ImageView reputpartie   = new ImageView(context);
        ImageView icone         = new ImageView(context);
        ImageView tmp           = new ImageView(context);
        barre2                  = new ImageView(context);
        barreprogress           = new ImageView(context);
        TextView nommission     = new TextView(context);
        start                   = new TextView(context);
        barre                   = new TextView(context);
        TextView nbmonde        = new TextView(context);
        TextView nbpays         = new TextView(context);
        TextView nbpartie       = new TextView(context);

        apercuhaut.setLayoutParams      (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1));
        nomreput.setLayoutParams        (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,1));
        nommission.setLayoutParams      (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,5));
        reputs.setLayoutParams          (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,4));
        apercubas.setLayoutParams       (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1));
        tmp.setLayoutParams             (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60));
        linear.setLayoutParams          (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60));
        barreprogress.setLayoutParams   (new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,0));
        barre2.setLayoutParams          (new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,100));
        progression.setLayoutParams     (new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,3));
        nomreput.setLayoutParams        (new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
        icone.setLayoutParams           (new LinearLayout.LayoutParams(100,100));
        reputmonde.setLayoutParams      (new LinearLayout.LayoutParams(50,50));
        reputpays.setLayoutParams       (new LinearLayout.LayoutParams(50,50));
        reputpartie.setLayoutParams     (new LinearLayout.LayoutParams(50,50));
        fleche.setLayoutParams          (new LinearLayout.LayoutParams(70,70));
        start.setLayoutParams           (new LinearLayout.LayoutParams(0,120,1));
        nbmonde.setLayoutParams         (new LinearLayout.LayoutParams(150,50));
        nbpays.setLayoutParams          (new LinearLayout.LayoutParams(150,50));
        nbpartie.setLayoutParams        (new LinearLayout.LayoutParams(150,50));


        apercuhaut.setOrientation(LinearLayout.HORIZONTAL);
        nomreput.setOrientation(LinearLayout.VERTICAL);
        reputs.setOrientation(LinearLayout.HORIZONTAL);
        apercubas.setOrientation(LinearLayout.HORIZONTAL);
        linear.setOrientation(LinearLayout.HORIZONTAL);

        fleche.setImageResource(R.drawable.icone_fleche);
        reputmonde.setImageResource(R.drawable.icone_reputmonde);
        reputpays.setImageResource(R.drawable.icone_reputpays);
        reputpartie.setImageResource(R.drawable.icone_reputpartie);
        icone.setImageResource(R.drawable.icone_document);

        icone.setBackground(ContextCompat.getDrawable(context,R.drawable.testbg));
        tmp.setBackground(ContextCompat.getDrawable(context,R.drawable.testbg));
        start.setBackground(ContextCompat.getDrawable(context,R.drawable.bouton2));

        fleche.setScaleType(ImageView.ScaleType.FIT_XY);

        progression.setPadding  (0,30,20,0);
        barre.setPadding        (0,0,0,20);
        nommission.setPadding   (20,0,0,0);
        start.setPadding        (0,0,0,10);

        long time = bddmission.temps();

        int seconds = (int) (time / 1000);
        int minutes = seconds / 60;
        int heures = minutes / 60;
        seconds = seconds % 60;
        minutes = minutes % 60;

        barre.setText       (String.format("%02d:%02d:%02d", heures, minutes, seconds));
        nommission.setText  (bddmission.nom());
        start.setText       ("Lancer");
        nbpartie.setText    ("+"+bddmission.rpartie());
        nbpays.setText      ("+"+bddmission.rpays());
        nbmonde.setText     ("+"+bddmission.rmonde());

        nommission.setTextColor(Color.parseColor("#ffffff"));
        barre.setTextColor(Color.parseColor     ("#ffffff"));
        start.setTextColor(Color.parseColor     ("#ffffff"));
        nbpartie.setTextColor(Color.parseColor  ("#ffffff"));
        nbpays.setTextColor(Color.parseColor    ("#ffffff"));
        nbmonde.setTextColor(Color.parseColor   ("#ffffff"));

        nbpartie.setTextSize(12);
        nbpays.setTextSize(12);
        nbmonde.setTextSize(12);

        nommission.setTypeface(nommission.getTypeface(), Typeface.BOLD);
        barre.setTypeface(barre.getTypeface(), Typeface.BOLD);
        start.setTypeface(start.getTypeface(), Typeface.BOLD);

        barre.setGravity(Gravity.CENTER);
        start.setGravity(Gravity.CENTER);
        reputs.setGravity(Gravity.CENTER);

        barre.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        start.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        barreprogress.setScaleType(ImageView.ScaleType.FIT_XY);

        start.setFocusable(true);
        start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TextView tmp = (TextView) v;
                if(tmp.getText().equals("Lancer"))
                {
                    startTime = System.currentTimeMillis();
                    handler.postDelayed(runnable, 0);
                    start.setText("Annuler");
                    start.setBackground(ContextCompat.getDrawable(context,R.drawable.bouton2disabled));
                    barreprogress.setImageResource(R.drawable.progressv);
                }
                else if(tmp.getText().equals("Annuler"))
                {
                    handler.removeCallbacks(runnable);
                    start.setText("Lancer");
                    start.setBackground(ContextCompat.getDrawable(context,R.drawable.bouton2));
                    barreprogress.setImageResource(R.drawable.progressr);
                }
                else if(tmp.getText().equals("Finir"))
                {
                    partie.finishMission(bddmission);

                }
            }
        });

        reputs.addView(reputpartie);
        reputs.addView(nbpartie);
        reputs.addView(reputpays);
        reputs.addView(nbpays);
        reputs.addView(reputmonde);
        reputs.addView(nbmonde);

        nomreput.addView(nommission);
        nomreput.addView(reputs);
        linear.addView(barreprogress);
        linear.addView(barre2);

        progression.addView(tmp);
        progression.addView(linear);
        progression.addView(barre);

        apercuhaut.addView(icone);
        apercuhaut.addView(nomreput);
        apercuhaut.addView(fleche);

        apercubas.addView(progression);
        apercubas.addView(start);

        mission.addView(apercuhaut);
        mission.addView(apercubas);

        background.addView(fondmission);
        background.addView(mission);
    }

    public FrameLayout layout(){return background;}
}
