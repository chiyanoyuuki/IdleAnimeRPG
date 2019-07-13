package com.chiya.idleanimerpg;

import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends Master
{
    /*private LinearLayout centerlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addViews();
        //addForeground();
        verifStart();
    }

    public void addViews()
    {
        centerlayout = new LinearLayout(this);
        centerlayout.setOrientation(LinearLayout.VERTICAL);
        centerlayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1));
        mainlayout.addView(addHeader());
        mainlayout.addView(addReputs(-1,-1));
        addBot();
        mainlayout.addView(addFooter(true));
        background.addView(mainlayout);
    }

    public void refreshMuraille(){}

    public void verifStart()
    {
        ArrayList<BDDDialogue> dialogues = db.selectAllDialogues(""+-1,""+-1);
        Dialogue textes = new Dialogue(this, db, dialogues,this);
        background.addView(textes.getView());
    }

    private void addBot()
    {
        mainlayout.addView(new LinearLayoutMonde(this));
    }
    public void refreshPersos(){}*/
}