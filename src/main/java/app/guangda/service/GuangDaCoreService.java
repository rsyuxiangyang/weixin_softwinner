package app.guangda.service;

import app.guangda.constant.SubscribeType;
import app.guangda.repository.model.GdSubscribeUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by XIANGYANG on 2016-1-7.
 */
@Service
public class GuangDaCoreService {
    private static Logger logger = LoggerFactory.getLogger(GuangDaCoreService.class);
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    SubscribeUserService subscribeUserService;


    public void subscribe(Map<String, String> requestMap){
        // 发送方帐号（一个OpenID）
        String fromUserName = requestMap.get("FromUserName");
        // 开发者微信号
        String toUserName = requestMap.get("ToUserName");
        // 消息创建时间 （整型）
        String createTime = requestMap.get("CreateTime");
        // 消息类型
        String msgType = requestMap.get("MsgType");
        //普通关注该参数没有，只有扫描带参数二维码事件,事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
        String eventKey = requestMap.get("EventKey");
        //普通关注该参数没有，只有扫描二维码时才有，二维码的ticket，可用来换取二维码图片
        String ticket = requestMap.get("Ticket");

        GdSubscribeUser gdSubscribeUser=new GdSubscribeUser();
        gdSubscribeUser.setDeptCode(eventKey);
        gdSubscribeUser.setOpenid(fromUserName);
        gdSubscribeUser.setCreateTime(sdf.format(new Date(Integer.parseInt(createTime)*1000L)));
        if (subscribeUserService.isExistsRecord(gdSubscribeUser)){
            subscribeUserService.updateRecord(gdSubscribeUser, SubscribeType.SUBSCRIBE);
        }else{
            subscribeUserService.insertRecord(gdSubscribeUser);
        }
    }

    public void unSubscribe(Map<String, String> requestMap){
        // 发送方帐号（一个OpenID）
        String fromUserName = requestMap.get("FromUserName");
        // 开发者微信号
        String toUserName = requestMap.get("ToUserName");
        // 消息创建时间 （整型）
        String createTime = requestMap.get("CreateTime");
        // 消息类型
        String msgType = requestMap.get("MsgType");

        GdSubscribeUser gdSubscribeUser=new GdSubscribeUser();
        gdSubscribeUser.setOpenid(fromUserName);
        gdSubscribeUser.setCreateTime(sdf.format(new Date(Integer.parseInt(createTime)*1000L)));
        if (subscribeUserService.isExistsRecord(gdSubscribeUser)){
            subscribeUserService.updateRecord(gdSubscribeUser, SubscribeType.UNSUBSCRIBE);
        }
    }
}
