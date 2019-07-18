package com.chiya.idleanimerpg;

import com.chiya.BDD.BDD;

public class Conditions
{
    private BDD db;
    private boolean ok;

    public Conditions(String s, BDD db)
    {
        this.db = db;
        ok = true;
        String[] conds = s.substring(s.indexOf("{")+1,s.indexOf("}")).split(",");
        for(String cond:conds)
        {
            verifCond(cond);
        }
    }

    public void verifCond(String s)
    {
        if(s.equals("FIRST_EVER"))          ok = !db.compte().selectCompte().started();
        else if(s.startsWith("NOGOT_"))     ok = !db.equipe().gotPerso(s.substring(s.indexOf("_")+1));
    }

    public boolean isok()
    {
        return ok;
    }
}
