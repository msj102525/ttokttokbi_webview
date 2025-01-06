package com.jyes.www.util;

import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.UUID;
import org.apache.commons.codec.digest.DigestUtils;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * String Utility Class
 * 
 * @author ZeroOne
 * @version 1.0, 2009.06.25
 * 
 */

public class StringUtil {

	protected StringUtil() {
		
	}

	/**
	 * 입력값이 null인지를 체크하여 "" 을 돌려줌
	 * 
	 * @param str
	 * @return
	 */
	public static String nvl(String str) {
		String nv = "";
		try {
			if (str == null || str.length() == 0 || str.equals("null")
					|| str.equals("NULL"))
				nv = "";
			else
				nv = str;
		} catch (Exception e) {
			System.out.println("Utilb.nvl" + e.toString());
		}
		return nv;
	}
	
	public static String nvl(String src, String s) {
		src = nvl(src);
		if ("".equals(src))
			return nvl(s);
		else
			return src;
	}
	
	public static String getUrlParamsKeyValue(String url_params, String key) {
		String returnValue = "";
		try {
			if(url_params.indexOf(key)>-1) {
				if(url_params.length()>=(url_params.indexOf(key)+key.length()+1)) {
					returnValue = url_params.substring(url_params.indexOf(key)+key.length()+1).split("&")[0];
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	
	public static HashMap getUrlParamsHashMap(String url_params) {
		HashMap params = new HashMap();
		try {
			int length = url_params.split("&").length;
			System.out.println(length);
			for (int i = 0; i < length; i++) {
				int param_length = url_params.split("&")[i].split("=").length;
				String key = "";
				String value = "";
				if(param_length==2) {
					key = url_params.split("&")[i].split("=")[0];
					value = URLDecoder.decode(url_params.split("&")[i].split("=")[1], "utf-8");
					params.put(key, value);
				} else if(param_length==1) {
					key = url_params.split("&")[i].split("=")[0];
					params.put(key, "");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			params = new HashMap();
		}
		return params;
	}
	
	public static String getSHA256(String str) {
		String SHA = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(str.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			SHA = hexString.toString();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return SHA;
	}
	
	public static String createCustomUUID(String id) {
	    String ts = String.valueOf(System.currentTimeMillis());
	    String rand = UUID.randomUUID().toString();
	    System.out.println(id);
	    System.out.println(ts);
	    System.out.println(rand);
	    System.out.println(id+"-"+ts+"-"+rand);
	    return DigestUtils.shaHex(id+"-"+ts);
	}
	
	public static void main(String[] args) {
		System.out.println(createCustomUUID("01084092025"));
	}
	
	public static String getStartUrlPath(String url) {
		String returnValue = "";
		try {
			for (int i = 0; i < url.split("/").length; i++) {
				if(i>2) {
					returnValue += "/";
					returnValue += url.split("/")[i];
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	
	public static String getRamdomNumbers(int digit) {
		String returnValue = "";
		Random random = new Random();
		int data = 0;
		for (int i = 0; i < digit; i++) {
            data = random.nextInt(10);
            returnValue += data;
        }
		return returnValue;
	}
	
	public static String phoneNumberHyphenAdd(String num, String mask) {
		String formatNum = "";
		if (nvl(num).equals("")) {
			return formatNum;
		}
		num = num.replaceAll("-", "");
		if (num.length() == 11) {
			if (mask.equals("Y")) {
				formatNum = num.replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-****-$3");
			} else {
				formatNum = num.replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-$2-$3");
			}
		} else if (num.length() == 8) {
			formatNum = num.replaceAll("(\\d{4})(\\d{4})", "$1-$2");
		} else {
			if (num.indexOf("02") == 0) {
				if (mask.equals("Y")) {
					formatNum = num.replaceAll("(\\d{2})(\\d{3,4})(\\d{4})", "$1-****-$3");
				} else {
					formatNum = num.replaceAll("(\\d{2})(\\d{3,4})(\\d{4})", "$1-$2-$3");
				}
			} else {
				if (mask.equals("Y")) {
					formatNum = num.replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-****-$3");
				} else {
					formatNum = num.replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-$2-$3");
				}
			}
		}
		return formatNum;
	}
	
	public static String getMillisFromFormat(String format, String milis) {
		String returnValue = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			returnValue = sdf.format(Long.parseLong(milis))+"";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnValue;
	}
	
	public static String separatorTrim(String str, String separator, boolean valueTrim, boolean valueSort, int sort) {
		String returnValue = "";
		try {
			returnValue = separatorTrimExcute(str, separator, valueTrim);
			if(valueSort) {
				returnValue = separatorStringSortExcute(returnValue, separator, valueTrim, sort);
			}
		} catch(Exception e) {
			e.printStackTrace();
			returnValue = str;
		}
		return returnValue;
	}
	
	public static String separatorTrimExcute(String str, String separator, boolean valueTrim) {
		String returnValue = "";
		try {
			String temp = "";
			for (int i = 0; i < str.split("["+separator+"]").length; i++) {
				if(str.split("["+separator+"]")[i].equals("")) {
					continue;
				}
				if(!temp.equals("")) {
					temp += ""+separator+"";
				}
				if(valueTrim) {
					temp += str.split("["+separator+"]")[i].trim();
				} else {
					temp += str.split("["+separator+"]")[i];
				}
			}
			returnValue = temp;
		} catch(Exception e) {
			e.printStackTrace();
			returnValue = str;
		}
		return returnValue;
	}
	
	public static String separatorStringSortExcute(String str, String separator, boolean valueTrim, final int sort) {
		String returnValue = "";
		try {
			List<String> car = Arrays.asList(str.split("["+separator+"]"));
			Collections.sort(car, new Comparator<String>() {
				public int compare(String obj1, String obj2) {
					if(sort==0) {
						//오름차순
						return obj1.compareToIgnoreCase(obj2);
					} else {
						//내림차순
						return obj2.compareToIgnoreCase(obj1);
					}
				}
			});
			String temp = "";
			for (int i = 0; i < car.size(); i++) {
				if(!temp.equals("")) {
					temp += ""+separator+"";
				}
				if(valueTrim) {
					temp += car.get(i).trim();
				} else {
					temp += car.get(i);
				}
			}
			returnValue = temp;
		} catch(Exception e) {
			e.printStackTrace();
			returnValue = str;
		}
		return returnValue;
	}
	
	/**
	 * 폰번호 마스크 처리
	 * @param number
	 * @return
	 */
	public static String getPhonenumberMask(String number) {
		if(11==number.length()) {
			number = number.substring(0,3) + "XXXX" + number.substring(7,11);
		} else if(10==number.length()) {
			number = number.substring(0,3) + "XXX" + number.substring(6,10);
		}
		return number;
	}
	
	/**
	 * user-agent AppVersion 버전정보 가져오기
	 * @param user_agent
	 * @return
	 */
	public static String getAppVersion(String user_agent) throws Exception {
		int app_version = user_agent.indexOf("AppVersion");
		user_agent = user_agent.substring(app_version+11, user_agent.length()-1);
		user_agent = user_agent.split(";")[0];
		return user_agent;
	}
	
	/**
	 * user-agent AppVersion 버전정보 비교하기
	 * @param app_version
	 * @return
	 */
	public static int getAppVersionE(String app_version, String app_version_e) {
		int compare = 0;
		try {
			int app_version_e1 = 0;
			int app_version_e2 = 0;
			int app_version_e3 = 0;
			if(app_version.split("[.]").length==3) {
				app_version_e1 = Integer.parseInt(app_version_e.split("[.]")[0]);
				app_version_e2 = Integer.parseInt(app_version_e.split("[.]")[1]);
				app_version_e3 = Integer.parseInt(app_version_e.split("[.]")[2]);
			}
			int app_version1 = 0;
			int app_version2 = 0;
			int app_version3 = 0;
			if(app_version.split("[.]").length==3) {
				app_version1 = Integer.parseInt(app_version.split("[.]")[0]);
				app_version2 = Integer.parseInt(app_version.split("[.]")[1]);
				app_version3 = Integer.parseInt(app_version.split("[.]")[2]);
			}
			if(app_version.equals(app_version_e)) {
				compare = 0;
			} else {
				if(app_version_e1>app_version1) {
					compare = 1;
				} else if(app_version_e1<app_version1) {
					compare = -1;
				} else if(app_version_e1==app_version1) {
					if(app_version_e2>app_version2) {
						compare = 1;
					} else if(app_version_e2<app_version2) {
						compare = -1;
					} else if(app_version_e2==app_version2) {
						if(app_version_e3>app_version3) {
							compare = 1;
						} else if(app_version_e3<app_version3) {
							compare = -1;
						} else if(app_version_e3==app_version3) {
							compare = 0;
						}
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return compare;
	}
	
}