package com.example.blackwhite.materialdrawer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.blackwhite.materialdrawer.R;
import com.example.blackwhite.materialdrawer.entity.RecordEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by blackwhite on 2017/6/21.
 */

public class RecordAdapter extends BaseAdapter {

    private List<RecordEntity> mList = new ArrayList<>();
    private Context mContext;

    public RecordAdapter(List<RecordEntity> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        Log.i("listSize+++", mList.size() * 10 + "");
        return mList.get(0).getData().size();
//        return mList.size() * 10;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(0).getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_record, null);
            viewHolder = new ViewHolder();
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTime.setText(mList.get(mList.size() - 1).getData().get(position % 10).getTime_date());
        viewHolder.tvContent.setText(mList.get(mList.size() - 1).getData().get(position % 10).getSpeak_data());
        return convertView;
    }

    class ViewHolder {
        TextView tvTime;
        TextView tvContent;
    }


}
