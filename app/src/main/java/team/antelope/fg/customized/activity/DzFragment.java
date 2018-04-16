package team.antelope.fg.customized.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team.antelope.fg.R;
import team.antelope.fg.constant.SkillAboutDetails;
import team.antelope.fg.customized.adapter.FragAdapter;
import team.antelope.fg.customized.adapter.TopPagerAdapter;
import team.antelope.fg.ui.base.BaseFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

/**
 * Created by Kyrene on 2017/12/11.
 */

public class DzFragment extends BaseFragment implements View.OnClickListener {
    /**
     * @说明 声明组件对象
     * @创建日期 2017/12/8 下午8:36
     */
//    private Toolbar mToolbar;
//    private ImageView imageSearch;
    private Button button_search;
    private static final String[] CHANNELS = new String[]{
            SkillAboutDetails.SKILLTYPE1,
            SkillAboutDetails.SKILLTYPE2,
            SkillAboutDetails.SKILLTYPE3,
            SkillAboutDetails.SKILLTYPE4,
            SkillAboutDetails.SKILLTYPE5,
            SkillAboutDetails.SKILLTYPE6,
            SkillAboutDetails.SKILLTYPE7,
            SkillAboutDetails.SKILLTYPE8,
            "Test"};
    private List<String> mDataList = new ArrayList<String>(Arrays.asList(CHANNELS));
    private TopPagerAdapter mTopPagerAdapter = new TopPagerAdapter(mDataList);//适配器
    private ViewPager mViewPager;   //ViewPager
    private MagicIndicator mMagicIndicator;     //指示器
    private CommonNavigator mCommonNavigator;      //Tab
    //Fragment
    private PagerFragment1 pfg1=new PagerFragment1();
    private PagerFragment2 pfg2=new PagerFragment2();
    private PagerFragment3 pfg3=new PagerFragment3();
    private PagerFragment4 pfg4=new PagerFragment4();
    private PagerFragment5 pfg5=new PagerFragment5();
    private PagerFragment6 pfg6=new PagerFragment6();
    private PagerFragment7 pfg7=new PagerFragment7();
    private PagerFragment8 pfg8=new PagerFragment8();
    //    private PublishFragmentNeed pfg7=new PublishFragmentNeed();
    private FragAdapter fragAdapter;
    private List<Fragment> list=new ArrayList<Fragment>();

    /**
     * @说明 获取布局
     * @创建日期 2017/12/11 下午7:23
     */
    @Override
    protected int getLayoutId() {
        return R.layout.lx_activity_index;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
//        mToolbar=view.findViewById(R.id.toolbar);    //获取ToolBar
//        initToolBar();
        button_search=view.findViewById(R.id.search_icon);  //获取搜索图标按钮

//        imageSearch=view.findViewById(R.id.fakeSearch);   //获取搜索栏(picture)
        //ViewPager指示器
        mViewPager=view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(mTopPagerAdapter);    //创建适配器
        mMagicIndicator=view.findViewById(R.id.magic_indicator1);
        mMagicIndicator.setBackgroundColor(Color.parseColor("#ffffff"));    //设置背景颜色
        mCommonNavigator=new CommonNavigator(getActivity());    //创建对象
        mCommonNavigator.setSkimOver(true);
        mCommonNavigator.setAdapter(adapter);
        mMagicIndicator.setNavigator(mCommonNavigator);     //设置指示器
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);      //绑定ViewPager

        list.add(new PagerFragment1());
        list.add(new PagerFragment2());
        list.add(new PagerFragment3());
        list.add(new PagerFragment4());
        list.add(new PagerFragment5());
        list.add(new PagerFragment6());
        list.add(new PagerFragment7());
        list.add(new PagerFragment8());
//        list.add(new PublishFragmentNeed());
        fragAdapter = new FragAdapter(mActivity.getSupportFragmentManager(), list);
        mViewPager.setAdapter(fragAdapter);
        mViewPager.setCurrentItem(0);

    }

    @Override
    protected void init() {
        setListener();

    }

//    /**
//    * @说明 设置ToolBar相关属性
//    * @创建日期 2017/12/11 下午7:40
//    */
//    private void initToolBar(){
//        mToolbar.setTitle("定制");   //设置Toolbar标题
//        mToolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
//        mActivity.setSupportActionBar(mToolbar);
//
//    }

    /**
     * @说明 选项卡相关设置，初始化MagicIndicator，设置页面切换
     * @创建日期 2017/12/18 下午2:38
     */

    private CommonNavigatorAdapter adapter = new CommonNavigatorAdapter() {     //设置选项卡的数据
        @Override
        public int getCount() {
            return mDataList==null? 0:mDataList.size();
        }

        //样式
        @Override
        public IPagerTitleView getTitleView(Context context, final int index) {
            SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
            simplePagerTitleView.setText(mDataList.get(index));   //设置Tab内容
            simplePagerTitleView.setTextSize(12);
            simplePagerTitleView.setNormalColor(Color.GRAY);   //设置字体颜色
            simplePagerTitleView.setSelectedColor(Color.BLACK);   //是指选中颜色
            simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(index);   //设置ViewPager显示界面

//                    mFragmentContainerHelper.handlePageSelected(pageIndex);
                }
            });
            return simplePagerTitleView;
        }

        //样式
        @Override
        public IPagerIndicator getIndicator (Context context) {
            BezierPagerIndicator indicator = new BezierPagerIndicator(context);
            indicator.setColors(Color.parseColor("#ff4a42"), Color.parseColor("#fcde64"), Color.parseColor("#73e8f4"), Color.parseColor("#76b0ff"), Color.parseColor("#c683fe"));
            //indicator.setInnerRectColor(Color.parseColor("#e94220"));
            return indicator;
            //return null;
        }
    };

    /**
     * @说明 设置某些组件的点击监听事件
     * @创建日期 2017/12/11 下午8:44
     */
    public void setListener(){
        button_search.setOnClickListener(this); //设置搜索图标按钮点击监听事件

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //
            case R.id.search_icon:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                getActivity().overridePendingTransition(R.anim.lx_search_open,R.anim.lx_search_close);
                break;
            //
            default:
                break;
        }//switch
    }//Click


}//DzFragment
