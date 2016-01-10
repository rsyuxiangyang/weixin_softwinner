package weixin.servlet;

import app.AbstractEventProcessor;
import common.utils.PropertyManager;
import net.sf.jxls.parser.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weixin.service.CoreService;
import weixin.util.SignUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by XIANGYANG on 2015-8-1.
 * 请求处理的核心类
 */
public class CoreServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(CoreServlet.class);
	private static final long serialVersionUID = 4440739483644821986L;

	/**
	 * 请求校验（确认请求来自微信服务器）
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		String token = request.getParameter("token");

		PrintWriter out = response.getWriter();
		// 请求校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature, timestamp, nonce,token)) {
			out.print(echostr);
		}
		out.close();
		out = null;
	}

	/**
	 * 处理微信服务器发来的消息
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
        String token=request.getParameter("token");

        // 调用核心事件处理类接收消息、处理消息
        Class txnClass = null;
        String respXml=null;
        try {
            txnClass = Class.forName( PropertyManager.getProperty("app.token.processor."+token));
        } catch (ClassNotFoundException e) {
            txnClass = null;
        }
        if (txnClass != null) {
            AbstractEventProcessor processor = null;
            try {
                processor = (AbstractEventProcessor) txnClass.newInstance();
                respXml = processor.process(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		// 响应消息
		PrintWriter out = response.getWriter();
		out.print(respXml);
		out.close();
	}
}
