package team.antelope.fg.me.me_utils;


import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @Author：Carlos
 * @Date: 2018/4/11 11:10
 * @Description:   根据传入的字符串转换为拼音字母
 **/
public class PinyinUtils {


    public static String getPinyin(String str) {

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        StringBuilder sb = new StringBuilder();

        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            // 如果是空格, 跳过
            if(Character.isWhitespace(c)){
                continue;
            }
            if (c>=65&&c<=90)
            {   //大写字母
                sb.append(c);
                break;
            }
            if (c>=97&&c<=122){
                //小写字母转换为大写字母
                char b ;
                b= (char) (c-32);
                sb.append(b);
                break;
            }
            if(c >= -127 && c < 65&&c>90&&c<97&&c>122&&c<128){
                // 肯定不是汉字
                sb.append(c);
                break;
            }else {
                String s = "";
                try {
                    // 通过char得到拼音集合. 单 -> dan, shan
                    s = PinyinHelper.toHanyuPinyinStringArray(c, format)[0];
                    sb.append(s);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                    sb.append(s);
                }
            }
        }

        return sb.toString();
    }

}



