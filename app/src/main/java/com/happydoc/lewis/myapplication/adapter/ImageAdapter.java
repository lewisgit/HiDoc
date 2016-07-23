
package com.happydoc.lewis.myapplication.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.atts.ImageSize;

import java.util.ArrayList;
import java.util.List;




/**
 * Created by Lewis on 2016/7/22.
 *//*

public class ImageAdapter extends PagerAdapter {

    private List<String> datas = new ArrayList<String>();
    private LayoutInflater inflater;
    private Context context;
    private ImageSize imageSize;
    private ImageView smallImageView = null;
    private OnPhotoViewTapListener onPhotoViewTapListener;
    public void setOnPhotoViewTapListener(OnPhotoViewTapListener listener){
        this.onPhotoViewTapListener=listener;
    }

    public void setDatas(List<String> datas) {
        if(datas != null )
            this.datas = datas;
    }
    public void setImageSize(ImageSize imageSize){
        this.imageSize = imageSize;
    }

    public ImageAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if(datas == null) return 0;
        return datas.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = inflater.inflate(R.layout.item_pager_image, container, false);
        if(view != null){
            final PhotoView imageView = (PhotoView) view.findViewById(R.id.image);


            //tap and back
            imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float v, float v1) {
                    onPhotoViewTapListener.onTap();
                }

                @Override
                public void onOutsidePhotoTap() {
                    onPhotoViewTapListener.onTap();
                }
            });
                */
/*imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        this.
                    }
                });*//*



            if(imageSize!=null){
                //预览imageView
                smallImageView = new ImageView(context);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(imageSize.getWidth(), imageSize.getHeight());
                layoutParams.gravity = Gravity.CENTER;
                smallImageView.setLayoutParams(layoutParams);
                smallImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ((FrameLayout)view).addView(smallImageView);
            }

            //loading
            final ProgressBar loading = new ProgressBar(context);
            FrameLayout.LayoutParams loadingLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            loadingLayoutParams.gravity = Gravity.CENTER;
            loading.setLayoutParams(loadingLayoutParams);
            ((FrameLayout)view).addView(loading);

            final String imgurl = datas.get(position);

            Glide.with(context)
                    .load(imgurl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存多个尺寸
                    .thumbnail(0.1f)//先显示缩略图  缩略图为原图的1/10
                    .error(R.drawable.ic_launcher)
                    .into(new GlideDrawableImageViewTarget(imageView){
                        @Override
                        public void onLoadStarted(Drawable placeholder) {
                            super.onLoadStarted(placeholder);
                               */
/* if(smallImageView!=null){
                                    smallImageView.setVisibility(View.VISIBLE);
                                    Glide.with(context).load(imgurl).into(smallImageView);
                                }*//*

                            loading.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                                */
/*if(smallImageView!=null){
                                    smallImageView.setVisibility(View.GONE);
                                }*//*

                            loading.setVisibility(View.GONE);
                        }

                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                            super.onResourceReady(resource, animation);
                            loading.setVisibility(View.GONE);
                                */
/*if(smallImageView!=null){
                                    smallImageView.setVisibility(View.GONE);
                                }

                        }
                    });

            container.addView(view, 0);
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    public interface OnPhotoViewTapListener{
        void onTap();
    }

}
*/