package com.example.curious.model;

import androidx.lifecycle.ViewModel;

import com.example.curious.util.ResultCode;

public class CommentViewModel extends ViewModel {
    public ResultCode resultCode;
    public CommentData commentData;
    public String intentString;
    public String id;
    public Throwable throwable;
}
