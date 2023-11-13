package com.example.myapplicationtest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myapplicationtest.MainActivity;
import com.example.myapplicationtest.R;
import com.example.myapplicationtest.dao.UserDBOpenHelper;
import com.example.myapplicationtest.util.Code;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private String realCode;
    private UserDBOpenHelper mUserDBOpenHelper;
    private Button mBtRegisteractivityRegister;
    private RelativeLayout mRlRegisteractivityTop;
    private ImageView mIvRegisteractivityBack;
    private LinearLayout mLlRegisteractivityBody;
    private EditText mEtRegisteractivityUsername;
    private EditText mEtRegisteractivityUserid;
    private EditText mEtRegisteractivityUserphone;
    private EditText mEtRegisteractivityUseraddress;
    private EditText mEtRegisteractivityPhonecodes;
    private ImageView mIvRegisteractivityShowcode;
    private RelativeLayout mRlRegisteractivityBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        mUserDBOpenHelper = new UserDBOpenHelper(this);

        //将验证码用图片的形式显示出来
        mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
    }

    private void initView(){
        mBtRegisteractivityRegister = findViewById(R.id.bt_registeractivity_register);
        mRlRegisteractivityTop = findViewById(R.id.rl_registeractivity_top);
        mIvRegisteractivityBack = findViewById(R.id.iv_registeractivity_back);
        mLlRegisteractivityBody = findViewById(R.id.ll_registeractivity_body);
        mEtRegisteractivityUsername = findViewById(R.id.et_registeractivity_name);
        mEtRegisteractivityUserid = findViewById(R.id.et_registeractivity_id);
        mEtRegisteractivityUserphone = findViewById(R.id.et_registeractivity_phone);
        mEtRegisteractivityUseraddress = findViewById(R.id.et_registeractivity_address);
        mEtRegisteractivityPhonecodes = findViewById(R.id.et_registeractivity_phoneCodes);
        mIvRegisteractivityShowcode = findViewById(R.id.iv_registeractivity_showCode);
        mRlRegisteractivityBottom = findViewById(R.id.rl_registeractivity_bottom);

        /**
         * 注册页面能点击的就三个地方
         * top处返回箭头、刷新验证码图片、注册按钮
         */
        mIvRegisteractivityBack.setOnClickListener(this);
        mIvRegisteractivityShowcode.setOnClickListener(this);
        mBtRegisteractivityRegister.setOnClickListener(this);
    }

    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.iv_registeractivity_back) {
            Intent intent1 = new Intent(this, FloginActivity.class);
            startActivity(intent1);
            finish();
        } else if (viewId == R.id.iv_registeractivity_showCode) {
            mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
            realCode = Code.getInstance().getCode().toLowerCase();
        } else if (viewId == R.id.bt_registeractivity_register) {
            String username = mEtRegisteractivityUsername.getText().toString().trim();
            String userid = mEtRegisteractivityUserid.getText().toString().trim();
            String userphone = mEtRegisteractivityUserphone.getText().toString().trim();
            String useraddress = mEtRegisteractivityUseraddress.getText().toString().trim();
            String phoneCode = mEtRegisteractivityPhonecodes.getText().toString().toLowerCase();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(userid) || TextUtils.isEmpty(phoneCode) ||
                    TextUtils.isEmpty(userphone) || TextUtils.isEmpty(useraddress)) {
                Toast.makeText(this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!phoneCode.equals(realCode)) {
                Toast.makeText(this, "验证码错误，注册失败", Toast.LENGTH_SHORT).show();
                return;
            }

            mUserDBOpenHelper.add(username, userid, userphone, useraddress);
            Intent intent2 = new Intent(this, MainActivity.class);
            startActivity(intent2);
            finish();
            Toast.makeText(this, "验证通过，注册成功", Toast.LENGTH_SHORT).show();
        }
    }
}

