package weixin.model.message.resp;

import java.util.List;

/**
 * Created by XIANGYANG on 2015-8-3.
 * �ı���Ϣ
 */
public class NewsMessage extends BaseMessage {
	// ͼ����Ϣ����������Ϊ10������
	private int ArticleCount;
	// ����ͼ����Ϣ��Ϣ��Ĭ�ϵ�һ��itemΪ��ͼ
	private List<Article> Articles;

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
}
