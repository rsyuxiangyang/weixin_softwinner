package weixin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weixin.model.menu.*;
import weixin.model.pojo.Token;

/**
 * 菜单管理器类
 * 
 * @author liufeng
 * @date 2013-10-17
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	/**
	 * 定义菜单结构
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		ClickButton btn11 = new ClickButton();
		btn11.setName("书画部落");
		btn11.setType("click");
		btn11.setKey("shuhuabuluo");

		ClickButton btn12 = new ClickButton();
		btn12.setName("诗词部落");
		btn12.setType("click");
		btn12.setKey("shuhuabuluo");

		ViewButton btn13 = new ViewButton();
		btn13.setName("其他");
		btn13.setType("view");
		btn13.setUrl("http://www.iteye.com");

        ViewButton btn21 = new ViewButton();
        btn21.setName("唐双宁");
        btn21.setType("view");
        btn21.setUrl("http://show1.syscenter.net/v-U61164538F");

        ViewButton btn22 = new ViewButton();
        btn22.setName("于向阳");
        btn22.setType("view");
//        String strUrlEncoder=CommonUtil.urlEncodeUTF8("http://yxyhsk.6655.la/weixin/oauthServlet");
//        String strUrlEncoder=CommonUtil.urlEncodeUTF8("http://yxyhsk.6655.la/weixin/oauthTicketServlet");
        String strUrlEncoder=CommonUtil.urlEncodeUTF8("http://yxyhsk.6655.la/weixin/wx/getQRcode?deptCode=88&token=guangda");

        btn22.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb67cf3f14d6861e4&redirect_uri="+strUrlEncoder+"&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect");

		ViewButton btn31 = new ViewButton();
        btn31.setName("JSSDK测试");
        btn31.setType("view");
        btn31.setUrl("http://yxyhsk.6655.la/weixin/UI/weixin/success.html");

		ViewButton btn32 = new ViewButton();
        btn32.setName("PFMobile测试");
        btn32.setType("view");
        btn32.setUrl("http://yxyhsk.6655.la/pf");

		ViewButton btn33 = new ViewButton();
        btn33.setName("首页定位");
        btn33.setType("view");
        btn33.setUrl("http://yxyhsk.6655.la/weixin/UI/weixin/index.xhtml");

		ViewButton btn34 = new ViewButton();
        btn34.setName("外卖测试");
        btn34.setType("view");
        String strUrlEncoder2=CommonUtil.urlEncodeUTF8("http://yxyhsk.6655.la/x5/UI2/index.html");
        btn34.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb67cf3f14d6861e4&redirect_uri="+strUrlEncoder2+"&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect");

		ViewButton btn35 = new ViewButton();
        btn35.setName("WEX5测试JSSDK");
        btn35.setType("view");
        btn35.setUrl("http://yxyhsk.6655.la/x52/UI2/index.html");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("精品栏目");
		mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("名人名家");
		mainBtn2.setSub_button(new Button[] { btn21,btn22 });

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("测试");
		mainBtn3.setSub_button(new Button[] { btn31,btn32,btn33,btn34,btn35 });

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}

	public static void main(String[] args) {
		// 第三方用户唯一凭证
		String appId = "wxb67cf3f14d6861e4";
		// 第三方用户唯一凭证密钥
		String appSecret = "90371de91616f7139074d6f4f51bb78d";

		// 调用接口获取凭证
		Token token = CommonUtil.getToken(appId, appSecret);

		if (null != token) {
			// 创建菜单
			boolean result = MenuUtil.createMenu(getMenu(), token.getAccessToken());

			// 判断菜单创建结果
			if (result)
				log.info("菜单创建成功！");
			else
				log.info("菜单创建失败！");
		}
	}
}
