package com.example.waterreminder;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    TabsAdapter tabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        this.getSupportActionBar().hide();

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        int index = getIntent().getIntExtra("index", 1);


        tabsAdapter = new TabsAdapter(TabActivity.this, getSupportFragmentManager());
        mViewPager.setAdapter(tabsAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            Objects.requireNonNull(tab).setCustomView(tabsAdapter.getTabView(i));
        }
        Objects.requireNonNull(mTabLayout.getTabAt(0)).select();
        mTabLayout.setTabIndicatorFullWidth(false);
        mTabLayout.selectTab(mTabLayout.getTabAt(index));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {
                    ImageView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tabIcon);
                    tv.setImageDrawable(getResources().getDrawable(R.drawable.ic_group_3241));
                }
                if (tab.getPosition() == 1) {
                    ImageView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tabIcon);
                    tv.setImageDrawable(getResources().getDrawable(R.drawable.ic_group_322));
                }
                if (tab.getPosition() == 2) {
                    ImageView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tabIcon);
                    tv.setImageDrawable(getResources().getDrawable(R.drawable.ic_group_3231));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    ImageView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tabIcon);
                    tv.setImageDrawable(getResources().getDrawable(R.drawable.ic_group_324));
                }
                if (tab.getPosition() == 1) {
                    ImageView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tabIcon);
                    tv.setImageDrawable(getResources().getDrawable(R.drawable.ic_group_3221));
                }
                if (tab.getPosition() == 2) {
                    ImageView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tabIcon);
                    tv.setImageDrawable(getResources().getDrawable(R.drawable.ic_group_323));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    @Override
    public void onBackPressed() {

        if (mTabLayout.getSelectedTabPosition() == 0 || mTabLayout.getSelectedTabPosition() == 2) {
            mTabLayout.selectTab(mTabLayout.getTabAt(1));
        } else {
            finishAffinity();
        }
    }
}