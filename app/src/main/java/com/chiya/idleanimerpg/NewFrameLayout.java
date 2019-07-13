package com.chiya.idleanimerpg;

import android.content.Context;
import android.widget.FrameLayout;

public abstract class NewFrameLayout extends FrameLayout
{
    protected Master master;
    protected BDD db;
    protected Context context;
    protected String packageName;

    public NewFrameLayout(Master master)
    {
        super(master);
        this.master         = master;
        this.db             = master.getDb();
        this.context        = master;
        this.packageName    = master.getPackageName();
    }
}
