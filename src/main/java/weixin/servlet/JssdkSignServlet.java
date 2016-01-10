package weixin.servlet;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weixin.model.pojo.JsapiTicket;
import weixin.model.pojo.Token;
import weixin.util.AdvancedUtil;
import weixin.util.CommonUtil;
import weixin.util.SignUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by XIANGYANG on 2015-8-1.
 * 授权后的回调请求处理
 */
public class JssdkSignServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(JssdkSignServlet.class);
    private static final long serialVersionUID = -1847238807216447030L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("gb2312");
        response.setCharacterEncoding("gb2312");
        String sourceReqUrl=request.getParameter("sourceReqUrl");
        logger.info("*********sourceReqUrl:" + sourceReqUrl);

        Token token = CommonUtil.getToken("wxb67cf3f14d6861e4", "90371de91616f7139074d6f4f51bb78d");//阳光的接口测试账号
//			Token token = CommonUtil.getToken("wx3267506118bc9677", "cfb4e1a6828a766b397b2ef1a43310c5");//青岛融商
        // 网页授权接口访问凭证
        String accessToken = token.getAccessToken();
        logger.info("*********accessToken:" + accessToken);
        JsapiTicket jsapiTicket = CommonUtil.getJsapiTicket(accessToken);//阳光的接口测试账号

        Map<String, String> retMap=SignUtil.sign4Jssdk(sourceReqUrl, jsapiTicket.getTicket());
        JSONObject jsonObject = JSONObject.fromObject(retMap);
        String jsonString=jsonObject.toString();
        response.getWriter().write(jsonObject.toString());
    }
}
