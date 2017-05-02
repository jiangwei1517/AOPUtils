package com.jiangwei.aoputils;

import com.jiangwei.aop_utils.btnclickhelper.BtnClickTwice;
import com.jiangwei.aop_utils.clockhelper.Clock;
import com.jiangwei.aop_utils.memorycachehelper.MemoryCache;
import com.jiangwei.aop_utils.permissionhelper.CheckPermission;
import com.jiangwei.aop_utils.permissionhelper.PermissionCheck;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.R.attr.permission;

public class ExampleActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Object btn3Person;
    private Object btn3Person2;
    private Object btn3Man;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        btn = (Button) findViewById(R.id.btn);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn3Person = factoryApi(Person.class);
                btn3Person2 = factoryApi(Person.class);
                if (btn3Person == btn3Person2) {
                    Toast.makeText(ExampleActivity.this, "btn3Person == btn3Person2", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ExampleActivity.this, "btn3Person != btn3Person2", Toast.LENGTH_SHORT).show();
                }
                btn3Man = factoryApi(Man.class);
                if (btn3Person == btn3Man) {
                    Toast.makeText(ExampleActivity.this, "btn3Person == btn3Man", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ExampleActivity.this, "btn3Person != btn3Man", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
        CheckPermission.getInstance().init(this);
    }

    /**
     * 1 计时功能
     */
    @Clock
    private void test() {
        SystemClock.sleep(2000);
    }

    /**
     * 2 检查权限
     */
    @PermissionCheck(permission = "android.permission.INTERNET")
    private void checkPermission() {

    }

    @MemoryCache
    private Object factoryApi(Class clazz) {
        switch (clazz.getSimpleName()) {
            case "Person":
                return new Person();
            case "Man":
                return new Man();
            case "Woman":
                return new Woman();
        }
        return null;
    }

    @BtnClickTwice
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn) {
            test();
        } else if (v.getId() == R.id.btn2) {

        }
    }
}
