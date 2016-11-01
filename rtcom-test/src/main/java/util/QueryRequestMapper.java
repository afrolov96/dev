package util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

public class QueryRequestMapper {

    private String surName;
    private String name;
    private String patronymic;
    private int cityId;
    private String carName;
    private String carNumber;
    private String carColor;
    private String carSize;

    public String getSurName() {
        return surName;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public int getCityId() {
        return cityId;
    }

    public String getCarName() {
        return carName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public String getCarColor() {
        return carColor;
    }

    public String getCarSize() {
        return carSize;
    }

    private QueryRequestMapper(String params) {
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(params, new TypeToken<Map<String, String>>() {
        }.getType());
        surName = map.get("persons_surname");
        name = map.get("persons_name");
        patronymic = map.get("persons_patronymic");
        cityId = map.get("cities_id") != null ? new Integer(map.get("cities_id")).intValue() : 0;
        carName = map.get("cars_name");
        carNumber = map.get("cars_num");
        carColor = map.get("cars_color");
        carSize = map.get("cars_size");
    }

    public static QueryRequestMapper getPersonFindRequestParams(String params) {
        return new QueryRequestMapper(params);
    }
}
