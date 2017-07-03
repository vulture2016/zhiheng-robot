package com.example.blackwhite.materialdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.blackwhite.materialdrawer.constant.Params;
import com.example.blackwhite.materialdrawer.control.RetrofitApi;
import com.example.blackwhite.materialdrawer.entity.LoginEntity;
import com.example.blackwhite.materialdrawer.entity.RegisterLoginEntity;
import com.example.blackwhite.materialdrawer.entity.TokenEntity;
import com.example.blackwhite.materialdrawer.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_forget_pwd)
    TextView tvForgetPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.bt_login)
    public void loginClick() {
        login();
    }

    @OnClick(R.id.tv_register)
    public void registerClick() {
        register();
    }


    private void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }


    private void login() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            ToastUtil.errorToast(getApplicationContext(), "用户名或密码不能为空！");
        } else {
            requestApiByRetrofit_RxJava(username, password);
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

    // 请求后台登录信息
    private void requestApiByRetrofit_RxJava(String username, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Params.RegisterLogin_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RetrofitApi api = retrofit.create(RetrofitApi.class);
        LoginEntity entity = new LoginEntity();
        entity.setUsername(username);
        entity.setPassword(password);
        api.postLoginInfo(entity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseLoginState, Throwable::printStackTrace);
    }


    // 处理获得到的问答信息
    private void handleResponseLoginState(RegisterLoginEntity entity) {
        if (entity == null) {
            return;
        }
        BaseActivity.token = entity.getData();
        if (entity.getCode() == 100 && !TextUtils.isEmpty(entity.getData())) {
            requestApiByRetrofit_RxJava(BaseActivity.token);
        } else if (entity.getCode() == 0) {
            ToastUtil.errorToast(getApplicationContext(), "用户名或者密码错误，请重试！");
        }
    }

    // 请求后台是否登陆
    private void requestApiByRetrofit_RxJava(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Params.RegisterLogin_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RetrofitApi api = retrofit.create(RetrofitApi.class);
        api.postIsLoginInfo(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseLogin, Throwable::printStackTrace);

    }

    // 处理获得到的问答信息
    private void handleResponseLogin(TokenEntity entity) {
        if (entity == null) return;
        if (entity.getResult().equals("login")) {
            ToastUtil.successToast(getApplicationContext(), "登陆成功！");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            ToastUtil.errorToast(getApplicationContext(), "登陆失败，请重试！");
        }
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
