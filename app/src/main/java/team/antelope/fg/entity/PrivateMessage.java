package team.antelope.fg.entity;

import java.util.Date;

/**
 * @Author hwc
 * @Date 2017/12/6
 * @TODO PrivateMessage  私人消息，一般是别的用户发送过来的
 * 
 */
public class PrivateMessage {
	private long id;		//消息id
	private long senderId;	//发送者id
	private String senderName;//...姓名
	private long receiverId;//接受者id
	private String receiverName;//接受者姓名
	private Date sendTime;		//消息发送时间
	private String content;	//消息内容 可以为json对象
	private boolean isRead;	//是否已经读取

	/**
	 * @Description 公有无参构造函数
	 * @date 2017/12/6
	 */
	public PrivateMessage() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}



	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public PrivateMessage(long id, long senderId, String senderName,
						  long receiverId, String receiverName,
						  Date sendTime, String content, boolean isRead) {
		this.id = id;
		this.senderId = senderId;
		this.senderName = senderName;
		this.receiverId = receiverId;
		this.receiverName = receiverName;
		this.sendTime = sendTime;
		this.content = content;
		this.isRead = isRead;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean read) {
		isRead = read;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PrivateMessage that = (PrivateMessage) o;

		if (id != that.id) return false;
		if (senderId != that.senderId) return false;
		if (receiverId != that.receiverId) return false;
		if (isRead != that.isRead) return false;
		if (!senderName.equals(that.senderName)) return false;
		if (!receiverName.equals(that.receiverName)) return false;
		if (!sendTime.equals(that.sendTime)) return false;
		return content.equals(that.content);
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (int) (senderId ^ (senderId >>> 32));
		result = 31 * result + senderName.hashCode();
		result = 31 * result + (int) (receiverId ^ (receiverId >>> 32));
		result = 31 * result + receiverName.hashCode();
		result = 31 * result + sendTime.hashCode();
		result = 31 * result + content.hashCode();
		result = 31 * result + (isRead ? 1 : 0);
		return result;
	}

	@Override
	public String toString() {
		return "PrivateMessage{" +
				"id=" + id +
				", senderId=" + senderId +
				", senderName='" + senderName + '\'' +
				", receiverId=" + receiverId +
				", receiverName='" + receiverName + '\'' +
				", sendTime=" + sendTime +
				", content='" + content + '\'' +
				", isRead=" + isRead +
				'}';
	}
}
