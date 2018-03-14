package team.antelope.fg.db.dao;

import java.util.List;
/**
 * @Author hwc
 * @Date 2017/12/6
 * @TODO IDao 基dao 接口
 *
 */

public interface IBaseDao<T> {
	public long insert(T t);	//添加操作  成功返回插入的id，失败返回-1
	public int update(T t);	//更新操作	返回受影响的行
	public int delete(T t);	//删除操作	返回删除的行
	public T queryById(Long id);//通过id查询
}
