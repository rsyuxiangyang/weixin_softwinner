package app;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by XIANGYANG on 2016-1-7.
 */
public interface EventProcessor {
    String process(HttpServletRequest request) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException;
}
