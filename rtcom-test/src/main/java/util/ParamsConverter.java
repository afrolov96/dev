package util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;


public class ParamsConverter {
    private String[] keys;
    private String[] values;

    public ParamsConverter(String params) {
        Map<String, String>[] map = new Gson().fromJson(params, new TypeToken<Map<String, String>[]>() {
        }.getType());
        keys = new String[map.length];
        values = new String[map.length];

        if (map.length > 0) {
            for (int i = 0; i < map.length; i++) {
                for (Map.Entry<String, String> entry : map[i].entrySet()) {
                    keys[i] = entry.getKey().replace("_", ".");
                    values[i] = entry.getValue();
                }
            }
        }
    }

    public String[] getKeys() {
        return keys;
    }

    public String[] getValues() {
        return values;
    }
}
