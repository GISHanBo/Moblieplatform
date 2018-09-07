package com.example.administrator.moblieplatform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SetDeviceInfo extends AppCompatActivity {
    private long id;//全局唯一ID
    private String name;//设备名称
    private String type;//设备类别
    private float height;//设备高度
    private String material;//设备材质
    private String sLine;//所属线路
    private String picture;//设备图片
    private String category;//设备类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_device_info);
        final Intent intent=getIntent();
        id=intent.getLongExtra("id",0);
        height=intent.getFloatExtra("height",0f);
        name=intent.getStringExtra("name");
        type=intent.getStringExtra("type");
        material=intent.getStringExtra("material");
        sLine=intent.getStringExtra("sLine");
        picture=intent.getStringExtra("picture");
        category=intent.getStringExtra("category");

        final EditText device_name=findViewById(R.id.device_name);
        device_name.setText(name);
        final EditText device_height=findViewById(R.id.device_height);
        device_height.setText(String.valueOf(height));
        final EditText device_material=findViewById(R.id.device_material);
        device_material.setText(material);
        final EditText device_picture=findViewById(R.id.device_picture);
        device_picture.setText(picture);
        final EditText device_sLine=findViewById(R.id.device_sLine);
        device_sLine.setText(sLine);
        final EditText device_type=findViewById(R.id.device_type);
        device_type.setText(type);
        final EditText device_category=findViewById(R.id.device_category);
        device_category.setText(category);
        //点击保存按钮，保存设备信息
        Button device_save=findViewById(R.id.device_save);
        device_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             name= device_name.getText().toString();
             type= device_type.getText().toString();
                material= device_material.getText().toString();
                sLine= device_sLine.getText().toString();
                picture= device_picture.getText().toString();
                height= Float.parseFloat(device_height.getText().toString());
                category=device_category.getText().toString();
                Intent intent1=new Intent();
                intent1.putExtra("id", id);
                intent1.putExtra("type", type);
                intent1.putExtra("name", name);
                intent1.putExtra("height", height);
                intent1.putExtra("material", material);
                intent1.putExtra("sLine", sLine);
                intent1.putExtra("picture",picture);
                intent1.putExtra("category",category);
                setResult(1001,intent1);
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
                setResult(1000,intent1);
                finish();
            }
        });
    }
}
