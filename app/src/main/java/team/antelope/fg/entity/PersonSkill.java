package team.antelope.fg.entity;

import java.sql.Timestamp;

/**
 * @author uniquelry
 */
public class PersonSkill {
	private long id;
    private long uid;
    private String title; 
    private String content; 
    private Timestamp publishdate;
    private Timestamp stopdate;
    private String img; 
    private String  skilltype;
    private boolean iscomplete;
    private boolean isonline;   
    private String addressdesc;	
    
	private String name;   
	private String email;   
	private String headimg;
	private Float starnum;	
	private String phonenum;	
	private int dealnum;
	private int fansnum; 
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getUid() {
		return uid;
	}


	public void setUid(long uid) {
		this.uid = uid;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Timestamp getPublishdate() {
		return publishdate;
	}


	public void setPublishdate(Timestamp publishdate) {
		this.publishdate = publishdate;
	}


	public Timestamp getStopdate() {
		return stopdate;
	}


	public void setStopdate(Timestamp stopdate) {
		this.stopdate = stopdate;
	}


	public String getImg() {
		return img;
	}


	public void setImg(String img) {
		this.img = img;
	}


	public String getSkilltype() {
		return skilltype;
	}


	public void setSkilltype(String skilltype) {
		this.skilltype = skilltype;
	}


	public boolean isIscomplete() {
		return iscomplete;
	}


	public void setIscomplete(boolean iscomplete) {
		this.iscomplete = iscomplete;
	}


	public boolean isIsonline() {
		return isonline;
	}


	public void setIsonline(boolean isonline) {
		this.isonline = isonline;
	}


	public String getAddressdesc() {
		return addressdesc;
	}


	public void setAddressdesc(String addressdesc) {
		this.addressdesc = addressdesc;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getHeadimg() {
		return headimg;
	}


	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}


	public Float getStarnum() {
		return starnum;
	}


	public void setStarnum(Float starnum) {
		this.starnum = starnum;
	}


	public String getPhonenum() {
		return phonenum;
	}


	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}


	public int getDealnum() {
		return dealnum;
	}


	public void setDealnum(int dealnum) {
		this.dealnum = dealnum;
	}


	public int getFansnum() {
		return fansnum;
	}


	public void setFansnum(int fansnum) {
		this.fansnum = fansnum;
	}


	@Override
	public String toString() {
		return "PersonSkill [id=" + id + ", uid=" + uid + ", title=" + title + ", content=" + content + ", publishdate="
				+ publishdate + ", stopdate=" + stopdate + ", img=" + img + ", skilltype=" + skilltype + ", iscomplete="
				+ iscomplete + ", isonline=" + isonline + ", addressdesc=" + addressdesc + ", name=" + name + ", email="
				+ email + ", headimg=" + headimg + ", starnum=" + starnum + ", phonenum=" + phonenum + ", dealnum="
				+ dealnum + ", fansnum=" + fansnum + "]";
	}
	
	
	
}
