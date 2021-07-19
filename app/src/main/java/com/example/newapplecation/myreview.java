package com.example.newapplecation;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.io.Serializable;

public class myreview implements Serializable {
    //여기에 이미지만 추가가 된다묜 좋겠다.... `^`
    private String title;
    private String review;
    private String userid;
    private float ratingBar;
    private byte[] bytes;
    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(float ratingBar) {
        this.ratingBar = ratingBar;
    }

    public myreview(String title, String review, float ratingBar, String userid) {
        this.title = title;
        this.review = review;
        this.ratingBar = ratingBar;
        this.userid = userid;
    }

    public myreview(String title, String review, float ratingBar, String userid, byte[] bytes1) {
        this.title = title;
        this.review = review;
        this.ratingBar = ratingBar;
        this.userid = userid;
        this.bytes = bytes1;
    }


}