package team.antelope.fg.ui.activity;

import android.os.Bundle;

import org.apache.http.util.EncodingUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import team.antelope.fg.R;
import team.antelope.fg.constant.AccessNetConst;
import team.antelope.fg.constant.ForwardConst;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.L;
import team.antelope.fg.util.PropertiesUtil;
import team.antelope.fg.util.SpUtil;
import team.antelope.fg.widght.MyWebView;

/**
 * @Author hwc
 * @Date 2018/5/12
 * @TODO NeedInfoActivity   需求信息页面
 *
 */
public class NeedInfoActivity extends BaseActivity{
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
        PropertiesUtil prop = PropertiesUtil.getInstance();
        String baseUrl =prop.getProperty(AccessNetConst.BASEPATH);
        String url = baseUrl
                + prop.getProperty(AccessNetConst.TOPERSONINFOENDPATH);
        L.i("webvv", "url:" + url );
        syncCookie(baseUrl); //同步cookie要在loadUrl之前设置
        StringBuilder builder1 = new StringBuilder();
        Bundle params = getIntent().getExtras();
//        try {//拼接post提交参数
            builder1
//                    .append("interfaceName=").append(params.getString("interfaceName")).append("&")
//                    .append("interfaceVersion=").append(params.getString("interfaceVersion")).append("&")
//                    .append("id=").append(URLEncoder.encode(params.getString("id"), "UTF-8")).append("&")
//                    .append("merSignMsg=").append(URLEncoder.encode(params.getString("merSignMsg"), "UTF-8")).append("&")
//                    .append("appId=").append(params.getString("appId")).append("&")
                    .append("id=").append(params.getLong("id"))
            ;
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        String postData = builder1.toString();
        L.i("webvv", "url:" + url + "postData: "+postData);

        webView.postUrl(url, EncodingUtils.getBytes(postData, "UTF-8"));
    }

    private void syncCookie(String baseUrl) {
        //初始化设置
        String cookies = (String) SpUtil.getSp(this, SpUtil.KEY_COOKIE, "");
        L.i("cookies", cookies);
        boolean b = webView.syncCookie(baseUrl, cookies.split(";"));
        L.i("cookie_is_ok", b+"");
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
