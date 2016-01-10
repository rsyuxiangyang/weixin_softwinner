package weixin.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weixin.util.LngAndLatUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetAddressServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(GetAddressServlet.class);
    private static final long serialVersionUID = -1847238807216447030L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String lng = request.getParameter("lng");
        String lat = request.getParameter("lat");

        logger.info("*********lng:" + lng+"*********lat:" + lat);
double lngD=Double.parseDouble(lng);
double latD=Double.parseDouble(lat);
       String city=LngAndLatUtil.getAddress(lngD,latD);
        logger.info("*********city:" + city);
        response.getWriter().write(city);
    }
}
