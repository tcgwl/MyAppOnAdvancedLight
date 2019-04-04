package com.thunderhou.app.advancedlight.chapter03.stickynavlayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.thunderhou.app.advancedlight.R;

public class StickyNavLayoutActivity extends AppCompatActivity {

    private String[] mTitles = new String[] { "简介", "评价", "相关" };
//    private TabFragmentWithScrollView[] mFragments = new TabFragmentWithScrollView[mTitles.length];
//    private TabFragmentWithListView[] mFragments = new TabFragmentWithListView[mTitles.length];
    private TabFragmentWithRecyclerView[] mFragments = new TabFragmentWithRecyclerView[mTitles.length];
    private SimpleViewPagerIndicator mIndicator;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_nav_layout);

        initViews();
        initDatas();
        initEvents();
    }

    private void initViews() {
        mIndicator = findViewById(R.id.id_stickynavlayout_indicator);
        mViewPager = findViewById(R.id.id_stickynavlayout_viewpager);
    }

    private void initDatas() {
        mIndicator.setTitles(mTitles);
        for (int i = 0; i < mTitles.length; i++) {
//            mFragments[i] = TabFragmentWithScrollView.newInstance(mTitles[i]);
//            mFragments[i] = TabFragmentWithListView.newInstance(mTitles[i]);
            mFragments[i] = TabFragmentWithRecyclerView.newInstance(mTitles[i]);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
    }

    private void initEvents() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIndicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
