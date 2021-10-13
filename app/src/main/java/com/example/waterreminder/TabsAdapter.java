package com.example.waterreminder;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class TabsAdapter extends FragmentPagerAdapter {
    private final ArrayList<Fragment> mData = new ArrayList<>();
    private final Context mContext;

    private final int[] tabIcons = {
            R.drawable.ic_group_324,
            R.drawable.ic_group_322,
            R.drawable.ic_group_323
    };

    public TabsAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        mContext = context;
        initData();
    }

    public ArrayList<Fragment> getData() {
        return mData;
    }

    private void initData() {

        mData.add(new HistoryFragment());
        mData.add(new HomeFragment());
        mData.add(new SettingsFragment());


    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    public View getTabView(int position) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.custom_tab, null);
        ImageView tabIcon = v.findViewById(R.id.tabIcon);

        if (position == 0) {
            tabIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_group_324));

        }
        if (position == 1) {
            tabIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_group_322));

        }
        if (position == 2) {
            tabIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_group_323));

        }
        return v;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        Drawable image = mContext.getResources().getDrawable(tabIcons[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());

        SpannableString sb = new SpannableString(" ");

        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_CENTER);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}

