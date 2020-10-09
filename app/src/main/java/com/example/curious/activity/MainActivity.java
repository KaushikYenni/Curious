package com.example.curious.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.curious.R;
import com.example.curious.util.CheckURL;
import com.example.curious.util.ResultCode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonClick(View view) {
        EditText editText=findViewById(R.id.edit_text);
        String url= String.valueOf(editText.getText());
        CheckURL checkURL = new CheckURL();
        String id= checkURL.check(url);
        ResultCode resultCode=checkURL.getResultCode();
        if(resultCode==ResultCode.NOT_URL)
            Toast.makeText(this,"Please enter a valid URL",Toast.LENGTH_SHORT).show();
        else
        {
            Intent commentIntent=new Intent(this,CommentActivity.class);
            commentIntent.putExtra(Intent.EXTRA_TEXT,url);
            startActivity(commentIntent);
        }

    }
}