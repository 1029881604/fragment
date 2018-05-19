package team.antelope.fg.customized.dialog;

import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private static BaseActivity instance = null;

    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        instance = this;
    }

    public static BaseActivity getInstance() {
        return instance;
    }
}
