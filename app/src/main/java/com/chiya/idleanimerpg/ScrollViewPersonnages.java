package com.chiya.idleanimerpg;

import android.database.Cursor;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ScrollViewPersonnages extends NewScrollView
{
    public ScrollViewPersonnages(Master master, Cursor cursor)
    {
        super(master);
        init(cursor);
    }

    private void init(Cursor cursor)
    {
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.setPadding(50,50,50,50);

        LinearLayout vertic = new LinearLayout(context);
        vertic.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        vertic.setOrientation(LinearLayout.VERTICAL);

        int cpt = 0;
        ArrayList<LinearLayout> layouts = new ArrayList<>();
        addLayout(layouts);

        while (cursor.moveToNext())
        {
            BDDPersonnage personnage = new BDDPersonnage(cursor);
            FrameLayout fond = createFond(personnage);
            ImageView img = createImg(personnage);

            fond.addView(img);
            layouts.get(layouts.size()-1).addView(fond);
            cpt++;
            if(cpt%4==0)addLayout(layouts);
        }
        cursor.close();
        for(LinearLayout layout:layouts)vertic.addView(layout);
        this.addView(vertic);
    }

    private FrameLayout createFond(BDDPersonnage personnage)
    {
        FrameLayout fond = new FrameLayout(context);
        LayoutParams lp = new FrameLayout.LayoutParams(170,170);
        lp.setMargins(20,0,20,0);
        fond.setLayoutParams(lp);
        fond.setBackgroundResource(super.getResources().getIdentifier("lvl_"+personnage.niveau(),"drawable",packageName));
        fond.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //master.changeEcran("icone");
            }
        });
        return fond;
    }

    private ImageView createImg(BDDPersonnage personnage)
    {
        ImageView perso = new ImageView(context);
        perso.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        perso.setImageResource(this.getResources().getIdentifier(personnage.image(),"drawable",packageName));
        perso.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if(!personnage.viewable())perso.setColorFilter(Color.parseColor("#000000"));
        perso.setPadding(100,40,100,0);
        perso.setScaleX(2);
        perso.setScaleY(2);
        return perso;
    }

    private void addLayout(ArrayList<LinearLayout> layouts)
    {
        LinearLayout layout = new LinearLayout(context);
        LinearLayout.LayoutParams lp0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,200);
        lp0.setMargins(20,20,20,0);
        layout.setLayoutParams(lp0);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        layouts.add(layout);
    }
}