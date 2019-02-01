package com.fangcm.common.rest;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by FangCM on 2018/5/24.
 */
public class ResponseUtil {
    private static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

    /**
     * 使用response输出JSON
     */
    public static void responseOutput(ServletResponse response, Map<String, Object> resultMap) {

        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(new Gson().toJson(resultMap));
        } catch (Exception e) {
            logger.error("out JSON error.", e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    public static Map<String, Object> resultMap(int code, String msg) {
        return resultMap(code, msg, null);
    }

    public static Map<String, Object> resultMap(int code, String msg, Object data) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", code);
        resultMap.put("message", msg);
        if (data != null) {
            resultMap.put("data", data);
        }
        return resultMap;
    }
}
