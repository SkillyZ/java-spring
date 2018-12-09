package Common;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    public static String objectConvertJson(Object object)
    {
        ObjectMapper mapper = new ObjectMapper();
        String rs;
        try {
            rs = mapper.writeValueAsString(object);
        } catch (Exception e) {
            return "[]";
        }
        return rs;
    }
}
