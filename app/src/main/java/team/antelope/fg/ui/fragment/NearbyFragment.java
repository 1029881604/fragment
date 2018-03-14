package team.antelope.fg.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import team.antelope.fg.R;
import team.antelope.fg.constant.LocationConst;
import team.antelope.fg.ui.base.BaseFragment;
import team.antelope.fg.util.L;

/**
 * @Author hwc
 * @Date 2017/12/20
 * @TODO NearbyFragment  附近fragment
 *
 */
public class NearbyFragment extends BaseFragment {
    public LocationClient mLocationClient;
    List<String> permissionList;
    TextView tv_position;
    private static final int BAIDU_READ_PHONE_STATE =100;
    FragmentPagerItemAdapter adapter;
    ViewPager viewPager;
    SmartTabLayout viewPagerTab; //Fragment的View加载完毕的标记
    public double longitude;
    public double latitide;

    @Override
    protected void init() {
//        ToastUtil.showShort(mActivity.getApplicationContext(), "NearbyFragmentinit");

        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
                public void onPageSelected(int position) {
                Fragment page = adapter.getPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * @Description 初始化视图控件
     * @date 2018/1/4
     */
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        //初始化位置textview
        tv_position = view.findViewById(R.id.tv_position);
        initPosition();
        L.i("TAG", "initview");
        viewPager =  view.findViewById(R.id.vp_contain_nearby);
        viewPagerTab = view.findViewById(R.id.ly_vp_tab);
        adapter = new FragmentPagerItemAdapter(
                mActivity.getSupportFragmentManager(), FragmentPagerItems.with(mActivity)
                .add(R.string.nearby_category_photography, PhotographyFragment.class)
                .add(R.string.nearby_category_accompany, AccompanyFragment.class)
                .add(R.string.nearby_category_manual, ManualFragment.class)
                .add(R.string.nearby_category_errand, ErrandFragment.class)
                .add(R.string.nearby_category_guide, GuideFragment.class)
                .add(R.string.nearby_category_other, NearbyOtherFragment.class)
                .create());
        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);
    }

    private void initPosition() {
        mLocationClient = new LocationClient(getmActivity().getApplicationContext());
        mLocationClient.registerLocationListener(new NearbyFragment.MyLocationListener());
        permissionList = new ArrayList<String>();
        if(ContextCompat.checkSelfPermission(getmActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(getmActivity(),
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(getmActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[] permission = permissionList.toArray(new String[permissionList.size()]);
            //requestCode 请求权限的码为1
            ActivityCompat.requestPermissions(getmActivity(), permission, BAIDU_READ_PHONE_STATE);
        } else {
            requestLocation();
        }
    }
    /**
     * @Description开始定位
     * @date 2017/12/19
     */
    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }
    /**
     * @Description 更新位置
     * @date 2017/12/19
     */
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setScanSpan(5000);
        option.setOpenGps(true);
        option.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case BAIDU_READ_PHONE_STATE:
                if (grantResults.length > 0){
                    for (int result : grantResults){
                        if(result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(getmActivity().getApplicationContext(),
                                    "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            getmActivity().finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(getmActivity().getApplicationContext(), "发生未知错误1", Toast.LENGTH_SHORT).show();
                    getmActivity().finish();
                }
                break;
            default: break;
        }
    }

    private class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {
            StringBuilder currentPosition = new StringBuilder();
            longitude = bdLocation.getLongitude();
            latitide = bdLocation.getLatitude();
            String substring;
            int length = currentPosition.append(bdLocation.getLocationDescribe()).length();
            if(length < 3 ){
                substring = LocationConst.NET_ERROR;
            } else {
                substring = currentPosition.substring(1, length - 2);
            }
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
//                currentPosition.append("GPS");
            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
//                currentPosition.append("网络");
            } else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {
                //当前为网络定位结果
            } else if (bdLocation.getLocType() == BDLocation.TypeServerError) {
                substring = LocationConst.LOCATION_ERROR;
                //当前网络定位失败
            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
                //当前网络不通
                substring = LocationConst.NET_ERROR;
                L.i("TypeNetWorkException");
            } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
                substring = LocationConst.LACK_PERMISSION;
            }
            tv_position.setText(substring);
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
            super.onConnectHotSpotMessage(s, i);
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nearby;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLocationClient.stop();// 销毁时候停止定位
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
