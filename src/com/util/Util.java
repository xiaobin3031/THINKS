package com.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
/**
 * 通用�?
 * @author xuweibin
 *
 */
public class Util {

	//用来为明文密码加密和解密
	private static final int INDEX1 = 7;
	private static final int INDEX2 = 19;
	private static final int INDEX3 = 27;
//	private static final String SUPP = "0123456789abcdef";
	
	private static final String regExp_DOUBLE = "^[-]?[0-9]*[.]?[0-9]*";
	private static final String regExp_LessOne = "^[-]?[0]+[.][0-9]*";
	private static final String regExp_INTEGER = "^[-]?[0-9]*";
	/**
	 * 判断obj是否为空
	 * @param obj
	 * @return
	 */
	public static boolean isNotNull(Object obj){
		if(obj == null) return false;
		return ((String)obj).trim().length() > 0;
	}
	
	public static Object ifObjNull(Object obj,Object ret){
		if(isNotNull(obj)) return obj;
		return ret;
	}
	
	/**
	 * 获取字符串的字节�?
	 * @param str  输入的字符串
	 * @param charset  字符串编�?
	 * @return
	 */
/*	private static byte[] getBytes(String str,String charset){
		if (!isNotNull(charset)) {
			return str.getBytes();
		}
		try {
			return str.getBytes(charset);
		} catch (Exception e) {
			throw new RuntimeException("MD5签名过程中出现错�?指定的编码集不对,您目前指定的编码集是:" + charset);
		}
	}
	*/
	/**
	 * 验证密文形式的密�?
	 * @param input  输入的密文密�?
	 * @param database  数据库中的密�?
	 * @return
	 */
	public static boolean chkPassword(String input,String database){
		if(isNotNull(database) && isNotNull(input)){
			if(input.substring(0,INDEX1).equals(database.substring(0,INDEX1)) 
					&& input.substring(INDEX2,INDEX3).equals(database.substring(INDEX2,INDEX3))){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否为数�?
	 * @param value
	 * @return
	 */
	public static boolean isFloat(String value){
		return value.matches(regExp_DOUBLE) && !(Float.parseFloat(value)+"").equals("Infinity") && !(Float.parseFloat(value)+"").equals("-Infinity");
	}
	
	public static boolean isDouble(String value){
		return value.matches(regExp_DOUBLE) && !(Double.parseDouble(value)+"").equals("Infinity") && !(Double.parseDouble(value)+"").equals("-Infinity");
	}
	
	public static boolean isLessOne(String value){
		return value.matches(regExp_LessOne);
	}
	
	public static boolean isInteger(String value){
		return value.matches(regExp_INTEGER) && !(Integer.parseInt(value)+"").equals("Infinity") && !(Integer.parseInt(value)+"").equals("-Infinity");
	}
	
	public static boolean isLong(String value){
		return value.matches(regExp_INTEGER) && !(Long.parseLong(value)+"").equals("Infinity") && !(Long.parseLong(value)+"").equals("-Infinity");
	}
	
	/**
	 * 判断�?��字符串是否为�?
	 * @param value
	 * @return
	 */
	public static boolean isTrue(String value){
		if(isNotNull(value)){
			if(value.equalsIgnoreCase("t") || Boolean.parseBoolean(value) || value.equals("1")) return true;
		}
		return false;
	}
	/**
	 * 获取JSON的格�?
	 * @param resultMsg  返回提示信息
	 * @param code  代码
	 * @param success  是否正确
	 * @param map  自定义键值对
	 * @return
	 */
	public static String getJson(String resultMsg,int code,boolean success,HashMap<String,Object> map){
		StringBuffer json = new StringBuffer();
		json.append("{");
		json.append("\"resultMsg\":\""+resultMsg+"\",");
		json.append("\"code\":"+code+",");
		json.append("\"success\":"+success);
		if(map != null && map.size() > 0){
			Set<String> keys = map.keySet();
			for(String key : keys){
				json.append(",\""+key+"\":");
				if(map.get(key) instanceof String){
					json.append("\""+map.get(key)+"\"");
				}else {
					json.append(map.get(key));
				}
			}
		}
		json.append("}");
		return json.toString();
	}
	
	/**
	 * 获取JSON的格�?
	 * @param resultMsg  返回提示信息
	 * @param code  代码
	 * @param success  是否正确
	 * @param keyAndValue  自定义键值对，由字符串组�?
	 * @return
	 */
	public static String getJson(String resultMsg,int code,boolean success){
		return getJson(resultMsg,code,success,"");
	}
	
	public static String getJson(String resultMsg,int code,boolean success,String keyAndValue){
		StringBuffer json = new StringBuffer();
		json.append("{");
		json.append("\"resultMsg\":\""+resultMsg+"\",");
		json.append("\"code\":"+code+",");
		json.append("\"success\":"+success);
		if(isNotNull(keyAndValue)){
			String[] keys = keyAndValue.split(",");
			for(int i=0;i<keys.length;i++){
				if(isNotNull(keys[i])){
					json.append(",\""+keys[i]+"\":");
					i++;
					if(i<keys.length){
						json.append("\""+keys[i]+"\"");
					}
				}else{
					i++;
				}
			}
		}
		json.append("}");
		return json.toString();
	}
	
	public static String getCommJson(boolean success){
		StringBuffer json = new StringBuffer();
		json.append("{");
		json.append("\"resultMsg\":\"\",");
		if(success){
			json.append("\"code\":0,");
			json.append("\"success\":"+success);
		}else{
			json.append("\"code\":-1,");
			json.append("\"success\":"+success);
		}
		json.append("}");
		return json.toString();
	}
	
	/**
	 * 获取JSON数组
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getJsonArray(List<?> list){
		StringBuffer json = new StringBuffer();
		if(isNotNull(list)){
			for(HashMap<String,String> map : (List<HashMap<String,String>>)list){
				json.append("{");
				for(String key : map.keySet()){
					json.append("\""+key+"\":"+"\""+map.get(key)+"\",");
				}
				json = new StringBuffer(json.substring(0,json.length()-1));
				json.append("},");
			}
		}
		return "["+json.substring(0,json.length()-1)+"]";
	}
	
	/**
	 * 格式化Double位数
	 * @param value  要格式的�?
	 * @param digits  格式化后保留的位�?
	 * @return  如果格式化后的�?不是double,返回原�?，否则，返回新�?
	 */
	public static double formatDouble(double value,int digits){
		String reg = "%."+digits+"d";
		String result = String.format(reg);
		if(isDouble(result)){
			return Double.parseDouble(result);
		}
		return value;
	}
	
	/**
	 * 格式化float位数
	 * @param value  要格式的�?
	 * @param digits  格式化后保留的位�?
	 * @return  如果格式化后的�?不是float,返回原�?，否则，返回新�?
	 */
	public static float formatFloat(float value,int digits){
		String reg = "%."+digits+"f";
		String result = String.format(reg);
		if(isFloat(result)){
			return Float.parseFloat(result);
		}
		return value;
	}
	
	/**
	 * 返回格式化日�?
	 * @param format  格式化参�?
	 * @return
	 */
	public static String getFormatDate(String format,Date date){
		if(date != null){
			return new SimpleDateFormat(format).format(date);
		}
		return new SimpleDateFormat(format).format(new Date());
	}
	
	/**
	 * 获取唯一的ID�?
	 * @return
	 */
	public static String getUuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}