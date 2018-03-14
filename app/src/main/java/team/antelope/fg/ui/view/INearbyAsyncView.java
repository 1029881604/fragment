package team.antelope.fg.ui.view;

import android.app.Dialog;

import java.util.List;

import team.antelope.fg.entity.NeedPreInfo;
import team.antelope.fg.entity.SkillPreInfo;

/**
 * @Author：hwc
 * @Date：2017/12/27 10:35
 * @Desc: INearbyAsyncView 视图
 */

public interface INearbyAsyncView{
    void showNeedListData(List<NeedPreInfo> needs);  // 显示title数据
    void showSkillListData(List<SkillPreInfo> skills);  // 显示title数据
    void showToast(String msg);
    Dialog showProgressDialog(String str);  //显示进度
    void hideProgressDialog();
    void showDataError();
}
