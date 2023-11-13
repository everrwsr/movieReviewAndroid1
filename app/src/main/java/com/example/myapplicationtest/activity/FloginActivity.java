package com.example.myapplicationtest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplicationtest.MainActivity;
import com.example.myapplicationtest.R;
import com.example.myapplicationtest.dao.UserDBOpenHelper;
import com.example.myapplicationtest.model.User;

import java.util.ArrayList;


public class FloginActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 声明自己写的 DBOpenHelper 对象
     * DBOpenHelper(extends SQLiteOpenHelper) 主要用来
     * 创建数据表
     * 然后再进行数据表的增、删、改、查操作
     */
    private UserDBOpenHelper mUserDBOpenHelper;
    private TextView mTvLoginactivityRegister;
    private RelativeLayout mRlLoginactivityTop;
    private EditText mEtLoginactivityUserPhone;
    private LinearLayout mLlLoginactivityTwo;
    private Button mBtLoginactivityLogin;
    private EditText pass;
    /**
     * 创建 Activity 时先来重写 onCreate() 方法
     * 保存实例状态
     * super.onCreate(savedInstanceState);
     * 设置视图内容的配置文件
     * setContentView(R.layout.activity_login);
     * 上面这行代码真正实现了把视图层 View 也就是 layout 的内容放到 Activity 中进行显示
     * 初始化视图中的控件对象 initView()
     * 实例化 DBOpenHelper，待会进行登录验证的时候要用来进行数据查询
     * mDBOpenHelper = new DBOpenHelper(this);
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        mUserDBOpenHelper = new UserDBOpenHelper(this);
    }

    /**
     * onCreaete()中大的布局已经摆放好了，接下来就该把layout里的东西
     * 声明、实例化对象然后有行为的赋予其行为
     * 这样就可以把视图层View也就是layout 与 控制层 Java 结合起来了
     */
    private void initView() {
        // 初始化控件
        mBtLoginactivityLogin = findViewById(R.id.bt_loginactivity_login);
        mTvLoginactivityRegister = findViewById(R.id.tv_loginactivity_register);
        mRlLoginactivityTop = findViewById(R.id.rl_loginactivity_top);
        mEtLoginactivityUserPhone = findViewById(R.id.et_loginactivity_phone);
        mLlLoginactivityTwo = findViewById(R.id.ll_loginactivity_two);
        pass=findViewById(R.id.et_loginactivity_pass);
        // 设置点击事件监听器
        mBtLoginactivityLogin.setOnClickListener(this);
        mTvLoginactivityRegister.setOnClickListener(this);
    }


    /**
     * 登录验证：
     *
     * 从EditText的对象上获取文本编辑框输入的数据，并把左右两边的空格去掉
     *  String name = mEtLoginactivityUsername.getText().toString().trim();
     *  String password = mEtLoginactivityPassword.getText().toString().trim();
     *  进行匹配验证,先判断一下用户名密码是否为空，
     *  if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password))
     *  再进而for循环判断是否与数据库中的数据相匹配
     *  if (name.equals(user.getName()) && password.equals(user.getPassword()))
     *  一旦匹配，立即将match = true；break；
     *  否则 一直匹配到结束 match = false；
     *
     *  登录成功之后，进行页面跳转：
     *
     *  Intent intent = new Intent(this, MainActivity.class);
     *  startActivity(intent);
     *  finish();//销毁此Activity
     */
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.tv_loginactivity_register) {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        } else if (viewId == R.id.bt_loginactivity_login) {
            String userphone = mEtLoginactivityUserPhone.getText().toString().trim();
            String password = pass.getText().toString().trim();
            if (!TextUtils.isEmpty(userphone) && !TextUtils.isEmpty(password)) {
                ArrayList<User> data = mUserDBOpenHelper.getAllData();
                boolean match = false;
                for (int i = 0; i < data.size(); i++) {
                    User user = data.get(i);
                    if (userphone.equals(user.getUserphone()) && (password.equals(user.getUserid()))) {
                        match = true;
                        break;
                    } else {
                        match = false;
                    }
                }
                if (match) {
                    // 把账号保存到本地
                    SharedPreferences.Editor editor = getSharedPreferences("get_user", MODE_PRIVATE).edit();
                    editor.putString("userphone", userphone);
                    editor.apply();

                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish(); // 销毁此Activity
                } else {
                    Toast.makeText(this, "手机号或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "请完整地输入你的手机号和密码（注册id）", Toast.LENGTH_SHORT).show();
            }
        }
    }
}



