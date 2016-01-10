package app.guangda.processor;

import app.AbstractEventProcessor;
import app.guangda.service.GuangDaCoreService;
import com.thoughtworks.xstream.converters.ConversionException;
import common.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weixin.model.message.resp.Article;
import weixin.model.message.resp.NewsMessage;
import weixin.model.message.resp.TextMessage;
import weixin.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by XIANGYANG on 2016-1-7.
 */

public class GuangDaEventProcessor extends AbstractEventProcessor {
    private static Logger logger = LoggerFactory.getLogger(GuangDaEventProcessor.class);
    private static GuangDaCoreService guangDaCoreService = SpringUtils.getBean("guangDaCoreService");

    public String process(HttpServletRequest request) throws ClassNotFoundException,ConversionException, InstantiationException, IllegalAccessException, IOException {
        // xml格式的消息数据
        String respXml = null;
        try {
            // 调用parseXml方法解析请求消息
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 发送方帐号
            String fromUserName = requestMap.get("FromUserName");
            // 开发者微信号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");

            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            // 事件推送
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    String eventKey = requestMap.get("EventKey");
                    logger.info("关注事件：（"+"EventKey:"+eventKey+";fromUserName:"+fromUserName);

                    try{
                        guangDaCoreService.subscribe(requestMap);
                    }catch (Exception e){
                        logger.error("关注事件处理异常",e);
                    }


                    Article article = new Article();
                    article.setTitle("感谢您的关注");
//                    article.setDescription("开源中国社区成立于2008年8月，是目前中国最大的开源技术社区。\n\n开源中国的目的是为中国的IT技术人员提供一个全面的、快捷更新的用来检索开源软件以及交流开源经验的平台。\n\n经过不断的改进,目前开源中国社区已经形成了由开源软件库、代码分享、资讯、讨论区和博客等几大频道内容。");
//						article.setPicUrl("");
                    article.setPicUrl("http://yxyhsk.6655.la/weixin/images/guangda/subscribe.jpg");
//                    article.setUrl("http://v.syscenter.net/index.php?g=Wap&m=Index&a=content&token=ymhkkc1447548531&id=24&wecha_id=outJOt3tPneL8RbjzDjWslLUqWIk&from=singlemessage&isappinstalled=0");
                    article.setUrl("http://yxyhsk.6655.la/weixin/UI/app/thankSubscribe.html");
                    List<Article> articleList = new ArrayList<Article>();
                    articleList.add(article);
                    // 创建图文消息
                    NewsMessage newsMessage = new NewsMessage();
                    newsMessage.setToUserName(fromUserName);
                    newsMessage.setFromUserName(toUserName);
                    newsMessage.setCreateTime(new Date().getTime());
                    newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                    newsMessage.setArticleCount(articleList.size());
                    newsMessage.setArticles(articleList);
                    respXml = MessageUtil.messageToXml(newsMessage);
                }
                // 取消订阅
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    logger.info("取消关注事件：（"+"fromUserName:"+fromUserName+")");
                    try{
                        guangDaCoreService.unSubscribe(requestMap);
                    }catch (Exception e){
                        logger.error("取消关注事件处理异常",e);
                    }

                    // TODO 暂不做处理
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // 事件KEY值，与创建菜单时的key值对应
                    String eventKey = requestMap.get("EventKey");
                    // 根据key值判断用户点击的按钮
                    if (eventKey.equals("shuhuabuluo")) {
                        Article article = new Article();
                        article.setTitle("书画部落官方粉丝社区");
//						article.setDescription("开源中国社区成立于2008年8月，是目前中国最大的开源技术社区。\n\n开源中国的目的是为中国的IT技术人员提供一个全面的、快捷更新的用来检索开源软件以及交流开源经验的平台。\n\n经过不断的改进,目前开源中国社区已经形成了由开源软件库、代码分享、资讯、讨论区和博客等几大频道内容。");
                        article.setPicUrl("http://yxyhsk.6655.la/weixin/images/guangda/shuhuabuluo.jpg");
                        article.setUrl("http://s.p.qq.com/pub/jump?d=AAAXoSIZ");
//						article.setUrl("http://v.syscenter.net/index.php?g=Wap&m=Forum&a=index&&token=ymhkkc1447548531&wecha_id=oPsaFxBMBWoswK0KEBoPsTovM37Q");
                        List<Article> articleList = new ArrayList<Article>();
                        articleList.add(article);
                        // 创建图文消息
                        NewsMessage newsMessage = new NewsMessage();
                        newsMessage.setToUserName(fromUserName);
                        newsMessage.setFromUserName(toUserName);
                        newsMessage.setCreateTime(new Date().getTime());
                        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                        newsMessage.setArticleCount(articleList.size());
                        newsMessage.setArticles(articleList);
                        respXml = MessageUtil.messageToXml(newsMessage);
                    } else if (eventKey.equals("iteye")) {
                        textMessage.setContent("ITeye即创办于2003年9月的JavaEye,从最初的以讨论Java技术为主的技术论坛，已经逐渐发展成为涵盖整个软件开发领域的综合性网站。\n\nhttp://www.iteye.com");
                        respXml = MessageUtil.messageToXml(textMessage);
                    }
                }else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW)){
                    String eventKey = requestMap.get("EventKey");
                    logger.info("eventKey:"+eventKey);

                }
            }// 当用户发消息时
            else {
                textMessage.setContent("青岛融商感谢您的关注~");
                respXml = MessageUtil.messageToXml(textMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respXml;
    }
}
