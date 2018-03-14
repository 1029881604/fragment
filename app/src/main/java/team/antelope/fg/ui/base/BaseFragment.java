package team.antelope.fg.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.antelope.fg.R;
import team.antelope.fg.ui.MainActivity;

/**
 * @Author：hwc
 * @Date：2017/12/8 17:17
 * @Desc: ...
 */

public abstract class BaseFragment extends Fragment {
    protected MainActivity mActivity;
    /**
     * @Description 抽象方法，子类实现初始化view
     * @date 2017/12/8
     */
    protected abstract void initView(View view, Bundle savedInstanceState);

    //获取布局文件ID
    /**
     * @Description 抽象方法，子类实现
     * @date 2017/12/8
     */
    protected abstract int getLayoutId();

    /**
     * @Description 用自定义的获取activity
     * @date 2017/12/18
     */
    public MainActivity getmActivity() {
        return mActivity;
    }
    /**
     * @Description 将Activity当做fragment的一个成员变量，
     * 不用它自己的getActivity(),防止空指针问题
     * @date 2017/12/8
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
    }
    /**
     * @Description 新增方法
     * @date 2017/12/19
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view, savedInstanceState);
        init();
        return view;
    }
    /**
     * @Description 抽象方法，子类实现
     * @date 2017/12/8
     */
    protected abstract void init();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    /**
     * @Description 带数据的跳转
     * @date 2017/12/8
     */
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getmActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    /**
     * @Description 不传值的跳转，传入class即可
     * @date 2017/12/8
     */
    public void startActivity  (Class<?> cls) {
        startActivity(cls, null);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getmActivity().overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    /**
     * @Description 带返回值的不传值跳转
     * @date 2017/12/8
     */
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }
    /**
     * @Description 带返回值的传值跳转
     * @date 2017/12/8
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getmActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        getmActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
