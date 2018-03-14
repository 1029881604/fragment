package team.antelope.fg.entity;

import java.util.Date;

/**
 * @Author hwc
 * @Date 2018/1/6
 * @TODO NearbyModularInfo  附近模块信息
 * 
 */
public class NearbyModularInfo {
	private String needtitle;
	private String needbody;
	private String needimg;
	private String skilltitle;
	private String skillbody;
	private String skillimg;
	private String type;
	private Date needupdatetime;
	private Date skillupdatetime;
	public NearbyModularInfo(String needtitle, String needbody, String needimg, String skilltitle, String skillbody,
			String skillimg, String type, Date needupdatetime, Date skillupdatetime) {
		super();
		this.needtitle = needtitle;
		this.needbody = needbody;
		this.needimg = needimg;
		this.skilltitle = skilltitle;
		this.skillbody = skillbody;
		this.skillimg = skillimg;
		this.type = type;
		this.needupdatetime = needupdatetime;
		this.skillupdatetime = skillupdatetime;
	}
	public NearbyModularInfo() {
		super();
	}
	public String getNeedtitle() {
		return needtitle;
	}
	public void setNeedtitle(String needtitle) {
		this.needtitle = needtitle;
	}
	public String getNeedbody() {
		return needbody;
	}
	public void setNeedbody(String needbody) {
		this.needbody = needbody;
	}
	public String getNeedimg() {
		return needimg;
	}
	public void setNeedimg(String needimg) {
		this.needimg = needimg;
	}
	public String getSkilltitle() {
		return skilltitle;
	}
	public void setSkilltitle(String skilltitle) {
		this.skilltitle = skilltitle;
	}
	public String getSkillbody() {
		return skillbody;
	}
	public void setSkillbody(String skillbody) {
		this.skillbody = skillbody;
	}
	public String getSkillimg() {
		return skillimg;
	}
	public void setSkillimg(String skillimg) {
		this.skillimg = skillimg;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getNeedupdatetime() {
		return needupdatetime;
	}
	public void setNeedupdatetime(Date needupdatetime) {
		this.needupdatetime = needupdatetime;
	}
	public Date getSkillupdatetime() {
		return skillupdatetime;
	}
	public void setSkillupdatetime(Date skillupdatetime) {
		this.skillupdatetime = skillupdatetime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((needbody == null) ? 0 : needbody.hashCode());
		result = prime * result + ((needimg == null) ? 0 : needimg.hashCode());
		result = prime * result + ((needtitle == null) ? 0 : needtitle.hashCode());
		result = prime * result + ((needupdatetime == null) ? 0 : needupdatetime.hashCode());
		result = prime * result + ((skillbody == null) ? 0 : skillbody.hashCode());
		result = prime * result + ((skillimg == null) ? 0 : skillimg.hashCode());
		result = prime * result + ((skilltitle == null) ? 0 : skilltitle.hashCode());
		result = prime * result + ((skillupdatetime == null) ? 0 : skillupdatetime.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NearbyModularInfo other = (NearbyModularInfo) obj;
		if (needbody == null) {
			if (other.needbody != null)
				return false;
		} else if (!needbody.equals(other.needbody))
			return false;
		if (needimg == null) {
			if (other.needimg != null)
				return false;
		} else if (!needimg.equals(other.needimg))
			return false;
		if (needtitle == null) {
			if (other.needtitle != null)
				return false;
		} else if (!needtitle.equals(other.needtitle))
			return false;
		if (needupdatetime == null) {
			if (other.needupdatetime != null)
				return false;
		} else if (!needupdatetime.equals(other.needupdatetime))
			return false;
		if (skillbody == null) {
			if (other.skillbody != null)
				return false;
		} else if (!skillbody.equals(other.skillbody))
			return false;
		if (skillimg == null) {
			if (other.skillimg != null)
				return false;
		} else if (!skillimg.equals(other.skillimg))
			return false;
		if (skilltitle == null) {
			if (other.skilltitle != null)
				return false;
		} else if (!skilltitle.equals(other.skilltitle))
			return false;
		if (skillupdatetime == null) {
			if (other.skillupdatetime != null)
				return false;
		} else if (!skillupdatetime.equals(other.skillupdatetime))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "NearbyModularInfo [needtitle=" + needtitle + ", needbody=" + needbody + ", needimg=" + needimg
				+ ", skilltitle=" + skilltitle + ", skillbody=" + skillbody + ", skillimg=" + skillimg + ", type="
				+ type + ", needupdatetime=" + needupdatetime + ", skillupdatetime=" + skillupdatetime + "]";
	}
	
}
