package team.antelope.fg.entity;

import java.util.Date;

/**
 * @Author：hwc
 * @Date：2017/12/12 18:18
 * @Desc: 定制，随时可以提供；已经完成的，提供随时交易
 */

public class CompleteCustom {
    private long id;//publiskill表的id
    private long uId;//用户 id
    private String title;
    private String content;//发布内容
    private Date customDate;//发布日期
    private String img; //发布预览图
    private String  skillType;//发布技能类型
    private boolean isOpen; //是否公开

    public CompleteCustom() {
    }

    public CompleteCustom(long id, long uId, String title,
                          String content, Date customDate,
                          String img, String skillType,
                          boolean isOpen) {
        this.id = id;
        this.uId = uId;
        this.title = title;
        this.content = content;
        this.customDate = customDate;
        this.img = img;
        this.skillType = skillType;
        this.isOpen = isOpen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getuId() {
        return uId;
    }

    public void setuId(long uId) {
        this.uId = uId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCustomDate() {
        return customDate;
    }

    public void setCustomDate(Date customDate) {
        this.customDate = customDate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSkillType() {
        return skillType;
    }

    public void setSkillType(String skillType) {
        this.skillType = skillType;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompleteCustom that = (CompleteCustom) o;

        if (id != that.id) return false;
        if (uId != that.uId) return false;
        if (isOpen != that.isOpen) return false;
        if (!content.equals(that.content)) return false;
        if (!customDate.equals(that.customDate)) return false;
        if (img != null ? !img.equals(that.img) : that.img != null) return false;
        return skillType.equals(that.skillType);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (uId ^ (uId >>> 32));
        result = 31 * result + content.hashCode();
        result = 31 * result + customDate.hashCode();
        result = 31 * result + (img != null ? img.hashCode() : 0);
        result = 31 * result + skillType.hashCode();
        result = 31 * result + (isOpen ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CompleteCustom{" +
                "id=" + id +
                ", uId=" + uId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", customDate=" + customDate +
                ", img='" + img + '\'' +
                ", skillType='" + skillType + '\'' +
                ", isOpen=" + isOpen +
                '}';
    }
}
