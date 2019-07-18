package com.chiya.Layouts;

import android.widget.ImageView;
import android.widget.TextView;
import com.chiya.Activities.R;
import com.chiya.Activities.TestActivityFragment;
import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDCompte;
import com.chiya.BDD.BDDPersonnage;

public class LayoutTopEcran
{
    private TextView pseudo, nbressources;
    private ImageView perso;

    public LayoutTopEcran(TestActivityFragment master)
    {
        pseudo          = master.findViewById(R.id.accueil_pseudo);
        nbressources    = master.findViewById(R.id.accueil_nbressources);
        perso           = master.findViewById(R.id.accueil_perso);


        BDD db = master.getDb();
        BDDCompte compte = db.compte().selectCompte();
        BDDPersonnage personnage = db.personnage().selectPersonnage("Okori");

        pseudo.setText(compte.pseudo());
        nbressources.setText(compte.ressources()+"");
        perso.setImageResource(master.getResources().getIdentifier(personnage.image(),"drawable",master.getPackageName()));
    }
}
