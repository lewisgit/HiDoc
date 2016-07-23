package com.happydoc.lewis.myapplication.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.circle.adapter.CircleAdapter;
import com.happydoc.lewis.myapplication.circle.bean.CircleItem;
import com.happydoc.lewis.myapplication.circle.bean.CommentConfig;
import com.happydoc.lewis.myapplication.circle.bean.CommentItem;
import com.happydoc.lewis.myapplication.circle.bean.FavortItem;
import com.happydoc.lewis.myapplication.circle.mvp.contract.CircleContract;
import com.happydoc.lewis.myapplication.circle.mvp.presenter.CirclePresenter;
import com.happydoc.lewis.myapplication.circle.utils.CommonUtils;
import com.happydoc.lewis.myapplication.circle.widgets.CommentListView;
import com.happydoc.lewis.myapplication.circle.widgets.DivItemDecoration;
import com.happydoc.lewis.myapplication.circle.widgets.TitleBar;
import com.happydoc.lewis.myapplication.circle.widgets.dialog.UpLoadDialog;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.List;

/**
 * Created by Lewis on 2016/7/19.
 */
public class CircleFragment extends Fragment implements CircleContract.View{
    private CircleAdapter circleAdapter;
    private LinearLayout edittextbody;
    private EditText editText;
    private ImageView sendIv;

    private int screenHeight;
    private int editTextBodyHeight;
    private int currentKeyboardH;
    private int selectCircleItemH;
    private int selectCommentItemOffset;

    private CirclePresenter presenter;
    private CommentConfig commentConfig;
    private SuperRecyclerView recyclerView;
    private RelativeLayout bodyLayout;
    private LinearLayoutManager layoutManager;
    private TitleBar titleBar;

    private final static int TYPE_PULLREFRESH = 1;
    private final static int TYPE_UPLOADREFRESH = 2;
    private UpLoadDialog uploadDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView=inflater.inflate(R.layout.fragment_circle,container,false);
        presenter = new CirclePresenter(this);
        initView(myView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.loadData(TYPE_PULLREFRESH,0);
            }
        },100);
        //presenter.loadData(TYPE_UPLOADREFRESH);
        return myView;
    }
    @Override
    public void onDestroy() {
        if(presenter !=null){
            presenter.recycle();
        }
        super.onDestroy();
    }
    @SuppressLint({ "ClickableViewAccessibility", "InlinedApi" })
    public void initView(View v){
        initUploadDialog();

        recyclerView = (SuperRecyclerView) v.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());//this
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DivItemDecoration(2, true));
        recyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (edittextbody.getVisibility() == View.VISIBLE) {
                    updateEditTextBodyVisible(View.GONE, null);
                    return true;
                }
                return false;
            }
        });

        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        presenter.loadData(TYPE_PULLREFRESH,0);
                        recyclerView.setRefreshing(false);
                    }
                });
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Glide.with(getActivity()).resumeRequests();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState != RecyclerView.SCROLL_STATE_IDLE){
                    Glide.with(getActivity()).pauseRequests();
                }

            }
        });


        circleAdapter = new CircleAdapter(getActivity());//this
        circleAdapter.setCirclePresenter(presenter);
        recyclerView.setAdapter(circleAdapter);
        edittextbody = (LinearLayout) v.findViewById(R.id.editTextBodyLl);
        editText = (EditText) v.findViewById(R.id.circleEt);
        sendIv = (ImageView) v.findViewById(R.id.sendIv);
        sendIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter != null) {
                    //发布评论
                    String content =  editText.getText().toString().trim();
                    if(TextUtils.isEmpty(content)){
                        Toast.makeText(getActivity(), "评论内容不能为空...", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    presenter.addComment(content, commentConfig,(CircleItem) circleAdapter.getDatas().get(commentConfig.circlePosition));
                }
                updateEditTextBodyVisible(View.GONE, null);
            }
        });

        setViewTreeObserver(v);
    }
    private void setViewTreeObserver(View v) {
        bodyLayout = (RelativeLayout) v.findViewById(R.id.bodyLayout);
        final ViewTreeObserver swipeRefreshLayoutVTO = bodyLayout.getViewTreeObserver();
        swipeRefreshLayoutVTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                bodyLayout.getWindowVisibleDisplayFrame(r);
                int statusBarH =  getStatusBarHeight();//状态栏高度
                int screenH = bodyLayout.getRootView().getHeight();
                if(r.top != statusBarH ){
                    //在这个demo中r.top代表的是状态栏高度，在沉浸式状态栏时r.top＝0，通过getStatusBarHeight获取状态栏高度
                    r.top = statusBarH;
                }
                int keyboardH = screenH - (r.bottom - r.top);
                Log.d("msg", "screenH＝ "+ screenH +" &keyboardH = " + keyboardH + " &r.bottom=" + r.bottom + " &top=" + r.top + " &statusBarH=" + statusBarH);

                if(keyboardH == currentKeyboardH){//有变化时才处理，否则会陷入死循环
                    return;
                }

                currentKeyboardH = keyboardH;
                screenHeight = screenH;//应用屏幕的高度
                editTextBodyHeight = edittextbody.getHeight();

                //偏移listview
                if(layoutManager!=null && commentConfig != null){
                    layoutManager.scrollToPositionWithOffset(commentConfig.circlePosition + CircleAdapter.HEADVIEW_SIZE, getListviewOffset(commentConfig));
                }
            }
        });
    }
    /**
     * 获取状态栏高度
     * @return
     */
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

/*    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if(edittextbody != null && edittextbody.getVisibility() == View.VISIBLE){
                edittextbody.setVisibility(View.GONE);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public void update2DeleteCircle(String circleId) {
        List<CircleItem> circleItems = circleAdapter.getDatas();
        for(int i=0; i<circleItems.size(); i++){
            if(circleId.equals(circleItems.get(i).getId())){
                circleItems.remove(i);
                circleAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public void update2AddFavorite(int circlePosition, FavortItem addItem) {
        if(addItem != null){
            CircleItem item = (CircleItem) circleAdapter.getDatas().get(circlePosition);
            item.getFavorters().add(addItem);
            circleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void update2DeleteFavort(int circlePosition, String favortId) {
        CircleItem item = (CircleItem) circleAdapter.getDatas().get(circlePosition);
        List<FavortItem> items = item.getFavorters();
        for(int i=0; i<items.size(); i++){
            if(favortId.equals(items.get(i).getId())){
                items.remove(i);
                circleAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public void update2AddComment(int circlePosition, CommentItem addItem) {
        if(addItem != null){
            CircleItem item = (CircleItem) circleAdapter.getDatas().get(circlePosition);
            item.getComments().add(addItem);
            circleAdapter.notifyDataSetChanged();
        }
        //清空评论文本
        editText.setText("");
    }

    @Override
    public void update2DeleteComment(int circlePosition, String commentId) {
        CircleItem item = (CircleItem) circleAdapter.getDatas().get(circlePosition);
        List<CommentItem> items = item.getComments();
        for(int i=0; i<items.size(); i++){
            if(commentId.equals(items.get(i).getId())){
                items.remove(i);
                circleAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig) {
        this.commentConfig = commentConfig;
        edittextbody.setVisibility(visibility);

        measureCircleItemHighAndCommentItemOffset(commentConfig);

        if(View.VISIBLE==visibility){
            editText.requestFocus();
            //弹出键盘
            CommonUtils.showSoftInput( editText.getContext(),  editText);

        }else if(View.GONE==visibility){
            //隐藏键盘
            CommonUtils.hideSoftInput( editText.getContext(),  editText);
        }
    }

    @Override
    public void update2loadData(int loadType, List<CircleItem> datas) {
        if (loadType == TYPE_PULLREFRESH){
            circleAdapter.setDatas(datas);
        }else if(loadType == TYPE_UPLOADREFRESH){
            circleAdapter.getDatas().addAll(datas);
        }
        circleAdapter.notifyDataSetChanged();

        if(circleAdapter.getDatas().size()<45 + CircleAdapter.HEADVIEW_SIZE){
            recyclerView.setupMoreListener(new OnMoreListener() {
                @Override
                public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            presenter.loadData(TYPE_UPLOADREFRESH,circleAdapter.getDatas().size());
                        }
                    }, 2000);

                }
            }, 1);
        }else{
            recyclerView.removeMoreListener();
        }

    }


    /**
     * 测量偏移量
     * @param commentConfig
     * @return
     */
    private int getListviewOffset(CommentConfig commentConfig) {
        if(commentConfig == null)
            return 0;
        //这里如果你的listview上面还有其它占高度的控件，则需要减去该控件高度，listview的headview除外。
        //int listviewOffset = mScreenHeight - mSelectCircleItemH - mCurrentKeyboardH - mEditTextBodyHeight;
        int listviewOffset = screenHeight - selectCircleItemH - currentKeyboardH - editTextBodyHeight;
        if(commentConfig.commentType == CommentConfig.Type.REPLY){
            //回复评论的情况
            listviewOffset = listviewOffset + selectCommentItemOffset;
        }
        Log.i("msg", "listviewOffset : " + listviewOffset);
        return listviewOffset;
    }

    private void measureCircleItemHighAndCommentItemOffset(CommentConfig commentConfig){
        if(commentConfig == null)
            return;

        int firstPosition = layoutManager.findFirstVisibleItemPosition();
        //只能返回当前可见区域（列表可滚动）的子项
        View selectCircleItem = layoutManager.getChildAt(commentConfig.circlePosition + CircleAdapter.HEADVIEW_SIZE - firstPosition);

        if(selectCircleItem != null){
            selectCircleItemH = selectCircleItem.getHeight();
        }

        if(commentConfig.commentType == CommentConfig.Type.REPLY){
            //回复评论的情况
            CommentListView commentLv = (CommentListView) selectCircleItem.findViewById(R.id.commentList);
            if(commentLv!=null){
                //找到要回复的评论view,计算出该view距离所属动态底部的距离
                View selectCommentItem = commentLv.getChildAt(commentConfig.commentPosition);
                if(selectCommentItem != null){
                    //选择的commentItem距选择的CircleItem底部的距离
                    selectCommentItemOffset = 0;
                    View parentView = selectCommentItem;
                    do {
                        int subItemBottom = parentView.getBottom();
                        parentView = (View) parentView.getParent();
                        if(parentView != null){
                            selectCommentItemOffset += (parentView.getHeight() - subItemBottom);
                        }
                    } while (parentView != null && parentView != selectCircleItem);
                }
            }
        }
    }
    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {

    }
    private void initUploadDialog() {
        uploadDialog = new UpLoadDialog(getActivity());
    }



    public SuperRecyclerView getRecyclerView(){
        return recyclerView;
    }


    public void showMsg(int stringId){
        Toast.makeText(getActivity(),getString(stringId),Toast.LENGTH_SHORT).show();
    }
}
