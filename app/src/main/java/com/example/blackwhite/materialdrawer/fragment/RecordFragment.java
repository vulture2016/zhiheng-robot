package com.example.blackwhite.materialdrawer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.blackwhite.materialdrawer.BaseActivity;
import com.example.blackwhite.materialdrawer.R;
import com.example.blackwhite.materialdrawer.adapter.RecordAdapter;
import com.example.blackwhite.materialdrawer.constant.Params;
import com.example.blackwhite.materialdrawer.control.RetrofitApi;
import com.example.blackwhite.materialdrawer.entity.RecordEntity;

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
 * Created by blackwhite on 2017/6/20.
 */

public class RecordFragment extends Fragment implements AbsListView.OnScrollListener {
    @BindView(R.id.lv_record)
    ListView lvRecord;
    public List<RecordEntity> mList;
    public List<RecordEntity> mAllList;
    private RecordAdapter mAdapter;
    private int count = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        ButterKnife.bind(this, view);
        mList = new ArrayList<>();
        mAllList = new ArrayList<>();
        mAdapter = new RecordAdapter(mAllList, getContext());
        lvRecord.setOnScrollListener(this);
        requestApiByRetrofit_RxJava("$ 查询聊天记录 " + count, BaseActivity.token);
        return view;
    }

    // 请求图灵API接口，获得问答信息
    private void requestApiByRetrofit_RxJava(String input_data, String token) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Params.Chat_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RetrofitApi api = retrofit.create(RetrofitApi.class);
        api.postRecordInfo(input_data, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseMessage, Throwable::printStackTrace);

    }

    // 处理获得到的问答信息
    private void handleResponseMessage(RecordEntity entity) {
        if (entity == null) return;
        mList.add(entity);
        mAllList.addAll(mList);
        Log.i("Size+++", mList.size() + "");
        lvRecord.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollstate) {
        if (scrollstate == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            // 判断是否滚动到底部
            if (absListView.getLastVisiblePosition() == absListView.getCount() - 1) {
                //加载更多功能的代码
                count++;
                String input_data = "$ 查询聊天记录 " + count;
                Log.i("input_data+++", input_data);
                requestApiByRetrofit_RxJava(input_data, BaseActivity.token);
            }
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }


}
