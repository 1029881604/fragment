package team.antelope.fg.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @Author：hwc
 * @Date：2017/12/6 20:49
 * @Desc: 定制实体类   发布自己的需求 发布模块可以查看到所有人的需求
 */

public class PublishNeed {
    private long id;    //定制表id
    @SerializedName("uid") private long uId;   //用户id
    private String title;   // 需求标题
    private String content; //需求详情
    @SerializedName("needtype") private String needtype;
    @SerializedName("customdate") private Date customDate;  //定制日期   //存入数据库中是long类型
    @SerializedName("requestdate") private Date requestDate;  //要求完成日期//存入数据库中是long类型
    @SerializedName("iscomplete") private boolean isComplete; //是否完成或结束
    @SerializedName("isonline") private boolean isOnline;   //是否线上
    @SerializedName("addressdesc") private String addressDesc; // 地址描述
    private double longitude;    //longitude
    private double latitude;    //latitude

    public PublishNeed(long id, long uId, String title, String content, String needtype,
                       Date customDate, Date requestDate, boolean isComplete,
                       boolean isOnline, String addressDesc, double longitude, double latitude) {
        this.id = id;
        this.uId = uId;
        this.title = title;
        this.content = content;
        this.needtype = needtype;
        this.customDate = customDate;
        this.requestDate = requestDate;
        this.isComplete = isComplete;
        this.isOnline = isOnline;
        this.addressDesc = addressDesc;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getNeedtype() {
        return needtype;
    }

    public void setNeedtype(String needtype) {
        this.needtype = needtype;
    }


    /**
     * @Description 无参公有构造方法
     * @date 2017/12/6
     */
    public PublishNeed() {
    }


    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }



    public Date getCustomDate() {
        return customDate;
    }

    public void setCustomDate(Date customDate) {
        this.customDate = customDate;
    }

    public Date getStartDate() {
        return customDate;
    }

    public void setStartDate(Date startDate) {
        this.customDate = customDate;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
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

        PublishNeed that = (PublishNeed) o;

        if (id != that.id) return false;
        if (uId != that.uId) return false;
        if (isComplete != that.isComplete) return false;
        if (isOnline != that.isOnline) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (!content.equals(that.content)) return false;
        if (!needtype.equals(that.needtype)) return false;
        if (!customDate.equals(that.customDate)) return false;
        if (!requestDate.equals(that.requestDate)) return false;
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
        result = 31 * result + needtype.hashCode();
        result = 31 * result + customDate.hashCode();
        result = 31 * result + requestDate.hashCode();
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
        return "PublishNeed{" +
                "id=" + id +
                ", uId=" + uId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", needtype='" + needtype + '\'' +
                ", customDate=" + customDate +
                ", requestDate=" + requestDate +
                ", isComplete=" + isComplete +
                ", isOnline=" + isOnline +
                ", addressDesc='" + addressDesc + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
