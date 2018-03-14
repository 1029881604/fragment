package team.antelope.fg.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtil {
	public static String getString(InputStream is) throws IOException{
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while((len = is.read(buffer)) != -1){
			baos.write(buffer, 0, len);
		}
		return new String(baos.toByteArray(), "utf-8");
	}
}
