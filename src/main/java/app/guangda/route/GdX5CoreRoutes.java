package app.guangda.route;

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

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by XIANGYANG on 2016-1-7.
 */

@Component
public class GdX5CoreRoutes implements RouteRegister {
    private static final Logger logger = LoggerFactory.getLogger(GdX5CoreRoutes.class);
    private static final String GET_PRODUCT = "/gd/X5/getProduct";

    @Override
    public void regist() {
        get(GET_PRODUCT,(request,response)->{
            return null;
        });
    }

}
