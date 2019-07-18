package com.chiya.Layouts;

import android.content.Context;
import android.widget.LinearLayout;

import com.chiya.Activities.Master;
import com.chiya.Activities.NewActivity;
import com.chiya.BDD.BDD;

public abstract class NewLinearLayout extends LinearLayout
{
    protected Master master;
    protected BDD db;
    protected String packageName;

    public NewLinearLayout(Master master)
    {
        super(master);
        this.master         = master;
        this.db             = master.getDb();
        this.packageName    = master.getPackageName();
    }
}
