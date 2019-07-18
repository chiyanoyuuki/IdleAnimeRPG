package com.chiya.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.chiya.BDD.BDD;
import com.chiya.Layouts.FrameLayoutForeground;

public abstract class NewActivity extends Activity
{
    protected FrameLayoutForeground foreground;
    protected FrameLayout background;
    protected BDD db;
    protected FrameLayout mid;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_main);

        init();
    }

    private void init()
    {
        db          = new BDD(this);
        background  = findViewById(R.id.accueil_topecran);
        //foreground = new FrameLayoutForeground(this);

        initMid();
        addMid();
        addForeground();
    }

    private void initMid()
    {
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        FrameLayout top = new FrameLayout(this);
        //top.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, foreground.getTopSize()));
        layout.addView(top);

        mid = new FrameLayout(this);
        mid.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1));
        layout.addView(mid);

        FrameLayout bot = new FrameLayout(this);
        bot.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, foreground.getBotSize()+110));
        layout.addView(bot);

        background.addView(layout);
    }

    protected abstract void addMid();

    private void addForeground()
    {
        background.addView(foreground);
    }

    public BDD getDb(){return db;}

    public void changeEcran(long i, long j)
    {
        db.close();
        if(i==-1&&j==-1)goToAccueil();
        else if(i!=-1&&j==-1)goToPays(i);
        else if(i!=-1&&j!=-1)goToMissions(i,j);
    }
    private void goToAccueil()
    {
        Intent intent = new Intent(this,EcranMonde.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
    private void goToPays(long i)
    {
        Intent intent = new Intent(this,EcranPays.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("animeid",i);
        startActivity(intent);
    }
    private void goToMissions(long i, long j)
    {
        Intent intent = new Intent(this, EcranMissionsHist.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("animeid",i);
        intent.putExtra("partieid",j);
        startActivity(intent);
    }
}
