package com.example.blackwhite.materialdrawer;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.blackwhite.materialdrawer.constant.Params;
import com.example.blackwhite.materialdrawer.control.RetrofitApi;
import com.example.blackwhite.materialdrawer.entity.InfoEntity;
import com.example.blackwhite.materialdrawer.entity.UpdateEntity;
import com.example.blackwhite.materialdrawer.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MeActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_id)
    TextView tvID;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_register_time)
    TextView tvRegisterTime;
    @BindView(R.id.tv_pickname)
    TextView tvPickName;
    @BindView(R.id.tv_email)
    TextView tvEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setBackgroundResource(R.color.md_toolbar_bg);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(4.0f);
        }
        toolbar.setTitle("个人信息");
        toolbar.setNavigationOnClickListener(this);

        requestApiByRetrofit_RxJava(BaseActivity.token);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_me, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//
            case R.id.menu_edit_describe:
                new MaterialDialog.Builder(this)
                        .title("修改个人信息")
                        .customView(R.layout.custom_view, true)
                        .cancelable(true)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                                EditText etPickName = (EditText) materialDialog.findViewById(R.id.et_pickname);
                                RadioButton rbMan = (RadioButton) materialDialog.findViewById(R.id.rb_man);
                                EditText etInputEmail = (EditText) materialDialog.findViewById(R.id.et_input_email);
                                EditText etPhone = (EditText) materialDialog.findViewById(R.id.et_phone);
                                EditText etLocation = (EditText) materialDialog.findViewById(R.id.et_local);

                                String pickName = etPickName.getText().toString().trim();
                                String group = "0";
                                if (rbMan.isChecked()) {
                                    group = "0";
                                } else {
                                    group = "1";
                                }
                                String email = etInputEmail.getText().toString().trim();
                                int phone = Integer.parseInt(etPhone.getText().toString().trim());
                                String local = etLocation.getText().toString().trim();
                                Log.i("Email+++", email);
                                requestApiByRetrofit(BaseActivity.token, pickName, phone, group,
                                        local, email);
                                ToastUtil.successToast(getApplicationContext(), "修改成功！");
                                requestApiByRetrofit_RxJava(BaseActivity.token);
                            }
                        })
                        .positiveText("确定")
                        .negativeText("取消")
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    // 请求图灵API接口，获得问答信息
    private void requestApiByRetrofit_RxJava(String token) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Params.Chat_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RetrofitApi api = retrofit.create(RetrofitApi.class);
        api.getUserInfo(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseMessage, Throwable::printStackTrace);

    }

    // 请求图灵API接口，获得问答信息
    private void requestApiByRetrofit(String token, String pickName, int phone, String group,
                                      String location, String email) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Params.Chat_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RetrofitApi api = retrofit.create(RetrofitApi.class);
        api.updateUserInfo(token, pickName, phone, group, location, email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleUpdateMessage, Throwable::printStackTrace);

    }

    // 处理获得到的问答信息
    private void handleResponseMessage(InfoEntity entity) {
        if (entity == null) return;
        tvPickName.setText(entity.getData().getNickname());
        tvID.setText("UID：" + entity.getData().get_id() + "");
        tvPhone.setText("电话号码：" + entity.getData().getPhone());
        tvLocation.setText("居住地：" + entity.getData().getLocal());
        tvRegisterTime.setText("注册时间：" + entity.getData().getAdd_time());
        tvEmail.setText("邮箱：" + entity.getData().getEmail());
        if ("0".equals((entity.getData().getGender()))) {
            tvSex.setText("性别：男");
        } else {
            tvSex.setText("性别：女");
        }
    }

    // 处理获得到的问答信息
    private void handleUpdateMessage(UpdateEntity entity) {
        if (entity == null) return;
        Log.i("code+++", entity.getCode() + "");
    }
}
