package com.netty.util;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.GsonBuilder;

public class JsonUtil {
    static GsonBuilder gb=new GsonBuilder();
    static {
        gb.disableHtmlEscaping();
    }
    //序列化
    public static String pojoToJson(Object obj){
        String json=gb.create().toJson(obj);
        return json;
    }
    //反序列化
    public static<T> T jsonToPojo(String json,Class<T> tClass){
        T t= JSONObject.parseObject(json, tClass);
        return t;
    }
}
