package weixin.model.message.req;

/**
 * Created by XIANGYANG on 2015-8-3.
 * 文本消息
 */
public class TextMessage extends BaseMessage {
	// 消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
