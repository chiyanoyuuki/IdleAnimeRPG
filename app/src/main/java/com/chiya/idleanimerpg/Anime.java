package com.chiya.idleanimerpg;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class Anime extends Master
{
    private BDDAnime anime;
    private LinearLayout centerlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        String animeid = getIntent().getStringExtra("animeid");
        anime = db.selectAnime(animeid);
        addViews();
        //addForeground();
        verifStart();
    }

    public void addViews()
    {
        centerlayout = new LinearLayout(this);
        centerlayout.setOrientation(LinearLayout.VERTICAL);
        centerlayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1));
        //centerlayout.setPadding(16,20,16,20);
        centerlayout.setBackground(ContextCompat.getDrawable(this,R.drawable.testbg));

        mainlayout.addView(addHeader());
        mainlayout.addView(addReputs(anime.id(),-1));
        addTop();
        addBot();
        mainlayout.addView(addFooter(false));

        background.addView(mainlayout);
    }

    public void verifStart()
    {
        ArrayList<BDDDialogue> dialogues = db.selectAllDialogues(""+anime.id(),""+-1);
        Dialogue textes = new Dialogue(this, db, dialogues,this);
        background.addView(textes.getView());
    }

    private void addTop()
    {
        FrameLayout top = new FrameLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,300);
        //top.setPadding(50,50,50,50);
        top.setLayoutParams(lp);
        top.setBackground(ContextCompat.getDrawable(this,R.drawable.midbg));

        ImageView topimage = new ImageView(this);
        topimage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        topimage.setImageResource(super.getResources().getIdentifier(anime.image(),"drawable",packageName));
        topimage.setScaleType(ImageView.ScaleType.FIT_XY);

        top.addView(topimage);
        mainlayout.addView(top);
    }

    private void addBot()
    {
        Cursor cursor = db.selectAllParties(""+anime.id());
        while (cursor.moveToNext())
        {
            BDDPartie partie = new BDDPartie(cursor);
            FrameLayout layout = new FrameLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1);
            layout.setLayoutParams(params);
            layout.setTag(anime.id()+":"+partie.id());
            layout.setFocusable(true);
            layout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(Anime.this, Partie.class);
                    String id = (String) v.getTag();
                    intent.putExtra("animeid",id.substring(0,id.indexOf(":")));
                    intent.putExtra("partieid",id.substring(id.indexOf(":")+1));
                    closeDb();
                    startActivity(intent);
                }
            });

            FrameLayout fond = new FrameLayout(this);
            fond.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

            ImageView image = new ImageView(this);
            image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
            image.setImageResource(super.getResources().getIdentifier(partie.image(),"drawable",packageName));
            image.setScaleType(ImageView.ScaleType.FIT_XY);

            TextView image2 = new TextView(this);
            image2.setText(partie.nom());
            image2.setTextColor(Color.parseColor("#ffffff"));
            image2.setTypeface(image2.getTypeface(), Typeface.BOLD_ITALIC);
            image2.getPaint().setShader(shader2);
            image2.setTextSize(14);
            image2.setGravity(Gravity.BOTTOM);
            image2.setPadding(20,0,0,20);

            fond.addView(image);
            fond.addView(image2);

            layout.addView(fond);

            centerlayout.addView(layout);
        }
        cursor.close();
        mainlayout.addView(centerlayout);
    }
}
