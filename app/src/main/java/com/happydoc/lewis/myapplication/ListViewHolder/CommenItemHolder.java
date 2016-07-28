package com.happydoc.lewis.myapplication.ListViewHolder;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.avoscloud.leanchatlib.viewholder.CommonViewHolder;
import com.happydoc.lewis.myapplication.R;

/**
 * Created by LewisMS on 1/16/2016.
 */
public class CommenItemHolder extends CommonViewHolder {
    private TextView commentText;
    private TextView userPhone;
    public CommenItemHolder(ViewGroup root) {
        super(root.getContext(), root, R.layout.comment_item);
        initView();
    }
    public void initView(){
        commentText=(TextView)itemView.findViewById(R.id.comment_item);
        userPhone=(TextView)itemView.findViewById(R.id.comment_phone);
    }
    @Override
    public void bindData(final Object o) {
        AVObject commentObject=(AVObject) o;
        commentText.setText(commentObject.getString("comment"));
        userPhone.setText(encryUser(commentObject.getString("PatientID")));
    }
    //------------------???Holder_creator ???--------
    public static ViewHolderCreator HOLDER_CREATOR = new ViewHolderCreator<CommenItemHolder>() {
        @Override
        public CommenItemHolder createByViewGroupAndType(ViewGroup parent, int viewType) {
            return new CommenItemHolder(parent);
        }
    };
    //-----------------------------------------------

    private String encryUser(String string){
        return string.substring(0,4)+"********";
    }
}
