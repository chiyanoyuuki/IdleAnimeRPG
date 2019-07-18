package com.chiya.idleanimerpg;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chiya.Activities.Master;
import com.chiya.BDD.BDDDialogue;
import com.chiya.Layouts.NewFrameLayout;
import com.chiya.Activities.R;

import java.util.ArrayList;

public class Dialogue extends NewFrameLayout
{
    private String pseudo;
    private TextView texte;
    private ImageView image, bulle;
    private ArrayList<BDDDialogue> dialogues;
    private BDDDialogue dialogue;
    private int visible;
    private Reactions reactions;

    public Dialogue(Master master)
    {
        super(master);
        init();
    }

    private void init()
    {
        reactions = new Reactions(db, master);
        dialogues = new ArrayList<>();
        initLayout();
        this.setVisibility(View.INVISIBLE);
    }

    public void reinit(ArrayList<BDDDialogue> dialogues)
    {
        if(dialogues.size()>0)
        {
            this.setVisibility(View.VISIBLE);
            this.dialogues = dialogues;
            this.dialogue = dialogues.get(0);
            refresh();
        }
    }

    private void initLayout()
    {
        Constructor c = new Constructor(master);
        ImageView img           = c.img();
        LinearLayout all        = c.all();
        FrameLayout haut        = c.haut();
        bulle                   = c.bulle();
        texte                   = c.texte();
        LinearLayout bas        = c.bas();
        image                   = c.image();

        haut.addView(bulle);
        haut.addView(texte);
        bas.addView(image);
        all.addView(haut);
        all.addView(bas);

        this.setClickable(true);
        this.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changeText();
            }
        });

        this.addView(img);
        this.addView(all);
    }

    private void refresh()
    {
        bulle.setVisibility(INVISIBLE);
        image.setVisibility(INVISIBLE);
        String s = dialogue.texte();
        s = conditions(s);
        if(s.equals(""))return;
        s = reactions(s);
        texte.setTextColor(Color.parseColor("#505050"));
        texte.setTypeface(texte.getTypeface(), Typeface.BOLD);
        s = s.replaceAll("#pseudo",db.compte().selectCompte().pseudo());
        visible = -1;
        if(s.matches("^\\([01]\\).*"))
        {
            visible = Integer.parseInt(s.substring(1,2));
            s = s.substring(3);
        }
        long orientation = dialogue.orientation();
        String perso = dialogue.image();
        texte.setText(s);
       if(orientation==0)
       {
           bulle.setVisibility(VISIBLE);
           image.setVisibility(VISIBLE);
           image.setImageResource(this.getContext().getResources().getIdentifier(perso,"drawable",this.getContext().getPackageName()));
           image.setScaleX(-1);
           animate(-500);
       }
       else if(orientation==-100)
       {
           setpseudo();
       }
       else if(orientation==-1)
       {
           texte.setTextColor(Color.parseColor("#ffffff"));
           texte.setTypeface(texte.getTypeface(), Typeface.BOLD_ITALIC);
       }
       else
       {
           bulle.setVisibility(VISIBLE);
           image.setVisibility(VISIBLE);
           image.setImageResource(this.getContext().getResources().getIdentifier(perso,"drawable",this.getContext().getPackageName()));
           image.setScaleX(1);
           animate(500);
       }

       if(visible!=-1)
           image.setColorFilter(Color.parseColor("#000000"));
       else
           image.clearColorFilter();
    }

    private String conditions(String s)
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
                passText();
                if(dialogues.indexOf(dialogue)!=dialogues.size()-1)s = dialogue.texte();
                else {db.dialogue().readDialogue(""+dialogue.id());this.setVisibility(View.INVISIBLE);return "";}
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
                image.clearAnimation();
            }
        });
        animation.setFillAfter(true);
        image.setAnimation(animation);
    }

    private void setpseudo()
    {
        bulle.setVisibility(INVISIBLE);
        image.setVisibility(INVISIBLE);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setIcon(R.drawable.icone_reputmonde);
        builder.setTitle("Pseudo :");
        builder.setMessage("Veuillez rentrer votre pseudo");

        final EditText input = new EditText(this.getContext());
        input.setText("Nouveau joueur");
        input.setPadding(50,0,0,0);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                pseudo = input.getText().toString();
                db.compte().changepseudo(pseudo);
                master.refreshAcc();
                changeText();
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

    private void changeText()
    {
        int ind = dialogues.indexOf(dialogue);

        if(ind+1<dialogues.size())
        {
            BDDDialogue tmp = dialogues.get(ind+1);
            if(tmp.id()!=dialogue.id())
            {
                db.dialogue().readDialogue(""+dialogue.id());
                reactions.set();
            }
            dialogue = tmp;
            refresh();
        }
        else {
            db.dialogue().readDialogue(""+dialogue.id());
            reactions.set();
            this.setVisibility(View.INVISIBLE);
        }
    }

    private void passText()
    {
        int ind = dialogues.indexOf(dialogue);

        if(ind+1<dialogues.size())
        {
            BDDDialogue tmp = dialogues.get(ind+1);
            if(tmp.id()!=dialogue.id())
            {
                db.dialogue().readDialogue(""+dialogue.id());
                reactions.set();
            }
            dialogue = tmp;
        }
        else {
            db.dialogue().readDialogue(""+dialogue.id());
            reactions.set();
            this.setVisibility(View.INVISIBLE);
        }
    }
}
