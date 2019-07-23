package com.chiya.idleanimerpg;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chiya.Activities.R;
import com.chiya.Activities.TestActivityFragment;
import com.chiya.BDD.BDD;
import java.util.ArrayList;

public class Up implements View.OnClickListener
{
    private ImageView monde, pays, gentil, mechant;
    private FrameLayout layout;
    private TextView reput, level, grade, desc, bouton;
    private LinearLayout recomp;
    private TestActivityFragment master;
    private ArrayList<String[]> ups;

    public Up(TestActivityFragment master)
    {
        layout  = (FrameLayout) LayoutInflater.from(master).inflate(R.layout.up,null);
        level   = layout.findViewById(R.id.up_level);
        reput   = layout.findViewById(R.id.up_reput);
        grade   = layout.findViewById(R.id.up_grade);
        desc    = layout.findViewById(R.id.up_desc);
        bouton  = layout.findViewById(R.id.up_bouton);
        recomp  = layout.findViewById(R.id.up_recomp);

        monde   = layout.findViewById(R.id.up_monde);
        pays    = layout.findViewById(R.id.up_pays);
        gentil  = layout.findViewById(R.id.up_gentil);
        mechant = layout.findViewById(R.id.up_mechant);

        this.master = master;
        ups = new ArrayList<>();
        layout.setVisibility(View.INVISIBLE);

        bouton.setOnClickListener(this);
    }

    public void refresh()
    {
        BDD db = master.getDb();
        ups = db.up().select();
        if(ups.size()>0)up();
    }

    private void up()
    {
        recomp.removeAllViews();
        monde.setVisibility(View.INVISIBLE);
        pays.setVisibility(View.INVISIBLE);
        gentil.setVisibility(View.INVISIBLE);
        mechant.setVisibility(View.INVISIBLE);

        String[] up = ups.get(0);
        layout.setVisibility(View.VISIBLE);
        if(up[1].equals("0"))
        {
            gentil.setVisibility(View.VISIBLE);
            reput.setText("Alignement du bien");
        }
        else if(up[1].equals("1"))
        {
            mechant.setVisibility(View.VISIBLE);
            reput.setText("Alignement du mal");
        }
        else if(!up[0].equals("-1"))
        {
            pays.setVisibility(View.VISIBLE);
            reput.setText("Réputation de pays");
        }
        else
        {
            monde.setVisibility(View.VISIBLE);
            reput.setText("Réputation mondiale");
        }

        level.setText(up[2]);
        grade.setText(up[3]);
        desc.setText(up[4]);
        for(String s : up[5].split(","))addRecomp(s);
    }

    private void addRecomp(String recomp)
    {
        TextView tmp = new TextView(master);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);
        lp.setMargins(0,0,0,10);
        tmp.setLayoutParams(lp);
        tmp.setTextColor(Color.parseColor("#ffffff"));
        tmp.setText("- "+recomp);
        this.recomp.addView(tmp);
    }

    public FrameLayout layout(){return layout;}

    public void onClick(View view)
    {
        String[] up = ups.get(0);
        master.getDb().up().remove(up);
        ups.remove(0);

        if(ups.size()>0) up();
        else layout.setVisibility(View.INVISIBLE);
    }
}
