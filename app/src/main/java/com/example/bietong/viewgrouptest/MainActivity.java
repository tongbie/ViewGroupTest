package com.example.bietong.viewgrouptest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static RightSlip rightSlip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToolClass toolClass=new ToolClass(this);
        start();
    }

    public void button(View view) {
        start();
    }

    private void start(){
        Intent intent=new Intent(MainActivity.this,TestActivity.class);
        startActivity(intent);
    }
}
