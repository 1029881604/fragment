package team.antelope.fg.entity;

import java.util.Date;

/**
 * @Author hwc
 * @Date 2017/12/6
 * @TODO PublicMessage   系统消息
 */
public class PublicMessage {
	private long id;		//系统消息id
	private Date sendTime;	//消息发送时间
	private long receiverId;
	private String title;	//消息标题
	private String content;	//消息内容
	private boolean isRead;

	/**
	 * @Description 公有无参构造函数
	 * @date 2017/12/6
	 */
	public PublicMessage() {
	}

	public PublicMessage(long id, Date sendTime,
						 long receiverId, String title,
						 String content, boolean isRead) {
		this.id = id;
		this.sendTime = sendTime;
		this.receiverId = receiverId;
		this.title = title;
		this.content = content;
		this.isRead = isRead;
	}

	public long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean read) {
		isRead = read;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PublicMessage message = (PublicMessage) o;

		if (id != message.id) return false;
		if (receiverId != message.receiverId) return false;
		if (isRead != message.isRead) return false;
		if (!sendTime.equals(message.sendTime)) return false;
		if (title != null ? !title.equals(message.title) : message.title != null) return false;
		return content != null ? content.equals(message.content) : message.content == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + sendTime.hashCode();
		result = 31 * result + (int) (receiverId ^ (receiverId >>> 32));
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (content != null ? content.hashCode() : 0);
		result = 31 * result + (isRead ? 1 : 0);
		return result;
	}

	@Override
	public String toString() {
		return "PublicMessage{" +
				"id=" + id +
				", sendTime=" + sendTime +
				", receiverId=" + receiverId +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", isRead=" + isRead +
				'}';
	}
}
