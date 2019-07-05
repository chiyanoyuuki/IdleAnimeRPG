package com.chiya.idleanimerpg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Dialogue
{
    private String pseudo;
    private FrameLayout derriere;
    private TextView texte;
    private ImageView image, bulle, image0;
    private Context context;
    private BDD db;
    private ArrayList<BDDDialogue> dialogues;
    private BDDDialogue dialogue;
    private Master master;
    private int visible;

    public Dialogue(Context context, BDD db, ArrayList<BDDDialogue> dialogues, Master master)
    {
        this.master = master;
        this.context = context;
        this.db = db;
        derriere                = new FrameLayout(context);
        initLayout();
        if(dialogues.size()>0) {
            this.dialogues = dialogues;
            this.dialogue = dialogues.get(0);
            refresh();
        }
        else
        {
            derriere.setVisibility(View.INVISIBLE);
        }
    }

    public void reinit(ArrayList<BDDDialogue> dialogues)
    {
        if(dialogues.size()>0) {
            derriere.setVisibility(View.VISIBLE);
            this.dialogues = dialogues;
            this.dialogue = dialogues.get(0);
            refresh();
        }
    }

    private void initLayout()
    {
        ImageView img           = new ImageView(context);
        LinearLayout all        = new LinearLayout(context);
        FrameLayout haut        = new FrameLayout(context);
        bulle                   = new ImageView(context);
        texte                   = new TextView(context);
        LinearLayout bas        = new LinearLayout(context);
        image0                  = new ImageView(context);
        image                   = new ImageView(context);

        all.setOrientation(LinearLayout.VERTICAL);
        bas.setOrientation(LinearLayout.HORIZONTAL);

        texte.setLayoutParams   (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        all.setLayoutParams     (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        bulle.setLayoutParams   (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        haut.setLayoutParams    (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1));
        bas.setLayoutParams     (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1));
        image0.setLayoutParams  (new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1));
        image.setLayoutParams   (new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1));

        haut.setPadding(0,270,0,0);

        img.setBackgroundColor(Color.parseColor("#505050"));
        img.setAlpha(0.9f);

        texte.setPadding(150,0,150,80);
        texte.setGravity(Gravity.CENTER);
        texte.setTextSize(15);

        haut.addView(bulle);
        haut.addView(texte);
        bas.addView(image0);
        bas.addView(image);
        all.addView(haut);
        all.addView(bas);

        derriere.setFocusable(true);
        derriere.setClickable(true);
        derriere.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changeText();
            }
        });

        derriere.addView(img);
        derriere.addView(all);
    }

    private void refresh()
    {
        String s = dialogue.texte();
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
                else {db.readDialogue(""+dialogue.id());derriere.setVisibility(View.INVISIBLE);return;}
            }
        }
        bulle.setImageResource(R.drawable.bulle);
        texte.setTextColor(Color.parseColor("#505050"));
        texte.setTypeface(texte.getTypeface(), Typeface.BOLD);
        s = s.replaceAll("#pseudo",db.selectCompte().pseudo());
        visible = -1;
        if(s.matches("^\\([01]\\):.*"))
        {
            visible = Integer.parseInt(s.substring(1,2));
            s = s.substring(4);
        }

        long orientation = dialogue.orientation();
        String perso = dialogue.image();
        texte.setText(s);
        long scaleX = dialogue.scalex();
        long scaleY = dialogue.scaley();
       if(orientation==0)
       {
           image0.setImageResource(context.getResources().getIdentifier(perso,"drawable",context.getPackageName()));
           image0.setScaleX(-1*scaleX);
           image0.setScaleY(scaleY);
           bulle.setScaleX(-1);
           image.setImageResource(0);
       }
       else if(orientation==-100)
       {
           setpseudo();
       }
       else if(orientation==-1)
       {
           bulle.setImageResource(0);
           image.setImageResource(0);
           image0.setImageResource(0);
           texte.setTextColor(Color.parseColor("#ffffff"));
           texte.setTypeface(texte.getTypeface(), Typeface.BOLD_ITALIC);
       }
       else
       {
           image.setImageResource(context.getResources().getIdentifier(perso,"drawable",context.getPackageName()));
           image.setScaleX(scaleX);
           image.setScaleY(scaleY);
           bulle.setScaleX(1);
           image0.setImageResource(0);
       }

       if(visible!=-1)
       {
           image0.setColorFilter(Color.parseColor("#000000"));
           image.setColorFilter(Color.parseColor("#000000"));
       }
       else
       {
           image0.clearColorFilter();
           image.clearColorFilter();
       }
    }

    private void setpseudo()
    {
        bulle.setImageResource(0);
        image.setImageResource(0);
        image0.setImageResource(0);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.icone_reputmonde);
        builder.setTitle("Pseudo :");
        builder.setMessage("Veuillez rentrer votre pseudo");

        final EditText input = new EditText(context);
        input.setText("Nouveau joueur");
        input.setPadding(50,0,0,0);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                pseudo = input.getText().toString();
                db.changepseudo(pseudo);
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

    public FrameLayout getView()
    {
        return derriere;
    }

    private void changeText()
    {
        if(visible==1)
        {
            //CODER POUR VOIR PERSO
        }
        int ind = dialogues.indexOf(dialogue);

        if(ind+1<dialogues.size())
        {
            BDDDialogue tmp = dialogues.get(ind+1);
            if(tmp.id()!=dialogue.id())db.readDialogue(""+dialogue.id());
            dialogue = tmp;
            refresh();
        }
        else {
            db.readDialogue(""+dialogue.id());
            derriere.setVisibility(View.INVISIBLE);
        }
    }

    private void passText()
    {
        int ind = dialogues.indexOf(dialogue);

        if(ind+1<dialogues.size())
        {
            BDDDialogue tmp = dialogues.get(ind+1);
            if(tmp.id()!=dialogue.id())db.readDialogue(""+dialogue.id());
            dialogue = tmp;
        }
        else {
            db.readDialogue(""+dialogue.id());
            derriere.setVisibility(View.INVISIBLE);
        }
    }
}
