package com.happydoc.lewis.myapplication.circle.mvp.presenter;

import android.util.Log;
import android.view.View;

import com.happydoc.lewis.myapplication.Model.DocCircleModel;
import com.happydoc.lewis.myapplication.circle.bean.CircleItem;
import com.happydoc.lewis.myapplication.circle.bean.CommentConfig;
import com.happydoc.lewis.myapplication.circle.bean.CommentItem;
import com.happydoc.lewis.myapplication.circle.bean.FavortItem;
import com.happydoc.lewis.myapplication.circle.mvp.contract.CircleContract;
import com.happydoc.lewis.myapplication.circle.mvp.modle.CircleModel;
import com.happydoc.lewis.myapplication.circle.listener.IDataRequestListener;
import com.happydoc.lewis.myapplication.circle.utils.DatasUtil;
import com.happydoc.lewis.myapplication.event.MyCallBack;

import java.util.List;

/**
 * 
* @ClassName: CirclePresenter 
* @Description: 通知model请求服务器和通知view更新
* @author yiw
* @date 2015-12-28 下午4:06:03 
*
 */
public class CirclePresenter implements CircleContract.Presenter{
	private CircleModel mCircleModel;
	private CircleContract.View view;
	private DocCircleModel docCircleModel;
	public CirclePresenter(CircleContract.View view){
		mCircleModel = new CircleModel();
		docCircleModel=new DocCircleModel();
		this.view = view;
	}

	public void loadData(final int loadType){

        //List<CircleItem> datas = DatasUtil.createCircleDatas();
		docCircleModel.getCircleData(10, loadType, new MyCallBack<List<CircleItem>>() {
			@Override
			public void done(List<CircleItem> data) {
				if(view!=null){
				view.update2loadData(loadType, data);}
}	});

	}


	/**
	 * 
	* @Title: deleteCircle 
	* @Description: 删除动态 
	* @param  circleId     
	* @return void    返回类型 
	* @throws
	 */
	public void deleteCircle(final String circleId){
		mCircleModel.deleteCircle(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
				view.update2DeleteCircle(circleId);
			}
		});
	}
	/**
	 * 
	* @Title: addFavort 
	* @Description: 点赞
	* @param  circlePosition     
	* @return void    返回类型 
	* @throws
	 */
	public void addFavort(final int circlePosition){
		mCircleModel.addFavort(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
				FavortItem item = DatasUtil.createCurUserFavortItem();
				view.update2AddFavorite(circlePosition, item);
			}
		});
	}
	/**
	 * 
	* @Title: deleteFavort 
	* @Description: 取消点赞 
	* @param @param circlePosition
	* @param @param favortId     
	* @return void    返回类型 
	* @throws
	 */
	public void deleteFavort(final int circlePosition, final String favortId){
		mCircleModel.deleteFavort(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
				view.update2DeleteFavort(circlePosition, favortId);
			}
		});
	}
	
	/**
	 * 
	* @Title: addComment 
	* @Description: 增加评论
	* @param  content
	* @param  config  CommentConfig
	* @return void    返回类型 
	* @throws
	 */
	public void addComment(final String content, final CommentConfig config){
		if(config == null){
			return;
		}
		mCircleModel.addComment(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
				CommentItem newItem = null;
				if (config.commentType == CommentConfig.Type.PUBLIC) {
					newItem = DatasUtil.createPublicComment(content);
				} else if (config.commentType == CommentConfig.Type.REPLY) {
					newItem = DatasUtil.createReplyComment(config.replyUser, content);
				}

				view.update2AddComment(config.circlePosition, newItem);
			}

		});
	}
	
	/**
	 * 
	* @Title: deleteComment 
	* @Description: 删除评论 
	* @param @param circlePosition
	* @param @param commentId     
	* @return void    返回类型 
	* @throws
	 */
	public void deleteComment(final int circlePosition, final String commentId){
		mCircleModel.deleteComment(new IDataRequestListener(){

			@Override
			public void loadSuccess(Object object) {
				view.update2DeleteComment(circlePosition, commentId);
			}
			
		});
	}

	/**
	 *
	 * @param commentConfig
	 */
	public void showEditTextBody(CommentConfig commentConfig){
		view.updateEditTextBodyVisible(View.VISIBLE, commentConfig);
	}


    /**
     * 清除对外部对象的引用，反正内存泄露。
     */
    public void recycle(){
        this.view = null;
    }
}
