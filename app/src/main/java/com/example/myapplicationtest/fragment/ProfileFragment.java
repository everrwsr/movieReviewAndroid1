package com.example.myapplicationtest.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplicationtest.R;
import com.example.myapplicationtest.activity.EditProfileActivity;
import com.example.myapplicationtest.activity.FloginActivity;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = ProfileFragment.class.getSimpleName();

    private Button buttonEditProfile;
    private Button buttonLogout;

    private TextView tv_username;

    public ProfileFragment() {
        // Required empty public constructor
    }



    public void initData() {

        // 读取本地保存用户的登录信息
        SharedPreferences sp = getActivity().getSharedPreferences("get_user", Context.MODE_PRIVATE);
        String user = sp.getString("userphone","");
        // 把用户名显示到textView控件上
        tv_username.setText(user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);



        // Initialize views
        buttonEditProfile = view.findViewById(R.id.buttonEditProfile);
        buttonLogout = view.findViewById(R.id.buttonLogout);
        tv_username = view.findViewById(R.id.userphone);

        // Set click listeners
        buttonEditProfile.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

        initData();

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == buttonEditProfile) {
            // Handle edit profile button click
            // Add your logic to open the edit profile screen

            Intent intent = new Intent(getActivity(), EditProfileActivity.class);

            // 携带用户名数据
            String username = tv_username.getText().toString();
            intent.putExtra("username", username);

            startActivity(intent);

        } else if (v == buttonLogout) {
            // Handle logout button click

            SharedPreferences.Editor editor = getActivity().getSharedPreferences("get_user", Context.MODE_PRIVATE).edit();
            editor.clear(); // 清除本地保存信息
            editor.apply();

            Intent intent = new Intent(getActivity(), FloginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
}