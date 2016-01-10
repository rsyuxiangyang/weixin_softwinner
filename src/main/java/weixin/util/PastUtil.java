package weixin.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import net.sf.json.JSONObject;
import weixin.model.pojo.Token;

/**
 * Created by XIANGYANG on 2015-8-2.
 * ???????????
 */
public class PastUtil {
    public static Token token = null;
    public static String time = null;
    public static String jsapi_ticket = null;

    public static void main(String[] args) {
//        Map<String,String> strJson=PastUtil.getParam("wxb67cf3f14d6861e4","90371de91616f7139074d6f4f51bb78d");
    }

    public static Map<String,String> getParam(String appId, String appSecret,String requestUrl,String queryString) {
        if (token == null) {
            token = CommonUtil.getToken(appId, appSecret);
            jsapi_ticket = CommonUtil.getJsApiTicket(token.getAccessToken());
            time = getTime();
        } else {
            if (!time.substring(0, 13).equals(getTime().substring(0, 13))) {
                token = null;
                token = CommonUtil.getToken(appId, appSecret);
                jsapi_ticket = CommonUtil.getJsApiTicket(token.getAccessToken());
                time = getTime();
            }
        }
        String url = getUrl(requestUrl,queryString);
        Map<String, String> params = sign(jsapi_ticket, url);
        params.put("appid", appId);
        JSONObject jsonObject = JSONObject.fromObject(params);
        String jsonStr = jsonObject.toString();
        Map<String,String> map=new HashMap<>();
        map.put("timestamp",jsonObject.getString("timestamp"));
        map.put("appid",jsonObject.getString("appid"));
        map.put("nonceStr",jsonObject.getString("nonceStr"));
        map.put("jsapi_ticket",jsonObject.getString("jsapi_ticket"));
        map.put("signature",jsonObject.getString("signature"));
        System.out.println(jsonStr);

        return map;
    }

    private static String getUrl(String requestUrl,String queryString) {
        String url = requestUrl;
        if (queryString!=null){
            url = requestUrl + "?" + queryString;
        }
        return url;
    }

    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String str;
        String signature = "";
        str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(str.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    public static String getTime() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(dt);
    }
}