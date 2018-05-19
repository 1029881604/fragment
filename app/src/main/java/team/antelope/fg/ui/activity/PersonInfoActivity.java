package team.antelope.fg.ui.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import team.antelope.fg.R;
import team.antelope.fg.constant.AccessNetConst;
import team.antelope.fg.constant.ForwardConst;
import team.antelope.fg.ui.base.BaseActivity;
import team.antelope.fg.util.L;
import team.antelope.fg.util.PropertiesUtil;
import team.antelope.fg.util.SpUtil;
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
    private static final String TAG = "PersonInfoActivity";
    private MyWebView webView;
    private FrameLayout loadingLayout; //提示用户正在加载数据
    private LinearLayout webParentView;
    private View mErrorView; //加载错误的视图

    /**
     * 初始化webview视图
     * @param savedInstanceState
     */
    @Override
    protected void initView(Bundle savedInstanceState) {
        webView =  findViewById(R.id.webview_person);
        loadingLayout = (FrameLayout) findViewById(R.id.load_layout);
        initErrorPage();//初始化自定义页面
        initWebView();
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
        PropertiesUtil prop = PropertiesUtil.getInstance();
        String url = prop.getProperty(AccessNetConst.BASEPATH)
                + prop.getProperty(AccessNetConst.TOPERSONINFOENDPATH)
                +"?id="+id;
        syncCookie(); //同步cookie要在loadUrl之前设置
        webView.addJavascriptInterface(this, "android");
        webView.loadUrl(url);
    }

    private void syncCookie() {
        //初始化设置
        String baseUrl = PropertiesUtil.getInstance().getProperty(AccessNetConst.BASEPATH);
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
    @Override
    protected void onResume() {
        //激活WebView为活跃状态，能正常执行网页的响应
        webView.onResume();
        //恢复pauseTimers状态
        webView.resumeTimers();
        super.onResume();
    }

    @Override
    protected void onPause() {
        //当页面被失去焦点被切换到后台不可见状态，需要执行onPause
        //通过onPause动作通知内核暂停所有的动作，比如DOM的解析、plugin的执行、JavaScript执行。
        webView.onPause();
        //当应用程序(存在webview)被切换到后台时，这个方法不仅仅针对当前的webview而是全局的全应用程序的webview
        //它会暂停所有webview的layout，parsing，javascripttimer。降低CPU功耗。
        webView.pauseTimers();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //在关闭了Activity时，如果Webview的音乐或视频，还在播放。就必须销毁Webview
        //但是注意：webview调用destory时,webview仍绑定在Activity上
        //这是由于自定义webview构建时传入了该Activity的context对象
        //因此需要先从父容器中移除webview,然后再销毁webview:
        ViewGroup rootLayout = findViewById(R.id.rootView);
        rootLayout.removeView(webView);
        webView.destroy();
        super.onDestroy();
    }
    //javasricpt调用android代码
    @JavascriptInterface
    public void close(){
        Toast.makeText(this, "hahah", Toast.LENGTH_LONG).show();
        finish();
    }

    //javasricpt调用android代码
    @JavascriptInterface
    public void back(){
        //是否可以后退
        if(webView.canGoBack()){
            webView.goBack();
        }
    }

    //javasricpt调用android代码
    @JavascriptInterface
    public void forward(){
        //是否可以前进
        if(webView.canGoForward()){
            //前进网页
            webView.goForward();
        }
    }
    //javasricpt调用android代码
    @JavascriptInterface
    public void reload(){
        webView.reload();
    }
    private void initWebView() {
        //加载需要显示的网页
        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);    //允许加载javascript
        mWebSettings.setSupportZoom(true);          //允许缩放
        mWebSettings.setBuiltInZoomControls(true);  //原网页基础上缩放
        mWebSettings.setUseWideViewPort(true);      //任意比例缩放
        mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        final String cachePath = getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        mWebSettings.setAppCachePath(cachePath);
        mWebSettings.setAppCacheMaxSize(5 * 1024 * 1024);
        //设置数据库缓存路径 存储管理复杂数据 方便对数据进行增加、删除、修改、查询 不推荐使用
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        //开启 DOM storage API 功能 较大存储空间，使用简单
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");//设置网页默认编码
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                //6.0以下执行
                Log.i(TAG, "onReceivedError: ------->errorCode" + errorCode + ":" + description);
                //网络未连接
                showErrorPage();
            }

            //处理网页加载失败时
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //6.0以上执行
                Log.i(TAG, "onReceivedError: ");
                showErrorPage();//显示错误页面
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                L.e("TTT", "shouldOverrideUrlLoading 0");
                Uri uri = Uri.parse(url);
                String scheme = uri.getScheme();
                if (TextUtils.isEmpty(scheme)) return true;
                if (scheme.equals("nativeapi")) {
                    //如定义nativeapi://showImg是用来查看大图，这里添加查看大图逻辑
                    return true;
                } else if (scheme.equals("http") || scheme.equals("https")) {
                    return false;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.i(TAG, "onProgressChanged:----------->" + newProgress);
                if (newProgress == 100) {
                    //加载完毕进度条消失
                    loadingLayout.setVisibility(View.GONE);
                    webView.getProgressView().setVisibility(View.GONE);
                } else {
                    //更新进度
                    webView.getProgressView().setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }


            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.i(TAG, "onReceivedTitle:title ------>" + title);
                if (title.contains("404")){
                    showErrorPage();
                }
            }
        });

        webParentView = (LinearLayout) webView.getParent(); //获取父容器
    }

    /**
     * 显示自定义错误提示页面，用一个View覆盖在WebView
     */
    private void showErrorPage() {
        webParentView.removeAllViews(); //移除加载网页错误时，默认的提示信息
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        webParentView.addView(mErrorView, 0, layoutParams); //添加自定义的错误提示的View
    }

    /***
     * 显示加载失败时自定义的网页
     */
    private void initErrorPage() {
        if (mErrorView == null) {
            mErrorView = View.inflate(this, R.layout.layout_load_error, null);
        }
    }

}
