package com.netty.json;

import com.netty.util.JsonUtil;
import lombok.Data;

@Data
public class JsonMsg {
    private int id;
    private String context;
    public String convertToJson(){
        return JsonUtil.pojoToJson(this);
    }
    public static JsonMsg parseFromJson(String json) {
        return JsonUtil.jsonToPojo(json, JsonMsg.class);
    }
}
