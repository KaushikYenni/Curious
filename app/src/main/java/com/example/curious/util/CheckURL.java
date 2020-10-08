package com.example.curious.util;

import android.net.Uri;
import android.util.Log;
import android.webkit.URLUtil;

import java.util.List;

public class CheckURL {
    private static final String TAG = "CheckURL";
    private ResultCode resultCode;

    public String check(String intentString) {
        String id = null;

        if (URLUtil.isValidUrl(intentString)) {
            Uri validUrl = Uri.parse(intentString);
            String host = validUrl.getHost();

            if (host != null && host.contains("reddit.com")) {
                List<String> pathSegments = validUrl.getPathSegments();

                // URL structure: reddit.com/r/{subreddit}/comments/{submission id}/{submission title}/{comment id}/
                if (pathSegments.size() == 5) { // submission
                    id = pathSegments.get(3);
                    Log.i(TAG, "Submission ID: " + id);
                    setResultCode(ResultCode.SUBMISSION);
                }
                else if (pathSegments.size() == 6) { // comment
                    id = pathSegments.get(5);
                    Log.i(TAG, "Comment ID: " + id);
                    setResultCode(ResultCode.VALID_COMMENT);
                }
                else if (pathSegments.get(0).equals("comments")) {
                    if (pathSegments.size() == 4) { // reddit.com/comments/{submission id}/{submission title}/{comment id}/
                        id = pathSegments.get(3);
                        Log.i(TAG, "Comment ID: " + id);
                        setResultCode(ResultCode.VALID_COMMENT);
                    }
                    else {
                        id = pathSegments.get(1);
                        Log.i(TAG, "Submission ID: " + id);
                        setResultCode(ResultCode.SUBMISSION);
                    }
                }
                else {
                    Log.e(TAG, "Not a valid comment link.");
                    setResultCode(ResultCode.INVALID_COMMENT);
                }
            }
            else {
                Log.e(TAG, "Not a reddit link. (" + host + ")");
                setResultCode(ResultCode.NOT_REDDIT_LINK);
            }
        }
        else {
            Log.e(TAG, "Not a valid URL.");
            setResultCode(ResultCode.NOT_URL);
        }

        return id;
    }

    public ResultCode getResultCode() {
        return this.resultCode;
    }

    private void setResultCode(ResultCode resultCode){
        this.resultCode = resultCode;
    }
}
