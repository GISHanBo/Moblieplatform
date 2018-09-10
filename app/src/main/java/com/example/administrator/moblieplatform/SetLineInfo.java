package com.example.administrator.moblieplatform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class SetLineInfo extends AppCompatActivity {
    private static String TAG="设置线路信息";

    private long id;//全局唯一ID
    private String name;//线路名称
    private String type;//线路类别
    private String abbreviation;//类别简称
    private float length;//线段长度
    private String level;//电压等级
    private String model;//导线型号
    private String sort;//导线分类
    private byte width;//线样式-宽度
    private String color;//线样式-颜色
    private boolean showLabel;//线样式-注记
    private float opacity;//线样式-透明度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_set_line_info);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
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
        width=intent.getByteExtra("width",(byte) 1);
        showLabel=intent.getBooleanExtra("showLabel",false);
        opacity=intent.getFloatExtra("opacity",3f);

        final EditText line_name=findViewById(R.id.line_name);
        line_name.setText(name);
        final EditText line_length=findViewById(R.id.line_length);
        line_length.setText(String.valueOf(length));
        final EditText line_type=findViewById(R.id.line_type);
        line_type.setText(type);
        final EditText line_abbreviation=findViewById(R.id.line_abbreviation);
        line_abbreviation.setText(abbreviation);
        final EditText line_level=findViewById(R.id.line_level);
        line_level.setText(level);
        final EditText line_model=findViewById(R.id.line_model);
        line_model.setText(model);
        final EditText line_sort=findViewById(R.id.line_sort);
        line_sort.setText(sort);
        final EditText line_color=findViewById(R.id.line_color);
        line_color.setText(color);
        final EditText line_width=findViewById(R.id.line_width);
        line_width.setText(String.valueOf(width));
        final CheckBox line_showLabel=findViewById(R.id.line_label);
        line_showLabel.setChecked(showLabel);
        final EditText line_opacity=findViewById(R.id.line_opacity);
        line_opacity.setText(String.valueOf(opacity));

        //点击保存按钮，保存设备信息
        Button device_save=findViewById(R.id.device_save);
        device_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name= line_name.getText().toString();
                type= line_type.getText().toString();
                length= Float.parseFloat(line_length.getText().toString());
                abbreviation=line_abbreviation.getText().toString();
                level=line_level.getText().toString();
                model=line_model.getText().toString();
                sort=line_sort.getText().toString();
                color=line_color.getText().toString();
                width= Byte.parseByte(line_width.getText().toString());
                showLabel=line_showLabel.isChecked();
                opacity= Float.parseFloat(line_opacity.getText().toString());

                Intent intent1=new Intent();
                intent1.putExtra("id", id);
                intent1.putExtra("type", type);
                intent1.putExtra("name", name);
                intent1.putExtra("abbreviation", abbreviation);
                intent1.putExtra("length", length);
                intent1.putExtra("level", level);
                intent1.putExtra("model", model);
                intent1.putExtra("sort", sort);
                intent1.putExtra("showLabel", showLabel);
                intent1.putExtra("width", width);
                intent1.putExtra("color", color);
                intent1.putExtra("opacity",opacity);
                setResult(1002,intent1);
                finish();
            }
        });
        //点击取消按钮
        Button device_cancel=findViewById(R.id.device_cancel);
        device_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(9999);
                finish();
            }
        });

        Button device_delete=findViewById(R.id.delete);
        //点击删除按钮，返回删除设备id
        device_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent();
                intent1.putExtra("id", id);
                setResult(1003,intent1);
                finish();
            }
        });
    }
}
