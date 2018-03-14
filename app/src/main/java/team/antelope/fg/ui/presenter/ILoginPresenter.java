package team.antelope.fg.ui.presenter;


import team.antelope.fg.entity.User;
/**
 * @Author hwc
 * @Date 2017/12/13
 * @TODO ILoginPresenter  代理接口
 *
 */
public interface ILoginPresenter<T> {
	public T initData();	//初始化数据
	void saveData(T t);
	void doLogin(User user);  //执行登入
	void onDestroy();	//销毁时执行
}
