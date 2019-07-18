package com.chiya.Layouts;

import com.chiya.Activities.TestActivityFragment;

public class LayoutForeground
{
    private LayoutTopEcran top;
    private LayoutBarres barres;
    private LayoutMuraille muraille;
    private LinearLayoutBotEcran bot;

    public LayoutForeground(TestActivityFragment master)
    {
        this.top        = new LayoutTopEcran(master);
        this.barres     = new LayoutBarres(master);
        this.muraille   = new LayoutMuraille(master);
        //this.bot        = new LinearLayoutBotEcran();
    }
}
