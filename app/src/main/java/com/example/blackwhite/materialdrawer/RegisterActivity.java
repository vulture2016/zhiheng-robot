package com.example.blackwhite.materialdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.blackwhite.materialdrawer.constant.Params;
import com.example.blackwhite.materialdrawer.control.RetrofitApi;
import com.example.blackwhite.materialdrawer.entity.RegisterLoginEntity;
import com.example.blackwhite.materialdrawer.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password2)
    EditText etPassword2;
    @BindView(R.id.bt_register)
    Button btRegister;
    @BindView(R.id.tv_login)
    TextView tvLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(this);
    }

    @OnClick(R.id.tv_login)
    public void loginClick() {
        login();
    }

    private void login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.bt_register)
    public void register() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String password2 = etPassword2.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty() || password2.isEmpty()) {
            ToastUtil.errorToast(getApplicationContext(), "用户名或密码不能为空！");
        } else if (!TextUtils.equals(password, password2)) {
            ToastUtil.errorToast(getApplicationContext(), "密码确认错误，请重新输入！");
        } else {
            requestApiByRetrofit_RxJava(username, password, password2);
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isShouldHideKeyboard(view, event)) {
                hideKeyboard(view != null ? view.getWindowToken() : null);
            }
        }
        return super.dispatchTouchEvent(event);
    }

    // 请求后台注册信息
    private void requestApiByRetrofit_RxJava(String username, String password, String password2) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Params.RegisterLogin_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RetrofitApi api = retrofit.create(RetrofitApi.class);
        api.postRegisterInfo(username, password, password2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseRegister, Throwable::printStackTrace);
    }

    // 处理获得到的问答信息
    private void handleResponseRegister(RegisterLoginEntity entity) {
        if (entity == null) return;
        if (entity.getCode() == 0) {
            ToastUtil.errorToast(getApplicationContext(), "用户名已存在！");
        } else if (entity.getCode() == 200) {
            ToastUtil.successToast(getApplicationContext(), "注册成功！");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private int mBackKeyPressedTimes = 0;//记录回退键点击次数

    /**
     * 双击退出程序
     */
    @Override
    public void onBackPressed() {
        if (mBackKeyPressedTimes == 0) {
            ToastUtil.infoToast(getApplicationContext(), "再按一次退出程序");
            mBackKeyPressedTimes = 1;
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        mBackKeyPressedTimes = 0;
                    }
                }
            }.start();
            return;
        } else {
            exitApp();
        }
        super.onBackPressed();
    }
}
