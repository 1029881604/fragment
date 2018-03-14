package team.antelope.fg.db.dao;

import java.util.List;

import team.antelope.fg.entity.Attention;
import team.antelope.fg.entity.Person;

/**
 * @Author：hwc
 * @Date：2017/12/6 22:50
 * @Desc: 关注dao接口
 */

public interface IAttentionDao extends IBaseDao<Attention> {
    List<Attention> queryAllAttention();    //查询所有的Attention
    int queryTotalRecords();    //查询总Attention条目
    List <Attention> queryAllPrivateAttention(int from, int to);//从from到to查寻Attention
    public Attention queryById(Long uid, long fid);//通过id查询
    List<Person> findFriends(Long id);
}
