package com.happydoc.lewis.myapplication.circle.widgets.videolist.model;

import android.media.MediaPlayer;

import com.happydoc.lewis.myapplication.circle.widgets.videolist.widget.TextureVideoView;


/**
 * @author Wayne
 */
public interface VideoLoadMvpView {

    TextureVideoView getVideoView();

    void videoBeginning();

    void videoStopped();

    void videoPrepared(MediaPlayer player);

    void videoResourceReady(String videoPath);
}
