package team.antelope.fg.me.me_utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @Author：Carlos
 * @Date： 2018/5/19 13:36
 * @Description:   解决部分滑动问题
 **/
public class MeViewPager extends ViewPager {
    private static final String TAG = "xujun";

    int lastX = -1;
    int lastY = -1;

    public MeViewPager(Context context) {
        super(context);
    }

    public MeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        int dealtX = 0;
        int dealtY = 0;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN://第一个触点按下之后就会触发到这个事件
                dealtX = 0;
                dealtY = 0;
                // 保证子View能够接收到Action_move事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE://当触点在屏幕上移动时触发
                dealtX += Math.abs(x - lastX);
                dealtY += Math.abs(y - lastY);
                Log.i(TAG, "dealtX:=" + dealtX);
                Log.i(TAG, "dealtY:=" + dealtY);
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (dealtX >= dealtY) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                //父view通过使函数onInterceptTouchEvent()返回true,从子view拿回处理
                // 事件的控制权，就会给子view发一个ACTION_CANCEL事件，
                // 子view就再也不会收到后续事件了
                break;
            case MotionEvent.ACTION_UP://手起来触发
                break;

        }
        return super.dispatchTouchEvent(ev);
    }
}
