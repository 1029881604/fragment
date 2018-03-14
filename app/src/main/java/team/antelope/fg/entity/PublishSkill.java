package team.antelope.fg.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @Author：hwc
 * @Date：2017/12/6 21:05
 * @Desc: 发布技能实体类,所有人发布的
 * （所有人发布的技能，不能随时提供，但是在停止服务时间前可以帮你做），
 * 也可以理解为自己发布的实体
 */

public class PublishSkill {
    @SerializedName("id")  private long id;//publiskill表的id
    @SerializedName("uid") private long uId;//用户 id
    @SerializedName("title") private String title;   // 技能标题
    @SerializedName("content") private String content; //技能内容
    @SerializedName("publishdate") private Date publishDate;//发布日期
    @SerializedName("stopdate") private Date stopDate;  //停止服务时间
    @SerializedName("img") private String img; //技能预览图
    @SerializedName("skilltype") private String  skillType; //发布技能类型
    @SerializedName("iscomplete") private boolean isComplete; //定制为true, 发布模块发布的技能为false
    @SerializedName("isonline") private boolean isOnline;   //是否线上
    @SerializedName("addressdesc") private String addressDesc; // 地址描述
    @SerializedName("longitude") private double longitude;    //距离
    @SerializedName("latitude") private double latitude;    //距离
    /**
     * @Description 公有无参构造方法
     * @date 2017/12/6
     */
    public PublishSkill() {
    }

    public PublishSkill(long id, long uId, String title, String content, Date publishDate, Date stopDate,
                        String img, String skillType, boolean isComplete, boolean isOnline, String addressDesc, double longitude, double latitude) {
        this.id = id;
        this.uId = uId;
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
        this.stopDate = stopDate;
        this.img = img;
        this.skillType = skillType;
        this.isComplete = isComplete;
        this.isOnline = isOnline;
        this.addressDesc = addressDesc;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getAddressDesc() {
        return addressDesc;
    }

    public void setAddressDesc(String addressDesc) {
        this.addressDesc = addressDesc;
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

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSkillType() {
        return skillType;
    }

    public void setSkillType(String skillType) {
        this.skillType = skillType;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PublishSkill that = (PublishSkill) o;

        if (id != that.id) return false;
        if (uId != that.uId) return false;
        if (isComplete != that.isComplete) return false;
        if (isOnline != that.isOnline) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (!content.equals(that.content)) return false;
        if (!publishDate.equals(that.publishDate)) return false;
        if (!stopDate.equals(that.stopDate)) return false;
        if (!img.equals(that.img)) return false;
        if (!skillType.equals(that.skillType)) return false;
        return addressDesc.equals(that.addressDesc);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (uId ^ (uId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + content.hashCode();
        result = 31 * result + publishDate.hashCode();
        result = 31 * result + stopDate.hashCode();
        result = 31 * result + img.hashCode();
        result = 31 * result + skillType.hashCode();
        result = 31 * result + (isComplete ? 1 : 0);
        result = 31 * result + (isOnline ? 1 : 0);
        result = 31 * result + addressDesc.hashCode();
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "PublishSkill{" +
                "id=" + id +
                ", uId=" + uId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publishDate=" + publishDate +
                ", stopDate=" + stopDate +
                ", img='" + img + '\'' +
                ", skillType='" + skillType + '\'' +
                ", isComplete=" + isComplete +
                ", isOnline=" + isOnline +
                ", addressDesc='" + addressDesc + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
