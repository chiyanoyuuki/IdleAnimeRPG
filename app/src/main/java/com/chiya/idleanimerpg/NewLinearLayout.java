package com.chiya.idleanimerpg;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public abstract class NewLinearLayout extends LinearLayout
{
    protected Master master;
    protected BDD db;
    protected Context context;
    protected String packageName;

    public NewLinearLayout(Master master)
    {
        super(master);
        this.master         = master;
        this.db             = master.getDb();
        this.context        = master;
        this.packageName    = master.getPackageName();
    }
}
