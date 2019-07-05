package com.chiya.idleanimerpg;

import android.database.Cursor;

public class BDDDialogue
{
    private long id;
    private long animeid;
    private long partieid;
    private long orientation;
    private String image;
    private String texte;
    private long scalex;
    private long scaley;

    public BDDDialogue(Cursor c)
    {
        id          = c.getLong(0);
        animeid     = c.getLong(1);
        partieid    = c.getLong(2);
        orientation = c.getLong(7);
        image       = c.getString(8);
        texte       = c.getString(9);
        scalex      = c.getLong(10);
        scaley      = c.getLong(11);
    }

    public long id()        {return id;}
    public long animeid()   {return animeid;}
    public long partieid()  {return partieid;}
    public long orientation(){return orientation;}
    public String texte()   {return texte;}
    public String image()   {return image;}
    public long scalex()    {return scalex;}
    public long scaley()    {return scaley;}
}
