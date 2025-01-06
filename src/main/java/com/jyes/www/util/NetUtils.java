package com.jyes.www.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class NetUtils {
	
	public static String getClientIp(HttpServletRequest request) {
		String returnValue = "";
		try {
			returnValue = request.getHeader("X-Forwarded-For");
			if (returnValue == null || returnValue.length() == 0 || "unknown".equalsIgnoreCase(returnValue)) {
				returnValue = request.getHeader("Proxy-Client-IP");
			}
			if (returnValue == null || returnValue.length() == 0 || "unknown".equalsIgnoreCase(returnValue)) {
				returnValue = request.getHeader("WL-Proxy-Client-IP");
			}
			if (returnValue == null || returnValue.length() == 0 || "unknown".equalsIgnoreCase(returnValue)) {
				returnValue = request.getHeader("HTTP_CLIENT_IP");
			}
			if (returnValue == null || returnValue.length() == 0 || "unknown".equalsIgnoreCase(returnValue)) {
				returnValue = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (returnValue == null || returnValue.length() == 0 || "unknown".equalsIgnoreCase(returnValue)) {
				returnValue = request.getRemoteAddr();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	public static String getServerIp() {
		String hostAddr = "";
		try {
			Enumeration<NetworkInterface> nienum = NetworkInterface.getNetworkInterfaces();
			while (nienum.hasMoreElements()) {
				NetworkInterface ni = nienum.nextElement();
				Enumeration<InetAddress> kk = ni.getInetAddresses();
				while (kk.hasMoreElements()) {
					InetAddress inetAddress = kk.nextElement();
					if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
						hostAddr = inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return hostAddr;
	}

}
