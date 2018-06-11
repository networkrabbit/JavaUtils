package com.xly.utils;/**
 * @author DELL
 * @date 2018/5/28
 */

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author DELL
 * @date 2018/5/28 18:20
 **/
public class GetIPUtils {
  /**
   * 获取客户端ip地址
   *
   * @param request HttpServletRequest 对象
   * @return ip地址
   */
  public static String getIpAddr(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");
    if (ipNotExist(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ipNotExist(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ipNotExist(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ipNotExist(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ipNotExist(ip)) {
      ip = request.getRemoteAddr();
    }
    //"***.***.***.***".length() = 15
    int ipMaxLen = 15;
    //对于通过多个代理的情况，第一个IP为客户端真实IP
    if (ip != null && ip.length() > ipMaxLen) {
      ip = ip.split(",")[0];
    }
    return ip;
  }

  private static Boolean ipNotExist(String ip) {
    // 未获取标识
    String unKnow = "unknown";
    return !(ip != null && ip.length() != 0 && !unKnow.equalsIgnoreCase(ip));
  }
}
