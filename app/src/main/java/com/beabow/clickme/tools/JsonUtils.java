package com.beabow.clickme.tools;

import com.beabow.clickme.domain.BaseJson;
import com.beabow.clickme.domain.BaseJsonList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * json管理
 *
 * @author Administrator
 */
public class JsonUtils {
    private static Gson gson = new Gson();

    private JsonUtils() {
    }

    /**
     * 转换json中data字段是对象
     * @param json
     * @param clazz 泛型
     * @return
     */
    public static BaseJson toDataObj(String json, Class clazz) {
        Type objectType = type(BaseJson.class, clazz);
        return gson.fromJson(json, objectType);
    }

    /**
     * 转换Json中data字段是list集合
     *
     * @param json
     * @param clazz
     * @return
     */
    public static BaseJsonList toDataList(String json, Class clazz) {
        Type objectType = type(BaseJsonList.class, clazz);
        return gson.fromJson(json, objectType);
    }

    /**
     * 转为参数化类型
     */
    public static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }
            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
}
