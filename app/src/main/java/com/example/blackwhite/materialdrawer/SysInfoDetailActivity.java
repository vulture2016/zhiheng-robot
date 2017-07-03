package com.example.blackwhite.materialdrawer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SysInfoDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_from)
    TextView tvFrom;
    @BindView(R.id.tv_context)
    TextView tvContext;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_info_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundResource(R.color.md_toolbar_bg);
        toolbar.setTitle("消息");
        toolbar.setNavigationIcon(R.drawable.icon_back);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(4.0f);
        }
        toolbar.setNavigationOnClickListener(this);
        getData();
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

    private void getData() {
        Intent intent = getIntent();
        String time = intent.getStringExtra("time");
        String from = intent.getStringExtra("from");
        String context = intent.getStringExtra("context");

        tvFrom.setText(from);
        tvTime.setText(time);
        tvContext.setText("\u3000\u3000" + context);
    }
}
