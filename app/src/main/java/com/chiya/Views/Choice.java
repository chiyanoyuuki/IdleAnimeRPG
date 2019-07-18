package com.chiya.Views;


import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chiya.Activities.R;
import com.chiya.Fragments.NewFragment;

public class Choice implements View.OnClickListener
{
    private long anime;
    private long partie;
    private Shader shader;
    private FrameLayout layout, fondtexte;
    private ImageView background, img, fondtxt;
    private TextView texte, prochainement;
    private NewFragment master;

    public Choice(NewFragment master, String couleur, boolean isup, String image, String nom, long anime, long partie)
    {
        layout          = new FrameLayout(master.getContext());
        fondtexte       = new FrameLayout(master.getContext());
        fondtxt         = new ImageView(master.getContext());
        background      = new ImageView(master.getContext());
        img             = new ImageView(master.getContext());
        texte           = new TextView(master.getContext());
        prochainement   = new TextView(master.getContext());

        this.master     = master;
        this.anime      = anime;
        this.partie     = partie;

        layout.setOnClickListener(this);

        background.setImageResource(master.getResources().getIdentifier(image,"drawable",master.getContext().getPackageName()));
        texte.setText(nom);
        shader = new LinearGradient(250,0,0,80, Color.parseColor(couleur),Color.parseColor("#ffffff"), Shader.TileMode.CLAMP);

        addLayout();
        addImage();
        addText();
        if(isup)addCache(nom);
    }

    private void addLayout()
    {
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,500));
        layout.setBackgroundResource(R.drawable.testbg);
        layout.setClickable(true);
        layout.setOnClickListener(this);
    }

    private void addImage()
    {
        background.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        background.setScaleType(ImageView.ScaleType.FIT_XY);
        layout.addView(background);
    }

    private void addText()
    {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        fondtexte.setLayoutParams(lp);
        fondtexte.setPadding(50,50,0,0);
        //fondtexte.setTranslationY(-20);

        fondtxt.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fondtxt.setBackgroundColor(Color.parseColor("#202020"));
        fondtxt.setAlpha(0.5f);

        texte.setTextColor(Color.parseColor("#ffffff"));
        texte.setTypeface(texte.getTypeface(), Typeface.BOLD_ITALIC);
        texte.setTextSize(20);
        texte.getPaint().setShader(shader);

        fondtexte.addView(fondtxt);
        fondtexte.addView(texte);

        layout.addView(fondtexte);
    }

    private void addCache(String nom)
    {
        img.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        img.setBackgroundResource(R.drawable.testbg);
        img.setAlpha(0.9f);
        img.setClickable(true);

        prochainement.setText(nom+" bientôt...");
        prochainement.setTextColor(Color.parseColor("#ffffff"));
        prochainement.getPaint().setShader(shader);
        prochainement.setPadding(50,50,0,0);
        prochainement.setTextSize(20);
        prochainement.setTypeface(prochainement.getTypeface(),Typeface.BOLD_ITALIC);

        layout.addView(img);
        layout.addView(prochainement);
    }

    public FrameLayout getLayout(){return layout;}

    @Override
    public void onClick(View view)
    {
        master.click(anime,partie);
    }

    public void destroy()
    {
        master = null;
        layout.setOnClickListener(null);
        layout          = null;
        fondtexte       = null;
        fondtxt         = null;
        background      = null;
        img             = null;
        texte           = null;
        prochainement   = null;
    }
}
