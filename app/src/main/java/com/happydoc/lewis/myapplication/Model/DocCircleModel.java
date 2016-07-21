package com.happydoc.lewis.myapplication.Model;

import android.util.Log;

import org.json.JSONArray;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogUtil;
import com.avos.avoscloud.ObjectValueFilter;
import com.happydoc.lewis.myapplication.circle.bean.CircleItem;
import com.happydoc.lewis.myapplication.circle.bean.CommentItem;
import com.happydoc.lewis.myapplication.circle.bean.FavortItem;
import com.happydoc.lewis.myapplication.circle.bean.User;
import com.happydoc.lewis.myapplication.event.MyCallBack;

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
                            String commentContent=commenObj.getString("commentContent");
                            AVObject toUser=commenObj.getAVObject("toUser");
                            AVObject commentUser=commenObj.getAVObject("commentUser");
                            String toUserId=toUser==null?null:toUser.getObjectId();
                            String commentUserId=commentUser==null?"null":commentUser.getObjectId();
                            userObjIds.add(toUserId);
                            userObjIds.add(commentUserId);

                            CommentItem commentItem=new CommentItem();
                            commentItem.setUser(new User(commentUserId,"null",null));
                            if(toUserId!=null) commentItem.setToReplyUser(new User(toUserId,"null",null));
                            commentItem.setContent(commentContent);

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
                                    userHashMap.put(id,new User(id,name,fileUrl));
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

    public User createUser(AVObject object)
    {
        String name=object.getString("Name");
        name=name==null?"UNKNOWN":name;
        String id=object.getObjectId();
        AVFile file=object.getAVFile("Photo");
        String url=file==null?null:file.getUrl();
        return new User(id,name,url);
    }
}
