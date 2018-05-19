package team.antelope.fg.me.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.List;
import java.util.Properties;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import team.antelope.fg.R;
import team.antelope.fg.constant.AccessNetConst;
import team.antelope.fg.db.dao.impl.PersonDaoImpl;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.PublishNeed;
import team.antelope.fg.entity.User;
import team.antelope.fg.me.activity.MeNeedActivity;
import team.antelope.fg.me.adapter.MeNeedAdapter;
import team.antelope.fg.me.constant.MeAccessNetConst;
import team.antelope.fg.ui.base.BaseFragment;
import team.antelope.fg.ui.fragment.AccompanyFragment;
import team.antelope.fg.ui.fragment.ErrandFragment;
import team.antelope.fg.ui.fragment.GuideFragment;
import team.antelope.fg.ui.fragment.ManualFragment;
import team.antelope.fg.ui.fragment.NearbyOtherFragment;
import team.antelope.fg.ui.fragment.PhotographyFragment;
import team.antelope.fg.util.L;
import team.antelope.fg.util.OkHttpUtils;
import team.antelope.fg.util.PropertiesUtil;

/**
 * @Author：Carlos
 * @Date： 2018/5/17 11:06
 * @Description:     记录
 **/
public class MeMomentFragment extends Fragment {

    private  long id;
    private Properties mProp;
    private List<PublishNeed> pnList;
    private List<PublishNeed> publishNeeds;
    private ListView listView;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            publishNeeds= (List<PublishNeed>) msg.obj;
            MeNeedAdapter meNeedAdapter =new MeNeedAdapter(getActivity(),publishNeeds);
            listView.setAdapter(meNeedAdapter);
        }
    };
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);

        listView =(ListView) view.findViewById(R.id.listView);
        id=getActivity().getIntent().getLongExtra("person_id",0L);
        Log.i("MeMomentFragment1", String.valueOf(id));
        sendOkHttpRequest();
        return view;
    }

    private void sendOkHttpRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient.Builder builder = OkHttpUtils.createHttpClientBuild();
                    OkHttpClient client = builder.build();

                    mProp = PropertiesUtil.getInstance();
                    String path = mProp.getProperty(AccessNetConst.BASEPATH)
                            +mProp.getProperty(MeAccessNetConst.GetUserNeedServletEndPath);
                    Request request = new Request.Builder()
                            .url(path+"?id="+id)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithGSON(responseData);
                    Message message = new Message();
                    message.obj = pnList;
                    handler.sendMessage(message);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGSON(String responseData) {
        Gson gson = new Gson();
        pnList = gson.fromJson(responseData, new TypeToken<List<PublishNeed>>() {
        }.getType());
        for (PublishNeed publishNeed : pnList) {

            Log.d("JSONActivity", "id is" + publishNeed.getNeedtype());
            Log.d("JSONActivity", "name is" + publishNeed.getContent());
            Log.d("JSONActivity", "version is" + publishNeed.getTitle());
        }
    }


    protected int getLayoutId() {
        return R.layout.me_moment_fragment;
    }

}
