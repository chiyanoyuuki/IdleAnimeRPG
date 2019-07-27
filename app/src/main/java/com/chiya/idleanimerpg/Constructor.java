package com.chiya.idleanimerpg;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chiya.Activities.R;

import androidx.core.content.ContextCompat;

public class Constructor
{
    private Context context;

    public Constructor(Context master)
    {
        this.context = master;
    }

    public LinearLayout mission()
    {
        LinearLayout mission = new LinearLayout(context);
        mission.setPadding(50,20,30,20);
        mission.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mission.setOrientation(LinearLayout.VERTICAL);
        return mission;
    }

    public FrameLayout background()
    {
        FrameLayout background = new FrameLayout(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,250);
        lp.setMargins(0,0,0,20);
        background.setLayoutParams(lp);
        return background;
    }

    public ImageView tmp()
    {
        ImageView tmp           = new ImageView(context);
        tmp.setLayoutParams             (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60));
        tmp.setBackground(ContextCompat.getDrawable(context,R.drawable.testbg));
        return tmp;
    }

    public ImageView img()
    {
        ImageView img           = new ImageView(context);
        img.setBackgroundColor(Color.parseColor("#505050"));
        img.setAlpha(0.9f);
        return img;
    }

    public LinearLayout all()
    {
        LinearLayout all        = new LinearLayout(context);
        all.setOrientation(LinearLayout.VERTICAL);
        all.setLayoutParams     (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        return all;
    }

    public FrameLayout haut()
    {
        FrameLayout haut        = new FrameLayout(context);
        haut.setLayoutParams    (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1));
        return haut;
    }

    public ImageView bulle()
    {
        ImageView bulle = new ImageView(context);
        bulle.setLayoutParams   (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        bulle.setScaleType(ImageView.ScaleType.FIT_XY);
        bulle.setImageResource(R.drawable.bulle);
        return bulle;
    }

    public TextView texte()
    {
        TextView texte = new TextView(context);
        texte.setLayoutParams   (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        texte.setPadding(150,0,150,80);
        texte.setGravity(Gravity.CENTER);
        texte.setTextSize(15);
        return texte;
    }

    public LinearLayout bas()
    {
        LinearLayout bas = new LinearLayout(context);
        bas.setOrientation(LinearLayout.HORIZONTAL);
        bas.setLayoutParams     (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1));
        return bas;
    }

    public ImageView image()
    {
        ImageView image = new ImageView(context);
        image.setLayoutParams   (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        image.setPadding(1000,0,1000,0);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //image.setScaleX(2);
        //image.setScaleY(2);
        return image;
    }
}
