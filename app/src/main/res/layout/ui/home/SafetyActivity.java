package com.mnashat_dev.touqa.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.mnashat_dev.touqa.CustomPagerAdapter;
import com.mnashat_dev.touqa.R;
import com.mnashat_dev.touqa.base.BaseActivity;
import com.mnashat_dev.touqa.databinding.ActivitySafetyBinding;
import com.mnashat_dev.touqa.model.PageItem;

import java.util.ArrayList;
import java.util.List;
public class SafetyActivity extends BaseActivity {
private ActivitySafetyBinding binding;
private CustomPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    binding =  DataBindingUtil.setContentView(this,R.layout.activity_safety);





        List<PageItem> items = new ArrayList<>();
        items.add(new PageItem(R.drawable.img1, "لا تجازف بتجاوز مجارى السيول فقد تداهمك المياه ويزداد تدفقها ومنسوبها بسرعة عالية فيتعذر عليك مغادرة مركبتك"));
        items.add(new PageItem(R.drawable.img2, "قد تغطى المياه جسرا منجرفا أو طريقا مكسرا فليزم الحذر واﻹنتباه"));
        items.add(new PageItem(R.drawable.img3, "عدم ترك اﻷطفال يلعبون حول مناطق تجمع المياه والسيول فهم لا يدركون مدى الخطورة فمراقبتهم واجبة "));
        items.add(new PageItem(R.drawable.img4, "أتبع المسارات والطرق المستخدمة قدر اﻹمكان"));
        items.add(new PageItem(R.drawable.img5, "عدم اﻹقتراب من تجمعات المياة فعادة ما تكون حوافها قابلة للإنهيار"));
        items.add(new PageItem(R.drawable.img6, "لا تجازف بالجلوس فى مجارى السيول او عبورها"));

        pagerAdapter = new CustomPagerAdapter(this, items);
        binding.viewPager.setAdapter(pagerAdapter);

        addIndicators(items.size());
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = binding.viewPager.getCurrentItem();
                if (currentItem < items.size() - 1) {
                    binding.viewPager.setCurrentItem(currentItem + 1);
                }
            }
        });

        // Handle previous button click
        binding.btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = binding.viewPager.getCurrentItem();
                if (currentItem > 0) {
                    binding.viewPager.setCurrentItem(currentItem - 1);
                }
            }
        });

        // Set listener for ViewPager page change to update indicators
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                updateIndicators(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    private void addIndicators(int count) {
        for (int i = 0; i < count; i++) {
            View indicator = new View(this);
            indicator.setBackgroundResource(R.drawable.circle_blue);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.setMargins(8, 0, 8, 0);
            indicator.setLayoutParams(params);
            binding.indicatorLayout.addView(indicator);
        }
        updateIndicators(0);
    }

    private void updateIndicators(int position) {
        for (int i = 0; i < binding.indicatorLayout.getChildCount(); i++) {
            View indicator = binding.indicatorLayout.getChildAt(i);
            if (i == position) {
                indicator.setBackgroundResource(R.drawable.circle_blue2);
            } else {
                indicator.setBackgroundResource(R.drawable.circle_blue);
            }
        }
        if (position == 0) {
            binding.btnPrevious.setAlpha(0.0f);
        } else {
            binding.btnPrevious.setAlpha(1.0f);
        }

        if (position == pagerAdapter.getCount() - 1) {
            binding.btnNext.setAlpha(0.0f);
        } else {
            binding.btnNext.setAlpha(1.0f);
        }
    }

}