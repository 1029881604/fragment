package team.antelope.fg.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import team.antelope.fg.R;
import team.antelope.fg.constant.ForwardConst;
import team.antelope.fg.customized.activity.DzFragment;
import team.antelope.fg.me.activity.MeFragment;
import team.antelope.fg.publish.fragment.PublishFragment;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.ui.base.BaseFragment;
import team.antelope.fg.ui.fragment.NearbyFragment;
import team.antelope.fg.util.L;

/**
 * @Author hwc
 * @Date 2017/12/8
 * @TODO MainActivity  主页面，包含多个fragment,如定制，附近，我
 * fragment重叠的情况。
 * 这是由于在手机内存不足的时候Activity被回收后重启所导致的Fragment重复创建和重叠的问题。
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    public static final int BAIDU_READ_PHONE_STATE =100;
    public DzFragment fg_dz;
    public PublishFragment fg_publish;
    public NearbyFragment fg_nearby;
    public MeFragment fg_me;
    public BaseFragment[] fgs; //fragment数组
    public static String[] fgTags = new String[]{//fragment标记
      "fg1", "fg2", "fg3", "fg_me"
    };
    private int selectIndex;//当前选的index
    private int currentIndex;//当前show中的index
    private ImageView[] bottom_Ivs;//底部导航图片
    private TextView[] bottom_Tvs;//底部导航文字
    private View[] bottom_Rls;  //底部item

    private boolean isFirst = true;

    @Override
    protected void initView(Bundle savedInstanceState) {
        initBottomView();//初始化底部控件
        stateCheck(savedInstanceState);
//        initTabView(); 不能在这里初始化，可能会出现重复的fg
        initEvent();
        L.i("hh", "mainactivity");
    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
        overridePendingTransition(R.anim.exit_scale_start, R.anim.exit_scale_end);
        return;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ForwardConst.SPLASHACTIVITY.equals(getIntent().getStringExtra(ForwardConst.FORM)) && isFirst){
            overridePendingTransition(0, 0);
            isFirst = false;
            L.i("forward", ForwardConst.SPLASHACTIVITY);
        }
    }

    private void initBottomView() {
        //初始化rl
        bottom_Rls = new View[4];
        bottom_Rls[0] = findViewById(R.id.rl_custom);
        bottom_Rls[1] = findViewById(R.id.rl_publish);
        bottom_Rls[2] = findViewById(R.id.rl_near);
        bottom_Rls[3] = findViewById(R.id.rl_me);

        //初始化ImageView
        bottom_Ivs = new ImageView[4];
        bottom_Ivs[0] = (ImageView) findViewById(R.id.iv_customed);
        bottom_Ivs[1] = (ImageView) findViewById(R.id.iv_publish);
        bottom_Ivs[2] = (ImageView) findViewById(R.id.iv_near);
        bottom_Ivs[3] = (ImageView) findViewById(R.id.iv_me);

        //初始化Textview
        bottom_Tvs = new TextView[4];
        bottom_Tvs[0] = (TextView) findViewById(R.id.tv_custom);
        bottom_Tvs[1] = (TextView) findViewById(R.id.tv_publish);
        bottom_Tvs[2] = (TextView) findViewById(R.id.tv_near);
        bottom_Tvs[3] = (TextView) findViewById(R.id.tv_me);
    }


    /**
     * @Description 绑定事件
     * @date 2017/12/8
     */
    private void initEvent() {
        for (int i=0; i<bottom_Rls.length; i++) {
             bottom_Rls[i].setOnClickListener(this);
        }
    }

    /**
     * @Description
     * @date 2017/12/8
     */
    private void initTabView() {
        fg_dz = new DzFragment();
        fg_publish = new PublishFragment();
        fg_nearby = new NearbyFragment();
        fg_me = new MeFragment();
        fgs = new BaseFragment[]{
          fg_dz, fg_publish, fg_nearby, fg_me
        };
        //设置默认选中项为 定制
        bottom_Ivs[0].setSelected(true);
        bottom_Tvs[0].setTextColor(getResources().getColor(R.color.text_select));
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fg_dz, fgTags[0])
                .add(R.id.fragment_container, fg_publish, fgTags[1])
                .add(R.id.fragment_container, fg_nearby, fgTags[2])
                .add(R.id.fragment_container, fg_me, fgTags[3])
                .hide(fg_publish).hide(fg_nearby).hide(fg_me).show(fg_dz).commit();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    /**
     * @Description 状态检测 用于内存不足时的时候保证fragment不会重叠
     * @date 2017/12/9
     */
    private void stateCheck(Bundle saveInstanceState) {
        if (saveInstanceState == null) {
            initTabView();
        } else {
            //通过tag找回失去引用但是存在内存中的fragment.id相同
            DzFragment fg1 = (DzFragment) getSupportFragmentManager().findFragmentByTag(fgTags[0]);
            PublishFragment fg2 = (PublishFragment) getSupportFragmentManager().findFragmentByTag(fgTags[1]);
            NearbyFragment fg3 = (NearbyFragment) getSupportFragmentManager().findFragmentByTag(fgTags[2]);
            MeFragment fg4 = (MeFragment) getSupportFragmentManager().findFragmentByTag(fgTags[3]);
            getSupportFragmentManager().beginTransaction()
                    .show(fg1).hide(fg2).hide(fg3).hide(fg4)
                    .commit();
        }
    }
    /**
     * @Description 切换fragment响应事件
     * @date 2017/12/9
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.rl_custom:
                selectIndex = 0;
                break;
            case R.id.rl_publish:
                selectIndex = 1;
                break;
            case R.id.rl_near:
                selectIndex = 2;
                break;
            case R.id.rl_me:
                selectIndex = 3;
                break;
        }
        if(currentIndex != selectIndex){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(!fgs[selectIndex].isAdded()){
                transaction.add(R.id.fragment_container, fgs[selectIndex], fgTags[selectIndex]);
            }
            //换fragment
            transaction.hide(fgs[currentIndex]).show(fgs[selectIndex]).commit();
            //换图片
            bottom_Ivs[currentIndex].setSelected(false);
            bottom_Ivs[selectIndex].setSelected(true);
            //改变文字颜色
            bottom_Tvs[currentIndex].setTextColor(getResources().getColor(R.color.text_normal));
            bottom_Tvs[selectIndex].setTextColor(getResources().getColor(R.color.text_select));
        }
        currentIndex = selectIndex;//将当前index设置为选中的index
    }
}
