package com.chiya.idleanimerpg;

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
        if(s.equals("FIRST_EVER"))    ok = isFirstMissionEver();
    }

    private boolean isFirstMissionEver(){return !db.selectCompte().started();}

    public boolean isok()
    {
        return ok;
    }
}
