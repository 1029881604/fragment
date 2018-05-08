package team.antelope.fg.ui.activity;

import android.os.Bundle;

import team.antelope.fg.R;
import team.antelope.fg.constant.AccessNetConst;
import team.antelope.fg.constant.ForwardConst;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.PropertiesUtil;
import team.antelope.fg.widght.MyWebView;

import static java.lang.System.getProperty;

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
        long id = getIntent().getLongExtra(ForwardConst.USERID, -1l);
        webView =  findViewById(R.id.webview_person);
        PropertiesUtil prop = PropertiesUtil.getInstance();
        String url = prop.getProperty(AccessNetConst.BASEPATH)
                + prop.getProperty(AccessNetConst.TOPERSONINFOENDPATH)
                +"?id="+id;
        webView.loadUrl(url);
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
