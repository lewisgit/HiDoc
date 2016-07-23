package com.happydoc.lewis.myapplication.Model;

import android.util.Log;

import org.json.JSONArray;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogUtil;
import com.avos.avoscloud.ObjectValueFilter;
import com.avos.avoscloud.SaveCallback;
import com.happydoc.lewis.myapplication.circle.bean.CircleItem;
import com.happydoc.lewis.myapplication.circle.bean.CommentConfig;
import com.happydoc.lewis.myapplication.circle.bean.CommentItem;
import com.happydoc.lewis.myapplication.circle.bean.FavortItem;
import com.happydoc.lewis.myapplication.circle.bean.User;
import com.happydoc.lewis.myapplication.circle.utils.DatasUtil;
import com.happydoc.lewis.myapplication.event.MyCallBack;
import com.happydoc.lewis.myapplication.utils.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lewis on 2016/7/19.
 */
public class DocCircleModel {
    private final static int TYPE_PULLREFRESH = 1;
    private final static int TYPE_UPLOADREFRESH = 2;
    public void getCircleData(int skipNum, final int getType, final MyCallBack<List<CircleItem>> callBack){
        final AVQuery<AVObject> query=new AVQuery<>("Album");
        query.whereEqualTo("isDel",false);
        //query.include("creator");
        query.include("albumPhotos");
        query.include("comments");
        query.orderByDescending("createdAt");
       // query.include("comments.toUser");
        //query.include("comments.commentUser");
        //query.include("digUsers");
        if(getType==TYPE_UPLOADREFRESH) {
            query.skip(skipNum);
        }
        query.limit(10);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e==null){

                    //query for users
                    AVQuery<AVObject> queryUser=new AVQuery("_User");
                    List<String> userObjIds=new ArrayList<String>();

                    //get circle data
                    final  List<CircleItem> items=new ArrayList<CircleItem>();
                    for(AVObject object:list)
                    {
                        CircleItem item=new CircleItem();


                        item.setType("2");

                        item.setCreateTime(TimeUtils.millisecsToDateString(object.getCreatedAt().getTime()));
                        item.setCirlceObj(object);

                        //get photos
                        List<Object> photos=object.getList("albumPhotos");
                        List<String> photoUrls=new ArrayList<String>();
                        for(Object photo:photos)
                        {
                            AVFile photoObj=(AVFile) photo;
                            photoUrls.add(photoObj.getUrl());
                        }
                        item.setPhotos(photoUrls);

                        //User Data
                        AVObject userObj = object.getAVObject("creator");
                        //AVObject userObjData = object.fetchIfNeeded();
                        String userObjId=userObj.getObjectId();
                        User user=new User(userObjId,"null",null);
                        item.setUser(user);


                        userObjIds.add(userObjId);

                        //CircleText
                        String circleTxt = object.getString("albumContent");
                        circleTxt=circleTxt==null?"":circleTxt;
                        //circleTxt="123";

                        //CircleId
                        String id=object.getObjectId();



                        //Comments
                        List<AVObject> comments= object.getList("comments");
                        List<CommentItem> commentItems=new ArrayList<CommentItem>();
                        for(AVObject commenObj:comments)
                        {
                            String commentContent=commenObj.getString("commentContent");//null Pointer????
                            AVObject toUser=commenObj.getAVObject("toUser");
                            AVObject commentUser=commenObj.getAVObject("commentUser");
                            String toUserId=toUser==null?null:toUser.getObjectId();
                            String commentUserId=commentUser==null?"null":commentUser.getObjectId();
                            userObjIds.add(toUserId);
                            userObjIds.add(commentUserId);

                            CommentItem commentItem=new CommentItem();
                            commentItem.setId(commenObj.getObjectId());
                            commentItem.setUser(new User(commentUserId,"null",null));
                            if(toUserId!=null) commentItem.setToReplyUser(new User(toUserId,"null",null));
                            commentItem.setContent(commentContent);
                            commentItem.setCommentObj(commenObj);
                            commentItems.add(commentItem);
                        }
                        item.setComments(commentItems);
                        //Like Users
                        List<AVObject> likeUsers= object.getList("digUsers");
                        List<FavortItem> likeItems=new ArrayList<FavortItem>();
                        for(AVObject likeUser:likeUsers)
                        {
                            String likeUserId=likeUser.getObjectId();

                            userObjIds.add(likeUserId);

                            FavortItem likeItem=new FavortItem();
                            likeItem.setId(id+likeUserId);
                            likeItem.setUser(new User(likeUserId,"null",null));

                            likeItems.add(likeItem);
                        }
                        item.setFavorters(likeItems);
                        item.setId(object.getObjectId());
                        item.setContent(circleTxt);


                        /*if(photos.size()>0){
                            item.setType("1");
                        }else{item.setType("2");}
                        item.setPhotos(photoUrls);*/
                        items.add(item);
                    }
                    //get Users' data
                   /* AVQuery<AVObject> getUserQ;
                    ArrayList<AVQuery<AVObject>> queries=new ArrayList<>();
                    for(String objId:userObjIds)
                    {
                        getUserQ=new AVQuery<AVObject>("_User");
                        getUserQ.whereEqualTo("objectId",objId);
                        queries.add(getUserQ);
                    }*/
                    //AVQuery<AVObject> mainQuery=AVQuery.or(queries);
                    queryUser.whereContainedIn("objectId",userObjIds);
                    queryUser.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> userList, AVException e) {
                            if(e==null){
                                HashMap<String,User> userHashMap=new HashMap<String, User>();
                                for(AVObject object:userList)
                                {
                                    String id=object.getObjectId();
                                    String name=object.getString("Name");
                                    name=name==null?"null":name;
                                    AVFile file=object.getAVFile("Photo");
                                    String fileUrl=file==null?null:file.getUrl();
                                    User tempUser=new User(id,name,fileUrl);
                                    tempUser.setUserObj(object);
                                    userHashMap.put(id,tempUser);
                                }
                                for(CircleItem item:items)
                                {
                                    User user=item.getUser();
                                    User userNew=userHashMap.get(user.getId());
                                    if(userNew!=null) item.setUser(userNew);

                                    //set comment
                                    List<CommentItem> commentItems=item.getComments();

                                    for(CommentItem commentItem:commentItems)
                                    {
                                        User commentUser=commentItem.getUser();
                                        User toUser=commentItem.getToReplyUser();
                                        User commentUserNew=userHashMap.get(commentUser.getId());
                                        if(commentUserNew!=null) commentItem.setUser(commentUserNew);
                                        if(toUser!=null)
                                        {
                                            User toUserNew=userHashMap.get(toUser.getId());
                                            if(toUserNew!=null) commentItem.setToReplyUser(toUserNew);
                                        }
                                    }
                                    //set likes
                                    List<FavortItem> favortItems=item.getFavorters();
                                    for(FavortItem favortItem:favortItems){
                                        User likeUser=favortItem.getUser();
                                        User likeUserNew=userHashMap.get(likeUser.getId());
                                        if(likeUserNew!=null) favortItem.setUser(likeUserNew);
                                    }

                                }
                                callBack.done(items);
                            }else{Log.d("error",e.getMessage());
                                callBack.done(new ArrayList<CircleItem>());}
                        }
                    });

                }else{
                    Log.d("error",e.getMessage());
                    callBack.done(new ArrayList<CircleItem>());
                }
            }
        });
    }

    public User createUser(AVObject object) {
        String name=object.getString("Name");
        name=name==null?"UNKNOWN":name;
        String id=object.getObjectId();
        AVFile file=object.getAVFile("Photo");
        String url=file==null?null:file.getUrl();
        return new User(id,name,url);
    }

    public void likeCircle(int position, CircleItem circleItem, final MyCallBack<FavortItem> callBack){
        final AVObject avObject=circleItem.getCirlceObj();
        if(avObject!=null){
            List<AVObject> likeList=avObject.getList("digUsers");
            likeList.add(AVUser.getCurrentUser());
            avObject.put("digUsers",likeList);
            avObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if(e==null){
                        FavortItem item = new FavortItem();
                        item.setId(avObject.getObjectId()+AVUser.getCurrentUser().getObjectId());
                        item.setUser(DatasUtil.curUser);
                        callBack.done(item);
                    }else{e.printStackTrace();
                    callBack.done(null);}
                }
            });
        }else{callBack.done(null);}
    }
    public void unLikeCircle(CircleItem circleItem,final MyCallBack<String> callBack){
        final AVObject avObject=circleItem.getCirlceObj();
        if(avObject!=null){
            List<AVObject> likeList=avObject.getList("digUsers");
            int userPos=likeList.indexOf(AVUser.getCurrentUser());
            likeList.remove(userPos);
            avObject.put("digUsers",likeList);
            avObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if(e==null){
                        FavortItem item = new FavortItem();
                        item.setId(avObject.getObjectId()+AVUser.getCurrentUser().getObjectId());
                        item.setUser(DatasUtil.curUser);
                        callBack.done("success");
                    }else{e.printStackTrace();
                        callBack.done(null);}
                }
            });
        }else{callBack.done(null);}
    }

    public void addComment(final CommentItem commentItem, CircleItem circleItem, final MyCallBack<CommentItem> callBack){
        final AVObject avObject=circleItem.getCirlceObj();
        if(avObject!=null){
            final List<AVObject> avObjects=avObject.getList("comments");
            final AVObject commentObj=new AVObject("AlbumComment");
            if(commentItem.getToReplyUser()!=null) commentObj.put("toUser",commentItem.getToReplyUser().getUserObj());
            if(commentItem.getUser()!=null) commentObj.put("commentUser",commentItem.getUser().getUserObj());
            commentObj.put("commentContent",commentItem.getContent());
            commentObj.put("album",avObject);
            //avObjects.add(commentObj);
            //avObject.put("comments",avObjects);
            commentObj.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if(e==null){
                        avObjects.add(commentObj);
                        avObject.put("comments",avObjects);
                        commentItem.setCommentObj(commentObj);
                        avObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if(e==null){
                                    commentItem.setId(avObject.getObjectId());
                                    callBack.done(commentItem);
                                }else{
                                    callBack.done(null);
                                }
                            }
                        });
                    }else{callBack.done(null);}
                }
            });

        }else{callBack.done(null);}
    }

    public void deleteComment(final CommentItem commentItem,CircleItem circleItem,final MyCallBack<String> callBack){
        final AVObject commentObj=commentItem.getCommentObj();
        AVObject circleObj=circleItem.getCirlceObj();
        if(commentObj!=null && circleObj!=null){
            List<AVObject> comments=circleObj.getList("comments");
            int posId = comments.indexOf(commentObj);
            comments.remove(posId);
            circleObj.put("comments",comments);
            circleObj.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if(e==null){
                        commentObj.deleteInBackground(new DeleteCallback() {
                            @Override
                            public void done(AVException e) {
                                if(e==null){
                                    callBack.done("success");
                                }else{callBack.done(null);}
                            }
                        });
                    }else{callBack.done(null);}
                }
            });
        }else
        {callBack.done(null);}
    }

    public void deleteCircle(CircleItem circleItem,final MyCallBack<String> callBack){
        AVObject avObject=circleItem.getCirlceObj();
        if(avObject!=null){
            avObject.put("isDel",true);
            avObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if(e==null){callBack.done("success");}else{callBack.done(null);}
                }
            });
        }else{
            callBack.done(null);
        }
    }

}
