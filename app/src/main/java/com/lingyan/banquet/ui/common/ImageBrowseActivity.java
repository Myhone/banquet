package com.lingyan.banquet.ui.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityImageBrowserBinding;
import com.github.chrisbanes.photoview.PhotoView;
import com.lingyan.banquet.utils.MyImageUtils;

import java.util.ArrayList;


/**
 * Created by _hxb on 2018/6/2.
 */
public class ImageBrowseActivity extends BaseActivity {


    public static void start(Context context, String imageURL) {
        ArrayList<String> list = new ArrayList<>();
        list.add(imageURL);
        start(context, list, 0);
    }

    public static void start(Context context, ArrayList<String> imageList, int first) {
        Intent intent = new Intent(context, ImageBrowseActivity.class);
        intent.putStringArrayListExtra("list", imageList);
        intent.putExtra("first", first);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        }


        final ArrayList<String> imageList = getIntent().getStringArrayListExtra("list");
        final int first = getIntent().getIntExtra("first", 0);
        ActivityImageBrowserBinding binding = ActivityImageBrowserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                PhotoView photoView = new PhotoView(ImageBrowseActivity.this);
                container.addView(photoView, new ViewGroup.LayoutParams(-1, -1));
                String s = imageList.get(position);
                photoView.setBackgroundColor(Color.BLACK);
                MyImageUtils.display(photoView,s);
                photoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

                return photoView;
            }
        });
        binding.viewPager.setCurrentItem(first);
    }


}
