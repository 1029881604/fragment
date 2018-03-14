package team.antelope.fg.ui.model.callback;

public interface IOnRegisterCallback<T> {
	void onSuccess(T t);
	void onFail(String msg);
}
