package com.example.tianqiyubao.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tianqiyubao.MainActivity;
import com.example.tianqiyubao.R;
import com.example.tianqiyubao.util.ShakeHelper;
import com.example.tianqiyubao.util.SharePreferenceUtil;
import com.example.tianqiyubao.util.WelcomeGuideActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView idIvRegisterLogo;
    EditText idEtUserAccount;
    ImageView idIvCleanAccount;
    EditText idEtUserPwd;
    ImageView idIvCleanPwd;
    EditText idEtUserPwdSure;
    ImageView idIvCleanPwdSure;
    Button id_btn_register;
    CheckBox id_cb_pwd, id_cb_pwd_sure;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        initListener();
    }

    private void initView() {
        idIvRegisterLogo = (ImageView) findViewById(R.id.id_iv_register_logo);
        idEtUserAccount = (EditText) findViewById(R.id.id_et_user_account);
        idIvCleanAccount = (ImageView) findViewById(R.id.id_iv_clean_account);
        idEtUserPwd = (EditText) findViewById(R.id.id_et_user_pwd);
        idIvCleanPwd = (ImageView) findViewById(R.id.id_iv_clean_pwd);
        idEtUserPwdSure = (EditText) findViewById(R.id.id_et_user_pwd_sure);
        idIvCleanPwdSure = (ImageView) findViewById(R.id.id_iv_clean_pwd_sure);
        id_btn_register = (Button) findViewById(R.id.id_btn_register);
        id_cb_pwd = (CheckBox) findViewById(R.id.id_cb_pwd);
        id_cb_pwd_sure = (CheckBox) findViewById(R.id.id_cb_pwd_sure);
    }

    private void initListener() {
        idIvCleanAccount.setOnClickListener(this);
        idIvCleanPwd.setOnClickListener(this);
        idIvCleanPwdSure.setOnClickListener(this);
        id_btn_register.setOnClickListener(this);
        idEtUserAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    idIvCleanAccount.setVisibility(View.VISIBLE);
                } else {
                    idIvCleanAccount.setVisibility(View.GONE);
                }
            }
        });

        idEtUserPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    idIvCleanPwd.setVisibility(View.VISIBLE);
                } else {
                    idIvCleanPwd.setVisibility(View.GONE);
                }
            }
        });

        idEtUserPwdSure.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    idIvCleanPwdSure.setVisibility(View.VISIBLE);
                } else {
                    idIvCleanPwdSure.setVisibility(View.GONE);
                }
            }
        });

        idEtUserPwdSure.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean isFouces) {
                if (!isFouces) {
                    String userPwd = idEtUserPwd.getText().toString().trim();
                    if (TextUtils.isEmpty(userPwd)) {
                        idEtUserPwdSure.setText("");
                        Toast.makeText(RegisterActivity.this, "请先输入密码...", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!TextUtils.equals(idEtUserPwdSure.getText().toString().trim(), userPwd)) {
                        Toast.makeText(RegisterActivity.this, "两次输入的密码不一致...", Toast.LENGTH_SHORT).show();
                        ShakeHelper.shake(idEtUserPwdSure);
                        idEtUserPwdSure.setText("");
                    }
                }
            }
        });

        id_cb_pwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setEtInputType(idEtUserPwd, isChecked);
            }
        });

        id_cb_pwd_sure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setEtInputType(idEtUserPwdSure, isChecked);
            }
        });

    }

    /**
     * 设置edittext的inputtype
     *
     * @param et
     * @param isChecked
     */
    private void setEtInputType(EditText et, boolean isChecked) {
        if (isChecked) {
            et.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        String pwd = et.getText().toString();
        if (!TextUtils.isEmpty(pwd))
            et.setSelection(pwd.length());
    }

    public void skipActivity(Context context, Class<?> goal) {
        Intent intent = new Intent(context, goal);
        context.startActivity(intent);
    }

    /**
     * 判断edittext的值是否为空
     *
     * @param editText
     */
    public boolean isEtEmpty(String content, String msg, EditText editText) {
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            ShakeHelper.shake(editText);
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_iv_clean_account:
                idEtUserAccount.setText("");
                break;
            case R.id.id_iv_clean_pwd:
                idEtUserPwd.setText("");
                break;
            case R.id.id_iv_clean_pwd_sure:
                idEtUserPwdSure.setText("");
                break;
            case R.id.id_btn_register:
                String userName = idEtUserAccount.getText().toString().trim();
                if (isEtEmpty(userName, "用户名不能为空...", idEtUserAccount)) return;


                String userPwd = idEtUserPwd.getText().toString().trim();
                if (isEtEmpty(userPwd, "用户密码不能为空...", idEtUserPwd)) return;

                String userPwdSure = idEtUserPwdSure.getText().toString().trim();
                if (isEtEmpty(userPwdSure, "用户再次输入密码不能为空...", idEtUserPwdSure)) return;

                if (!TextUtils.equals(userPwd, userPwdSure)) {
                    Toast.makeText(this, "两次输入的密码不一致...", Toast.LENGTH_SHORT).show();
                    ShakeHelper.shake(idEtUserPwdSure);
                    idEtUserPwdSure.setText("");
                    return;
                }

                // 读SP用户名密码
                String pwd = SharePreferenceUtil.getString(userName, "");
                if (!TextUtils.isEmpty(pwd)) {
                    Toast.makeText(RegisterActivity.this, "该邮箱已被注册，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                } else {// 注册成功
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    SharePreferenceUtil.putString(userName, userPwd); // 保存用户名密码
                    SharePreferenceUtil.putBoolean("isLogin", true);
                    startActivity(new Intent(RegisterActivity.this, WelcomeGuideActivity.class));
                    finish();
                }
                break;
        }
    }
}
