package cn.qmz.tools.view.popup;

import cn.qmz.tools.R;
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
import android.widget.Toast;

/**
 * ����ѡ�����ջ�������alert  ��  ios7
 * @author Monica
 *
 */
public class PicturePopupWindow extends PopupWindow {
    private Button btn_take_photo, btn_pick_photo, btn_cancel;
    private View mMenuView;
    private Context mContext;
    private LinearLayout pop_layout;

    public PicturePopupWindow(Activity context,OnClickListener itemsOnClick) {
        super(context);
        mContext=context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.photo_album_popup, null);
        btn_take_photo = (Button) mMenuView.findViewById(R.id.btn_take_camare);
        btn_pick_photo = (Button) mMenuView.findViewById(R.id.btn_pick_photo);
        btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
        pop_layout=(LinearLayout)mMenuView.findViewById(R.id.pop_layout);
        //ȡ����ť
        btn_cancel.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                //���ٵ�����
                dismiss();
            }
        });
        //���ð�ť����
        btn_pick_photo.setOnClickListener(itemsOnClick);
        btn_take_photo.setOnClickListener(itemsOnClick);
        //����SelectPicPopupWindow��View
        this.setContentView(mMenuView);
        //����SelectPicPopupWindow��������Ŀ�
        this.setWidth(LayoutParams.FILL_PARENT);
        //����SelectPicPopupWindow��������ĸ�
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //����SelectPicPopupWindow��������ɵ��
        this.setFocusable(true);
        //����SelectPicPopupWindow�������嶯��Ч��
        this.setAnimationStyle(R.style.selpic_PopwiwndowsAnimation);
        //ʵ����һ��ColorDrawable��ɫΪ��͸��
        ColorDrawable dw = new ColorDrawable(0x22000000);
        //����SelectPicPopupWindow��������ı���
        this.setBackgroundDrawable(dw);
        //mMenuView���OnTouchListener�����жϻ�ȡ����λ�������ѡ������������ٵ�����
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
        this.pop_layout.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.popup_exit));

        //�½��̵߳ȴ�������ɺ��˳�popwindow
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
        this.pop_layout.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.popup_enter));
    }

    public  void showOnView(int id) {
        View v = ((Activity)mContext).findViewById(id);
        if(v == null) {
            Toast.makeText(mContext, "�޷���ʾpopwindow  ��Ϊid: main_lay������", Toast.LENGTH_SHORT).show();
            return;
        }
        this.showAtLocation(v, Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    public void setItemClick(OnClickListener  itemsOnClick) {
        //���ð�ť����
        btn_pick_photo.setOnClickListener(itemsOnClick);
        btn_take_photo.setOnClickListener(itemsOnClick);
    }

}
