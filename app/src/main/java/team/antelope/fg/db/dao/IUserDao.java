package team.antelope.fg.db.dao;

import java.util.List;

import team.antelope.fg.entity.User;
/**
 * @Author hwc
 * @Date 2017/12/6
 * @TODO IUserDao 用户dao接口
 *
 */
public interface IUserDao extends IBaseDao<User> {
	List <User> queryAllUser();
	User queryByName(String name);
	//删除所有用户
	int deleteAll();
}
