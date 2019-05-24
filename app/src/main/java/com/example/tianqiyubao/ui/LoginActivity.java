package com.example.tianqiyubao.ui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianqiyubao.MainActivity;
import com.example.tianqiyubao.R;
import com.example.tianqiyubao.util.DensityUtil;
import com.example.tianqiyubao.util.ShakeHelper;
import com.example.tianqiyubao.util.SharePreferenceUtil;
import com.example.tianqiyubao.util.SoftKeyBoardListener;
import com.example.tianqiyubao.util.UIutil;
import com.example.tianqiyubao.util.WelcomeGuideActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView idIvLoginLogo;
    EditText idEtUserAccount;
    ImageView idIvCleanAccount;
    EditText idEtUserPwd;
    ImageView idIvCleanPwd;
    CheckBox idCbPwd;
    TextView idTvForgetPwd;
    Button idBtnLogin;
    Button idBtnRegister;
    NestedScrollView idNsvLogin;
    LinearLayout idClContentLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
    }

    private void initView() {
        idIvLoginLogo = (ImageView) findViewById(R.id.id_iv_login_logo);
        idEtUserAccount = (EditText) findViewById(R.id.id_et_user_account);
        idIvCleanAccount = (ImageView) findViewById(R.id.id_iv_clean_account);
        idEtUserPwd = (EditText) findViewById(R.id.id_et_user_pwd);
        idIvCleanPwd = (ImageView) findViewById(R.id.id_iv_clean_pwd);
        idCbPwd = (CheckBox) findViewById(R.id.id_cb_pwd);
        idTvForgetPwd = (TextView) findViewById(R.id.id_tv_forget_pwd);
        idBtnLogin = (Button) findViewById(R.id.id_btn_login);
        idBtnRegister = (Button) findViewById(R.id.id_btn_register);
        idNsvLogin = (NestedScrollView) findViewById(R.id.id_nsv_login);
        idClContentLogin = (LinearLayout) findViewById(R.id.id_cl_content_login);

        /**
         * 禁止键盘弹起的时候可以滚动
         */
        idNsvLogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        idNsvLogin.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                SoftKeyBoardListener.setListener(LoginActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
                    @Override
                    public void keyBoardShow(int height) {
                        int dist = (int) (DensityUtil.dp2px(LoginActivity.this, (80) * 0.4f));
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(idClContentLogin, "translationY", 0.0f, -dist);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        UIutil.zoomIn(idIvLoginLogo, 0.6f, dist);
                    }

                    @Override
                    public void keyBoardHide(int height) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(idClContentLogin, "translationY", idClContentLogin.getTranslationY(), 0);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        //键盘收回后，logo恢复原来大小，位置同样回到初始位置
                        UIutil.zoomOut(idIvLoginLogo, 0.6f);
                    }
                });
            }
        });
    }

    private void initListener() {
        idIvCleanAccount.setOnClickListener(this);
        idIvCleanPwd.setOnClickListener(this);
        idTvForgetPwd.setOnClickListener(this);
        idBtnLogin.setOnClickListener(this);
        idBtnRegister.setOnClickListener(this);

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

        idCbPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    idEtUserPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    idEtUserPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                String pwd = idEtUserPwd.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    idEtUserPwd.setSelection(pwd.length());
            }
        });
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
            case R.id.id_tv_forget_pwd:
//                // TODO: 2018/10/14 跳转到忘记密码的界面
//                idEtUserAccount.setText("");
//                idEtUserPwd.setText("");
//                skipActivity(LoginActivity.this, ForgetPwdActivity.class);
                break;
            case R.id.id_btn_login:
                String userName = idEtUserAccount.getText().toString().trim();
                if (isEtEmpty(userName, "用户名不能为空...", idEtUserAccount)) return;

                String userPwd = idEtUserPwd.getText().toString().trim();
                if (isEtEmpty(userPwd, "用户密码不能为空...", idEtUserPwd)) return;

                // 读SP用户名密码
                String pwd = SharePreferenceUtil.getString(userName, "");
                if (!TextUtils.isEmpty(pwd) && userPwd.equals(pwd)) { // 登陆成功
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    SharePreferenceUtil.putString(userName, userPwd); // 保存用户名密码
                    SharePreferenceUtil.putBoolean("isLogin", true);
                    startActivity(new Intent(LoginActivity.this, WelcomeGuideActivity.class));
                    finish();
                } else if (!TextUtils.isEmpty(pwd) && !userPwd.equals(pwd)){
                    Toast.makeText(LoginActivity.this, "输入的用户名密码错误", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(pwd)) {
                    Toast.makeText(LoginActivity.this, "用户不存在，请先注册", Toast.LENGTH_SHORT).show();
                    return;
                }


                break;
            case R.id.id_btn_register:
                idEtUserAccount.setText("");
                idEtUserPwd.setText("");
                skipActivity(LoginActivity.this, RegisterActivity.class);
                finish();
                break;
        }
    }
}
