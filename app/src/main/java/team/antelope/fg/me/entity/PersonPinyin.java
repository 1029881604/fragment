package team.antelope.fg.me.entity;

import android.graphics.Bitmap;

import team.antelope.fg.me.me_utils.PinyinUtils;

/**
 * Created by Carlos on 2018/4/17.
 */

public class PersonPinyin implements Comparable<PersonPinyin> {
    private String name;
    private String pinyin;
    private String headImg;
    private  Long personId;

    public PersonPinyin(String name,String headImg,Long personId) {
        super();
        this.name = name;
        this.pinyin = PinyinUtils.getPinyin(name);
        this.headImg=headImg;
        this.personId=personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getHeadImg() {
        return headImg;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPinyin() {
        return pinyin;
    }
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public int compareTo(PersonPinyin another) {
        return this.pinyin.compareTo(another.getPinyin());
    }

}
