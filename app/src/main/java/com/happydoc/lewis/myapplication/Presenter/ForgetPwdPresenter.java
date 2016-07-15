package com.happydoc.lewis.myapplication.Presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.happydoc.lewis.myapplication.Model.ForgetPwdModel;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.ForgetPwdView;
import com.happydoc.lewis.myapplication.account.ModifyPwdInfo;
import com.happydoc.lewis.myapplication.event.EventListener;

/**
 * Created by Lewis on 2016/7/15.
 */
public class ForgetPwdPresenter {
    public ForgetPwdView view;
    public ForgetPwdModel model;
    public ForgetPwdPresenter(ForgetPwdView view,ForgetPwdModel model){
        this.view=view;
        this.model=model;
        initialize();
    }
    public void initialize(){
        view.whenOnClick(view.getView(R.id.back_pwd), new EventListener<ModifyPwdInfo>() {
            @Override
            public void onDispatch(ModifyPwdInfo data) {
                view.goBackLogin();
            }
        });
        view.whenOnClick(view.getView(R.id.reset_get_code_button), new EventListener<ModifyPwdInfo>() {
            @Override
            public void onDispatch(ModifyPwdInfo data) {
                if(data.phoneNum.isEmpty()){view.showMsg(R.string.forget_password_hint);}else{
                    model.requestAuthCode(data.phoneNum, new RequestMobileCodeCallback() {
                        @Override
                        public void done(AVException e) {
                            if(e==null){
                                view.showMsg(R.string.forget_password_send_sns);
                            }else{view.showMsg(e.getMessage());}
                        }
                    });
                }
            }
        });
        view.whenOnClick(view.getView(R.id.modify_pwd), new EventListener<ModifyPwdInfo>() {
            @Override
            public void onDispatch(ModifyPwdInfo data) {
                if(!data.phoneNum.isEmpty() && !data.authCode.isEmpty() && !data.newPwd.isEmpty()){
                    model.resetPwd(data, new UpdatePasswordCallback() {
                        @Override
                        public void done(AVException e) {
                            if(e==null){
                                view.showMsg(R.string.reset_success);
                                view.goBackLogin();
                            }else{view.showMsg(e.getMessage());}
                        }
                    });
                }else{view.showMsg(R.string.complete_info_pls);}
            }
        });
        view.setOnClickListener();
    }
}
