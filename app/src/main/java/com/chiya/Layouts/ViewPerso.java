package com.chiya.Layouts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chiya.Activities.R;
import com.chiya.Activities.TestActivityFragment;
import com.chiya.BDD.BDDEquipe;
import com.chiya.BDD.BDDPersonnage;

public class ViewPerso implements View.OnClickListener
{
    private BDDEquipe perso;
    private BDDPersonnage personnage;
    private TestActivityFragment master;
    private LinearLayout layout;
    private TextView prenom, nom, pseudo, description, sexe, age, taille, trad, lvl, pays, partie;
    private ImageView retour, image, rename;
    private FrameLayout fondimage;

    public ViewPerso(TestActivityFragment master)
    {
        this.master = master;
        layout      = (LinearLayout) LayoutInflater.from(master).inflate(R.layout.perso,null);
        prenom      = layout.findViewById(R.id.perso_prenom);
        nom         = layout.findViewById(R.id.perso_nomdesc);

        retour      = layout.findViewById(R.id.perso_retour);
        image       = layout.findViewById(R.id.perso_image);
        fondimage   = layout.findViewById(R.id.perso_fondimage);
        pseudo      = layout.findViewById(R.id.perso_pseudo);
        rename      = layout.findViewById(R.id.perso_rename);
        description = layout.findViewById(R.id.perso_description);
        sexe        = layout.findViewById(R.id.perso_sexe);
        age         = layout.findViewById(R.id.perso_age);
        taille      = layout.findViewById(R.id.perso_taille);
        trad        = layout.findViewById(R.id.perso_trad);
        lvl         = layout.findViewById(R.id.perso_lvl);
        pays        = layout.findViewById(R.id.perso_pays);
        partie      = layout.findViewById(R.id.perso_partie);

        rename.setOnClickListener(this);
        retour.setOnClickListener(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setVisibility(View.INVISIBLE);
    }

    private void reinit()
    {
        LinearLayout tmp = (LinearLayout) LayoutInflater.from(master).inflate(R.layout.perso,null);
        init(tmp,prenom,R.id.perso_prenom);
        init(tmp,nom,R.id.perso_nomdesc);
        init(tmp,sexe,R.id.perso_sexe);
        init(tmp,age,R.id.perso_age);
        init(tmp,taille,R.id.perso_taille);
        init(tmp,trad,R.id.perso_trad);
        init(tmp,lvl,R.id.perso_lvl);
        init(tmp,pays,R.id.perso_pays);
        init(tmp,partie,R.id.perso_partie);
    }

    private void init(LinearLayout tmp,TextView tv,int id)
    {
        TextView tv2 = tmp.findViewById(id);
        tv.setText(tv2.getText().toString());
    }

    public void view(BDDEquipe tmp)
    {
        reinit();
        perso = tmp;
        personnage = master.getDb().personnage().selectPersonnage(tmp.persoid());

        pseudo.setText(tmp.pseudo());
        known();
    }

    public void view(String s)
    {
        reinit();
        personnage = master.getDb().personnage().selectPersonnage(Long.parseLong(s));

        pseudo.setText(personnage.prenom());
        rename.setVisibility(View.INVISIBLE);

        known();
        if(!personnage.viewable()) unknown();
    }

    private void known()
    {
        image.setImageResource          (master.getResources().getIdentifier(personnage.image(),                "drawable",master.getPackageName()));
        fondimage.setBackgroundResource (master.getResources().getIdentifier("lvl_"+personnage.niveau(),  "drawable",master.getPackageName()));
        image.clearColorFilter();
        description.setText(personnage.description());
        change(prenom,personnage.prenom());
        change(nom,personnage.nom());
        change(sexe,personnage.sexe());
        change(age,personnage.age());
        change(taille,personnage.taille());
        change(trad,personnage.traduction());
        change(lvl,""+personnage.niveau());
        pays.setText(master.getDb().anime().select(personnage.animeid()).nom());
        partie.setText(master.getDb().partie().select(personnage.animeid(),personnage.partieid()).nom());
        layout.setVisibility(View.VISIBLE);
    }

    private void unknown()
    {
        change(prenom,"???");
        change(nom,"???");
        pseudo.setText("???");
        image.setColorFilter(Color.parseColor("#000000"));
        description.setText("Vous ne connaissez pas encore ce personnage...\n\nTemptez de recruter des personnes dans son lieu d'origine ou d'y effectuer des missions pour avoir une chance de le croiser.");
    }

    private void change(TextView tv,String s)
    {
        tv.setText(tv.getText().toString().replace("NAN",s));
    }

    public LinearLayout layout(){return layout;}

    public boolean isVisible(){return layout.getVisibility()==View.VISIBLE;}
    public void invis(){layout.setVisibility(View.INVISIBLE);}

    @Override
    public void onClick(View view)
    {
        if(view==retour)layout.setVisibility(View.INVISIBLE);
        else if(view==rename)rename();
    }

    private void rename()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(master);
        builder.setIcon(image.getDrawable());
        builder.setTitle(pseudo.getText().toString() + " devient : ");

        final EditText input = new EditText(master);
        input.setText(pseudo.getText().toString());
        input.setPadding(50,0,0,20);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                changePseudo(input.getText().toString());
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {}
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
            @Override
            public void afterTextChanged(Editable Token)
            {
                if(!Token.toString().matches(".{1,16}"))
                {
                    input.setBackgroundColor(Color.parseColor("#ffaaaa"));
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
                else
                {
                    input.setBackgroundColor(Color.parseColor("#dddddd"));
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }
        });
    }

    private void changePseudo(String s)
    {
        pseudo.setText(s);
        master.changePersoPseudo(perso.id(),s);
    }
}
