package cn.qmz.tools.view.header;

import android.app.Activity;
import android.content.Context;
import cn.qmz.tools.R;
import cn.qmz.tools.utils.ActivityUtils;
import cn.qmz.tools.view.header.view.HeaderLayout;
import cn.qmz.tools.view.header.view.HeaderLayout.onLeftClickListener;
import cn.qmz.tools.view.header.view.HeaderLayout.onRightClickListener;

/**
 * 自定义标题使用
 * @author Monica
 *
 */
public class Header {
	protected HeaderLayout mHeaderLayout;
	private Context mContext;
	private Activity mActivity;

	public Header(Context context, Activity activity) {
		this.mContext = context;
		this.mActivity = activity;
	}

	/**
	 * 标题文字 + 自定义返回键事件
	 * @param title 标题名称
	 */
	public void initTitleBar(CharSequence title,onLeftClickListener myLeftClickListener) {
		mHeaderLayout = (HeaderLayout) mActivity.findViewById(R.id.navi_bar);
		mHeaderLayout.init(mContext);
		mHeaderLayout.setTitle(title, myLeftClickListener);
	}

	/**
	 * 标题文字 + 返回键
	 * @param title 标题名称
	 */
	public void initTitleBar(CharSequence title) {
		mHeaderLayout = (HeaderLayout) mActivity.findViewById(R.id.navi_bar);
		mHeaderLayout.init(mContext);
		mHeaderLayout.setTitle(title, new OnLeftClickListener());
	}

	/**
	 * 标题图片 + 返回键
	 * @param centerId 标题图片ID
	 */
	public void initImageBar(int centerId) {
		mHeaderLayout = (HeaderLayout) mActivity.findViewById(R.id.navi_bar);
		mHeaderLayout.init(mContext);
		mHeaderLayout.setImage(centerId, new OnLeftClickListener());
	}

	/**
	 * 标题文字 + 无左右键
	 * @param title
	 */
	public void initTitleBarNoBtn(CharSequence title) {
		mHeaderLayout = (HeaderLayout) mActivity.findViewById(R.id.navi_bar);
		mHeaderLayout.init(mContext);
		mHeaderLayout.setTitle(title);
	}

	/**
	 * 标题图片 + 无左右键
	 * @param centerId
	 */
	public void initImageBarNoBtn(int centerId) {
		mHeaderLayout = (HeaderLayout) mActivity.findViewById(R.id.navi_bar);
		mHeaderLayout.init(mContext);
		mHeaderLayout.setImage(centerId);
	}

	/**
	 * 标题文字 + 返回键 + 右键文字（右键点击事件）
	 * @param title 标题名称
	 * @param rightText 右键按钮名称
	 * @param rightListener 右键点击事件
	 */
	public void initTitleBarForRight(CharSequence title, CharSequence rightText,
									onRightClickListener rightListener) {
		mHeaderLayout = (HeaderLayout) mActivity.findViewById(R.id.navi_bar);
		mHeaderLayout.init(mContext);
		mHeaderLayout.setTitleAndRight(title, rightText,
				new OnLeftClickListener(), rightListener);
	}

	public void initTitleBarForRight(CharSequence title, CharSequence rightText,onLeftClickListener myLeftClickListener,
									 onRightClickListener rightListener) {
		mHeaderLayout = (HeaderLayout) mActivity.findViewById(R.id.navi_bar);
		mHeaderLayout.init(mContext);
		mHeaderLayout.setTitleAndRight(title, rightText,
				myLeftClickListener, rightListener);
	}

	/**
	 * 标题图片 + 返回键 + 右键文字（右键点击事件）
	 * @param centerId 标题图片
	 * @param rightText 右键按钮名称
	 * @param rightListener 右键点击事件
	 */
	public void initImageBarForRight(int centerId, CharSequence rightText,
									 onRightClickListener rightListener) {
		mHeaderLayout = (HeaderLayout) mActivity.findViewById(R.id.navi_bar);
		mHeaderLayout.init(mContext);
		mHeaderLayout.setImageAndRight(centerId, rightText,
				new OnLeftClickListener(), rightListener);
	}

	/**
	 * 标题文字 + 返回键 + 右键图片（右键点击事件）
	 * @param title 标题名称
	 * @param rightDrawableId 右键图片
	 * @param rightListener 右键点击事件
	 */
	public void initTitleBarForRight(CharSequence title, int rightDrawableId,
									onRightClickListener rightListener) {
		mHeaderLayout = (HeaderLayout) mActivity.findViewById(R.id.navi_bar);
		mHeaderLayout.init(mContext);
		mHeaderLayout.setTitleAndRight(title, rightDrawableId,
				new OnLeftClickListener(), rightListener);
	}

	/**
	 * 标题图片 + 返回键 + 右键图片（右键点击事件）
	 * @param centerId 标题图片
	 * @param rightDrawableId 右键图片
	 * @param rightListener 右键点击事件
	 */
	public void initImageBarForRight(int centerId, int rightDrawableId,
									 onRightClickListener rightListener) {
		mHeaderLayout = (HeaderLayout) mActivity.findViewById(R.id.navi_bar);
		mHeaderLayout.init(mContext);
		mHeaderLayout.setImageAndRight(centerId, rightDrawableId,
				new OnLeftClickListener(), rightListener);
	}

	/**
	 * 左返回键点击事件
	 * @author Monica
	 *
	 */
	public class OnLeftClickListener implements onLeftClickListener {
		@Override
		public void onClick() {
			ActivityUtils.hideSoftInputView(mActivity);
			mActivity.finish();
		}
	}
}
