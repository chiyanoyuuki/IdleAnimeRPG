package com.chiya.Layouts;

import android.widget.FrameLayout;

import com.chiya.Activities.TestActivityFragment;
import com.chiya.BDD.BDD;

public abstract class NewFrameLayout extends FrameLayout
{
    protected TestActivityFragment master;
    protected BDD db;
    protected String packageName;

    public NewFrameLayout(TestActivityFragment master)
    {
        super(master);
        this.master         = master;
        this.db             = master.getDb();
        this.packageName    = master.getPackageName();
    }
}
