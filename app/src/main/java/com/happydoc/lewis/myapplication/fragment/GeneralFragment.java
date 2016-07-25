package com.happydoc.lewis.myapplication.fragment;

import android.app.Fragment;

import com.happydoc.lewis.myapplication.Presenter.GenPresenter;

/**
 * Created by LewisMS on 7/25/2016.
 */
public  class GeneralFragment extends Fragment {
    GenPresenter presenter;
    public GenPresenter getPresenter() {
        return presenter;
    }
    public void setPresenter(GenPresenter presenter) {
        this.presenter = presenter;
    }
}
