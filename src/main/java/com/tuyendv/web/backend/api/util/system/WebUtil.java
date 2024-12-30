package com.tuyendv.web.backend.api.util.system;

import com.tuyendv.web.backend.api.common.Constants;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.tuyendv.web.backend.api.common.GlobalEnum.EXTERNAL_IP;
import static com.tuyendv.web.backend.api.common.GlobalEnum.INTERNAL_IP;

/**
 * @author khoinm
 * util for web from practical project
 **/
public class WebUtil {

    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";

    private static final String X_REQUESTED_WITH = "X-Requested-With";

    private static final String CONTENT_TYPE = "Content-type";

    private static final String CONTENT_TYPE_JSON = "application/json";

    public static boolean isAsync(HttpServletRequest request) {
        return XML_HTTP_REQUEST.equals(request.getHeader(X_REQUESTED_WITH));
    }

    public static boolean isContentTypeJson(HttpServletRequest request) {
        return request.getHeader(CONTENT_TYPE).contains(CONTENT_TYPE_JSON);
    }

    public static String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        // In case the X-Forwarded-For header contains multiple IPs, take the first one
        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[ 0 ];
        }
        return ipAddress;
    }

    public static String isInternalIP(String ipAddress) {
        try {
            InetAddress addr = InetAddress.getByName(ipAddress);
            if (addr.isSiteLocalAddress()) {
                return INTERNAL_IP.toString();
            } else {
                return EXTERNAL_IP.toString();
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

    }

    public static String getUserAgentDevice(HttpServletRequest request) {
        String userAgentString = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();

        if (operatingSystem.getDeviceType().equals(DeviceType.MOBILE)) {
            return "Mobile";
        } else {
            return "Web";
        }
    }

    public static String getUserAgentOs(HttpServletRequest request) {
        String userAgentString = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();

        return operatingSystem.getName();
    }

    public static String getArgumentsAsString(Object[] args) {
        if (args != null && args.length > 0) {
            return Arrays.stream(args).map(Object::toString).collect(Collectors.joining(", "));
        }
        return "No arguments";
    }

    public static String getBrowserInfo(HttpServletRequest request) {
        String userAgentString = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
        String browser = null;

        if (! StringUtils.equals(userAgent.getBrowser().getName(), Constants.UNKNOWN)) {
            browser = userAgent.getBrowser().getName();
        }

        return browser;
    }

}
