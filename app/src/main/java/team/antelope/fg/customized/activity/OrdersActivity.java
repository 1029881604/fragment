package team.antelope.fg.customized.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team.antelope.fg.R;
import team.antelope.fg.customized.adapter.FragAdapter;
import team.antelope.fg.customized.adapter.TopPagerAdapter;
import team.antelope.fg.ui.base.BaseActivity;
/**
* @说明 暂时没用
* @创建日期 2018/5/20 下午9:45
*/
public class OrdersActivity extends BaseActivity implements View.OnClickListener {

    private Button back_button;

    private static final String[] CHANNELS = new String[]{"全部", "已付款", "未付款"};

    private List<String> mDataList = new ArrayList<String>(Arrays.asList(CHANNELS));
    private TopPagerAdapter mTopPagerAdapter = new TopPagerAdapter(mDataList);//适配器
    private ViewPager mViewPager;   //ViewPager
    private MagicIndicator mMagicIndicator;     //指示器
    private CommonNavigator mCommonNavigator;      //Tab
    //Fragment
    private OrdersAllActivity all = new OrdersAllActivity();
    private OrdersIsPayActivity ispay = new OrdersIsPayActivity();
    private OrdersNotPayActivity notpay = new OrdersNotPayActivity();

    private FragAdapter fragAdapter;
    private List<Fragment> list=new ArrayList<Fragment>();

    @Override
    protected void initView(Bundle savedInstanceState) {
//        back_button = findViewById(R.id.ordersBack);
        back_button.setOnClickListener(this);

        //ViewPager指示器
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(mTopPagerAdapter);    //创建适配器
        mMagicIndicator = findViewById(R.id.magic_indicator1);
        mMagicIndicator.setBackgroundColor(Color.parseColor("#ffffff"));    //设置背景颜色
        mCommonNavigator = new CommonNavigator(this);    //创建对象
        mCommonNavigator.setSkimOver(true);
        mCommonNavigator.setAdapter(adapter);
        mMagicIndicator.setNavigator(mCommonNavigator);     //设置指示器
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);      //绑定ViewPager

//        list.add(new OrdersAllActivity());
//        list.add(new OrdersIsPayActivity());
//        list.add(new OrdersNotPayActivity());

        fragAdapter = new FragAdapter(this.getSupportFragmentManager(), list);
        mViewPager.setAdapter(fragAdapter);
        mViewPager.setCurrentItem(0);
    }

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

    @Override
    public int getLayout() {
        return R.layout.lx_activity_orders;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.ordersBack:
//                finish();

        }
    }

}
