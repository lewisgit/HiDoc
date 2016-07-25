package com.happydoc.lewis.myapplication.account;

import com.avos.avoscloud.AVObject;

import java.io.Serializable;

/**
 * Created by Lewis on 2016/7/18.
 */
public class DoctorInfo implements Serializable{
    public String name;
    public String avatarUrl;
    public String docTitle;
    public String docHosp;
    public String docIntro;
    public String docOnceFee;
    public String docWeekFee;
    public AVObject docObject;
    public DoctorInfo( String avatarUrl,String name,String docTitle,String docHosp,String docIntro, String docOnceFee,String docWeekFee, AVObject docObject){
        this.name=name;
        this.avatarUrl=avatarUrl;
        this.docHosp=docHosp;
        this.docIntro=docIntro;
        this.docObject=docObject;
        this.docOnceFee=docOnceFee;
        this.docWeekFee=docWeekFee;
        this.docTitle=docTitle;
    }

    public AVObject getDocObject() {
        return docObject;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getDocHosp() {
        return docHosp;
    }

    public String getDocIntro() {
        return docIntro;
    }

    public String getDocOnceFee() {
        return docOnceFee;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public String getDocWeekFee() {
        return docWeekFee;
    }

    public String getName() {
        return name;
    }

}
