package team.antelope.fg.db.dao;

import java.util.List;

import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.PublicMessage;

/**
 * @Author：hwc
 * @Date：2017/12/12 20:26
 * @Desc: ...
 */

public interface IPersonDao extends IBaseDao<Person> {
    List<Person> queryAllPerson();    //查询所有的用户
    int queryTotalRecords();    //查询sqlite中总用户条目
    List <Person> queryAllPerson(int from, int to);//从from到to查寻
}
