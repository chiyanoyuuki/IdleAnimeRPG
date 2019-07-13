package com.chiya.idleanimerpg;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ScrollViewMonde extends NewScrollView
{
    public ScrollViewMonde(Master master) {
        super(master);
        init();
    }

    private void init()
    {
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1));

        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        Cursor cursor = db.selectAll("anime");
        while (cursor.moveToNext()) {
            BDDAnime anime = new BDDAnime(cursor);
            layout.addView(new FrameLayoutAnimeChoice(master, anime, "#ffaaaa"));
        }
        cursor.close();

        this.addView(layout);
    }
}