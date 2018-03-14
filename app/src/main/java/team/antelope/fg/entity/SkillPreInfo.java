package team.antelope.fg.entity;

public class SkillPreInfo {
	private Long id;
	private Long uid;
	private String name;
	private String headimg;
	private Double longitude;
	private Double latitude;
	private String title;
	private String addressdesc;
	
	public SkillPreInfo() {
		super();
	}
	
	
	public SkillPreInfo(Long id, Long uid, String name, String headimg, Double longitude, Double latitude, String title,
			String addressdesc) {
		super();
		this.id = id;
		this.uid = uid;
		this.name = name;
		this.headimg = headimg;
		this.longitude = longitude;
		this.latitude = latitude;
		this.title = title;
		this.addressdesc = addressdesc;
	}


	public Long getId() {
		return id;
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
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddressdesc() {
		return addressdesc;
	}
	public void setAddressdesc(String addressdesc) {
		this.addressdesc = addressdesc;
	}


	public Long getUid() {
		return uid;
	}


	public void setUid(Long uid) {
		this.uid = uid;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addressdesc == null) ? 0 : addressdesc.hashCode());
		result = prime * result + ((headimg == null) ? 0 : headimg.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
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
		SkillPreInfo other = (SkillPreInfo) obj;
		if (addressdesc == null) {
			if (other.addressdesc != null)
				return false;
		} else if (!addressdesc.equals(other.addressdesc))
			return false;
		if (headimg == null) {
			if (other.headimg != null)
				return false;
		} else if (!headimg.equals(other.headimg))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "SkillPreInfo [id=" + id + ", uid=" + uid + ", name=" + name + ", headimg=" + headimg + ", longitude="
				+ longitude + ", latitude=" + latitude + ", title=" + title + ", addressdesc=" + addressdesc + "]";
	}
	
}
