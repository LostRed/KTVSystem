package com.lostred.ktv.util;

import net.sf.json.JSONObject;

/**
 * Json工具
 */
public class JsonUtil {
    /**
     * 将普通对象转换为Json对象
     *
     * @param object 普通对象
     * @return Json对象
     */
    public static String toJsonString(Object object) {
        return JSONObject.fromObject(object).toString();
    }

    /**
     * 将Json字符串转换为Json对象
     *
     * @param jsonString Json字符串
     * @return Json对象
     */
    public static JSONObject toJsonObject(String jsonString) {
        return JSONObject.fromObject(jsonString);
    }
}
