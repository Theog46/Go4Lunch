package com.example.go4lunch;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.go4lunch.fragments.ListViewFragment;
import com.example.go4lunch.fragments.MapsFragment;
import com.example.go4lunch.fragments.WorkmatesFragment;

public class PageAdapter extends FragmentPagerAdapter {

    public PageAdapter(FragmentManager mgr) {
        super(mgr);
    }

    @Override
    public int getCount() {
        return(3);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MapsFragment();
            case 1:
                return new ListViewFragment();
            case 2:
                return new WorkmatesFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Map View";
            case 1:
                return "List View";
            case 2:
                return "Workmates";
            default:
                return null;
        }
    }
}
