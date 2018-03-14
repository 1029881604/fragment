package team.antelope.fg.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @Author：hwc
 * @Date：2017/12/12 20:22
 * @Desc: ...
 */

public class Person {
    private Long id; //id,后台系统设置
    private String name;    //用户名
    private String sex;     //性别
    private int age;    //年龄
    private String email;   // 邮箱
    @SerializedName("headimg") private String headImg; //头像
    private float starnum;	//星数
    private int dealnum;	//交易数量
    private long fansnum;   //粉丝数， 被关注数

    public Person() {
    }

    public Person(Long id, String name, String sex, int age,
                  String email, String headImg, float starnum,
                  int dealnum, long fansnum) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.email = email;
        this.starnum = starnum;
        this.headImg = headImg;
        this.dealnum = dealnum;
        this.fansnum = fansnum;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public float getStarnum() {
        return starnum;
    }

    public void setStarnum(float starnum) {
        this.starnum = starnum;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public int getDealnum() {
        return dealnum;
    }

    public void setDealnum(int dealnum) {
        this.dealnum = dealnum;
    }

    public long getFansnum() {
        return fansnum;
    }

    public void setFansnum(long fansnum) {
        this.fansnum = fansnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (age != person.age) return false;
        if (Float.compare(person.starnum, starnum) != 0) return false;
        if (dealnum != person.dealnum) return false;
        if (fansnum != person.fansnum) return false;
        if (!id.equals(person.id)) return false;
        if (!name.equals(person.name)) return false;
        if (sex != null ? !sex.equals(person.sex) : person.sex != null) return false;
        return email != null ? email.equals(person.email) : person.email == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (starnum != +0.0f ? Float.floatToIntBits(starnum) : 0);
        result = 31 * result + dealnum;
        result = 31 * result + (int) (fansnum ^ (fansnum >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", starnum=" + starnum +
                ", headImg='" + headImg + '\'' +
                ", dealnum=" + dealnum +
                ", fansnum=" + fansnum +
                '}';
    }
}
