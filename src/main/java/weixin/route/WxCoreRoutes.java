package weixin.route;

import app.AbstractEventProcessor;
import common.spark_route.RouteRegister;
import common.utils.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import weixin.model.pojo.SNSUserInfo;
import weixin.model.pojo.Token;
import weixin.model.pojo.WeixinOauth2Token;
import weixin.util.AdvancedUtil;
import weixin.util.CommonUtil;
import weixin.util.LngAndLatUtil;
import weixin.util.SignUtil;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by XIANGYANG on 2016-1-7.
 */
@Component
public class WxCoreRoutes implements RouteRegister {
    private static final Logger logger = LoggerFactory.getLogger(WxCoreRoutes.class);
    private static final String GET_POST_CORE_HANDLE = "/wx/coreRoute";
    private static final String GET_WEBPAGE_OAUTH = "/wx/webPageOAuth";
    private static final String GET_QRCODE = "/wx/getQRcode/:token/:deptCode/:deptSceneId";
    private static final String GET_QRY_ADDRESS = "/wx/qryAddress";

    @Override
    public void regist() {
        /**
         * ����У�飨ȷ����������΢�ŷ�������
         */
        get(GET_POST_CORE_HANDLE,(request,response)->{
            // ΢�ż���ǩ��
            String signature =request.raw().getParameter("signature");
            // ʱ���
            String timestamp = request.raw().getParameter("timestamp");
            // �����
            String nonce = request.raw().getParameter("nonce");
            // ����ַ���
            String echostr = request.raw().getParameter("echostr");

            String token =request.raw().getParameter("token");

            // ����У�飬��У��ɹ���ԭ������echostr����ʾ����ɹ����������ʧ��
            if (SignUtil.checkSignature(signature, timestamp, nonce, token)) {
                return echostr;
            }
            return null;
        });

        /**
         * ����΢�ŷ�������������Ϣ
         */
        post(GET_POST_CORE_HANDLE, (request, response) -> {
            String token =request.raw().getParameter("token");

            // ���ú����¼������������Ϣ��������Ϣ
            Class txnClass = null;
            String respXml=null;
            try {
                txnClass = Class.forName( PropertyManager.getProperty("app.token.processor." + token));
            } catch (ClassNotFoundException e) {
                txnClass = null;
            }
            if (txnClass != null) {
                AbstractEventProcessor processor = null;
                try {
                    processor = (AbstractEventProcessor) txnClass.newInstance();
                    respXml = processor.process(request.raw());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return respXml;
        });

        /**
         * �������ö�ά������
         */
        get(GET_QRCODE, (request, response) -> {
            String strToken = request.params(":token");
            String deptCode = request.params(":deptCode");
            String deptSceneId = request.params(":deptSceneId");
            int sceneId = Integer.parseInt(deptSceneId);

            logger.info("*********deptCode:" + deptCode);

            String strAppId = PropertyManager.getProperty("appId." + strToken);
            String strSecret = PropertyManager.getProperty("secret." + strToken);
            Token token = CommonUtil.getToken(strAppId, strSecret);//����Ľӿڲ����˺�
//			Token token = CommonUtil.getToken("wx3267506118bc9677", "cfb4e1a6828a766b397b2ef1a43310c5");//�ൺ����
            String accessToken = token.getAccessToken();
            logger.info("*********accessToken:" + accessToken);
            String ticket = AdvancedUtil.createPermanentQRCode(accessToken, sceneId);
            logger.info("*********ticket:" + ticket);

            String realPath = PropertyManager.getProperty("QRcode.fileSavePath." + strToken);
            logger.info("*********realPath:" + realPath);
            AdvancedUtil.getQRCode(ticket, realPath, deptCode);
            response.type("application/json");
            String jsonStr = "{\"success\":\"success\"}";
            return jsonStr;
        });

        /**
         * ��ҳ��Ȩ��֤
         */
        get(GET_WEBPAGE_OAUTH, (request, response) -> {
            // �û�ͬ����Ȩ���ܻ�ȡ��code
            String code = request.raw().getParameter("code");
            logger.info("*********code:"+code);
            // �û�ͬ����Ȩ
            if (!"".equals(code)&&code!=null) {
                // ��ȡ��ҳ��Ȩaccess_token
                WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken("wxb67cf3f14d6861e4", "90371de91616f7139074d6f4f51bb78d", code);
                // ��ҳ��Ȩ�ӿڷ���ƾ֤
                String accessToken = weixinOauth2Token.getAccessToken();
                logger.info("*********accessToken:"+accessToken);
                // �û���ʶ
                String openId = weixinOauth2Token.getOpenId();
                logger.info("*********openId:"+openId);
                // ��ȡ�û���Ϣ
                SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);

                // ����Ҫ���ݵĲ���
                request.raw().setAttribute("snsUserInfo", snsUserInfo);
            }
            // ��ת��index.jsp
            request.raw().getRequestDispatcher("index.jsp").forward(request.raw(), response.raw());
            return null;
        });

        /**
         * ��ȡ����λ��
         */
        get(GET_QRY_ADDRESS, (request, response) -> {
            String lng = request.raw().getParameter("lng");
            String lat = request.raw().getParameter("lat");

            logger.info("*********lng:" + lng+"*********lat:" + lat);
            double lngD=Double.parseDouble(lng);
            double latD=Double.parseDouble(lat);
            String city= LngAndLatUtil.getAddress(lngD, latD);
            logger.info("*********city:" + city);
            return city;
        });
    }

}
