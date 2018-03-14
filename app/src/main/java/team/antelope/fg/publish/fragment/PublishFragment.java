package team.antelope.fg.publish.fragment;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import team.antelope.fg.R;
import team.antelope.fg.publish.activity.PublishNeedActivity;
import team.antelope.fg.publish.activity.PublishSkillActivity;
import team.antelope.fg.publish.adapter.PublishFragmentPagerAdapter;
import team.antelope.fg.ui.base.BaseFragment;
import team.antelope.fg.util.DateUtil;
import team.antelope.fg.util.L;

/**
*@Author: lry
*@Date: 2017/12/12 22:04
*@Description: publish模块的fragment
*/

public class PublishFragment extends BaseFragment implements ViewPager.OnPageChangeListener,View.OnClickListener {

    private ViewPager publishviewpager;
    //fragment集合，对应每个子页面
    private ArrayList<Fragment> fragments;
    //选项卡中的按钮
    private Button btn_publishskill;
    private Button btn_publishneed;
    //所有选项卡中的按钮数组
    private Button[] btns;
    //所有选项卡中的按钮宽度的数组
    private int[] widths;
    private ImageView ivBtn;
    private PopupWindow popupWindow;
    private RelativeLayout rl;
    /**
    *@Description: 获取布局控件的ID
    *@Date: 2017/12/18 10:50
    */
    @Override
    protected int getLayoutId() {
        return R.layout.publish_fragment;
    }

    /**
    *@Description: 初始视图（界面控件）
    *@Date: 2017/12/18 10:52
    */
    @Override
    protected void initView(View layout, Bundle savedInstanceState) {
        publishviewpager=layout.findViewById(R.id.publishviewpager);
        btn_publishskill=layout.findViewById(R.id.btn_publishskill);
        btn_publishneed=layout.findViewById(R.id.btn_publishneed);
        //初始化按钮数组
        btns=new Button[]{btn_publishskill,btn_publishneed};
        //初始化fragments
        fragments=new ArrayList<Fragment>();
        fragments.add(new PublishFragmentSkill());
        fragments.add(new PublishFragmentNeed());
        ivBtn=layout.findViewById(R.id.publish_include_toubu).findViewById(R.id.publish_iv_fabu);
        rl=layout.findViewById(R.id.publish_include_toubu);
    }

    /**
    *@Description: 初始视图处理事件
    *@Date: 2017/12/18 10:53
    */
    @Override
    protected void init() {

        publishviewpager.setOnPageChangeListener(this);
        btn_publishskill.setOnClickListener(this);
        btn_publishneed.setOnClickListener(this);
        ivBtn.setOnClickListener(this);
        PublishFragmentPagerAdapter adapter=new PublishFragmentPagerAdapter(getFragmentManager(),fragments);
        publishviewpager.setAdapter(adapter);
        btn_publishskill.setBackground(getResources().getDrawable(R.drawable.publish_btn_fbskill_checked));
    }
    //重置按钮颜色
    public void resetButtonColor(int arg0){
        btn_publishskill.setBackground(getResources().getDrawable(R.drawable.publish_btn_fabu_skill));
        btn_publishneed.setBackground(getResources().getDrawable(R.drawable.publish_btn_fabu_need));
    }
    @Override
    public void onClick(View whichbtn){
        switch(whichbtn.getId()){
            case R.id.btn_publishskill:
                publishviewpager.setCurrentItem(0);
                break;
            case R.id.btn_publishneed:
                publishviewpager.setCurrentItem(1);
                break;
            case R.id.publish_iv_fabu:
                showPopupWindow();
                break;
            case R.id.publish_tv_fbskill:
                popupWindow.dismiss();
                Intent intent1=new Intent();
                intent1.setClass(getActivity(), PublishSkillActivity.class);
                startActivity(intent1);
                break;
            case R.id.publish_tv_fbneed:
                popupWindow.dismiss();
                Intent intent2=new Intent(getActivity(), PublishNeedActivity.class);
                startActivity(intent2);
                break;
        }
    }
    @Override
    public void onPageScrollStateChanged(int argo){

    }
    @Override
    public void onPageScrolled(int arg0,float arg1,int arg2){

    }
    @Override
    public void onPageSelected(int arg0){
        //每次滑动首先重置所有按钮颜色
        resetButtonColor(arg0);
        //将滑动到的当前按钮颜色设置为白色
        if(arg0==0){
            btn_publishskill.setBackground(getResources().getDrawable(R.drawable.publish_btn_fbskill_checked));
        }else{
            btn_publishneed.setBackground(getResources().getDrawable(R.drawable.publish_btn_fbneed_checked));
        }
    }
    /**
    *@Description: 用于显示弹出框popupwindow
    *@Date: 2017/12/23 14:34
    */
    public void showPopupWindow(){
        View contentView= LayoutInflater.from(getActivity()).inflate(R.layout.publish_popupwindow,null);
        popupWindow=new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT , LinearLayout.LayoutParams.WRAP_CONTENT,true);
        //设置各个控件的点击响应
        TextView tv1=(TextView)contentView.findViewById(R.id.publish_tv_fbskill);
        tv1.setOnClickListener(this);
        TextView tv2=(TextView)contentView.findViewById(R.id.publish_tv_fbneed);
        tv2.setOnClickListener(this);
        //显示PopupWindow
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);  //去除控件宽度
        int iv_width=ivBtn.getMeasuredWidth();//获取到ivBtn控件的宽度
        int popup_width=popupWindow.getContentView().getMeasuredWidth();
        L.i("tag","iv_width="+iv_width);
        L.i("tag","popup_width="+popup_width);
        int x=-popup_width+(iv_width/2)+(popup_width/4);
        L.i("tag","x="+x);
        popupWindow.showAsDropDown(ivBtn,x,6);
        //设置打开popupwindow时背景变暗
        backgroundAlpha(0.7f);
        //关闭popupwindow时背景恢复原来
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });


    }
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }
}
