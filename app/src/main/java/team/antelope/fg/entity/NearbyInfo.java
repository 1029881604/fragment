package team.antelope.fg.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author hwc
 * @Date 2018/1/6
 * @TODO NearbyInfo  附近各板块的信息
 * 
 */
public class NearbyInfo {
	private String title;
	private String body;
	private String img;
	private int tag;
	private String type;
	private Date updatetime;

	public NearbyInfo() {
	}

	public NearbyInfo(String title, String body, String img, int tag, String type, Date updatetime) {
		this.title = title;
		this.body = body;
		this.img = img;
		this.tag = tag;
		this.type = type;
		this.updatetime = updatetime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		NearbyInfo that = (NearbyInfo) o;

		if (!title.equals(that.title)) return false;
		if (!body.equals(that.body)) return false;
		if (!img.equals(that.img)) return false;
		if (!type.equals(that.type)) return false;
		return updatetime != null ? updatetime.equals(that.updatetime) : that.updatetime == null;
	}

	@Override
	public int hashCode() {
		int result = title.hashCode();
		result = 31 * result + body.hashCode();
		result = 31 * result + img.hashCode();
		result = 31 * result + type.hashCode();
		result = 31 * result + (updatetime != null ? updatetime.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "NearbyInfo{" +
				"title='" + title + '\'' +
				", body='" + body + '\'' +
				", img='" + img + '\'' +
				", tag='" + tag + '\'' +
				", type='" + type + '\'' +
				", updatetime=" + updatetime +
				'}';
	}
}
