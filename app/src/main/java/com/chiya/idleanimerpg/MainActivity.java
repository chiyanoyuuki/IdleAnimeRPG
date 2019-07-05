package com.chiya.idleanimerpg;

import androidx.core.content.ContextCompat;
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

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends Master
{
    private LinearLayout centerlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addViews();
        //addForeground();
        verifStart();
    }

    public void addViews()
    {
        centerlayout = new LinearLayout(this);
        centerlayout.setOrientation(LinearLayout.VERTICAL);
        centerlayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1));
        mainlayout.addView(addHeader());
        mainlayout.addView(addReputs(-1,-1));
        addBot();
        mainlayout.addView(addFooter(true));
        background.addView(mainlayout);
    }

    public void verifStart()
    {
        ArrayList<BDDDialogue> dialogues = db.selectAllDialogues(""+-1,""+-1);
        Dialogue textes = new Dialogue(this, db, dialogues,this);
        background.addView(textes.getView());
    }

    private void addBot()
    {
        Cursor cursor = db.selectAll("anime");
        while (cursor.moveToNext())
        {
            BDDAnime anime = new BDDAnime(cursor);

            FrameLayout layout = new FrameLayout(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1);
            //layout.setPadding(50,50,50,50);
            layout.setLayoutParams(lp);
            layout.setBackground(ContextCompat.getDrawable(this,R.drawable.testbg));
            layout.setTag(""+anime.id());
            layout.setFocusable(true);
            layout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(MainActivity.this, Anime.class);
                    String id = (String) v.getTag();
                    intent.putExtra("animeid",id);
                    closeDb();
                    startActivity(intent);
                }
            });

            FrameLayout fond = new FrameLayout(this);
            fond.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

            ImageView image = new ImageView(this);
            image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
            image.setImageResource(super.getResources().getIdentifier(anime.image(),"drawable",packageName));
            image.setScaleType(ImageView.ScaleType.FIT_XY);

            TextView image2 = new TextView(this);
            image2.setText(anime.nom());
            image2.setTextColor(Color.parseColor("#ffffff"));
            image2.setTypeface(image2.getTypeface(),Typeface.BOLD_ITALIC);
            image2.getPaint().setShader(shader);
            image2.setTextSize(20);
            image2.setGravity(Gravity.BOTTOM);
            image2.setPadding(50,0,0,50);

            fond.addView(image);
            fond.addView(image2);

            layout.addView(fond);

            if(!anime.isup())
            {
                ImageView img = new ImageView(this);
                img.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                img.setBackgroundColor(Color.parseColor("#505050"));
                img.setAlpha(0.9f);
                img.setClickable(true);
                img.setFocusable(true);

                TextView prochainement = new TextView(this);
                prochainement.setText("En cours de développement..");
                prochainement.setTextColor(Color.parseColor("#ffffff"));
                prochainement.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                prochainement.setGravity(Gravity.CENTER);
                prochainement.setTextSize(20);
                prochainement.setTypeface(prochainement.getTypeface(),Typeface.BOLD);
                layout.addView(img);
                layout.addView(prochainement);
            }

            centerlayout.addView(layout);
        }
        cursor.close();
        mainlayout.addView(centerlayout);
    }
}