package com.example.zwq.crimeactivity;

import java.util.Date;
import java.util.UUID;

public class Crime {
    private UUID mId;
    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    private String mTitle;

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    private Date mDate;

    public boolean getSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    private boolean mSolved;
    public Crime(){
        this(UUID.randomUUID());
    }
    public Crime(UUID id){
        mId=id;
        mDate=new Date();
    }

}