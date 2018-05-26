package com.fangcm.common.utils;

import cn.hutool.http.HttpUtil;
import com.fangcm.common.entity.IpLocate;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by FangCM on 2018/5/24.
 */
public class IpInfoUtil {
    private static final Logger logger = LoggerFactory.getLogger(IpInfoUtil.class);

    /**
     * Mob IP查询接口
     */
    private final static String GET_IP_INFO = "http://apicloud.mob.com/ip/query?key=appkey&ip=";

    /**
     * Mob IP查询接口
     */
    private final static String GET_IP_WEATHER = "http://apicloud.mob.com/v1/weather/ip?key=appkey&ip=";

    /**
     * 获取客户端IP地址
     *
     * @param request 请求
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ip = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    logger.info("Unknown Host !", e);
                }
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    /**
     * 获取IP返回地理天气信息
     *
     * @param ip ip地址
     * @return
     */
    public static String getIpInfo(String ip) {

        if (StringUtils.isNotBlank(ip)) {
            String url = GET_IP_WEATHER + ip;
            String result = HttpUtil.get(url);
            return result;
        }
        return null;
    }

    /**
     * 获取IP返回地理信息
     *
     * @param ip ip地址
     * @return
     */
    public static String getIpCity(String ip) {
        if (null != ip) {
            String url = GET_IP_INFO + ip;
            String result = "未知";
            try {
                String json = HttpUtil.get(url, 3000);
                IpLocate locate = new Gson().fromJson(json, IpLocate.class);
                if (("200").equals(locate.getRetCode())) {
                    if (StringUtils.isNotBlank(locate.getResult().getProvince())) {
                        result = locate.getResult().getProvince() + " " + locate.getResult().getCity();
                    } else {
                        result = locate.getResult().getCountry();
                    }
                }
            } catch (Exception e) {
                logger.info("获取IP信息失败");
            }
            return result;
        }
        return null;
    }

    public static void main(String[] args) {
        logger.info(getIpInfo("171.88.85.176"));
    }

}
