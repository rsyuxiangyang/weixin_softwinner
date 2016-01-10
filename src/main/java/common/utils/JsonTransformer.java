package common.utils;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import spark.ResponseTransformer;

@Component
public class JsonTransformer implements ResponseTransformer {

    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }

}
