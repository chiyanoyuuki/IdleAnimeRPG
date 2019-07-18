package com.chiya.Layouts;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chiya.Activities.Master;
import com.chiya.Activities.NewActivity;
import com.chiya.BDD.BDDAnime;
import com.chiya.BDD.BDDPartie;
import com.chiya.Activities.R;

public class LinearLayoutMissions extends NewLinearLayout implements View.OnClickListener
{
    private BDDAnime anime;
    private BDDPartie partie;
    private TextView btn01, btn02, btn03;
    private FrameLayoutMissions missions;
    private FrameLayoutPersonnages personnages;

    public LinearLayoutMissions(Master master, BDDAnime anime, BDDPartie partie)
    {
        super(master);
        this.anime = anime;
        this.partie = partie;
        init();
    }

    private void init()
    {
        this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.setOrientation(VERTICAL);

        missions = new FrameLayoutMissions(master,anime,partie);
        personnages = new FrameLayoutPersonnages(master,anime,partie);

        addTop();
        addLiaison();
        addMid();
    }

    private void addMid()
    {
        this.addView(missions);
    }

    private void addLiaison()
    {
        ImageView liaison = new ImageView(this.getContext());
        liaison.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,20));
        liaison.setImageResource(R.drawable.fond);
        liaison.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.addView(liaison);
    }

    private void addTop()
    {
        FrameLayout top = new FrameLayout(this.getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,400);
        top.setLayoutParams(lp);
        top.setBackground(ContextCompat.getDrawable(this.getContext(),R.drawable.midbg));

        ImageView topimage = new ImageView(this.getContext());
        topimage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        topimage.setImageResource(super.getResources().getIdentifier(partie.image(),"drawable",packageName));
        topimage.setScaleType(ImageView.ScaleType.FIT_XY);

        ImageView tmp = new ImageView(this.getContext());
        tmp.setImageResource(R.drawable.retour);
        FrameLayout.LayoutParams fp = new FrameLayout.LayoutParams(150,150);
        //fp.gravity = Gravity.
        tmp.setLayoutParams(fp);
        tmp.setTag("retour");
        tmp.setOnClickListener(this);

        top.addView(topimage);
        top.addView(tmp);
        top.addView(boutons());

        this.addView(top);
    }

    private LinearLayout boutons()
    {
        LinearLayout mid = new LinearLayout(this.getContext());
        mid.setGravity(Gravity.BOTTOM);
        mid.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mid.setOrientation(LinearLayout.HORIZONTAL);

        btn01 = new TextView(this.getContext());
        fmtBtn(btn01,"MissionsHistoire");
        btn01.setBackgroundResource(R.drawable.clicked);

        btn02 = new TextView(this.getContext());
        fmtBtn(btn02,"Habitants");
        btn02.setBackgroundResource(R.drawable.bouton);

        btn03 = new TextView(this.getContext());
        fmtBtn(btn03,"Stats");
        btn03.setBackgroundResource(R.drawable.bouton);

        mid.addView(btn01);
        mid.addView(btn02);
        mid.addView(btn03);

        return mid;
    }

    private void fmtBtn(TextView tv, String s)
    {
        tv.setLayoutParams(new LinearLayout.LayoutParams(0,150,1));
        tv.setTextColor(Color.parseColor("#ffffff"));
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        tv.setGravity(Gravity.CENTER);
        tv.setText(s);
        tv.setTag(s);
        tv.setOnClickListener(this);
    }

    public void refresh()
    {
        missions.refresh();
    }

    @Override
    public void onClick(View view)
    {
        String tag = view.getTag().toString();
        if(tag.equals("retour"))
        {
            master.changeEcran(anime.id(),-1);
        }
        else if(tag.equals("MissionsHistoire"))
        {
            btn01.setBackgroundResource(R.drawable.clicked);
            btn02.setBackgroundResource(R.drawable.bouton);
            btn03.setBackgroundResource(R.drawable.bouton);

            this.removeViewAt(2);
            this.addView(missions,2);
        }
        else if(tag.equals("Habitants"))
        {
            btn01.setBackgroundResource(R.drawable.bouton);
            btn02.setBackgroundResource(R.drawable.clicked);
            btn03.setBackgroundResource(R.drawable.bouton);

            this.removeViewAt(2);
            this.addView(personnages,2);
        }
        else if(tag.equals("Stats"))
        {
            btn01.setBackgroundResource(R.drawable.bouton);
            btn02.setBackgroundResource(R.drawable.bouton);
            btn03.setBackgroundResource(R.drawable.clicked);
        }
    }
}