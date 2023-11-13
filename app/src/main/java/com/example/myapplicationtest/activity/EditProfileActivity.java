package com.example.myapplicationtest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplicationtest.R;
import com.example.myapplicationtest.dao.UserDBOpenHelper;
import com.example.myapplicationtest.model.User;


public class EditProfileActivity extends AppCompatActivity {

    private EditText etEditName;
    private EditText etEditPhone;
    private EditText etEditHobby;


    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_edit_profile);


        Intent intent = getIntent();
        if (intent != null) {
            String username = intent.getStringExtra("username");
            // 其他操作...
            etEditName = findViewById(R.id.etEditName);
            etEditPhone = findViewById(R.id.etEditPhone);
            etEditHobby = findViewById(R.id.etEditHobby);
            btnSave = findViewById(R.id.btnSave);

            // Load existing user data to populate the fields
            loadUserData(username);


            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    saveChanges(username);
                }
            });
        }

    }

    private void loadUserData(String username) {
        UserDBOpenHelper dbHelper = new UserDBOpenHelper(this);
        User user = dbHelper.getSingleUserData(username);
        dbHelper.close();

        if (user != null) {
            // Found the user, populate the EditText fields
            etEditName.setText(user.getUsername());
            etEditPhone.setText(user.getUserphone());
            etEditHobby.setText(user.getUseraddress());

        } else {
            // Handle the case where the user is not found
            Toast.makeText(this, "用户不存在", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveChanges(String username) {
        String newName = etEditName.getText().toString().trim();
        String newPhone = etEditPhone.getText().toString().trim();
        String newHobby = etEditHobby.getText().toString().trim();

        // Add your logic to save changes to the user's profile (e.g., update in a database)
        UserDBOpenHelper dbHelper = new UserDBOpenHelper(this);

        String userIdByUsername = dbHelper.getUserIdByUsername(username);


        dbHelper.update(userIdByUsername,newName,newPhone,newHobby);

        // For demonstration, show a toast message
        Toast.makeText(this, "更改成功", Toast.LENGTH_SHORT).show();

        // Finish the activity or navigate back to the profile screen
        finish();




    }
}
