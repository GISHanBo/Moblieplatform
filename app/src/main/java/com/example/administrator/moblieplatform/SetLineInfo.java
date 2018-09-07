package com.example.administrator.moblieplatform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;


public class SetLineInfo extends AppCompatActivity {
    private long id;//全局唯一ID
    private String name;//线路名称
    private String type;//线路类别
    private String abbreviation;//类别简称
    private float length;//线段长度
    private String level;//电压等级
    private String model;//导线型号
    private String sort;//导线分类
    private Integer width;//线样式-宽度
    private String color;//线样式-颜色
    private boolean showLabel;//线样式-注记

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_line_info);
        //取值
        final Intent intent=getIntent();
        id=intent.getLongExtra("id",0);
        length=intent.getFloatExtra("length",0f);
        name=intent.getStringExtra("name");
        type=intent.getStringExtra("type");
        abbreviation=intent.getStringExtra("abbreviation");
        level=intent.getStringExtra("level");
        model=intent.getStringExtra("model");
        sort=intent.getStringExtra("sort");
        color=intent.getStringExtra("color");
        width=intent.getIntExtra("width",1);
        showLabel=intent.getBooleanExtra("showLabel",false);

        EditText line_name=findViewById(R.id.line_name);
        line_name.setText(name);
        EditText line_length=findViewById(R.id.line_length);
        line_name.setText(String.valueOf(length));
        EditText line_type=findViewById(R.id.line_type);
        line_name.setText(type);
        EditText line_abbreviation=findViewById(R.id.line_abbreviation);
        line_name.setText(abbreviation);
        EditText line_level=findViewById(R.id.line_level);
        line_name.setText(level);
        EditText line_model=findViewById(R.id.line_model);
        line_name.setText(model);
        EditText line_sort=findViewById(R.id.line_sort);
        line_name.setText(sort);
        EditText line_color=findViewById(R.id.line_color);
        line_name.setText(color);
        EditText line_width=findViewById(R.id.line_width);
        line_name.setText(String.valueOf(width));
        CheckBox line_showLabel=findViewById(R.id.line_label);
        line_showLabel.setChecked(showLabel);
    }
}
