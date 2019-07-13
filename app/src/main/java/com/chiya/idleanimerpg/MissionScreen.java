package com.chiya.idleanimerpg;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MissionScreen extends FrameLayout
{
    public MissionScreen(Context context)
    {
        super(context);
        init();
    }

    private void init()
    {
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


}
