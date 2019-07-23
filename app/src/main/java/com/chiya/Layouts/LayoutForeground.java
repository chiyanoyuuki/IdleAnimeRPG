package com.chiya.Layouts;

import com.chiya.Activities.TestActivityFragment;
import com.chiya.BDD.BDDEquipe;

public class LayoutForeground
{
    private LayoutTopEcran  top;
    private LayoutBarres    barres;
    private LayoutMuraille  muraille;
    private LayoutBotEcran  bot;

    public LayoutForeground(TestActivityFragment master)
    {
        this.top        = new LayoutTopEcran(master);
        this.barres     = new LayoutBarres(master);
        this.muraille   = new LayoutMuraille(master);
        this.bot        = new LayoutBotEcran(master);
    }

    public void refreshBarres()     {barres.refresh();}
    public BDDEquipe getFirstPerso(){return muraille.getFirstPerso();}
    public void refreshMuraille()   {muraille.refreshPersos();}
    public void refreshAcc()        {top.refresh();}
}
