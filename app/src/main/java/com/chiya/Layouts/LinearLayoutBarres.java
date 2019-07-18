package com.chiya.Layouts;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chiya.Activities.Master;
import com.chiya.Activities.NewActivity;
import com.chiya.BDD.BDDReputs;
import com.chiya.Activities.R;

public class LinearLayoutBarres extends NewLinearLayout
{
    private LinearLayout[] bars;
    private ImageView[][] barres;
    private final int size = 45;

    public LinearLayoutBarres(Master master)
    {
        super(master);
        init();
    }

    private void init()
    {
        bars = new LinearLayout[3];
        barres = new ImageView[3][2];
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, size));
        this.setOrientation(VERTICAL);

        BDDReputs tmp = db.reputs().selectReput(-1,-1);
        addReput("progressv", tmp.actual(),tmp.need(),0);
        addReput("progressg",100,100,1);
        addReput("progressg",100,100,2);
    }

    private void addReput(String couleur, long actual, long need, int i)
    {
        LinearLayout bars = new LinearLayout(this.getContext());
        bars.setOrientation(LinearLayout.HORIZONTAL);
        bars.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, size/3));

        int pct = (int)((actual*1.0)/(need*1.0)*100.0);

        ImageView b1 = new ImageView(this.getContext());
        b1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,pct));
        b1.setImageResource(this.getResources().getIdentifier(couleur,"drawable",packageName));
        b1.setScaleType(ImageView.ScaleType.FIT_XY);

        ImageView b2 = new ImageView(this.getContext());
        b2.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,100-pct));
        b2.setImageResource(this.getResources().getIdentifier(couleur,"drawable",packageName));
        b2.setScaleType(ImageView.ScaleType.FIT_XY);
        b2.setAlpha(0.5f);

        this.bars[i]=bars;
        barres[i][0] = b1;
        barres[i][1] = b2;

        bars.addView(b1);
        bars.addView(b2);

        this.addView(bars);
    }

    public void refreshbarres(long i, long j)
    {
        BDDReputs tmp = db.reputs().selectReput(-1,-1);
        refresh("progressv",0, tmp.actual(), tmp.need());
        if(i!=-1)
        {
            tmp = db.reputs().selectReput(i,-1);
            refresh("progressj",1, tmp.actual(), tmp.need());

            if(j!=-1)
            {
                tmp = db.reputs().selectReput(i,j);
                if(j==0)        refresh("progressb",2, tmp.actual(), tmp.need());
                else if(j==1)   refresh("progressr",2, tmp.actual(), tmp.need());
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
        barres[i][0].setImageResource(this.getResources().getIdentifier(couleur,"drawable",packageName));

        barres[i][1].setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,100-pct));
        barres[i][1].setImageResource(this.getResources().getIdentifier(couleur,"drawable",packageName));
    }

    private void refresh(int i)
    {
        barres[i][0].setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,100));
        barres[i][0].setImageResource(R.drawable.progressg);

        barres[i][1].setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,0));
        barres[i][1].setImageResource(R.drawable.progressg);
    }

    public int getSize(){return size;}
}
