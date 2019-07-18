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

import com.chiya.Activities.Master;
import com.chiya.Activities.R;

import androidx.core.content.ContextCompat;

public class Constructor
{
    private Context context;

    public Constructor(Context master)
    {
        this.context = master;
    }

    public FrameLayout progression()
    {
        FrameLayout progression = new FrameLayout(context);
        progression.setLayoutParams     (new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,3));
        progression.setPadding  (0,30,20,0);
        return progression;
    }

    public LinearLayout apercuhaut()
    {
        LinearLayout apercuhaut = new LinearLayout(context);
        apercuhaut.setLayoutParams      (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1));
        apercuhaut.setOrientation(LinearLayout.HORIZONTAL);
        return apercuhaut;
    }

    public ImageView fondMission()
    {
        ImageView fondmission = new ImageView(context);
        fondmission.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fondmission.setBackground(ContextCompat.getDrawable(context, R.drawable.fullbg2));
        fondmission.setAlpha(0.7f);
        return fondmission;
    }

    public LinearLayout nomreput()
    {
        LinearLayout nomreput   = new LinearLayout(context);
        nomreput.setLayoutParams        (new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
        nomreput.setOrientation(LinearLayout.VERTICAL);
        return nomreput;
    }

    public LinearLayout reputs()
    {
        LinearLayout reputs     = new LinearLayout(context);
        reputs.setLayoutParams          (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,4));
        reputs.setOrientation(LinearLayout.HORIZONTAL);
        reputs.setGravity(Gravity.CENTER);
        return reputs;
    }

    public LinearLayout apercubas()
    {
        LinearLayout apercubas  = new LinearLayout(context);
        apercubas.setLayoutParams       (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1));
        apercubas.setOrientation(LinearLayout.HORIZONTAL);
        return apercubas;
    }
    public LinearLayout linear()
    {
        LinearLayout linear     = new LinearLayout(context);
        linear.setLayoutParams          (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60));
        linear.setOrientation(LinearLayout.HORIZONTAL);
        return linear;
    }

    public ImageView fleche()
    {
        ImageView fleche        = new ImageView(context);
        fleche.setLayoutParams          (new LinearLayout.LayoutParams(70,70));
        fleche.setImageResource(R.drawable.icone_fleche);
        fleche.setScaleType(ImageView.ScaleType.FIT_XY);
        return fleche;
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

    public ImageView reput(int i)
    {
        ImageView reput     = new ImageView(context);
        reput.setLayoutParams       (new LinearLayout.LayoutParams(50,50));
        reput.setImageResource(i);
        return reput;
    }

    public TextView nombre(String s)
    {
        TextView nb        = new TextView(context);
        nb.setLayoutParams         (new LinearLayout.LayoutParams(150,50));
        nb.setText     (s);
        nb.setTextColor(Color.parseColor   ("#ffffff"));
        nb.setTextSize(12);
        return nb;
    }

    public ImageView icone()
    {
        ImageView icone         = new ImageView(context);
        icone.setLayoutParams           (new LinearLayout.LayoutParams(100,100));
        icone.setImageResource(R.drawable.icone_document);
        icone.setBackground(ContextCompat.getDrawable(context,R.drawable.testbg));
        return icone;
    }

    public ImageView tmp()
    {
        ImageView tmp           = new ImageView(context);
        tmp.setLayoutParams             (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60));
        tmp.setBackground(ContextCompat.getDrawable(context,R.drawable.testbg));
        return tmp;
    }

    public TextView nommission(String s)
    {
        TextView nommission     = new TextView(context);
        nommission.setLayoutParams      (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,5));
        nommission.setPadding   (20,0,0,0);
        nommission.setText  (s);
        nommission.setTextColor(Color.parseColor("#ffffff"));
        nommission.setTypeface(nommission.getTypeface(), Typeface.BOLD);
        return nommission;
    }

    public ImageView barre2()
    {
        ImageView barre2                  = new ImageView(context);
        barre2.setLayoutParams          (new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,100));
        return barre2;
    }

    public ImageView barreprogress()
    {
        ImageView barreprogress           = new ImageView(context);
        barreprogress.setLayoutParams   (new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,0));
        barreprogress.setScaleType(ImageView.ScaleType.FIT_XY);
        return barreprogress;
    }

    public TextView start()
    {
        TextView start                   = new TextView(context);
        start.setLayoutParams           (new LinearLayout.LayoutParams(0,120,1));
        start.setBackground(ContextCompat.getDrawable(context,R.drawable.bouton2));
        start.setPadding        (0,0,0,10);
        start.setText       ("Lancer");
        start.setTextColor(Color.parseColor     ("#ffffff"));
        start.setTypeface(start.getTypeface(), Typeface.BOLD);
        start.setGravity(Gravity.CENTER);
        start.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        start.setFocusable(true);
        return start;
    }

    public TextView barre(long time)
    {
        TextView barre                   = new TextView(context);
        barre.setPadding        (0,0,0,20);

        int seconds = (int) (time / 1000);
        int minutes = seconds / 60;
        int heures = minutes / 60;
        seconds = seconds % 60;
        minutes = minutes % 60;

        barre.setText       (String.format("%02d:%02d:%02d", heures, minutes, seconds));
        barre.setTextColor(Color.parseColor     ("#ffffff"));
        barre.setTypeface(barre.getTypeface(), Typeface.BOLD);
        barre.setGravity(Gravity.CENTER);
        barre.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        return barre;
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
