package com.happydoc.lewis.myapplication.account;

import com.avos.avoscloud.AVObject;

/**
 * Created by Lewis on 2016/7/18.
 */
public class DoctorInfo {
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
}
