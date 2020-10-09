package com.example.curious.network;

import com.example.curious.model.CommentData;
import com.example.curious.util.ResultCode;

public interface FetchDataCallback {
    void onSuccess(CommentData commentData);

    void onException(ResultCode noDataFound);

    void onException(ResultCode unknownError, Throwable throwable);
}
