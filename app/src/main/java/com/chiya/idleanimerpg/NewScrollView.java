package com.chiya.idleanimerpg;

import android.content.Context;
import android.widget.ScrollView;

public abstract class NewScrollView extends ScrollView
{
    protected Master master;
    protected BDD db;
    protected Context context;
    protected String packageName;

    public NewScrollView(Master master)
    {
        super(master);
        this.master         = master;
        this.db             = master.getDb();
        this.context        = master;
        this.packageName    = master.getPackageName();
    }
}
