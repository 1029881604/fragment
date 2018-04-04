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
	public String toString() {
		return "NearbyModularInfo [needtitle=" + needtitle + ", needbody=" + needbody + ", needimg=" + needimg
				+ ", skilltitle=" + skilltitle + ", skillbody=" + skillbody + ", skillimg=" + skillimg + ", type="
				+ type + ", needupdatetime=" + needupdatetime + ", skillupdatetime=" + skillupdatetime + "]";
	}
	
}
