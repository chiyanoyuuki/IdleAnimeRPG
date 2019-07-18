package com.chiya.Layouts;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chiya.Activities.Master;
import com.chiya.Activities.NewActivity;
import com.chiya.BDD.BDDAnime;

public class ScrollViewMonde extends NewScrollView
{
    private LinearLayout layout;

    public ScrollViewMonde(Master master) {
        super(master);
        init();
    }

    private void init()
    {
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        layout = new LinearLayout(this.getContext());
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        Cursor cursor = db.selectAll("anime");
        while (cursor.moveToNext()) {
            BDDAnime anime = new BDDAnime(cursor);
            layout.addView(new FrameChoice(master, "#ffaaaa",!anime.isup(),anime.image(),anime.nom(),anime.id(),-1));
        }
        cursor.close();

        this.addView(layout);
    }

    public void wipe()
    {
        layout.removeAllViews();
        layout = null;
    }
}