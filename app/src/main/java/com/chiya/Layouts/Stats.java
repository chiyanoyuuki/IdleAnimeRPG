package com.chiya.Layouts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chiya.Activities.R;
import com.chiya.Activities.TestActivityFragment;

public class Stats implements View.OnClickListener
{
    private TestActivityFragment master;
    private LinearLayout layout;
    private ImageView retour;

    public Stats(TestActivityFragment master)
    {
        this.master = master;
        layout = (LinearLayout) LayoutInflater.from(master).inflate(R.layout.stats,null);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        retour = layout.findViewById(R.id.stats_retour);
        retour.setOnClickListener(this);

        layout.setVisibility(View.INVISIBLE);
    }

    public LinearLayout layout(){return layout;}

    public void init()
    {
        layout.setVisibility(View.VISIBLE);
    }

    public boolean isVisible(){return layout.getVisibility()==View.VISIBLE;}
    public void invis(){layout.setVisibility(View.INVISIBLE);}

    @Override
    public void onClick(View view)
    {
        if(view==retour)layout.setVisibility(View.INVISIBLE);
    }
}
