package com.chiya.Layouts;

import android.app.AlertDialog;
import android.content.Context;
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
    private TextView prenom, nom, pseudo;
    private ImageView retour, image, rename;
    private FrameLayout fondimage;

    public ViewPerso(TestActivityFragment master)
    {
        this.master = master;
        layout      = (LinearLayout) LayoutInflater.from(master).inflate(R.layout.perso,null);
        prenom      = layout.findViewById(R.id.perso_prenom);
        nom         = layout.findViewById(R.id.perso_nom);

        retour      = layout.findViewById(R.id.perso_retour);
        image       = layout.findViewById(R.id.perso_image);
        fondimage   = layout.findViewById(R.id.perso_fondimage);
        pseudo      = layout.findViewById(R.id.perso_pseudo);
        rename      = layout.findViewById(R.id.perso_rename);

        rename.setOnClickListener(this);
        retour.setOnClickListener(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setVisibility(View.INVISIBLE);
    }

    private void reinit()
    {
        LinearLayout tmp = (LinearLayout) LayoutInflater.from(master).inflate(R.layout.perso,null);
        init(tmp,prenom,R.id.perso_prenom);
        init(tmp,nom,R.id.perso_nom);
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
        image.setImageResource          (master.getResources().getIdentifier(personnage.image(),                "drawable",master.getPackageName()));
        fondimage.setBackgroundResource (master.getResources().getIdentifier("lvl_"+personnage.niveau(),  "drawable",master.getPackageName()));

        change(prenom,personnage.nom());
        change(nom,"TEST");

        layout.setVisibility(View.VISIBLE);
    }

    private void change(TextView tv,String s)
    {
        tv.setText(tv.getText().toString().replace("NAN",s));
    }

    public LinearLayout layout(){return layout;}

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
