package cn.qmz.tools.view.header.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.qmz.tools.R;

/**
 * 控制头部标题
 * 左部：ImageView 或 TextView
 * 右部：ImageView 或 TextView
 * 中间：TextView 标题文字
 * @author Monica
 *
 */
public class HeaderLayout  extends RelativeLayout {
	private LayoutInflater mInflater;
	private View mHeader;
	private RelativeLayout mLayoutLeftContainer;
	private RelativeLayout mLayoutRightContainer;
	private RelativeLayout mLayoutCenterContainer;
	private TextView mHtvSubTitle;
	private ImageView mHtvSubImage;
	private TextView mHtvLeft;
	private TextView mHtvRight;
	private ImageView mHimgLeft;
	private ImageView mHimgRight;

	private onRightClickListener mRightClickListener;
	private onLeftClickListener mLeftClickListener;

	public HeaderLayout(Context context) {
		super(context);
		init(context);
	}

	public HeaderLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void init(Context context) {
		mInflater = LayoutInflater.from(context);
		mHeader = mInflater.inflate(R.layout.layout_header_bar, null);
		addView(mHeader);
		findWidgets();
	}

	/**
	 * 初始化控件
	 */
	public void findWidgets() {
		mLayoutLeftContainer = (RelativeLayout) findViewByHeaderId(R.id.left_layout);
		mLayoutRightContainer = (RelativeLayout) findViewByHeaderId(R.id.right_layout);
		mHtvSubTitle = (TextView) findViewByHeaderId(R.id.view312);
		mHtvSubImage = (ImageView) findViewByHeaderId(R.id.view207);
		//下面要初始化事件，所以分写在事件中
		initLeftContainer();
		initRightContainer();
	}

	public View findViewByHeaderId(int id) {
		return mHeader.findViewById(id);
	}

	/**
	 * 自定义左侧按钮
	 */
	private void initLeftContainer() {
		mHtvLeft = (TextView) findViewByHeaderId(R.id.view26);
		mHimgLeft = (ImageView) findViewByHeaderId(R.id.view206);
		mLayoutLeftContainer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mLeftClickListener != null) {
					mLeftClickListener.onClick();
				}

			}
		});
	}

	/**
	 * 自定义右侧按钮
	 */
	private void initRightContainer() {
		mHtvRight = (TextView) findViewByHeaderId(R.id.view145);
		mHimgRight = (ImageView) findViewByHeaderId(R.id.view346);
		mLayoutRightContainer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mRightClickListener != null) {
					mRightClickListener.onClick();
				}
			}
		});
	}

	//====================================设置按钮===============================
	/**
	 * 返回键 + 标题文字
	 * @param title
	 * @param leftListener
	 */
	public void setTitle(CharSequence title, onLeftClickListener leftListener) {
		mHtvSubTitle.setText(title);
		mHtvSubImage.setVisibility(View.GONE);
		//返回键
		initBackBtn(leftListener);

		mLayoutRightContainer.setVisibility(View.GONE);
	}

	/**
	 * 返回键 + 标题图片
	 * @param centerId
	 * @param leftListener
	 */
	public void setImage(int centerId, onLeftClickListener leftListener) {
		mHtvSubTitle.setVisibility(View.GONE);
		mHtvSubImage.setVisibility(View.VISIBLE);
		mHtvSubImage.setBackgroundResource(centerId);
		//返回键
		initBackBtn(leftListener);

		mLayoutRightContainer.setVisibility(View.GONE);
	}

	/**
	 * 只设置标题文字 + 无左右按钮
	 * @param title
	 */
	public void setTitle(CharSequence title) {
		mHtvSubTitle.setText(title);
		//返回键
		mLayoutLeftContainer.setVisibility(View.GONE);
		mLayoutRightContainer.setVisibility(View.GONE);
	}

	/**
	 * 只设置标题图片 + 无左右按钮
	 * @param centerId
	 */
	public void setImage(int centerId) {
		mHtvSubTitle.setVisibility(View.GONE);
		mHtvSubImage.setVisibility(View.VISIBLE);
		mHtvSubImage.setBackgroundResource(centerId);
		//返回键
		mLayoutLeftContainer.setVisibility(View.GONE);
		mLayoutRightContainer.setVisibility(View.GONE);
	}

	/**
	 * 返回键 + 标题文字 + 右键文字
	 * 左按钮：返回键
	 * 右按钮：文字按钮
	 */
	public void setTitleAndRight(CharSequence title, CharSequence text,
								 onLeftClickListener leftListener,
								 onRightClickListener rightListener) {
		mHtvSubTitle.setText(title);
		//返回键
		initBackBtn(leftListener);

		//右键
		mLayoutRightContainer.setVisibility(View.VISIBLE);
		mHtvRight.setVisibility(View.VISIBLE);
		mHimgRight.setVisibility(View.GONE);

		mHtvRight.setText(text);
		setOnRightClickListener(rightListener);
	}

	/**
	 * 返回键 + 标题图片 + 右键文字
	 * 左按钮：返回键
	 * 右按钮：文字按钮
	 */
	public void setImageAndRight(int centerId, CharSequence text,
								 onLeftClickListener leftListener,
								 onRightClickListener rightListener) {
		mHtvSubTitle.setVisibility(View.GONE);
		mHtvSubImage.setVisibility(View.VISIBLE);
		mHtvSubImage.setBackgroundResource(centerId);
		//返回键
		initBackBtn(leftListener);

		//右键
		mLayoutRightContainer.setVisibility(View.VISIBLE);
		mHtvRight.setVisibility(View.VISIBLE);
		mHimgRight.setVisibility(View.GONE);

		mHtvRight.setText(text);
		setOnRightClickListener(rightListener);
	}

	/**
	 * 返回键 + 标题文字 + 右键图片
	 * 左按钮：返回键
	 * 右按钮：图片按钮
	 * @param title
	 * @param rightId
	 * @param leftListener
	 * @param rightListener
	 */
	public void setTitleAndRight(CharSequence title, int rightId,
								 onLeftClickListener leftListener,
								 onRightClickListener rightListener) {
		mHtvSubTitle.setText(title);

		initBackBtn(leftListener);

		//右键
		mLayoutRightContainer.setVisibility(View.VISIBLE);
		mHtvRight.setVisibility(View.GONE);
		mHimgRight.setVisibility(View.VISIBLE);

		mHimgRight.setBackgroundResource(rightId);
		setOnRightClickListener(rightListener);
	}

	/**
	 * 返回键 + 标题图片 + 右键图片
	 * 左按钮：返回键
	 * 右按钮：图片按钮
	 * @param centerId
	 * @param rightId
	 * @param leftListener
	 * @param rightListener
	 */
	public void setImageAndRight(int centerId, int rightId,
								 onLeftClickListener leftListener,
								 onRightClickListener rightListener) {
		mHtvSubTitle.setVisibility(View.GONE);
		mHtvSubImage.setVisibility(View.VISIBLE);
		mHtvSubImage.setBackgroundResource(centerId);

		initBackBtn(leftListener);

		//右键
		mLayoutRightContainer.setVisibility(View.VISIBLE);
		mHtvRight.setVisibility(View.GONE);
		mHimgRight.setVisibility(View.VISIBLE);

		mHimgRight.setBackgroundResource(rightId);
		setOnRightClickListener(rightListener);
	}

	/**
	 * 返回键设置
	 */
	@SuppressLint("NewApi")
	private void initBackBtn(onLeftClickListener listener) {
		//返回键
		mLayoutLeftContainer.setVisibility(View.VISIBLE);
		mHtvLeft.setVisibility(View.GONE);
		mHimgLeft.setVisibility(View.VISIBLE);

		mHimgLeft.setBackground(getResources().getDrawable(R.drawable.left_back));
		setOnLeftClickListener(listener);
	}

	//====================================获取相关属性===============================
	public void setOnRightClickListener(onRightClickListener listener) {
		this.mRightClickListener = listener;
	}

	public void setOnLeftClickListener(onLeftClickListener listener) {
		this.mLeftClickListener = listener;
	}

	//====================================标题按钮接口===============================	
	public interface onRightClickListener {
		void onClick();
	}

	public interface onLeftClickListener {
		void onClick();
	}

}
