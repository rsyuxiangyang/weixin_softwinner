package weixin.model.pojo;

/**
 * Created by XIANGYANG on 2015-8-2.
 * ƾ֤
 */
public class Token {
	// �ӿڷ���ƾ֤
	private String accessToken;
	// ƾ֤��Ч�ڣ���λ����
	private int expiresIn;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}