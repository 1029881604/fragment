package team.antelope.fg.ui.activity;

import android.os.Bundle;

import team.antelope.fg.R;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.widght.MyWebView;
/**
 * @Author hwc
 * @Date 2018/4/6
 * @TODO PersonInfoActivity  个人信息页面
 *
 */
public class PersonInfoActivity extends BaseActivity{
    //定义webView控件
    private MyWebView webView;

    /**
     * 初始化webview视图
     * @param savedInstanceState
     */
    @Override
    protected void initView(Bundle savedInstanceState) {
        init();
    }

    /**
     * 获取布局
     * @return
     */
    @Override
    public int getLayout() {
        return R.layout.activity_person_info;
    }

    private void init() {
        webView =  findViewById(R.id.webview_person);
        webView.loadUrl("http://192.168.1.102:8080/crmBaseDao");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (webView.canGoBack()) {
            webView.goBack();   //返回上一页面
            return ;
        }
    }
}
