package weixin.model.message.event;

/**
 * Created by XIANGYANG on 2015-8-3.
 * �Զ���˵��¼�
 */
public class MenuEvent extends BaseEvent {
	// �¼�KEYֵ�����Զ���˵��ӿ���KEYֵ��Ӧ
	private String EventKey;

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
}
