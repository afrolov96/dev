package models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;


public class Converter {
    private String[] keys;
    private String[] values;

    public Converter(String params) {
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

    public void setKeys(String[] keys) {
        this.keys = keys;
    }

    public void setValues(String[] values) {
        this.values = values;
    }
}
