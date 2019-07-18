package com.chiya.Layouts;

import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.core.content.ContextCompat;

import com.chiya.Activities.Master;
import com.chiya.BDD.BDDAnime;
import com.chiya.BDD.BDDPartie;
import com.chiya.Activities.R;

public class LinearLayoutAnime extends NewLinearLayout implements View.OnClickListener
{
    private BDDAnime anime;

    public LinearLayoutAnime(Master master, BDDAnime anime) {
        super(master);
        this.anime = anime;
        init();
    }

    private void init()
    {
        this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.setBackgroundColor(Color.parseColor("#505050"));
        this.setOrientation(VERTICAL);

        addTop();
        addLiaison();
        addBottom();
    }

    private void addTop()
    {
        FrameLayout top = new FrameLayout(this.getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,400);
        top.setLayoutParams(lp);
        top.setBackground(ContextCompat.getDrawable(this.getContext(), R.drawable.midbg));

        ImageView topimage = new ImageView(this.getContext());
        topimage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        topimage.setImageResource(super.getResources().getIdentifier(anime.image(),"drawable",packageName));
        topimage.setScaleType(ImageView.ScaleType.FIT_XY);

        ImageView tmp = new ImageView(this.getContext());
        tmp.setImageResource(R.drawable.retour);
        FrameLayout.LayoutParams fp = new FrameLayout.LayoutParams(150,150);
        //fp.gravity = Gravity.
        tmp.setLayoutParams(fp);
        tmp.setOnClickListener(this);

        top.addView(topimage);
        top.addView(tmp);
        this.addView(top);
    }

    private void addLiaison()
    {
        ImageView liaison = new ImageView(this.getContext());
        liaison.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,20));
        liaison.setImageResource(R.drawable.fond);
        liaison.setScaleType(ImageView.ScaleType.FIT_XY);
        this.addView(liaison);
    }

    private void addBottom()
    {
        ScrollView scroll = new ScrollView(this.getContext());
        scroll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1));

        LinearLayout layout = new LinearLayout(this.getContext());
        scroll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(VERTICAL);

        Cursor cursor = db.partie().selectAllParties(""+anime.id());
        while (cursor.moveToNext())
        {
            BDDPartie partie = new BDDPartie(cursor);
            layout.addView(new FrameChoice(master, "#aaaaff", false,partie.image(),partie.nom(),partie.animeid(),partie.id()));
        }
        cursor.close();
        scroll.addView(layout);
        this.addView(scroll);
    }

    @Override
    public void onClick(View view)
    {
        master.changeEcran(-1,-1);
    }
    
    public void wipe()
    {
        
    }
}