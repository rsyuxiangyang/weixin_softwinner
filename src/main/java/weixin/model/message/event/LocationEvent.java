package weixin.model.message.event;

/**
 * Created by XIANGYANG on 2015-8-3.
 * �ϱ�����λ���¼�
 */
public class LocationEvent extends BaseEvent {
	// ����λ��γ��
	private String Latitude;
	// ����λ�þ���
	private String Longitude;
	// ����λ�þ���
	private String Precision;

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	public String getPrecision() {
		return Precision;
	}

	public void setPrecision(String precision) {
		Precision = precision;
	}
}
