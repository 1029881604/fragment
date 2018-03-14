package team.antelope.fg.ui.view;

import android.os.Bundle;
/**
 * @Author hwc
 * @Date 2017/12/13
 * @TODO ILoginView  登入调用
 *
 */
public interface ILoginView {
	void showToast(String msg);
	void showDialog(String msg);
	void closeDialog();
	void asychStartActivity();
}
