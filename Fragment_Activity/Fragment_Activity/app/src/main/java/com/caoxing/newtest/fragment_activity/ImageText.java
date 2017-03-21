package com.caoxing.newtest.fragment_activity;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImageText extends LinearLayout{
	private Context mContext = null;
	private ImageView mImageView = null;
	private TextView mTextView = null;
	private final static int DEFAULT_IMAGE_WIDTH = 18;
	private final static int DEFAULT_IMAGE_HEIGHT = 18;
	private int CHECKED_COLOR = Color.rgb(29, 118, 199); //选中蓝色
	private int UNCHECKED_COLOR = Color.GRAY;   //自然灰色
	public ImageText(Context context) {
		super(context);
		mContext = context;
	}

	public ImageText(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View parentView = inflater.inflate(R.layout.image_text_layout, this, true);
		mImageView = (ImageView)findViewById(R.id.image_iamge_text);
		mTextView = (TextView)findViewById(R.id.text_iamge_text);
	}
	public void setImage(int id){
		if(mImageView != null){
			mImageView.setImageResource(id);
			//dp转px
			setImageSize(DensityUtil.dip2px(getContext(), DEFAULT_IMAGE_WIDTH), DensityUtil.dip2px(getContext(), DEFAULT_IMAGE_HEIGHT));

		}
	}

	public void setText(String s){
		if(mTextView != null){
			mTextView.setText(s);
			mTextView.setTextColor(UNCHECKED_COLOR);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true;
	}
	private void setImageSize(int w, int h){
		if(mImageView != null){
			ViewGroup.LayoutParams params = mImageView.getLayoutParams();
			params.width = w;
			params.height = h;
			mImageView.setLayoutParams(params);
		}
	}
	public void setChecked(int itemID){
		if(mTextView != null){
			mTextView.setTextColor(CHECKED_COLOR);
		}
		int checkDrawableId = -1;
		switch (itemID){
			case Constant.BTN_FLAG_MESSAGE:
				checkDrawableId = R.drawable.message_selected;
				break;
			case Constant.BTN_FLAG_CONTACTS:
				checkDrawableId = R.drawable.contacts_selected;
				break;
			case Constant.BTN_FLAG_NEWS:
				checkDrawableId = R.drawable.news_selected;
				break;
			case Constant.BTN_FLAG_SETTING:
				checkDrawableId = R.drawable.setting_selected;
				break;
			default:break;
		}
		if(mImageView != null){
			mImageView.setImageResource(checkDrawableId);
		}
	}

}
