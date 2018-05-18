package team.antelope.fg.me.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import team.antelope.fg.FgApp;
import team.antelope.fg.R;
import team.antelope.fg.constant.AccessNetConst;
import team.antelope.fg.constant.LocationConst;
import team.antelope.fg.db.DBOpenHelper;
import team.antelope.fg.db.dao.IPersonDao;
import team.antelope.fg.db.dao.impl.PersonDaoImpl;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.User;
import team.antelope.fg.me.activity.MeProfileActivity;
import team.antelope.fg.ui.MainActivity;
import team.antelope.fg.ui.base.BaseFragment;
import team.antelope.fg.ui.business.MeBusiness;
import team.antelope.fg.ui.business.RetrofitServiceManager;
import team.antelope.fg.ui.fragment.AccompanyFragment;
import team.antelope.fg.ui.fragment.ErrandFragment;
import team.antelope.fg.ui.fragment.GuideFragment;
import team.antelope.fg.ui.fragment.ManualFragment;
import team.antelope.fg.ui.fragment.NearbyFragment;
import team.antelope.fg.ui.fragment.NearbyOtherFragment;
import team.antelope.fg.ui.fragment.PhotographyFragment;
import team.antelope.fg.util.L;
import team.antelope.fg.util.PropertiesUtil;
import team.antelope.fg.util.SetRoundImageViewUtil;

import static android.app.Activity.RESULT_OK;

/**
 * @Author：Carlos
 * @Date： 2018/5/18 16:29
 * @Description:    用户信息
 **/
public class MeProfileFragment extends Fragment {
    TextView tv_set_name, tv_age, tv_sex, tv_email, tv_dealNum, tv_fanNum;
    private String user_name;
    private String user_age,user_sex,user_email;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view, savedInstanceState);

        PersonDaoImpl personDao = new PersonDaoImpl(getActivity());
        User user = new UserDaoImpl(getActivity()).queryAllUser().get(0);
        Person person = personDao.queryById(getActivity().getIntent().getLongExtra("person_id",0L));
        tv_age.setText(String.valueOf(person.getAge()));
        tv_sex.setText(person.getSex());
        tv_email.setText(person.getEmail());
        user_age =tv_age.getText().toString();
        user_sex =tv_sex.getText().toString();
        user_email =tv_email.getText().toString();
        Log.i("8888888",user_age);
        return view;
    }

    public void initView(View view, Bundle savedInstanceState) {
        tv_age=(TextView)view.findViewById(R.id.tv_age);
        tv_sex=(TextView)view.findViewById(R.id.tv_sex);
        tv_email=(TextView)view.findViewById(R.id.tv_email);

    }

    public int getLayoutId() {
        return R.layout.me_profile_fragment;
    }

    public void onAttach(Context context) {
        super.onAttach(context);

    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
