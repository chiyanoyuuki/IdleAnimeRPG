package com.chiya.idleanimerpg;

import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FrameLayoutForeground extends NewFrameLayout
{
    private LinearLayoutTopEcran top;
    private LinearLayoutBarres barres;
    private FrameLayoutMuraillePersos muraille;
    private LinearLayoutBotEcran bot;

    public FrameLayoutForeground(Master master)
    {
        super(master);
        init();
    }

    private void init()
    {
        this.top        = new LinearLayoutTopEcran(master);
        this.barres     = new LinearLayoutBarres(master);
        this.muraille   = new FrameLayoutMuraillePersos(master);
        this.bot        = new LinearLayoutBotEcran(master);

        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(top);
        layout.addView(barres);
        layout.addView(muraille);
        layout.addView(bot);

        this.addView(layout);
    }

    public void refreshBarres(long i, long j)
    {
        barres.refreshbarres(i,j);
    }
    public void refreshMuraille(){muraille.refreshPersos();}
    public void refreshAcc(){top.refresh();}

    public int getTopSize(){return top.getSize()+barres.getSize();}
    public int getBotSize(){return bot.getSize();}
}
