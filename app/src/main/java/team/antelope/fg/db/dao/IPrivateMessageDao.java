package team.antelope.fg.db.dao;

import java.util.List;

import team.antelope.fg.entity.PrivateMessage;
import team.antelope.fg.entity.PublicMessage;

/**
 * @Author：hwc
 * @Date：2017/12/6 22:35
 * @Desc: 私有消息接口
 */

public interface IPrivateMessageDao extends IBaseDao<PrivateMessage> {
    List<PrivateMessage> queryAllPrivateMessage();    //查询所有的私发消息
    List<PrivateMessage> queryAllNotReadMessage();    //查询所有的未读私发消息
    int queryTotalRecords();    //查询总消息条目
    List <PrivateMessage> queryAllPrivateMessage(int from, int to);//从from到to查寻PrivateMessage
    List<PrivateMessage> queryBySenderId(long senderId);
    List<PrivateMessage> queryAllChatMessage(long userId, long otherId);
}
