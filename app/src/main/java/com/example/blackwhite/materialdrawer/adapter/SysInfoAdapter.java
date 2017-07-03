package com.example.blackwhite.materialdrawer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.blackwhite.materialdrawer.R;
import com.example.blackwhite.materialdrawer.entity.RecordEntity;
import com.example.blackwhite.materialdrawer.entity.SysInfoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by blackwhite on 2017/6/21.
 */

public class SysInfoAdapter extends BaseAdapter {

    private List<SysInfoEntity> mList = new ArrayList<>();
    private Context mContext;

    public SysInfoAdapter(List<SysInfoEntity> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.get(0).getData().size();
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
            convertView = View.inflate(mContext, R.layout.item_sys_info, null);
            viewHolder = new ViewHolder();
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.tvFrom = (TextView) convertView.findViewById(R.id.tv_from);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTime.setText("Time：" + mList.get(0).getData().get(position).getTime());
        viewHolder.tvFrom.setText("From：" + mList.get(0).getData().get(position).getFrom());
        return convertView;
    }

    class ViewHolder {
        TextView tvTime;
        TextView tvContent;
        TextView tvFrom;
    }


}
