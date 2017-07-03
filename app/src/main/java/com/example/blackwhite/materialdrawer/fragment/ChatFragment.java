package com.example.blackwhite.materialdrawer.fragment;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.baidu.speech.VoiceRecognitionService;
import com.example.blackwhite.materialdrawer.BaseActivity;
import com.example.blackwhite.materialdrawer.R;
import com.example.blackwhite.materialdrawer.adapter.ChatMessageAdapter;
import com.example.blackwhite.materialdrawer.constant.Params;
import com.example.blackwhite.materialdrawer.control.RetrofitApi;
import com.example.blackwhite.materialdrawer.entity.MessageEntity;
import com.example.blackwhite.materialdrawer.util.KeyBoardUtil;
import com.example.blackwhite.materialdrawer.util.TimeUtil;
import com.example.blackwhite.materialdrawer.util.ToastUtil;
import com.example.blackwhite.materialdrawer.util.VoiceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.shaohui.bottomdialog.BottomDialog;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by blackwhite on 2017/6/12.
 */

public class ChatFragment extends Fragment implements View.OnClickListener, View.OnTouchListener, RecognitionListener {

    @BindView(R.id.et_send_text)
    EditText etSendText;
    @BindView(R.id.bt_send)
    Button btSend;
    @BindView(R.id.lv_content)
    ListView lvContent;
    private List<MessageEntity> mList;
    private ChatMessageAdapter msgAdapter;
    private boolean isSpeech = false;
    @BindView(R.id.rl_text)
    RelativeLayout rlText;
    @BindView(R.id.ib_speech)
    ImageButton ibSpeech;
    @BindView(R.id.bt_speech)
    Button btSpeech;

    View speechTips;

    View speechWave;
    private SpeechRecognizer speechRecognizer;
    private long speechEndTime = -1;

    private static final String TAG = "Touch";
    private static final int EVENT_ERROR = 11;
    private BottomDialog dialog;

//    @BindView(R.id.txtResult)
//    TextView txtResult;
//    @BindView(R.id.txtLog)
//    TextView txtLog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView();
        initListener();
        return view;
    }

    @OnClick(R.id.ib_speech)
    public void speechOrTextClick() {
        speechOrText();
    }

    private void speechOrText() {
        if (!isSpeech) {
            isSpeech = true;
            ibSpeech.setBackgroundResource(R.drawable.icon_keyboard);
            rlText.setVisibility(View.GONE);
            btSpeech.setVisibility(View.VISIBLE);
        } else {
            isSpeech = false;
            ibSpeech.setBackgroundResource(R.drawable.icon_speech);
            rlText.setVisibility(View.VISIBLE);
            btSpeech.setVisibility(View.GONE);
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        //滑动时隐藏软键盘
        lvContent.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //滚动时当软键盘显示时隐藏
                KeyBoardUtil.hideKeyboard(getActivity());
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        //回车发送消息
        etSendText.setOnKeyListener((v, keyCode, event) -> {
            //这里写发送信息的方法
            if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {
                //处理事件
                sendMessage();
                return true;
            }
            return false;
        });
        btSend.setOnClickListener(this);

        btSpeech.setOnTouchListener(this);
    }

    private void initView() {
        lvContent.setDivider(null);
        msgAdapter = new ChatMessageAdapter(getContext(), mList);
        Log.i("---", mList.size() + "");
        lvContent.setAdapter(msgAdapter);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext(), new ComponentName(getContext(), VoiceRecognitionService.class));
        speechRecognizer.setRecognitionListener(this);

        speechTips = View.inflate(getContext(), R.layout.bd_asr_popup_speech, null);
        speechWave = speechTips.findViewById(R.id.wave);
        speechTips.setVisibility(View.GONE);
        getActivity().addContentView(speechTips, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void initData() {
        mList = new ArrayList<>();
        if (mList.size() == 0) {
            MessageEntity entity = new MessageEntity(ChatMessageAdapter.TYPE_LEFT, TimeUtil.getCurrentTimeMillis());
            entity.setText("你好！我是小智");
            mList.add(entity);

//            Log.i("---", mList.size() + "");
//            Log.i("---", mList.get(0).getText());
        }
    }

    // 给Turing发送问题
    public void sendMessage() {
        String msg = etSendText.getText().toString().trim();

        if (!TextUtils.isEmpty(msg)) {
            MessageEntity entity = new MessageEntity(ChatMessageAdapter.TYPE_RIGHT, TimeUtil.getCurrentTimeMillis(), msg);
            mList.add(entity);


            msgAdapter.notifyDataSetChanged();
            etSendText.setText("");
            // 使用 Retrofit 和 RxJava 请求接口
            requestApiByRetrofit_RxJava(msg, BaseActivity.token);
        }
    }


    // 请求图灵API接口，获得问答信息
    private void requestApiByRetrofit_RxJava(String input_data, String token) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Params.Chat_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RetrofitApi api = retrofit.create(RetrofitApi.class);
        Log.i("Info+++", input_data);
        Log.i("Token+++", BaseActivity.token);
        api.postMessageInfo(input_data, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseMessage, Throwable::printStackTrace);

    }

    // 处理获得到的问答信息
    private void handleResponseMessage(MessageEntity entity) {
        if (entity == null) return;

        entity.setTime(TimeUtil.getCurrentTimeMillis());
        entity.setType(ChatMessageAdapter.TYPE_LEFT);
        switch (entity.getCode()) {
            case Params.Code.URL:
                entity.setText(entity.getText() + "，点击网址查看：" + entity.getUrl());
                break;
            case Params.Code.TEXT:
                entity.setText(entity.getText());
                break;
        }
        mList.add(entity);
        msgAdapter.notifyDataSetChanged();
        //移动到最后一项
        lvContent.smoothScrollToPosition(lvContent.getCount() - 1);
    }

    @Override
    public void onClick(View v) {
        sendMessage();
    }

    @OnClick(R.id.ib_add)
    public void adviseClick() {
        if (dialog == null) {
            dialog = new BottomDialog();
        } else {
            dialog.setViewListener(new BottomDialog.ViewListener() {
                @Override
                public void bindView(View view) {
                    DialogClickListener dialogClickListener = new DialogClickListener();
                    view.findViewById(R.id.ll_weather).setOnClickListener(dialogClickListener);
                    view.findViewById(R.id.ll_constellation).setOnClickListener(dialogClickListener);
                    view.findViewById(R.id.ll_joke).setOnClickListener(dialogClickListener);
                    view.findViewById(R.id.ll_delivery).setOnClickListener(dialogClickListener);
                    view.findViewById(R.id.ll_story).setOnClickListener(dialogClickListener);
                }
            }).setLayoutRes(R.layout.layout_bottom)
                    .setDimAmount(0.2f)
                    .setCancelOutside(true)
                    .show(getFragmentManager());
        }
    }

    class DialogClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String msg;
            switch (view.getId()) {
                case R.id.ll_weather:
                    msg = "天气";
                    oneKeySendMsg(msg);
                    dialog.getDialog().cancel();
                    break;
                case R.id.ll_constellation:
                    msg = "星座运势";
                    oneKeySendMsg(msg);
                    dialog.getDialog().cancel();
                    break;
                case R.id.ll_joke:
                    msg = "讲一个笑话";
                    oneKeySendMsg(msg);
                    dialog.getDialog().cancel();
                    break;
                case R.id.ll_delivery:
                    msg = "查询快递";
                    oneKeySendMsg(msg);
                    dialog.getDialog().cancel();
                    break;
                case R.id.ll_story:
                    msg = "讲一个故事";
                    oneKeySendMsg(msg);
                    dialog.getDialog().cancel();
                    break;
            }

        }
    }

    private void oneKeySendMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            MessageEntity entity = new MessageEntity(ChatMessageAdapter.TYPE_RIGHT, TimeUtil.getCurrentTimeMillis(), msg);
            mList.add(entity);
            msgAdapter.notifyDataSetChanged();
            etSendText.setText("");
            // 使用 Retrofit 和 RxJava 请求接口
            requestApiByRetrofit_RxJava(msg, BaseActivity.token);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                speechTips.setVisibility(View.VISIBLE);
                speechRecognizer.cancel();
                Intent intent = new Intent();
                VoiceUtil.bindParams(intent, getContext());
                intent.putExtra("vad", "touch");
//                txtResult.setText("");
//                txtLog.setText("");
                speechRecognizer.startListening(intent);
                return true;
            case MotionEvent.ACTION_UP:
                speechRecognizer.stopListening();
                speechTips.setVisibility(View.GONE);
                break;
        }
        return false;
    }

    @Override
    public void onResults(Bundle results) {
        long end2finish = System.currentTimeMillis() - speechEndTime;
        ArrayList<String> nbest = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
//        print("识别成功：" + Arrays.toString(nbest.toArray(new String[nbest.size()])));
        String json_res = results.getString("origin_result");
        try {
//            print("origin_result=\n" + new JSONObject(json_res).toString(4));
        } catch (Exception e) {
//            print("origin_result=[warning: bad json]\n" + json_res);
        }
        String strEnd2Finish = "";
        if (end2finish < 60 * 1000) {
            strEnd2Finish = "(waited " + end2finish + "ms)";
        }
//        txtResult.setText(nbest.get(0) + strEnd2Finish);
        Log.i("onResults", nbest.get(0) + strEnd2Finish);

        String msg = nbest.get(0) + strEnd2Finish;

        if (!TextUtils.isEmpty(msg)) {
            MessageEntity entity = new MessageEntity(ChatMessageAdapter.TYPE_RIGHT, TimeUtil.getCurrentTimeMillis(), msg);
            mList.add(entity);

            msgAdapter.notifyDataSetChanged();
            etSendText.setText("");

            // 使用 Retrofit 和 RxJava 请求接口
            requestApiByRetrofit_RxJava(msg, BaseActivity.token);
        }
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        ArrayList<String> nbest = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (nbest.size() > 0) {
//            print("~临时识别结果：" + Arrays.toString(nbest.toArray(new String[0])));
//            txtResult.setText(nbest.get(0));
            Log.i("onPartialResults", nbest.get(0));
        }
    }

//    private void print(String msg) {
//        txtLog.append(msg + "\n");
//        ScrollView sv = (ScrollView) txtLog.getParent();
//        sv.smoothScrollTo(0, 1000000);
//        Log.d(TAG, "----" + msg);
//    }

    @Override
    public void onError(int error) {
        StringBuilder sb = new StringBuilder();
        switch (error) {
            case SpeechRecognizer.ERROR_AUDIO:
                sb.append("音频问题");
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                sb.append("没有语音输入");
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                sb.append("其它客户端错误");
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                sb.append("权限不足");
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                sb.append("网络问题");
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                sb.append("没有匹配的识别结果");
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                sb.append("引擎忙");
                break;
            case SpeechRecognizer.ERROR_SERVER:
                sb.append("服务端错误");
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                sb.append("连接超时");
                break;
        }
        sb.append(":" + error);
//        print("识别失败：" + sb.toString());
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        switch (eventType) {
            case EVENT_ERROR:
                String reason = params.get("reason") + "";
//                print("EVENT_ERROR, " + reason);
                break;
        }
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {
        final int VTAG = 0xFF00AA01;
        Integer rawHeight = (Integer) speechWave.getTag(VTAG);
        if (rawHeight == null) {
            rawHeight = speechWave.getLayoutParams().height;
            speechWave.setTag(VTAG, rawHeight);
        }

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) speechWave.getLayoutParams();
        params.height = (int) (rawHeight * rmsdB * 0.01);
        params.height = Math.max(params.height, speechWave.getMeasuredWidth());
        speechWave.setLayoutParams(params);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

}
