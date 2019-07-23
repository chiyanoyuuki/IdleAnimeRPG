package com.chiya.Layouts;

import android.view.View;
import android.widget.ImageView;
import com.chiya.Activities.R;
import com.chiya.Activities.TestActivityFragment;

public class LayoutBotEcran implements View.OnClickListener
{
    private ImageView accueil, equipe, stats;
    private TestActivityFragment master;

    public LayoutBotEcran(TestActivityFragment master)
    {
        this.master = master;

        accueil   = master.findViewById(R.id.accueil_accueil);
        equipe    = master.findViewById(R.id.accueil_equipe);
        stats     = master.findViewById(R.id.accueil_stats);

        addBtn(accueil,"accueil");
        addBtn(equipe,"equipe");
        addBtn(stats,"stats");
    }

    private void addBtn(ImageView tv, String tag)
    {
        tv.setOnClickListener(this);
        tv.setTag(tag);
    }

    @Override
    public void onClick(View view)
    {
        String tag = view.getTag().toString();
        master.changeEcran(tag);
    }
}
