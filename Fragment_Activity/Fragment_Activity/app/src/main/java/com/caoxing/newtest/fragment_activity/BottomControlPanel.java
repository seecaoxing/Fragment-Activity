package com.caoxing.newtest.fragment_activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class BottomControlPanel extends RelativeLayout implements View.OnClickListener {
    private Context mContext;
    private ImageText mMsgBtn = null;
    private ImageText mContactsBtn = null;
    private ImageText mNewsBtn = null;
    private ImageText mSettingBtn = null;
    private int DEFALUT_BACKGROUND_COLOR = Color.rgb(243, 243, 243); //Color.rgb(192, 192, 192)
    private BottomPanelCallback mBottomCallback = null;
    private List<ImageText> viewList = new ArrayList<ImageText>();

    public interface BottomPanelCallback {
        public void onBottomPanelClick(int itemId);
    }

    public BottomControlPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        mMsgBtn = (ImageText) findViewById(R.id.btn_message);
        mContactsBtn = (ImageText) findViewById(R.id.btn_contacts);
        mNewsBtn = (ImageText) findViewById(R.id.btn_news);
        mSettingBtn = (ImageText) findViewById(R.id.btn_setting);
        setBackgroundColor(DEFALUT_BACKGROUND_COLOR);
        viewList.add(mMsgBtn);
        viewList.add(mContactsBtn);
        viewList.add(mNewsBtn);
        viewList.add(mSettingBtn);

    }

    public void initBottomPanel() {
        if (mMsgBtn != null) {
            mMsgBtn.setImage(R.drawable.message_unselected);
            mMsgBtn.setText("消息");
        }
        if (mContactsBtn != null) {
            mContactsBtn.setImage(R.drawable.contacts_unselected);
            mContactsBtn.setText("联系人");
        }
        if (mNewsBtn != null) {
            mNewsBtn.setImage(R.drawable.news_unselected);
            mNewsBtn.setText("新闻");
        }
        if (mSettingBtn != null) {
            mSettingBtn.setImage(R.drawable.setting_unselected);
            mSettingBtn.setText("设置");
        }
        setBtnListener();

    }

    private void setBtnListener() {
        int num = this.getChildCount();
        for (int i = 0; i < num; i++) {
            View v = getChildAt(i);
            if (v != null) {
                v.setOnClickListener(this);
            }
        }
    }

    public void setBottomCallback(BottomPanelCallback bottomCallback) {
        mBottomCallback = bottomCallback;
    }

    @Override
    public void onClick(View v) {
        initBottomPanel();
        int index = -1;
        switch (v.getId()) {
            case R.id.btn_message:
                index = Constant.BTN_FLAG_MESSAGE;
                mMsgBtn.setChecked(Constant.BTN_FLAG_MESSAGE);
                break;
            case R.id.btn_contacts:
                index = Constant.BTN_FLAG_CONTACTS;
                mContactsBtn.setChecked(Constant.BTN_FLAG_CONTACTS);
                break;
            case R.id.btn_news:
                index = Constant.BTN_FLAG_NEWS;
                mNewsBtn.setChecked(Constant.BTN_FLAG_NEWS);
                break;
            case R.id.btn_setting:
                index = Constant.BTN_FLAG_SETTING;
                mSettingBtn.setChecked(Constant.BTN_FLAG_SETTING);
                break;
            default:
                break;
        }
        if (mBottomCallback != null) {
            mBottomCallback.onBottomPanelClick(index);
        }
    }

    public void defaultBtnChecked() {
        if (mMsgBtn != null) {
            mMsgBtn.setChecked(Constant.BTN_FLAG_MESSAGE);
        }
    }

    boolean isCall = false;
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //TODO 为什么onLayout会循环执行
        if (!isCall) {
            layoutItems(left, top, right, bottom);
            isCall = true;
        }
    }

    /**
     * 最左边和最右边的view由母布局的padding进行控制位置。这里需对第2、3个view的位置重新设置
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    private void layoutItems(int left, int top, int right, int bottom) {

        //TODO 为什么layoutItems会循环执行
        int n = getChildCount();
        //Toast.makeText(context,"获得父view的child个数"+n,Toast.LENGTH_SHORT).show();
        if (n == 0) {
            return;
        }
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        Log.i("caoxing", "paddingLeft = " + paddingLeft + " paddingRight = " + paddingRight);

        int width = right - left;
        int height = bottom - top;
        int averageWidth = width / 4;
        Log.i("caoxing", "width = " + width + " height = " + height);
        int allViewWidth = 0;
        for (int i = 0; i < n; i++) {
            //   Toast.makeText(context,"底部导航栏各个item宽度"+n,Toast.LENGTH_SHORT).show();
            View v = getChildAt(i);
            Log.i("caoxing", "v.getWidth() = " + v.getWidth());
            allViewWidth += v.getWidth();
        }
        //每个btn之间间隔的距离
        int blankWidth = (width - allViewWidth - paddingLeft - paddingRight) / (n - 1);
        Log.i("caoxing", "blankV = " + blankWidth);

        for (int i = 0; i <= n - 1; i++) {
            LayoutParams parsms = (LayoutParams) viewList.get(i).getLayoutParams();
            parsms.width = averageWidth;
//          parsms.leftMargin = blankWidth;//使各个按钮间没有间隔，用户点击时更加灵敏
            viewList.get(i).setLayoutParams(parsms);
        }
    }

}
