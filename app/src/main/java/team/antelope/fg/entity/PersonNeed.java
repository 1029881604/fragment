package team.antelope.fg.entity;

import java.sql.Timestamp;

/**
 * @author uniquelry
 * @Description
 */
public class PersonNeed {
	private long id; 
	private long uid;
	private String title; 
	private String content; 
	private String needtype; 
	private Timestamp customdate; 
	private Timestamp requestdate; 
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


	public String getNeedtype() {
		return needtype;
	}


	public void setNeedtype(String needtype) {
		this.needtype = needtype;
	}


	public Timestamp getCustomdate() {
		return customdate;
	}


	public void setCustomdate(Timestamp customdate) {
		this.customdate = customdate;
	}


	public Timestamp getRequestdate() {
		return requestdate;
	}


	public void setRequestdate(Timestamp requestdate) {
		this.requestdate = requestdate;
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
		return "PersonNeed [id=" + id + ", uid=" + uid + ", title=" + title + ", content=" + content + ", needtype="
				+ needtype + ", customdate=" + customdate + ", requestdate=" + requestdate + ", iscomplete="
				+ iscomplete + ", isonline=" + isonline + ", addressdesc=" + addressdesc + ", name=" + name + ", email="
				+ email + ", headimg=" + headimg + ", starnum=" + starnum + ", phonenum=" + phonenum + ", dealnum="
				+ dealnum + ", fansnum=" + fansnum + "]";
	}

	

}
