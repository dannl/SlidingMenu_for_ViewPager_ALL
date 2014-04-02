package com.jeremyfeinstein.slidingmenu.example;

import com.jeremyfeinstein.slidingmenu.example.fragments.ColorFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.TouchCallback;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;

public class ViewPagerActivity extends BaseActivity {

    public ViewPagerActivity() {
        super(R.string.viewpager);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ViewPager vp = new ViewPager(this);
        vp.setId("VP".hashCode());
        vp.setAdapter(new ColorPagerAdapter(getSupportFragmentManager()));
        setContentView(vp);

        vp.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) { }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) { }

            @Override
            public void onPageSelected(int position) {
            }

        });

        vp.setCurrentItem(0);
        getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
        getSlidingMenu().setTouchAboveCallback(new TouchCallback() {

            @Override
            public boolean isTouchAllowedBeforeTouchMove() {
                return vp.getCurrentItem() == 0 || vp.getCurrentItem() == 4;
            }

            @Override
            public boolean isTouchAllowedWithMotionDelta(View content, float xDiff) {
                Log.d("TEST", "xdiff: " + xDiff);
                if (vp.getCurrentItem() == 0 && xDiff  < 0) {
                    return false;
                } else if (vp.getCurrentItem() == 4 && xDiff > 0) {
                    return false;
                }
                return true;
            }
        });
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        getSlidingMenu().setTouchmodeMarginThreshold(30);

        SampleListFragment fragment = new SampleListFragment();
        fragment.setSampleText("sample list 2");

        getSlidingMenu().setSecondaryMenu(R.layout.menu_frame_two);
        getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadowright);
        getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.menu_frame_two, fragment)
        .commit();
    }

    public class ColorPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragments;

        private final int[] COLORS = new int[] {
            R.color.red,
            R.color.green,
            R.color.blue,
            R.color.white,
            R.color.black
        };

        public ColorPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragments = new ArrayList<Fragment>();
            for (int color : COLORS)
                mFragments.add(new ColorFragment(color));
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

    }

}
