package com.example.curious.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.curious.model.CommentData;
import com.example.curious.model.CommentViewModel;
import com.example.curious.network.FetchData;
import com.example.curious.network.FetchDataCallback;
import com.example.curious.util.BuildAlert;
import com.example.curious.util.CheckURL;
import com.example.curious.util.ResultCode;

public class CommentActivity extends AppCompatActivity implements FetchDataCallback {
    private static final String TAG = "CommentActivity";

    private CommentViewModel viewModel;
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Override // handle activity destruction issues (e.g. screen rotation)
    protected void onDestroy() {
        super.onDestroy();

        removeProgressDialog();

        if (alertDialog != null) {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        }
    }

    private void initialize() {
        this.viewModel=new ViewModelProvider(this).get(CommentViewModel.class);
        if (viewModel.throwable != null) {
            Log.i(TAG, "initialize: Already initialized, unknown error in FetchData.");
            buildAndShowError(viewModel.throwable);
        }
        else if (viewModel.commentData != null) {
            Log.i(TAG, "initialize: Already initialized, checked URL, and fetched data.");
            buildAndShowAlert(viewModel.resultCode);
        }
        else if (viewModel.resultCode == null) {
            Log.i(TAG, "initialize: Getting intentString and checking URL.");
            Intent intent = getIntent();

            if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                viewModel.intentString = intent.getStringExtra(Intent.EXTRA_TEXT);
                Log.i(TAG, "intentString: " + viewModel.intentString);

                CheckURL checkURL = new CheckURL();
                viewModel.id = checkURL.check(viewModel.intentString);
                viewModel.resultCode = checkURL.getResultCode();

                if (viewModel.resultCode == ResultCode.VALID_COMMENT) {
                    Log.i(TAG, "initialize: intentString is a valid comment. Fetching data.");
                    (new FetchData(viewModel.id)).fetch(this);
                    showProgressDialog();
                }
                else {
                    Log.i(TAG, "initialize: resultCode: " + viewModel.resultCode.name());
                    buildAndShowAlert(viewModel.resultCode);
                }
            }
        }
        else if (viewModel.resultCode == ResultCode.VALID_COMMENT) {
            Log.i(TAG, "initialize: Already initialized and checked URL. Fetching data.");
            (new FetchData(viewModel.id)).fetch(this);
            showProgressDialog();
        }
        else if (viewModel.intentString != null) {
            Log.i(TAG, "initialize: Already initialized and checked URL. Showing " +
                    viewModel.resultCode.name() + " alertDialog.");
            buildAndShowAlert(viewModel.resultCode);
        }
    }

    private void buildAndShowAlert(ResultCode resultCode) {
        switch (resultCode) {
            case VALID_COMMENT: {
                Log.i(TAG, "buildAndShowAlert: Showing unremoved comment alertDialog.");
                this.alertDialog = (new BuildAlert(this, resultCode, viewModel.intentString, viewModel.commentData)).build();
                break;
            }
            case SUBMISSION: {
                Log.i(TAG, "buildAndShowAlert: Showing submission alertDialog.");
                this.alertDialog = (new BuildAlert(this, resultCode, viewModel.intentString)).build();
                break;
            }
            case UNKNOWN_ERROR: {
                Log.i(TAG, "buildAndShowAlert: Showing error alertDialog.");
                this.alertDialog = (new BuildAlert(this, resultCode, viewModel.intentString)).build();
                break;
            }
            default: {
                Log.i(TAG, "buildAndShowAlert: Showing " + resultCode.name() + " alertDialog.");
                this.alertDialog = (new BuildAlert(this, resultCode)).build();
                break;
            }
        }
        removeProgressDialog();
        alertDialog.show();
    }

    private void buildAndShowError(Throwable throwable) {
        Log.i(TAG, "buildAndShowError: Showing UNKNOWN_ERROR alertDialog.");
        this.alertDialog = (new BuildAlert(this, viewModel.intentString, throwable)).build();
        removeProgressDialog();
        alertDialog.show();
    }

    private void showProgressDialog() {
        Log.i(TAG, "showProgressDialog: Showing progressDialog.");
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> finish());
            progressDialog.show();
        }
        else {
            progressDialog.show();
        }
    }

    private void removeProgressDialog() {
        Log.i(TAG, "removeProgressDialog: Removing progressDialog.");
        if (progressDialog != null) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void onSuccess(CommentData commentData) {
        Log.i(TAG, "onSuccess: Retrofit returned commentData.");
        viewModel.commentData = commentData;

        if (progressDialog != null) {
            buildAndShowAlert(viewModel.resultCode);
        }
    }

    @Override // FetchDataCallback
    public void onException(ResultCode resultCode) {
        Log.e(TAG, "onException: Retrofit threw exception and returned resultCode: " + resultCode.name());
        viewModel.resultCode = resultCode;

        if (progressDialog != null) {
            buildAndShowAlert(resultCode);
        }
    }

    @Override // FetchDataCallback
    public void onException(ResultCode resultCode, Throwable throwable) {
        Log.e(TAG, "onException: Retrofit threw exception", throwable);
        viewModel.resultCode = resultCode;
        viewModel.throwable = throwable;

        if (progressDialog != null) {
            buildAndShowError(throwable);
        }
    }
}
