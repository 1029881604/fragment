package team.antelope.fg.publish.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
*@Author: lry
*@Date: 2017/12/17 22:43
*@Description: 重写getItem()和getCount()方法，分别返回第几个fragment以及fragment的数量
*/
public class PublishFragmentPagerAdapter extends FragmentPagerAdapter {
    //存储所有的fragment
    private List<Fragment> list;
    public PublishFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list){
        super(fm);
        this.list=list;
    }
    @Override
    public Fragment getItem(int arg0){
        return list.get(arg0);
    }
    @Override
    public int getCount(){
        return list.size();
    }
}
