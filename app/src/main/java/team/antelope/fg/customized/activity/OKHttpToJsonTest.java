package team.antelope.fg.customized.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import team.antelope.fg.R;
import team.antelope.fg.customized.util.HttpUtil;
import team.antelope.fg.entity.PublishSkill;
import team.antelope.fg.ui.base.BaseFragment;

/**
 * Created by Kyrene on 2018/4/4.
 */

public class OKHttpToJsonTest extends BaseFragment implements View.OnClickListener {


    String url="http://localhost:8080/fragment_server/GetCustomizedSkillServlet";
    TextView textView;
    Button button;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        button=view.findViewById(R.id.send_request);
        textView=view.findViewById(R.id.response_text);
        button.setOnClickListener(this);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.lx_jsontest;
    }

    @Override
    protected void init() {
//        sendRequestWithOkHttp();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request){
            sendRequestWithOkHttp();
        }
    }

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtil.sendOkHttpRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String responseData = response.body().string();
                        System.out.println("parseJOSNWithGSON");
                        parseJSONWithGSON(responseData);
                        showResponse(responseData);
                    }
                });
            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData){
        Gson gson = new Gson();
        List<PublishSkill> publishSkills = gson.fromJson(jsonData, new TypeToken<List<PublishSkill>>(){}.getType());
        for (PublishSkill skill:publishSkills){
            Log.d("JsonTest", "id is" + skill.getId());
            Log.d("JsonTest", "type is" + skill.getSkillType());
            Log.d("JsonTest", "skill is" + skill.getTitle());
            Log.d("JsonTest", "content is" + skill.getContent());
        }

    }

    private void showResponse(final String response){
        getmActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(response);
            }
        });

    }
}
