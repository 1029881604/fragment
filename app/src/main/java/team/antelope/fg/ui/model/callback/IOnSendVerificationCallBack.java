package team.antelope.fg.ui.model.callback;

public interface IOnSendVerificationCallBack {
	void onSuccess(String msg);
	void onFail(String msg);
}
