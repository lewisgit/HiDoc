package com.happydoc.lewis.myapplication.Bean;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by LewisMS on 7/26/2016.
 */
public class BtnInfo {
    private ImageView img;
    private TextView txt;
    private int imgResId;

    public void setTxt(TextView txt) {
        this.txt = txt;
    }

    public TextView getTxt() {
        return txt;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public ImageView getImg() {
        return img;
    }

    public int getImgResId() {
        return imgResId;
    }

}
