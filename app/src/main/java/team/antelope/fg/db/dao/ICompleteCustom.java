package team.antelope.fg.db.dao;

import java.util.List;

import team.antelope.fg.entity.CompleteCustom;

/**
 * @Author：hwc
 * @Date：2017/12/12 20:02
 * @Desc: ...
 */

public interface ICompleteCustom extends IBaseDao<CompleteCustom> {
    List<CompleteCustom> queryAllCompleteCustom();  //查询所有的定制
    int queryTotalRecords();   //查询总定制条目
    List <CompleteCustom> queryAllCompleteCustom(int from, int to);//从from到to查寻Customized
}
