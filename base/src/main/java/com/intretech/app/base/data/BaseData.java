package com.intretech.app.base.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by yq06171 on 2018/7/4.
 */

public class BaseData {
    public String toJson() {
        Gson gson2 = new GsonBuilder().create();

        String obj2 = gson2.toJson(this);
        return obj2;
    }
    public String toJsonDisableHtmlEscaping() {
        Gson gson2 = new GsonBuilder().disableHtmlEscaping().create();
        String obj2 = gson2.toJson(this);
        return obj2;
    }


    public String toJsonArray(List<?> list) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String obj = gson.toJson(list);
        return obj;
    }



    public static boolean checkDataIsJson(String value) {
        try {
            if(value==null){
                return false;
            }
            new JSONObject(value);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    static public Object jsonStrToObject(String jsonData,Class<?> cls ) {
        if (jsonData == null)
            return null;
        if (checkDataIsJson(jsonData) == false)
            return null;
        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonData, cls);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static List<?> jsonStrToList(String jsonData, TypeToken<?> typeToken) {
        if (jsonData == null)
            return null;
        if (!checkDataIsJsonList(jsonData))
            return null;
        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonData, typeToken.getType());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static boolean checkDataIsJsonList(String value) {
        try {
            if (value == null) {
                return false;
            }
            new JSONArray(value);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

}
