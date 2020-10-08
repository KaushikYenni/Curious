package com.example.curious;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CommentActivity extends AppCompatActivity {
    String dummy="dummy";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);
        TextView author=findViewById(R.id.authorTV);
        author.setText(dummy);
        TextView body=findViewById(R.id.bodyTV);
        body.setText(dummy);
        TextView time=findViewById(R.id.timeTV);
        time.setText(dummy);
    }
}
