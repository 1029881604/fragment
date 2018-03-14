package team.antelope.fg.util;

import android.content.res.Resources;

import java.io.IOException;
import java.util.Properties;

import team.antelope.fg.R;
import team.antelope.fg.db.DBUtil;

public class PropertiesUtil extends Properties {
	private static PropertiesUtil prop;
	private  PropertiesUtil (){}
	public static PropertiesUtil getInstance0(Resources resources){
		if(prop == null){
			synchronized (PropertiesUtil.class){
				if(prop == null){
					prop = new PropertiesUtil();
					try {
						prop.load(resources.openRawResource(R.raw.urls));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return prop;
	}
	/**
	 * @Description 保证getInstance先执行
	 * @date 2017/12/13
	 */
	public static PropertiesUtil getInstance(){
		return prop;
	}
}
