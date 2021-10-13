package com.example.waterreminder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.Objects;

import static com.example.waterreminder.GenderPage1.weightIn;

public class AnimTabAdapter extends PagerAdapter {

    // Context object
    Context context;

    // Array of images
    String[] images;
    ImageView imageView, start, dots;
    TextView gifText, gifText1, skip;
    // Layout Inflater
    LayoutInflater mLayoutInflater;
    Database_Helper helper;
    Personal_Data data;


    // Viewpager Constructor
    public AnimTabAdapter(Context context, String[] images) {
        this.context = context;
        this.images = images;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        helper = new Database_Helper(context);
        data = helper.getAllPersonalData();
    }

    @Override
    public int getCount() {
        // return the number of images
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        // inflating the item.xml
        View itemView = mLayoutInflater.inflate(R.layout.item_anim, container, false);

        // referencing the image view from the item.xml file
        imageView = itemView.findViewById(R.id.img1);
        start = itemView.findViewById(R.id.start);
        dots = itemView.findViewById(R.id.dots);
        gifText = itemView.findViewById(R.id.gifText);
        gifText1 = itemView.findViewById(R.id.gifText1);

        Glide.with(context).load(images[position]).into(imageView);
        if (position == 0) {
            gifText.setText("Preparing a Plan For You...");
        }
        if (position == 1) {
            gifText.setText("Daily Water Drinking Goal");
            gifText1.setTextColor(Color.parseColor("#3E8AFB"));
            if (weightIn == "Kg") {
                gifText1.setText(data.getNeededML() + " ml");
            } else {
                gifText1.setText(data.getNeededOZ() + " fl oz");
            }
            dots.setVisibility(View.VISIBLE);
            dots.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_group_109));

        }
        if (position == 2) {
            dots.setVisibility(View.VISIBLE);
            dots.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_group_110));
            gifText.setText("You'll get reminders on Time!");
            gifText1.setVisibility(View.GONE);
        }
        if (position == 3) {
            gifText.setText("Track your water intake with");
            gifText1.setText("Report Chart");
            start.setVisibility(View.VISIBLE);
            dots.setVisibility(View.GONE);
        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, TabActivity.class);
                context.startActivity(in);
            }
        });
        // Adding the View
        Objects.requireNonNull(container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((RelativeLayout) object);
    }
}
