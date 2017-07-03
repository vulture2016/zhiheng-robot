package com.example.blackwhite.materialdrawer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.blackwhite.materialdrawer.AboutOurActivity;
import com.example.blackwhite.materialdrawer.BaseActivity;
import com.example.blackwhite.materialdrawer.LoginActivity;
import com.example.blackwhite.materialdrawer.MeActivity;
import com.example.blackwhite.materialdrawer.R;
import com.example.blackwhite.materialdrawer.constant.Params;
import com.example.blackwhite.materialdrawer.control.RetrofitApi;
import com.example.blackwhite.materialdrawer.entity.LogoutEntity;
import com.example.blackwhite.materialdrawer.util.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by blackwhite on 2017/6/12.
 */

public class SettingFragment extends Fragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        view.findViewById(R.id.tv_me_info).setOnClickListener(this);
        view.findViewById(R.id.bt_logout).setOnClickListener(this);
        view.findViewById(R.id.tv_about).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_me_info:
                Intent intent = new Intent(getContext(), MeActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_logout:
                showLogoutDialog();
                break;
            case R.id.tv_about:
                intent = new Intent(getContext(), AboutOurActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void showLogoutDialog() {
        new MaterialDialog.Builder(getContext())
                .title("确定退出账号？")
                .positiveText("确定")
                .negativeText("取消")
                .cancelable(true)
                .onPositive((dialog, which) -> {
                    Log.i("Token+++", BaseActivity.token);
                    requestApiByRetrofit_RxJava(BaseActivity.token);
                })
                .onNegative((dialog, which) -> {
                    dialog.cancel();
                    ToastUtil.infoToast(getContext(), "取消");
                }).show();
    }

    // 请求后台是否登陆
    private void requestApiByRetrofit_RxJava(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Params.RegisterLogin_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RetrofitApi api = retrofit.create(RetrofitApi.class);
        api.postLogoutInfo(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseLogout, Throwable::printStackTrace);

    }

    // 处理获得到的问答信息
    private void handleResponseLogout(LogoutEntity entity) {
        if (entity == null) return;
        if (entity.getCode() == 200) {
            ToastUtil.successToast(getContext(), "退出成功！");
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            ToastUtil.errorToast(getContext(), "退出失败！");
        }
    }

}
