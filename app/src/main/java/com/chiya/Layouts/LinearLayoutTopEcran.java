package com.chiya.Layouts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chiya.Activities.Master;
import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDCompte;
import com.chiya.BDD.BDDPersonnage;
import com.chiya.Activities.R;

public class LinearLayoutTopEcran extends Fragment
{
    private LinearLayout layout;
    private FrameLayout fondperso;
    private ImageView perso, imgressources;
    private TextView pseudo, nbressources;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.accueil, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layout          = view.findViewById(R.id.accueil_topecran);
        fondperso       = view.findViewById(R.id.accueil_fondperso);
        perso           = view.findViewById(R.id.accueil_perso);
        imgressources   = view.findViewById(R.id.accueil_imgressources);
        pseudo          = view.findViewById(R.id.accueil_pseudo);
        nbressources    = view.findViewById(R.id.accueil_nbressources);
    }

    public void refresh(Master master)
    {
        BDD db = master.getDb();
        BDDCompte compte = db.compte().selectCompte();
        BDDPersonnage personnage = db.personnage().selectPersonnage("Okori");

        perso.setImageResource(getResources().getIdentifier(personnage.image(),"drawable",master.getPackageName()));
        pseudo.setText(compte.pseudo());
        nbressources.setText(""+compte.ressources());
    }
}
