package weixin.model.message.resp;

/**
 * Created by XIANGYANG on 2015-8-3.
 * ��Ƶmodel
 */
public class Video {
	// ý���ļ�id
	private String MediaId;
	// ����ͼ��ý��id
	private String ThumbMediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
}
