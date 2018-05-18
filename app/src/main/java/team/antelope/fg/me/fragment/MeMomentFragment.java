package team.antelope.fg.me.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.List;

import team.antelope.fg.R;
import team.antelope.fg.db.dao.impl.PersonDaoImpl;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.User;
import team.antelope.fg.ui.base.BaseFragment;
import team.antelope.fg.ui.fragment.AccompanyFragment;
import team.antelope.fg.ui.fragment.ErrandFragment;
import team.antelope.fg.ui.fragment.GuideFragment;
import team.antelope.fg.ui.fragment.ManualFragment;
import team.antelope.fg.ui.fragment.NearbyOtherFragment;
import team.antelope.fg.ui.fragment.PhotographyFragment;
import team.antelope.fg.util.L;

/**
 * @Author：Carlos
 * @Date： 2018/5/17 11:06
 * @Description:     动态
 **/
public class MeMomentFragment extends Fragment {
    public LocationClient mLocationClient;
    List<String> permissionList;
    TextView tv_position;
    private static final int BAIDU_READ_PHONE_STATE =100;
    FragmentPagerItemAdapter adapter;
    ViewPager viewPager;
    SmartTabLayout viewPagerTab; //Fragment的View加载完毕的标记
    public double longitude;
    public double latitude;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);

        return view;
    }







    protected int getLayoutId() {
        return R.layout.me_moment_fragment;
    }

}
