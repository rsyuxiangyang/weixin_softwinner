package weixin.model.message.resp;

/**
 * Created by XIANGYANG on 2015-8-3.
 * ������Ϣ
 */
public class VoiceMessage extends BaseMessage {
	// ����
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}
}
