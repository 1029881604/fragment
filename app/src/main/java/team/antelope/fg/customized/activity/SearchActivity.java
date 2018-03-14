package team.antelope.fg.customized.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import team.antelope.fg.customized.searchview.ICallBack;
import team.antelope.fg.customized.searchview.SearchView;
import team.antelope.fg.customized.searchview.bCallBack;
import team.antelope.fg.R;
import team.antelope.fg.ui.MainActivity;
import team.antelope.fg.ui.base.BaseActivity;

/**
 * Created by Kyrene on 2017/12/9.
 */

public class SearchActivity extends BaseActivity {

    SearchView searchView;

    @Override
    public int getLayout() {
        return R.layout.lx_activity_search;
    }

    @Override
    protected void initView(Bundle savedInstanceState){
        //绑定组件
        searchView = (SearchView) findViewById(R.id.search_view);

        //设置点击搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
//                System.out.println("我收到了" + string);
                Intent intent=new Intent();
                intent.putExtra("searchword",string);
                intent.setClass(SearchActivity.this,SearchResult.class);  //指定传递对象
                startActivity(intent);
            }
        });

        //设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
//                startActivity(new Intent(SearchActivity.this, MainActivity.class));
//                SearchActivity.this.overridePendingTransition(R.anim.lx_search_open,R.anim.lx_search_close);
            }
        });
    }

}
