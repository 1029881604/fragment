package team.antelope.fg.entity;

/**
 * @Author：hwc
 * @Date：2017/12/6 22:00
 * @Desc: 关注实体
 */

public class Attention {
    private long uId;   //用户id
    private long attentionUserId;//被关注者id

    /**
     * @Description 公有无参构造方法
     * @date 2017/12/6
     */
    public Attention() {
    }


    public Attention(long uId, long attentionUserId) {

        this.uId = uId;
        this.attentionUserId = attentionUserId;
    }


    public long getuId() {
        return uId;
    }

    public void setuId(long uId) {
        this.uId = uId;
    }

    @Override
    public String toString() {
        return "Attention{" +
                "uId=" + uId +
                ", attentionUserId=" + attentionUserId +
                '}';
    }

    public long getAttentionUserId() {
        return attentionUserId;
    }

    public void setAttentionUserId(long attentionUserId) {
        this.attentionUserId = attentionUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attention attention = (Attention) o;

        if (uId != attention.uId) return false;
        return attentionUserId == attention.attentionUserId;
    }

    @Override
    public int hashCode() {
        int result = (int) (uId ^ (uId >>> 32));
        result = 31 * result + (int) (attentionUserId ^ (attentionUserId >>> 32));
        return result;
    }
}
