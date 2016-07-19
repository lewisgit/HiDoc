package com.happydoc.lewis.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.account.DoctorInfo;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lewis on 2016/7/18.
 */
public class AdapterDocList extends ArrayAdapter<DoctorInfo> {


   // private List<T> myList;
    public AdapterDocList(Context context, int resourceId){
        super(context,resourceId);
      //  myList=new ArrayList<>();
    }
    public AdapterDocList(Context context, int resourceId, List<DoctorInfo> list){
        super(context,resourceId,list);
       // myList=list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v=convertView;
        if(v==null){
            LayoutInflater vi=LayoutInflater.from(getContext());
            v=vi.inflate(R.layout.docitem,null);
        }
        DoctorInfo itemData=getItem(position);
        TextView docName=(TextView) v.findViewById(R.id.doc_name);
        TextView docTitle=(TextView) v.findViewById(R.id.doc_title);
        TextView docHosp=(TextView) v.findViewById(R.id.doc_agency);
        TextView docIntro=(TextView) v.findViewById(R.id.doc_intro);
        TextView docOnceFee=(TextView) v.findViewById(R.id.ask_price);
        TextView docWeekFee=(TextView) v.findViewById(R.id.personal_price);
        CircleImageView docAvatar=(CircleImageView) v.findViewById(R.id.doc_avatar);
        DisplayImageOptions imageOptions= new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
                .showImageForEmptyUri(R.drawable.chat).showImageOnFail(R.drawable.chat).cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoader.getInstance().displayImage(itemData.avatarUrl,docAvatar,imageOptions);
        docName.setText(itemData.name);
        docTitle.setText(itemData.docTitle);
        docHosp.setText(itemData.docHosp);
        docIntro.setText(itemData.docIntro);
        docOnceFee.setText(itemData.docOnceFee);
        docWeekFee.setText(itemData.docWeekFee);
        return v;
    }


  /*  public AdapterDocList(Context context,int id){
        super(context,id);
    }
    @Override
    public void convert(BaseAdapterHelper helper,DoctorInfo doctorInfo){
        helper.setText(R.id.doc_name,doctorInfo.name)
                .setText(R.id.doc_title,doctorInfo.docTitle)
                .setText(R.id.doc_agency,doctorInfo.docHosp)
                .setText(R.id.doc_intro,doctorInfo.docIntro)
                .setText(R.id.ask_price,doctorInfo.docOnceFee)
                .setText(R.id.personal_price,doctorInfo.docWeekFee)
                .setImageUrl(R.id.doc_avatar,doctorInfo.avatarUrl);
    }*/
}
