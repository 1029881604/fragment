package team.antelope.fg.ui.model.callback;

/**
 * @Author hwc
 * @Date 2017/12/13
 * @TODO IOnLoginCallback 登入服务器获取数据回调类
 *
 */
public interface IOnLoginCallback<T> {
	public abstract void onFail(final String msg);	//访问成功时执行
	public abstract void onSuccess(final T t);		//访问失败时执行
}
