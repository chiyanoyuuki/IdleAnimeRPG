package com.chiya.Layouts;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chiya.Activities.R;
import com.chiya.Activities.TestActivityFragment;
import com.chiya.BDD.BDD;
import com.chiya.BDD.BDDReputs;

public class LayoutBarres
{
    private TestActivityFragment master;
    private String packageName;
    private ImageView[][] barres;

    public LayoutBarres(TestActivityFragment master)
    {
        this.master = master;
        barres = new ImageView[3][2];
        barres[0][0] = master.findViewById(R.id.accueil_b00);
        barres[0][1] = master.findViewById(R.id.accueil_b01);
        barres[1][0] = master.findViewById(R.id.accueil_b10);
        barres[1][1] = master.findViewById(R.id.accueil_b11);
        barres[2][0] = master.findViewById(R.id.accueil_b20);
        barres[2][1] = master.findViewById(R.id.accueil_b21);

        packageName = master.getPackageName();

        BDD db = master.getDb();
        BDDReputs tmp = db.reputs().selectReput(-1,-1);
        refresh("progressv",0,tmp.actual(),tmp.need());
        refresh(1);
        refresh(2);
    }

    public void refresh()
    {
        long anime = master.getAnime();
        long partie = master.getPartie();
        BDD db = master.getDb();
        BDDReputs tmp = db.reputs().selectReput(-1,-1);
        refresh("progressv",0,tmp.actual(),tmp.need());
        if(anime!=-1)
        {
            tmp = db.reputs().selectReput(anime,-1);
            refresh("progressj",1,tmp.actual(),tmp.need());
            if(partie!=-1)
            {
                tmp = db.reputs().selectReput(anime,partie);
                if(partie==0)refresh("progressb",2,tmp.actual(),tmp.need());
                else if(partie==1)refresh("progressr",2,tmp.actual(),tmp.need());
            }
            else
            {
                refresh(2);
            }
        }
        else
        {
            refresh(1);
            refresh(2);
        }
    }

    private void refresh(String couleur, int i, long actual, long need)
    {
        int pct = (int)((actual*1.0)/(need*1.0)*100.0);

        barres[i][0].setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,pct));
        barres[i][0].setImageResource(master.getResources().getIdentifier(couleur,"drawable",packageName));

        barres[i][1].setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,100-pct));
        barres[i][1].setImageResource(master.getResources().getIdentifier(couleur,"drawable",packageName));
    }

    private void refresh(int i)
    {
        barres[i][0].setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,100));
        barres[i][0].setImageResource(R.drawable.progressg);

        barres[i][1].setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,0));
        barres[i][1].setImageResource(R.drawable.progressg);
    }
}
