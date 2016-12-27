package cn.qmz.tools.view.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import cn.qmz.tools.R;

/**
 *
 */
public class SharePopupWindow extends PopupWindow {
    private RelativeLayout invite_weixin_clrcle,invite_weixin,invite_weibo,invite_qq,invite_qzone;
    private Button cancle;
    private View mMenuView;
    private Context mContext;
    private LinearLayout pop_layout;

    public SharePopupWindow(Activity context, OnClickListener itemsOnClick) {
        super(context);
        mContext=context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.share_select_popup, null);
        invite_weixin_clrcle = (RelativeLayout) mMenuView.findViewById(R.id.invite_weixin_clrcle);
        invite_weibo = (RelativeLayout) mMenuView.findViewById(R.id.invite_weibo);
        invite_qq = (RelativeLayout) mMenuView.findViewById(R.id.invite_qq);
        invite_weixin = (RelativeLayout) mMenuView.findViewById(R.id.invite_weixin);
        invite_qzone = (RelativeLayout) mMenuView.findViewById(R.id.invite_qzone);
        cancle = (Button) mMenuView.findViewById(R.id.cancle);
        pop_layout=(LinearLayout)mMenuView.findViewById(R.id.pop_layout);

        setItemClick(itemsOnClick);

        this.setContentView(mMenuView);

        this.setWidth(LayoutParams.FILL_PARENT);

        this.setHeight(LayoutParams.WRAP_CONTENT);

        this.setFocusable(true);

        this.setAnimationStyle(R.style.selpic_PopwiwndowsAnimation);

        ColorDrawable dw = new ColorDrawable(0x22000000);

        this.setBackgroundDrawable(dw);

        mMenuView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(y < height){
                        dismiss();
                    }
                }
                return true;
            }
        });



    }

    private void ondis() {
        super.dismiss();
    }

    @Override
    public void dismiss() {
        this.pop_layout.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.popup_exit));


        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ondis();
            }
        },500);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent,gravity,x,y);
        this.pop_layout.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.popup_enter));
    }

    public  void showOnView(int id) {
        View v = ((Activity)mContext).findViewById(id);
        if(v == null) {
            return;
        }
        this.showAtLocation(v, Gravity.CENTER, 0, 0);
    }


    public void setItemClick(OnClickListener  itemsOnClick) {
        invite_qq.setOnClickListener(itemsOnClick);
        invite_weixin.setOnClickListener(itemsOnClick);
        invite_qzone.setOnClickListener(itemsOnClick);
        invite_weixin_clrcle.setOnClickListener(itemsOnClick);
        invite_weibo.setOnClickListener(itemsOnClick);
        cancle.setOnClickListener(itemsOnClick);
    }

}
