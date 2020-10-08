package com.example.curious.util;

import android.app.Activity;
import android.view.LayoutInflater;

import com.example.curious.model.CommentData;

public class BuildAlert {

    private Activity activity;
    private ResultCode resultCode;
    private String intentString;
    private Throwable throwable;
    private CommentData commentData;
    private LayoutInflater inflater;

    /**
     * Initialize BuildAlert for generic alert dialog.
     * @param activity Context to be passed to AlertDialog.Builder
     * @param resultCode Determines which dialog to display.
     */
    public BuildAlert(Activity activity, ResultCode resultCode) {
        this.activity = activity;
        this.resultCode = resultCode;
        this.inflater = this.activity.getLayoutInflater();
    }

    /**
     * Initialize BuildAlert for submission dialog.
     * @param activity Context to be passed to AlertDialog.Builder
     * @param resultCode Determines which dialog to display.
     * @param intentString Used either for removeddit link (submission) or debugging (error).
     */
    public BuildAlert(Activity activity, ResultCode resultCode, String intentString) {
        this.activity = activity;
        this.resultCode = resultCode;
        this.intentString = intentString;
        this.inflater = this.activity.getLayoutInflater();
    }

    /**
     * Initialize BuildAlert for valid comment dialog or more details dialog.
     * @param activity Context to be passed to AlertDialog.Builder
     * @param resultCode Determines which dialog to display.
     * @param intentString Used for removeddit link.
     * @param commentData Data fetched from Pushshift.
     */
    public BuildAlert(Activity activity, ResultCode resultCode, String intentString, CommentData commentData) {
        this.activity = activity;
        this.resultCode = resultCode;
        this.intentString = intentString;
        this.commentData = commentData;
        this.inflater = this.activity.getLayoutInflater();
    }

    /**
     * Initialize BuildAlert for error dialog (email info to dev).
     * @param activity Context to be passed to AlertDialog.Builder
     * @param intentString Intent string to be emailed to dev for debugging.
     * @param throwable Stack trace to be emailed to dev for debugging.
     */
    public BuildAlert(Activity activity, String intentString, Throwable throwable) {
        this.activity = activity;
        this.intentString = intentString;
        this.throwable = throwable;
        this.inflater = this.activity.getLayoutInflater();
    }


}
