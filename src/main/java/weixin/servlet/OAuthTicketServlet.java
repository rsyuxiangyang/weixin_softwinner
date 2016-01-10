package weixin.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weixin.model.pojo.SNSUserInfo;
import weixin.model.pojo.Token;
import weixin.model.pojo.WeixinOauth2Token;
import weixin.util.AdvancedUtil;
import weixin.util.CommonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by XIANGYANG on 2015-8-1.
 * 授权后的回调请求处理
 */
public class OAuthTicketServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(OAuthTicketServlet.class);
	private static final long serialVersionUID = -1847238807216447030L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("gb2312");
		response.setCharacterEncoding("gb2312");

		// 用户同意授权后，能获取到code
		String code = request.getParameter("code");

logger.info("*********code:"+code);
		// 用户同意授权
//		if (!"authdeny".equals(code)) {
		if (!"".equals(code)&&code!=null) {
			// 获取网页授权access_token
//			WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken("wxb67cf3f14d6861e4", "90371de91616f7139074d6f4f51bb78d", code);
			Token token = CommonUtil.getToken("wxb67cf3f14d6861e4", "90371de91616f7139074d6f4f51bb78d");//阳光的接口测试账号
//			Token token = CommonUtil.getToken("wx3267506118bc9677", "cfb4e1a6828a766b397b2ef1a43310c5");//青岛融商
			// 网页授权接口访问凭证
			String accessToken = token.getAccessToken();
            logger.info("*********accessToken:"+accessToken);
			/*// 用户标识
			String openId = weixinOauth2Token.getOpenId();
            logger.info("*********openId:"+openId);
			// 获取用户信息
			SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);

			// 设置要传递的参数
			request.setAttribute("snsUserInfo", snsUserInfo);*/
//            String ticket=AdvancedUtil.createPermanentQRCode(accessToken,6688);
            String ticket=AdvancedUtil.createPermanentQRCode(accessToken,123);
            logger.info("*********ticket:"+ticket);
            AdvancedUtil.getQRCode(ticket,"D:/",ticket);
		}
		// 跳转到index.jsp
//		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
}
