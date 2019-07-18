package com.chiya.Layouts;

import android.content.Context;
import android.widget.FrameLayout;

import com.chiya.Activities.Master;
import com.chiya.Activities.NewActivity;
import com.chiya.BDD.BDD;

public abstract class NewFrameLayout extends FrameLayout
{
    protected Master master;
    protected BDD db;
    protected String packageName;

    public NewFrameLayout(Master master)
    {
        super(master);
        this.master         = master;
        this.db             = master.getDb();
        this.packageName    = master.getPackageName();
    }
}
