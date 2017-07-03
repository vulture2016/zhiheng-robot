package com.example.blackwhite.materialdrawer.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.blackwhite.materialdrawer.DetailActivity;
import com.example.blackwhite.materialdrawer.R;
import com.example.blackwhite.materialdrawer.constant.Params;
import com.example.blackwhite.materialdrawer.entity.MessageEntity;
import com.example.blackwhite.materialdrawer.util.SpecialViewUtil;
import com.example.blackwhite.materialdrawer.util.TimeUtil;
import com.github.library.bubbleview.BubbleTextVew;

import java.util.List;

public class ChatMessageAdapter extends BaseListAdapter<MessageEntity> {

    private Context mContext;
    private List<MessageEntity> mList;

    public static final int TYPE_LEFT = 0;
    public static final int TYPE_RIGHT = 1;

    public ChatMessageAdapter(Context context, List<MessageEntity> list) {
        super(context, list);
        mContext = context;
        mList = list;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getType() == TYPE_LEFT) {
            return TYPE_LEFT;
        }
        return TYPE_RIGHT;
    }

    private View createViewByType(int position) {
        if (getItem(position).getType() == TYPE_LEFT) {
            return mInflater.inflate(R.layout.item_conversation_left, null);
        }
        return mInflater.inflate(R.layout.item_conversation_right, null);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = createViewByType(position);
        }

        final MessageEntity entity = getItem(position);

        TextView tvTime = ViewHolder.get(convertView, R.id.tv_time);
        BubbleTextVew btvMessage = ViewHolder.get(convertView, R.id.btv_message);

        if (isDisplayTime(position)) {
            tvTime.setVisibility(View.VISIBLE);
            tvTime.setText(TimeUtil.friendlyTime(mContext, entity.getTime()));
        } else {
            tvTime.setVisibility(View.GONE);
        }

        switch (entity.getCode()) {
            case Params.Code.URL:
                btvMessage.setText(SpecialViewUtil.getSpannableString(entity.getText(), entity.getUrl()));
                break;
            default:
                btvMessage.setText(entity.getText());
                break;
        }

        btvMessage.setOnClickListener(v -> {
            if (entity.getCode() == Params.Code.URL) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("URL", entity.getUrl());
                mContext.startActivity(intent);
//                ToastUtil.infoToast(mContext, entity.getUrl());
            }
        });

        btvMessage.setOnLongClickListener(v -> {
            copyDeleteDialog(mContext, entity);
            return false;
        });

        return convertView;
    }

    /**
     * 一分钟内的请求与回复不显示时间
     */
    public boolean isDisplayTime(int position) {
        if (position > 0) {
            if ((getItem(position).getTime() - getItem(position - 1).getTime()) > 60 * 1000) {
                return true;
            } else {
                return false;
            }
        } else if (position == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 复制或者删除工具类
     *
     * @param context
     * @param entity
     */
    private void copyDeleteDialog(Context context, MessageEntity entity) {
        new MaterialDialog.Builder(context)
                .items("复制该文本", "删除这一条")
                .itemsCallback((dialog, view, which, text) -> {
                    switch (which) {
                        case 0:
                            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                            cm.setText(entity.getText());
                            Toast.makeText(context, "已复制", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            mList.remove(entity);
                            notifyDataSetChanged();
                            break;
                    }
                })
                .show();
    }

}
