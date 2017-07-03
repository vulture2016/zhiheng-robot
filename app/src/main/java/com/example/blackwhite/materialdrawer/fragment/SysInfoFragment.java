package com.example.blackwhite.materialdrawer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.blackwhite.materialdrawer.BaseActivity;
import com.example.blackwhite.materialdrawer.R;
import com.example.blackwhite.materialdrawer.SysInfoDetailActivity;
import com.example.blackwhite.materialdrawer.adapter.RecordAdapter;
import com.example.blackwhite.materialdrawer.adapter.SysInfoAdapter;
import com.example.blackwhite.materialdrawer.constant.Params;
import com.example.blackwhite.materialdrawer.control.RetrofitApi;
import com.example.blackwhite.materialdrawer.entity.InfoEntity;
import com.example.blackwhite.materialdrawer.entity.SysInfoEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by blackwhite on 2017/6/13.
 */

public class SysInfoFragment extends Fragment {

    private List<SysInfoEntity> mList;
    private SysInfoAdapter mAdapter;
    @BindView(R.id.lv_sys_info)
    ListView lvSysInfo;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscribe, container, false);
        ButterKnife.bind(this, view);
        mList = new ArrayList<>();
        requestApiByRetrofit_RxJava(BaseActivity.token);
        return view;
    }

    // 请求图灵API接口，获得问答信息
    private void requestApiByRetrofit_RxJava(String token) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Params.Chat_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RetrofitApi api = retrofit.create(RetrofitApi.class);
        api.getSysInfo(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseMessage, Throwable::printStackTrace);

    }

    // 处理获得到的问答信息
    private void handleResponseMessage(SysInfoEntity entity) {
        if (entity == null) return;
        mList.add(entity);
        Log.i("UIU", mList.size() + "");
        mAdapter = new SysInfoAdapter(mList, getContext());
        lvSysInfo.setAdapter(mAdapter);
        lvSysInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SysInfoDetailActivity.class);
                intent.putExtra("from", mList.get(0).getData().get(position).getFrom());
                intent.putExtra("time", mList.get(0).getData().get(position).getTime());
                intent.putExtra("context", mList.get(0).getData().get(position).getContext());
                getActivity().startActivity(intent);
            }
        });
        mAdapter.notifyDataSetChanged();
    }
}
