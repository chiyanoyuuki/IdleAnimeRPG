package com.chiya.Layouts;

import android.content.ClipData;
import android.database.Cursor;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chiya.Activities.R;
import com.chiya.Activities.TestActivityFragment;
import com.chiya.BDD.BDDEquipe;

public class Equipe implements View.OnClickListener, View.OnTouchListener, View.OnDragListener
{
    private ImageView retour;
    private TestActivityFragment master;
    private LinearLayout layout, equipe, vertical;
    private View clicked;
    private boolean init;

    public Equipe(TestActivityFragment master)
    {
        this.master = master;
        layout = (LinearLayout)LayoutInflater.from(master).inflate(R.layout.equipe,null);
        equipe = layout.findViewById(R.id.equipe_team);
        vertical = layout.findViewById(R.id.equipe_vertical);
        retour = layout.findViewById(R.id.equipe_retour);

        retour.setOnClickListener(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setVisibility(View.INVISIBLE);
    }

    public void init()
    {
        Cursor cursor = master.getDb().equipe().selectEquipe();
        while(cursor.moveToNext())
        {
            BDDEquipe tmp = new BDDEquipe(cursor);
            //System.out.println(tmp.toString());
            equipe.addView(image(tmp.image(),"lvl_"+tmp.niveau(),""+tmp.id(),false));
        }
        cursor.close();

        int cpt = 0;
        LinearLayout horizontal = horizontal();
        cursor = master.getDb().equipe().selectAll();
        while(cursor.moveToNext())
        {
            BDDEquipe tmp = new BDDEquipe(cursor);
            if(cpt%4==0&&cpt!=0)
            {
                vertical.addView(horizontal);
                horizontal = horizontal();
            }
            horizontal.addView(image(tmp.image(),"lvl_"+tmp.niveau(),""+tmp.id(),true));
            cpt++;
        }
        cursor.close();
        vertical.addView(horizontal);

        layout.setVisibility(View.VISIBLE);
        init = true;
    }

    private ImageView image(String image, String fond, String tag, boolean bot)
    {
        ImageView tmp = new ImageView(master);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(200,200);
        lp.setMargins(0,0,20,0);
        tmp.setLayoutParams(lp);
        tmp.setImageResource(master.getResources().getIdentifier(image,"drawable",master.getPackageName()));
        tmp.setBackgroundResource(master.getResources().getIdentifier(fond,"drawable",master.getPackageName()));
        tmp.setScaleType(ImageView.ScaleType.CENTER_CROP);
        tmp.setPadding(200,0,200,0);
        tmp.setOnClickListener(this);
        tmp.setOnTouchListener(this);
        tmp.setOnDragListener(this);
        if(bot)tmp.setTag("BOT_"+tag);
        else tmp.setTag("TEAM_"+tag);
        return tmp;
    }

    private LinearLayout horizontal()
    {
        LinearLayout tmp = new LinearLayout(master);
        tmp.setOrientation(LinearLayout.HORIZONTAL);
        tmp.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 250));
        return tmp;
    }

    public LinearLayout layout(){return layout;}
    public boolean isVisible(){return layout.getVisibility()==View.VISIBLE;}
    public void invis(){layout.setVisibility(View.INVISIBLE);equipe.removeAllViews();vertical.removeAllViews();init=false;}

    @Override
    public void onClick(View view)
    {
        if(view==retour)
        {
            invis();
        }
        else
        {
            String s = view.getTag().toString();
            s = s.substring(s.indexOf("_")+1);
            BDDEquipe perso = master.getDb().equipe().select(s);
            master.showPerso(perso);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event)
    {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onDrag(View v, DragEvent event)
    {
        switch (event.getAction())
        {
            case DragEvent.ACTION_DROP:
                //System.out.println(v.getTag().toString());
                View view = (View) event.getLocalState();
                change(v.getTag().toString(),view.getTag().toString());
                break;
            default:
                break;
        }
        return true;
    }

    private void change(String s1, String s2)
    {
        if(!init)return;
        boolean b1 = s1.startsWith("TEAM_");s1=s1.substring(s1.indexOf("_")+1);
        boolean b2 = s2.startsWith("TEAM_");s2=s2.substring(s2.indexOf("_")+1);

        if(!s1.equals(s2))
        {
            if(b1&&b2){master.getDb().equipe().switchteam(s1,s2);master.refreshMuraille();invis();init();}
            else if(!master.getDb().equipe().select(s1).used()&&!master.getDb().equipe().select(s2).used())
            {
                if(b1){if(master.getDb().equipe().switchnew(s1,s2)){master.refreshMuraille();invis();init();}else{master.getDb().equipe().switchteam(s1,s2);master.refreshMuraille();invis();init();}}
                else if(b2){if(master.getDb().equipe().switchnew(s2,s1)){master.refreshMuraille();invis();init();}else{master.getDb().equipe().switchteam(s1,s2);master.refreshMuraille();invis();init();}}
            }
        }
    }
}
