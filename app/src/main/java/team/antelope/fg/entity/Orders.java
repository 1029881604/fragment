package team.antelope.fg.entity;

import java.sql.Timestamp;

/**
 * 订单表
 * @author 廖翔
 * @time:2018年5月19日
 * @Description:TODO
id Long(11) primary key ,	-- 订单号（主键）
uid Long(11) not null,	-- 用户id（当前登录者）
uid_s Long(11) not null,	-- 技能拥有者id
skillid Long (11) not null,  -- 技能id
title varchar(60),  -- 技能标题
content text,
img varchar(300),
skilltype varchar(50),
create_time datetime,	-- 创建日期
ispay boolean,	-- 是否付款
isdelete boolean, -- 是否删除
iscomment boolean,  -- 是否评论
 */
public class Orders {
    private Long id;
    private Long uid;
    private Long uid_s;
    private Long skillid;
    private String title;
    private String content;
    private Timestamp create_time;
    private String img;
    private String skilltype;
    private boolean ispay;
    private boolean isdelete;
    private boolean iscomment;

    public Orders() {
        // TODO Auto-generated constructor stub
    }

    public Orders(Long id, Long uid, Long uid_s, Long skillid, String title, String content, Timestamp create_time,
                  String img, String skilltype, boolean ispay, boolean isdelete, boolean iscomment) {
        super();
        this.id = id;
        this.uid = uid;
        this.uid_s = uid_s;
        this.skillid = skillid;
        this.title = title;
        this.content = content;
        this.create_time = create_time;
        this.img = img;
        this.skilltype = skilltype;
        this.ispay = ispay;
        this.isdelete = isdelete;
        this.iscomment = iscomment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getUid_s() {
        return uid_s;
    }

    public void setUid_s(Long uid_s) {
        this.uid_s = uid_s;
    }

    public Long getSkillid() {
        return skillid;
    }

    public void setSkillid(Long skillid) {
        this.skillid = skillid;
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

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
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

    public boolean isIspay() {
        return ispay;
    }

    public void setIspay(boolean ispay) {
        this.ispay = ispay;
    }

    public boolean isIsdelete() {
        return isdelete;
    }

    public void setIsdelete(boolean isdelete) {
        this.isdelete = isdelete;
    }

    public boolean isIscomment() {
        return iscomment;
    }

    public void setIscomment(boolean iscomment) {
        this.iscomment = iscomment;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((create_time == null) ? 0 : create_time.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((img == null) ? 0 : img.hashCode());
        result = prime * result + (iscomment ? 1231 : 1237);
        result = prime * result + (isdelete ? 1231 : 1237);
        result = prime * result + (ispay ? 1231 : 1237);
        result = prime * result + ((skillid == null) ? 0 : skillid.hashCode());
        result = prime * result + ((skilltype == null) ? 0 : skilltype.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((uid == null) ? 0 : uid.hashCode());
        result = prime * result + ((uid_s == null) ? 0 : uid_s.hashCode());
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
        Orders other = (Orders) obj;
        if (content == null) {
            if (other.content != null)
                return false;
        } else if (!content.equals(other.content))
            return false;
        if (create_time == null) {
            if (other.create_time != null)
                return false;
        } else if (!create_time.equals(other.create_time))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (img == null) {
            if (other.img != null)
                return false;
        } else if (!img.equals(other.img))
            return false;
        if (iscomment != other.iscomment)
            return false;
        if (isdelete != other.isdelete)
            return false;
        if (ispay != other.ispay)
            return false;
        if (skillid == null) {
            if (other.skillid != null)
                return false;
        } else if (!skillid.equals(other.skillid))
            return false;
        if (skilltype == null) {
            if (other.skilltype != null)
                return false;
        } else if (!skilltype.equals(other.skilltype))
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
        if (uid_s == null) {
            if (other.uid_s != null)
                return false;
        } else if (!uid_s.equals(other.uid_s))
            return false;
        return true;
    }


}
