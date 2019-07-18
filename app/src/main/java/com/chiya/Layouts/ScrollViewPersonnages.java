package com.chiya.Layouts;

import android.database.Cursor;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chiya.Activities.Master;
import com.chiya.Activities.NewActivity;
import com.chiya.BDD.BDDPersonnage;

import java.util.ArrayList;

public class ScrollViewPersonnages extends NewScrollView
{
    private long level = -1;

    public ScrollViewPersonnages(Master master, Cursor cursor)
    {
        super(master);
        init(cursor);
    }

    private void init(Cursor cursor)
    {
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.setPadding(50,50,50,50);

        LinearLayout vertic = new LinearLayout(this.getContext());
        vertic.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        vertic.setOrientation(LinearLayout.VERTICAL);

        int cpt = 0;
        ArrayList<LinearLayout> layouts = new ArrayList<>();
        addLayout(layouts);

        while (cursor.moveToNext())
        {
            BDDPersonnage personnage = new BDDPersonnage(cursor);
            if(level==-1)
            {
                level = personnage.niveau();
            }
            else if(level!=personnage.niveau())
            {
                level = personnage.niveau();
                addLayout(layouts);
                cpt = 0;
            }
            ImageView img = createImg(personnage);
            layouts.get(layouts.size()-1).addView(img);
            cpt++;
            if(cpt%4==0)addLayout(layouts);
        }
        cursor.close();
        for(LinearLayout layout:layouts)vertic.addView(layout);
        this.addView(vertic);
    }

    private ImageView createImg(BDDPersonnage personnage)
    {
        ImageView perso = new ImageView(this.getContext());
        LayoutParams lp = new FrameLayout.LayoutParams(170,170);
        lp.setMargins(10,0,10,0);
        perso.setLayoutParams(lp);
        perso.setBackgroundResource(super.getResources().getIdentifier("lvl_"+personnage.niveau(),"drawable",packageName));
        perso.setImageResource(this.getResources().getIdentifier(personnage.image(),"drawable",packageName));
        if(!personnage.viewable())perso.setColorFilter(Color.parseColor("#000000"));
        perso.setScaleType(ImageView.ScaleType.CENTER_CROP);
        perso.setPadding(200,0,200,0);
        return perso;
    }

    private void addLayout(ArrayList<LinearLayout> layouts)
    {
        LinearLayout layout = new LinearLayout(this.getContext());
        LinearLayout.LayoutParams lp0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,200);
        lp0.setMargins(10,10,10,0);
        layout.setLayoutParams(lp0);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        layouts.add(layout);
    }
}