package cn.zhanx.project05.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpUtil {
    public IpUtil() {
    }

    public static long ip2long(String ipAddress) {
        long result = 0L;
        String[] ipAddressInArray = ipAddress.split("\\.");

        for (int i = 3; i >= 0; --i) {
            long ip = Long.parseLong(ipAddressInArray[3 - i]);
            result |= ip << i * 8;
        }

        return result;
    }

    public static String long2ip(long ip) {
        return (ip >> 24 & 255L) + "." + (ip >> 16 & 255L) + "." + (ip >> 8 & 255L) + "." + (ip & 255L);
    }

    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",");
            return index != -1 ? ip.substring(0, index) : ip;
        } else {
            ip = request.getHeader("X-Real-IP");
            return StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip) ? ip : request.getRemoteAddr();
        }
    }

    public static String getLocalIp() throws UnknownHostException {
        String ip = null;

        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return ip;
    }

    public static String getLocalIp4() {
        try {
            Enumeration en = NetworkInterface.getNetworkInterfaces();

            while (en.hasMoreElements()) {
                NetworkInterface i = (NetworkInterface) en.nextElement();
                Enumeration en2 = i.getInetAddresses();

                while (en2.hasMoreElements()) {
                    InetAddress addr = (InetAddress) en2.nextElement();
                    if (!addr.isLoopbackAddress() && addr instanceof Inet4Address) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return null;
    }

    public static String getHost() {
        String host = null;

        try {
            host = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException var2) {
            var2.printStackTrace();
        }

        return host;
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    public static boolean isInner(String ip) {
        String reg = "(10|172|192)\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})";
        Pattern p = Pattern.compile(reg);
        Matcher matcher = p.matcher(ip);
        return matcher.find();
    }
}

