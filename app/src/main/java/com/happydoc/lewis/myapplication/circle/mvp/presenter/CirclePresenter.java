package com.happydoc.lewis.myapplication.circle.mvp.presenter;

import android.util.Log;
import android.view.View;

import com.happydoc.lewis.myapplication.Model.DocCircleModel;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.circle.adapter.CircleAdapter;
import com.happydoc.lewis.myapplication.circle.bean.CircleItem;
import com.happydoc.lewis.myapplication.circle.bean.CommentConfig;
import com.happydoc.lewis.myapplication.circle.bean.CommentItem;
import com.happydoc.lewis.myapplication.circle.bean.FavortItem;
import com.happydoc.lewis.myapplication.circle.mvp.contract.CircleContract;
import com.happydoc.lewis.myapplication.circle.mvp.modle.CircleModel;
import com.happydoc.lewis.myapplication.circle.listener.IDataRequestListener;
import com.happydoc.lewis.myapplication.circle.utils.DatasUtil;
import com.happydoc.lewis.myapplication.event.MyCallBack;
import com.happydoc.lewis.myapplication.fragment.CircleFragment;

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
	private CircleFragment circleFragment;
	//private CircleAdapter circleAdapter;
	public CirclePresenter(CircleContract.View view){
		mCircleModel = new CircleModel();
		docCircleModel=new DocCircleModel();
		circleFragment=(CircleFragment) view;
		this.view = view;
	}
	//public void setCircleAdapter(CircleAdapter circleAdapter){this.circleAdapter=circleAdapter;}
	//public CircleAdapter getCircleAdapter(){return this.circleAdapter;}
	public void loadData(final int loadType,int skipNum){

        //List<CircleItem> datas = DatasUtil.createCircleDatas();
		docCircleModel.getCircleData(skipNum, loadType, new MyCallBack<List<CircleItem>>() {
			@Override
			public void done(List<CircleItem> data) {
				if(view!=null){
				view.update2loadData(loadType, data);
					if(data.size()==0){
						CircleFragment circleFragment=(CircleFragment)view;
						circleFragment.getRecyclerView().hideMoreProgress();
					}
				}
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
	public void deleteCircle(final String circleId,CircleItem circleItem){
		docCircleModel.deleteCircle(circleItem, new MyCallBack<String>() {
			@Override
			public void done(String data) {
				if(data==null){
					circleFragment.showMsg(R.string.delete_circle_fail);
				}else{
					view.update2DeleteCircle(circleId);
					circleFragment.showMsg(R.string.delete_circle_success);
				}
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
	public void addFavort(final int circlePosition,CircleItem circleItem){
		docCircleModel.likeCircle(circlePosition, circleItem, new MyCallBack<FavortItem>() {
			@Override
			public void done(FavortItem data) {
				if(data==null){
				//发送失败通知
					circleFragment.showMsg(R.string.like_fail);
				}else{
					view.update2AddFavorite(circlePosition,data);
					circleFragment.showMsg(R.string.like_success);
				}
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
	public void deleteFavort(final int circlePosition,final String favortId,CircleItem item ){
		docCircleModel.unLikeCircle(item, new MyCallBack<String>() {
			@Override
			public void done(String data) {
				if(data==null){circleFragment.showMsg(R.string.delete_like_fail);
				}
				else{
					view.update2DeleteFavort(circlePosition,favortId);
					circleFragment.showMsg(R.string.delete_like_sucess);
				}
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
	public void addComment(final String content, final CommentConfig config,CircleItem circleItem){
		if(config == null){
			return;
		}
		CommentItem item=new CommentItem();
		item.setUser(DatasUtil.curUser);
		if(config.commentType== CommentConfig.Type.REPLY){
			item.setToReplyUser(config.replyUser);
		}
		item.setContent(content);
		docCircleModel.addComment(item, circleItem, new MyCallBack<CommentItem>() {
			@Override
			public void done(CommentItem data) {
				if(data==null){
					circleFragment.showMsg(R.string.comment_fail);
				}else{
					view.update2AddComment(config.circlePosition, data);
					circleFragment.showMsg(R.string.comment_success);
				}
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
	public void deleteComment(final int circlePosition, final String commentId,CommentItem commentItem,CircleItem circleItem){
		docCircleModel.deleteComment(commentItem, circleItem, new MyCallBack<String>() {
			@Override
			public void done(String data) {
				if(data==null){//show error msg
					circleFragment.showMsg(R.string.delete_comment_fail);
				}
				else{

					view.update2DeleteComment(circlePosition, commentId);
					circleFragment.showMsg(R.string.delete_comment_success);
				}
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
