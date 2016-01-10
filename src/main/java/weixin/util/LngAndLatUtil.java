package weixin.util;

/**
 * Created by XIANGYANG on 2015-12-28.
 */
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import net.sf.json.JSONObject;

public class LngAndLatUtil {

    /*public static Map<String,Double> getLngAndLat(String address){
        Map<String,Double> map=new HashMap<String, Double>();
        String url = "http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=json&ak=你自己的ak值";
        String json = loadJSON(url);
        JSONObject obj = JSONObject.fromObject(json);
        if(obj.get("status").toString().equals("0")){
            double lng=obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
            double lat=obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
            map.put("lng", lng);
            map.put("lat", lat);
            //System.out.println("经度："+lng+"---纬度："+lat);
        }else{
            //System.out.println("未找到相匹配的经纬度！");
        }
        return map;
    }*/

    public static String  getAddress( double lng,double lat){
        Map<String,String> map=new HashMap<String, String>();
        String url = "http://api.map.baidu.com/geocoder/v2/?location="+lat+","+lng+"&output=json&ak=eFjCIosSXiHAMWtmnLPjshWG";
        String json = loadJSON(url);
        JSONObject obj = JSONObject.fromObject(json);
        String city="";
        if(obj.get("status").toString().equals("0")){
            city=obj.getJSONObject("result").getJSONObject("addressComponent").getString("city");
            String district=obj.getJSONObject("result").getJSONObject("addressComponent").getString("district");
            map.put("city", city);
            map.put("district", district);
            //System.out.println("经度："+lng+"---纬度："+lat);
        }else{
            //System.out.println("未找到相匹配的经纬度！");
        }
        return city;
    }

    public static String loadJSON (String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return json.toString();
    }

    public static void main(String[] args) {
//        String jingwei="120.472808,36.116921";
//        Map<String,String> retMap= LngAndLatUtil.getAddress(120.472808,36.116921);

    }
}
/*把代码中的ak值（红色字部分）更改为你自己的ak值，在百度地图API中注册一下就有。
        调用方式：
        Map<String,Double> map=LngAndLatUtil.getLngAndLat("上海市黄浦区六合路");
        System.out.println("经度："+map.get("lng")+"---纬度："+map.get("lat"));*/
