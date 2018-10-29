package com.cseacademia.mominulcse1213.cseacademia;

/**
 * Created by Mominulcse1213 on 9/27/2018.
 */


public class PostConstructor {

    private String postId;
    private String postSession;
    private String postIngTime;
    private String postTimeOut;
    private String postDes;

    public PostConstructor() {
        //this constructor is required
    }

    public PostConstructor(String postId, String postSession, String postIngTime, String postTimeOut, String postDes) {
        this.postId = postId;
        this.postSession = postSession;
        this.postIngTime = postIngTime;
        this.postTimeOut = postTimeOut;
        this.postDes = postDes;
    }

    public String getPostId() {
        return postId;
    }

    public String getPostSession() {
        return postSession;
    }

    public String getPostIngTime() {
        return postIngTime;
    }

    public String getPostTimeOut() {
        return postTimeOut;
    }

    public String getPostDes() {
        return postDes;
    }
}
