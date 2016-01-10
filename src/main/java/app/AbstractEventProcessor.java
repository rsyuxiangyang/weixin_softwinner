package app;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
/**
 * Created by XIANGYANG on 2016-1-7.
 */
public abstract class AbstractEventProcessor implements EventProcessor {
    public  abstract String process(HttpServletRequest request) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException;
}
