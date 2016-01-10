package weixin.model.message.resp;

/**
 * Created by XIANGYANG on 2015-8-3.
 * 视频消息
 */
public class VideoMessage extends BaseMessage {
	// 视频
	private Video Video;

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		Video = video;
	}
}
