package team.antelope.fg.db.dao;

import java.util.List;

import team.antelope.fg.entity.PublicMessage;

/**
 * @Author：hwc
 * @Date：2017/12/6 22:32
 * @Desc: 系统消息dao接口
 */

public interface IPublicMessageDao extends IBaseDao<PublicMessage> {
    List<PublicMessage> queryAllPublicMessage();    //查询所有的系统消息
    int queryTotalRecords();    //查询总消息条目
    List <PublicMessage> queryAllPublicMessage(int from, int to);//从from到to查寻PublicMessage
}
