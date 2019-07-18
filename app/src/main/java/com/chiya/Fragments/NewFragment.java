package com.chiya.Fragments;
import androidx.fragment.app.Fragment;
import com.chiya.Activities.TestActivityFragment;

public class NewFragment extends Fragment
{
    public void click(long anime, long partie)
    {
        TestActivityFragment master = (TestActivityFragment)getActivity();
        master.changeEcran(anime,partie);
    }
}
