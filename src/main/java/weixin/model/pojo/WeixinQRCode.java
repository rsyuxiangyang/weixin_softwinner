package weixin.model.pojo;

/**
 * Created by XIANGYANG on 2015-8-2.
 * ��ʱ��ά����Ϣ
 */
public class WeixinQRCode {
	// ��ȡ�Ķ�ά��ticket
	private String ticket;
	// ��ά�����Чʱ�䣬��λΪ�룬��󲻳���1800
	private int expireSeconds;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public int getExpireSeconds() {
		return expireSeconds;
	}

	public void setExpireSeconds(int expireSeconds) {
		this.expireSeconds = expireSeconds;
	}
}
