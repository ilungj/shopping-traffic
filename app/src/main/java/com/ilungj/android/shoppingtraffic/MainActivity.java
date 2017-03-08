package com.ilungj.android.shoppingtraffic;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.ilungj.android.shoppingtraffic.com.example.ilung.myapplication.fragment.BlankFragment;
import com.ilungj.android.shoppingtraffic.com.example.ilung.myapplication.fragment.CustomFragment;
import com.ilungj.android.shoppingtraffic.com.example.ilung.myapplication.service.LocationService;
import com.ilungj.android.shoppingtraffic.com.example.ilung.myapplication.fragment.MainFragment;
import com.ilungj.android.shoppingtraffic.com.example.ilung.myapplication.fragment.SubFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    CustomViewPager pager;
    TabLayout tabLayout;
    List<Fragment> fragmentList;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this.getApplicationContext(), LocationService.class);
        intent.setAction("android.intent.action.START_LOC");
        intent.setType("text/plain");
        startService(intent);

        /*
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isMyServiceRunning(LocationService.class)) {
                    Log.d("LOC_SERVICE", "YES");
                    intent.setAction("android.intent.action.CHECK_LOC");
                    intent.setType("text/plain");
                    startService(intent);
                } else {
                    Log.d("LOC_SERVICE", "NO");
                }
            }
        });
        */

        /*
        Singleton.getInstance(this).extractJson();
        Log.d("MainActivity", "onCreate function is called");
        */

        pager = (CustomViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("onPageSelected", "is called");
                CustomFragment previousFragment = null;
                Class<?> previousType = null;

                if(position - 1 >= 0) {
                    if(pager.isSwipingUp) {
                        previousFragment = getCurrentFragmentOfId(position + 1);
                    } else if(!pager.isSwipingUp) {
                        Log.d("Swiping up", "Working");
                        previousFragment = getCurrentFragmentOfId(position - 1);
                    }
                    //previousType = previousFragment.getClass();
                }

                /*
                if(previousType == MainFragment.class) {
                    final MainFragment mainFragment = (MainFragment) previousFragment;
                    //mainFragment.register();
                }
                */
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                    if (pager.getCurrentItem() == 0)
                    {
                        // Hide the keyboard.
                        ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(pager.getWindowToken(), 0);
                    }

            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(pager, true);

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        Log.d("MainActivity", "onAttachFragment function is called");
        super.onAttachFragment(fragment);

        if(fragmentList == null) {
            fragmentList = new ArrayList<Fragment>();
        }

        Log.d("MainActivity", "fragmentList is not null");
            fragmentList.add(fragment);

    }

    CustomFragment getCurrentFragmentOfId(int id) {
        Log.d("MainActivity", "getCurrentFragmentofId function called");
        //List<Fragment> activeFragments = getActiveFragments();
        for(Fragment f : fragmentList) {
            CustomFragment cf = (CustomFragment) f;
            if(id == cf.getPosition()) {
                return cf;
            }
        }
        return null;
    }

    public static class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        //String tabTitles[] = {"Page 1", "Page 2", "Page 3"};

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    MainFragment mainFragment = new MainFragment();
                    Bundle newBundle = new Bundle();
                    newBundle.putInt("id", (position + 1));
                    mainFragment.setArguments(newBundle);
                    mainFragment.setPosition(position);
                    return mainFragment;
                case 1:
                    SubFragment subFragment = new SubFragment();
                    subFragment.setPosition(position);
                    return subFragment;
                default:
                    BlankFragment blankFragment = new BlankFragment();
                    blankFragment.setPosition(position);
                    return blankFragment;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

    }
}
