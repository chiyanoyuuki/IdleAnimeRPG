package com.chiya.Layouts;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiya.Activities.R;
import com.chiya.Activities.TestActivityFragment;
import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDDialogue;
import com.chiya.idleanimerpg.Conditions;
import com.chiya.idleanimerpg.Reactions;
import com.chiya.idleanimerpg.Up;

import java.util.ArrayList;

public class LayoutDialogue implements View.OnClickListener
{
    private TestActivityFragment master;
    private FrameLayout layout;
    private TextView bulle, off;
    private ImageView perso;
    private ArrayList<BDDDialogue> dialogues;
    private BDDDialogue actual;
    private Reactions reactions;

    public LayoutDialogue(TestActivityFragment master)
    {
        this.master = master;
        layout = (FrameLayout)LayoutInflater.from(master).inflate(R.layout.dialogue,null);
        layout.setOnClickListener(this);
        bulle = layout.findViewById(R.id.dialogue_textebulle);
        off = layout.findViewById(R.id.dialogue_texteoff);
        perso = layout.findViewById(R.id.dialogue_perso);
        BDD db = master.getDb();
        this.reactions = new Reactions(master);
    }

    public void set(ArrayList<BDDDialogue> dialogues)
    {
        if(dialogues.size()>0)
        {
            layout.setVisibility(View.VISIBLE);
            this.dialogues = dialogues;
            this.actual = dialogues.get(0);
            refresh();
        }
    }

    private void refresh()
    {
        off.setVisibility(View.INVISIBLE);
        bulle.setVisibility(View.INVISIBLE);
        perso.setVisibility(View.INVISIBLE);

        String texte = actual.texte();
        BDD db = master.getDb();

        texte = condition(texte,db);
        if(texte.equals(""))return;
        texte = reactions(texte);
        texte = texte.replaceAll("#pseudo",db.compte().selectCompte().pseudo());
        int visible = -1;
        if(texte.matches("^\\([01]\\).*"))
        {
            visible = Integer.parseInt(texte.substring(1,2));
            texte = texte.substring(3);
        }
        long orientation = actual.orientation();

        if(orientation==0)
        {
            bulle.setVisibility(View.VISIBLE);
            perso.setVisibility(View.VISIBLE);
            perso.setImageResource(master.getResources().getIdentifier(actual.image(),"drawable",master.getPackageName()));
            perso.setScaleX(-1);
            bulle.setText(texte);
            animate(-500);
        }
        else if(orientation==-100)
        {
            setpseudo();
        }
        else if(orientation==-1)
        {
            off.setVisibility(View.VISIBLE);
            off.setText(texte);
        }
        else
        {
            bulle.setVisibility(View.VISIBLE);
            perso.setVisibility(View.VISIBLE);
            perso.setImageResource(master.getResources().getIdentifier(actual.image(),"drawable",master.getPackageName()));
            perso.setScaleX(1);
            bulle.setText(texte);
            animate(500);
        }

        if(visible!=-1)
            perso.setColorFilter(Color.parseColor("#000000"));
        else
            perso.clearColorFilter();
    }

    private void animate(int i)
    {
        Animation animation = new TranslateAnimation(i,0,0,0);
        animation.setDuration(300);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                perso.clearAnimation();
            }
        });
        animation.setFillAfter(true);
        perso.setAnimation(animation);
    }

    private String condition(String s, BDD db)
    {
        while(s.matches("^\\{[^\\}]+\\}.*"))
        {
            Conditions c = new Conditions(s, db);
            if(c.isok())
            {
                s = s.substring(s.indexOf("}")+1);
            }
            else
            {
                next(false);
                if(dialogues.indexOf(actual)!=dialogues.size()-1)s = actual.texte();
                else {db.dialogue().readDialogue(""+actual.id());layout.setVisibility(View.INVISIBLE);return "";}
            }
        }
        return s;
    }

    private String reactions(String s)
    {
        if(s.matches("^\\[[^\\]]+\\].*"))
        {
            reactions.add(s);
            s = s.substring(s.indexOf("]")+1);
        }
        return s;
    }

    private void validatePseudo(String pseudo)
    {
        BDD db = master.getDb();
        db.compte().changepseudo(pseudo);
        master.refreshAcc();
        next(true);
    }

    private void setpseudo()
    {
        off.setVisibility(View.INVISIBLE);
        bulle.setVisibility(View.INVISIBLE);
        perso.setVisibility(View.INVISIBLE);

        AlertDialog.Builder builder = new AlertDialog.Builder(master);
        builder.setIcon(R.drawable.icone_reputmonde);
        builder.setTitle("Pseudo :");
        builder.setMessage("Veuillez rentrer votre pseudo");

        final EditText input = new EditText(master);
        input.setText("Nouveau joueur");
        input.setPadding(50,0,0,0);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                validatePseudo(input.getText().toString());
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {}
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
            @Override
            public void afterTextChanged(Editable Token)
            {
                if(!Token.toString().matches("[0-9a-zA-Z -]{3,20}"))
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

    private void next(boolean b)
    {
        BDD db = master.getDb();
        int ind = dialogues.indexOf(actual);

        if(ind+1<dialogues.size())
        {
            BDDDialogue tmp = dialogues.get(ind+1);
            if(tmp.id()!=actual.id())
            {
                db.dialogue().readDialogue(""+actual.id());
                reactions.set();
            }
            actual = tmp;
            if(b)refresh();
        }
        else {
            db.dialogue().readDialogue(""+actual.id());
            reactions.set();
            layout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view)
    {
        next(true);
    }

    public FrameLayout layout(){return layout;}
}
