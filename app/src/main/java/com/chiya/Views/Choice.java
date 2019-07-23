package com.chiya.Views;


import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chiya.Activities.R;
import com.chiya.BDD.BDD;
import com.chiya.Fragments.NewFragment;

public class Choice implements View.OnClickListener
{
    private long anime;
    private long partie;
    private Shader shader;
    private FrameLayout layout, cache;
    private ImageView background;
    private TextView texte, prochainement;
    private NewFragment master;

    public Choice(NewFragment master, String couleur, boolean isup, String image, String nom, long anime, long partie)
    {
        layout          = (FrameLayout)LayoutInflater.from(master.getContext()).inflate(R.layout.choice,null);
        cache           = layout.findViewById(R.id.choice_cache);
        background      = layout.findViewById(R.id.choice_fond);
        texte           = layout.findViewById(R.id.choice_texte);
        prochainement   = layout.findViewById(R.id.choice_soon);
        this.master = master; this.anime = anime; this.partie = partie;

        layout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600));

        layout.setOnClickListener(this);

        shader = new LinearGradient(250,0,0,80, Color.parseColor(couleur),Color.parseColor("#ffffff"), Shader.TileMode.CLAMP);
        background.setBackgroundResource(master.getResources().getIdentifier(image,"drawable",master.getContext().getPackageName()));
        texte.setText(nom);
        texte.getPaint().setShader(shader);
        prochainement.getPaint().setShader(shader);
        prochainement.setText(nom +" prochainement...");

        if(isup) cache.setVisibility(View.VISIBLE);
    }

    public FrameLayout getLayout(){return layout;}

    @Override
    public void onClick(View view)
    {
        if(view!=cache)
            master.click(anime,partie);
    }

    public void destroy()
    {
        master = null;
        layout.setOnClickListener(null);
        layout = null;
    }
}
