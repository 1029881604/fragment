package team.antelope.fg.db.dao;

import java.util.List;

import team.antelope.fg.entity.PublishNeed;

/**
 * @Author：hwc
 * @Date：2017/12/6 22:47
 * @Desc: 定制dao接口
 */

public interface IPublishNeedDao extends IBaseDao<PublishNeed> {
    List<PublishNeed> queryAllPublishNeed();    //查询所有的需求
    int queryTotalRecords();    //查询总定制需求
    List <PublishNeed> queryAllPublishNeed(int from, int to);//从from到to查寻需求
}
