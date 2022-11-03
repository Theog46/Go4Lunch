package com.example.go4lunch;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.go4lunch.fragments.Chat.MessagesFragment;
import com.example.go4lunch.fragments.ListViewFragment;
import com.example.go4lunch.fragments.MapsFragment;
import com.example.go4lunch.fragments.WorkmatesFragment;

public class PageAdapter extends FragmentPagerAdapter {


    public PageAdapter(FragmentManager mgr) {
        super(mgr);

    }



    @Override
    public int getCount() {
        return(4);
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
            case 3:
                return new MessagesFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return MainApp.getApp().getApplicationContext().getString(R.string.mapView);
            case 1:
                return MainApp.getApp().getApplicationContext().getString(R.string.listView);
            case 2:
                return MainApp.getApp().getApplicationContext().getString(R.string.workmate);
            case 3:
                return MainApp.getApp().getApplicationContext().getString(R.string.chat);
            default:
                return null;
        }
    }
}
