package cn.qmz.tools.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.qmz.tools.R;
import cn.qmz.tools.exception.ErrorException;
import cn.qmz.tools.utils.DrawableUtils;
import cn.qmz.tools.utils.Logger;
import cn.qmz.tools.view.image.CircleImageView;
import cn.qmz.tools.view.impl.OnTabSelectedListener;

public class MyTabWidget extends LinearLayout {
	private static final String TAG = "MyTabWidget";
	//图片集
	private int[] mDrawableIds = new int[] { R.drawable.bg_home_icon_page,
			R.drawable.bg_home_icon_check, R.drawable.bg_home_icon_shop };
	// 存放底部菜单的各个文字CheckedTextView
	private List<CheckedTextView> mCheckedList = new ArrayList<CheckedTextView>();
	// 存放在底部菜单的头像
	private CircleImageView mCheckImage;
	// 存放底部菜单每项View
	private List<View> mViewList = new ArrayList<View>();
	// 底部菜单的文字数组
	private CharSequence[] mLabels;
	
	public MyTabWidget(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.TabWidget, defStyle, 0);

		// 读取xml中，各个tab使用的文字
		mLabels = a.getTextArray(R.styleable.TabWidget_bottom_labels);

		if (null == mLabels || mLabels.length <= 0) {
			try {
				throw new ErrorException("底部菜单的文字数组未添加...");
			} catch (ErrorException e) {
				e.printStackTrace();
			} finally {
				Logger.i(MyTabWidget.class.getSimpleName() + " 出错");
			}
			a.recycle();
			return;
		}

		a.recycle();

		// 初始化控件
		init(context);
	}

	public MyTabWidget(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
//		init(context);
	}

	public MyTabWidget(Context context) {
		super(context);
//		init(context);
	}

	private CircleImageView mImage = null;
	/**
	 * 初始化控件
	 */
	private void init(final Context context) {
		this.setOrientation(LinearLayout.HORIZONTAL);
		this.setBackgroundResource(R.color.white_fa);

		LayoutInflater inflater = LayoutInflater.from(context);

		LayoutParams params = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
		
		int size = mLabels.length;
		for(int i = 0; i < size; i++) {
			final int index = i;
			CheckedTextView itemName = null;
			View viewText = null;
			View viewImage = null;
			if(i < (size - 1)) {
				
				//每个tab对应的layout
				viewText = inflater.inflate(R.layout.layout_bottom_tab_item, null);
				itemName = (CheckedTextView) viewText
						.findViewById(R.id.item_name);
				itemName.setCompoundDrawablesWithIntrinsicBounds(
						context.getResources().getDrawable(mDrawableIds[i]), null, null, null);
				itemName.setText(mLabels[i]);

//				if(i == 0) {
//					params.leftMargin = 20;
//				} else {
//					params.leftMargin = 10;
//				}

				this.addView(viewText, params);

				// CheckedTextView设置索引作为tag，以便后续更改颜色、图片等
				itemName.setTag(index);
				
				// 将CheckedTextView添加到list中，便于操作
				mCheckedList.add(itemName);
				// 将各个tab的View添加到list
				mViewList.add(viewText);

				viewText.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// 设置底部图片和文字的显示
						setTabsDisplay(context, index);

						if (null != mTabListener) {
							// tab项被选中的回调事件
							mTabListener.onTabSelected(index);
						}
					}
				});

				
			} else {
				viewImage = inflater.inflate(R.layout.layout_bottom_tab_image_bar, null);
				mImage = (CircleImageView) viewImage
						.findViewById(R.id.item_image);
				RelativeLayout imageLayout = (RelativeLayout)viewImage.findViewById(R.id.image_layout);
				
				//设置图片
//				params.weight = 1.0f;
//				params.rightMargin = 20;
				this.addView(viewImage, params);

				mViewList.add(viewImage);
				
				viewImage.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// 设置底部图片和文字的显示
						setTabsDisplay(context, index);

						if (null != mTabListener) {
							// tab项被选中的回调事件
							mTabListener.onTabSelected(index);
						}
					}
				});
			}

			// 初始化 底部菜单选中状态,默认第一个选中
			if(itemName != null) {
				if (i == 0) {
					itemName.setChecked(true);
					itemName.setTextColor(Color.rgb(255, 0, 64));
//					viewText.setBackgroundColor(Color.rgb(240, 241, 242));
				} else {
					itemName.setChecked(false);
					itemName.setTextColor(Color.rgb(48, 48, 48));
//					viewText.setBackgroundColor(Color.rgb(250, 250, 250));
				}
			}
		}
	}
	
	public void setHeaderImage(String url) {
		if(mImage != null) {
			DrawableUtils.displayFromUrl(url, mImage);
		}
	}

	/**
	 * 设置指示点的显示
	 *
	 * @param context
	 * @param position
	 *            显示位置
	 * @param visible
	 *            是否显示，如果false，则都不显示
	 */
//	public void setIndicateDisplay(Context context, int position,
//								   boolean visible) {
//		int size = mIndicateImgs.size();
//		if (size <= position) {
//			return;
//		}
//		ImageView indicateImg = mIndicateImgs.get(position);
//		indicateImg.setVisibility(visible ? View.VISIBLE : View.GONE);
//	}

	/**
	 * 设置底部导航中图片显示状态和字体颜色
	 */
	public void setTabsDisplay(Context context, int index) {
		int size = mLabels.length;
		for (int i = 0; i < size; i++) {
			if(i < (size - 1)) {
				CheckedTextView checkedTextView = mCheckedList.get(i);
				if ((Integer) (checkedTextView.getTag()) == index) {
					Logger.i(mLabels[index] + " is selected...");
					checkedTextView.setChecked(true);
					checkedTextView.setTextColor(Color.rgb(255, 0, 64));
//					mViewList.get(i).setBackgroundColor(Color.rgb(240, 241, 242));
				} else {
					checkedTextView.setChecked(false);
					checkedTextView.setTextColor(Color.rgb(48, 48, 48));
//					mViewList.get(i).setBackgroundColor(Color.rgb(250, 250, 250));
				}
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

		int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

		if (widthSpecMode != MeasureSpec.EXACTLY) {
			widthSpecSize = 0;
		}

		if (heightSpecMode != MeasureSpec.EXACTLY) {
			heightSpecSize = 0;
		}

		if (widthSpecMode == MeasureSpec.UNSPECIFIED
				|| heightSpecMode == MeasureSpec.UNSPECIFIED) {
		}

		// 控件的最大高度，就是下边tab的背景最大高度
		int width;
		int height;
		width = Math.max(getMeasuredWidth(), widthSpecSize);
		height = Math.max(this.getBackground().getIntrinsicHeight(),
				heightSpecSize);
		setMeasuredDimension(width, height);
	}

	// 回调接口，用于获取tab的选中状态
	private OnTabSelectedListener mTabListener;

	public void setOnTabSelectedListener(OnTabSelectedListener listener) {
		this.mTabListener = listener;
	}

}
