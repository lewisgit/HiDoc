/*

package com.happydoc.lewis.myapplication.circle.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.happydoc.lewis.myapplication.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
//import com.happydoc.lewis.myapplication.adapter.ImageAdapter;
import com.happydoc.lewis.myapplication.atts.ImageSize;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;




*/
/**
 * Created by yiw on 2016/1/6.
 *//*



public class ImagePagerActivity extends Activity implements ImageAdapter.OnPhotoViewTapListener{
    public static final String INTENT_IMGURLS = "imgurls";
    public static final String INTENT_POSITION = "position";
    public static final String INTENT_IMAGESIZE = "imagesize";

    private List<View> guideViewList = new ArrayList<View>();
    private LinearLayout guideGroup;
    public ImageSize imageSize;
    private int startPos;
    private ArrayList<String> imgUrls;


    public static void startImagePagerActivity(Context context, List<String> imgUrls, int position, ImageSize imageSize){
        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putStringArrayListExtra(INTENT_IMGURLS, new ArrayList<String>(imgUrls));
        intent.putExtra(INTENT_POSITION, position);
        intent.putExtra(INTENT_IMAGESIZE, imageSize);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagepager);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        guideGroup = (LinearLayout) findViewById(R.id.guideGroup);

        getIntentData();

        ImageAdapter mAdapter = new ImageAdapter(this);
        mAdapter.setOnPhotoViewTapListener(this);
        mAdapter.setDatas(imgUrls);
        mAdapter.setImageSize(imageSize);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0; i<guideViewList.size(); i++){
                    guideViewList.get(i).setSelected(i==position ? true : false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(startPos);

        addGuideView(guideGroup, startPos, imgUrls);

    }

    private void getIntentData() {
        startPos = getIntent().getIntExtra(INTENT_POSITION, 0);
        imgUrls = getIntent().getStringArrayListExtra(INTENT_IMGURLS);
        imageSize = (ImageSize) getIntent().getSerializableExtra(INTENT_IMAGESIZE);
    }

    private void addGuideView(LinearLayout guideGroup, int startPos, ArrayList<String> imgUrls) {
        if(imgUrls!=null && imgUrls.size()>0){
            guideViewList.clear();
            for (int i=0; i<imgUrls.size(); i++){
                View view = new View(this);
                view.setBackgroundResource(R.drawable.selector_guide_bg);
                view.setSelected(i==startPos ? true : false);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.gudieview_width),
                        getResources().getDimensionPixelSize(R.dimen.gudieview_heigh));
                layoutParams.setMargins(10, 0, 0, 0);
                guideGroup.addView(view, layoutParams);
                guideViewList.add(view);
            }
        }
    }

    @Override
    protected void onDestroy() {
        guideViewList.clear();
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        try{return super.dispatchTouchEvent(event);}catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void onTap(){
        goBack();
    }
    public void goBack(){
        this.onBackPressed();
    }
}

*/

