package com.chiya.BDD;

import android.database.Cursor;

public class BDDPersonnage
{
    private long id;
    private String prenom;
    private long animeid;
    private long partieid;
    private String image;
    private long niveau;
    private boolean viewable;
    private String nom;
    private String sexe;
    private String age;
    private String taille;
    private String traduction;
    private String description;

    public BDDPersonnage(Cursor c)
    {
        id      = c.getLong(0);
        prenom  = c.getString(1);
        animeid = c.getLong(2);
        partieid= c.getLong(3);
        image   = c.getString(4);
        niveau  = c.getLong(5);
        viewable = c.getLong(12)==1;
        nom     = c.getString(6);
        sexe    = c.getString(7);
        age     = c.getString(8);
        taille  = c.getString(9);
        traduction = c.getString(10);
        description = c.getString(11);
    }

    public long id()        {return id;}
    public String prenom()  {return prenom;}
    public long animeid()   {return animeid;}
    public long partieid()  {return partieid;}
    public String image()   {return image;}
    public long niveau()    {return niveau;}
    public boolean viewable(){return viewable;}
    public String nom()   {return nom;}
    public String sexe()   {return sexe.equals("0")?"Homme":"Femme";}
    public String age()   {return age;}
    public String taille()   {return taille;}
    public String traduction()   {return traduction;}
    public String description()   {return description;}
}
