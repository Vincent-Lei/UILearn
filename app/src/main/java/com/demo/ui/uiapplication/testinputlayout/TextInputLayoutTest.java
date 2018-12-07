package com.demo.ui.uiapplication.testinputlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2018/7/4.
 * Title：
 * Note：
 */
public class TextInputLayoutTest extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout textInputLayout_username;
    TextInputLayout textInputLayout_password;
    EditText editText_username;
    EditText editText_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_input_test);
        textInputLayout_username = findViewById(R.id.text_input_layout_username);
        textInputLayout_password = findViewById(R.id.text_input_layout_password);
        editText_username = findViewById(R.id.et_username);
        editText_password = findViewById(R.id.et_password);
        editText_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                String userName = editText_username.getText().toString().trim();
                String password = editText_password.getText().toString().trim();
                textInputLayout_username.setError(null);
                textInputLayout_password.setError(null);
                if (TextUtils.isEmpty(userName) || userName.length() < 5) {
                    textInputLayout_username.setError("username error");
                    textInputLayout_username.getEditText().setFocusable(true);
                    textInputLayout_username.getEditText().setFocusableInTouchMode(true);
                    textInputLayout_username.getEditText().requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password) || password.length() < 8) {
                    textInputLayout_password.setError("password error");
                    textInputLayout_password.getEditText().setFocusable(true);
                    textInputLayout_password.getEditText().setFocusableInTouchMode(true);
                    textInputLayout_password.getEditText().requestFocus();
                }
                break;
        }
    }
}
