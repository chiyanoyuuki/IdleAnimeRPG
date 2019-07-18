package com.chiya.BDD;

import android.database.Cursor;

public class BDDReputs
{
    private long niveau;
    private long need;
    private long actual;

    public BDDReputs(Cursor reput)
    {
        niveau = reput.getLong(0);
        actual = reput.getLong(1);
        need = reput.getLong(2);
    }

    public long niveau(){return niveau;}
    public long actual(){return actual;}
    public long need(){return need;}
}
