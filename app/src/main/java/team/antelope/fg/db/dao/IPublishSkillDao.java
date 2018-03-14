package team.antelope.fg.db.dao;

import java.util.List;

import team.antelope.fg.entity.PublishSkill;

/**
 * @Author：hwc
 * @Date：2017/12/6 22:28
 * @Desc: 发布技能dao接口
 */

public interface IPublishSkillDao extends IBaseDao<PublishSkill> {
    List<PublishSkill> queryAllPublishSkill();    //查询所有的发布
    int queryTotalRecords();    //查询总发布条目
    List <PublishSkill> queryAllPublishSkill(int from, int to);//从from到to查寻Publish
}
