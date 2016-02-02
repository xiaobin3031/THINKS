package com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 操作SQL的通用方法
 * @author xuweibin
 *
 */
public class SqlUtil {

	/**
	 * 执行SQL方法
	 * @param sql
	 * @param map
	 * @return
	 */
	/*public static Object doSql(String sql,HashMap<String,Object> map){
		Object obj = null;
		Session session = DBConfig.getInstance().getSession();
		Transaction ts = session.beginTransaction();
		Query query = session.createSQLQuery(sql);
		if(map != null && !map.isEmpty()){
			for(String key : map.keySet()){
				Object para = map.get(key);
				if(para instanceof Collection<?>){
					query.setParameterList(key, (Collection<?>)para);
				}else if(para instanceof Object[]){
					query.setParameterList(key, (Object[])para);
				}else{
					query.setParameter(key, map.get(key));
				}
			}
		}
		try {
			if(sql.trim().startsWith("select")){
				obj = query.list();
			}else{
				obj = query.executeUpdate();
				ts.commit();
			}
			System.out.println("SQL:"+sql);
		} catch (Exception e) {
			e.printStackTrace();
			ts.rollback();
		}finally{
			DBConfig.getInstance().closeSession();
		}
		return obj;
	}*/
	
	/*public static List<Object> doSql(List<String> list_sql,List<HashMap<String,Object>> list_map){
		List<Object> list = new ArrayList<Object>();
		Object obj = null;
		Session session = DBConfig.getInstance().getSession();
		Transaction ts = session.beginTransaction();
		try {
			for(int i=0;i<list_sql.size();i++){
				Query query = session.createSQLQuery(list_sql.get(i));
				for(String key : list_map.get(i).keySet()){
					query.setParameter(key, list_map.get(i).get(key));
				}
				if(list_sql.get(i).trim().startsWith("select")){
					obj = query.list();
				}else{
					obj = query.executeUpdate();
				}
				list.add(obj);
			}
			ts.commit();
		} catch (Exception e) {
			list = new ArrayList<Object>();
			e.printStackTrace();
			ts.rollback();
		}
		return list;
	}*/
	
	/**
	 * 执行HQL语句
	 * @param hql
	 * @param map
	 * @return
	 */
	/*public static Object doHql(String hql,HashMap<String,Object> map){
		Object obj = null;
		Session session = DBConfig.getInstance().getSession();
		Query query = session.createSQLQuery(hql);
		Transaction ts = session.beginTransaction();
		if(map != null && !map.isEmpty()){
			for(String key : map.keySet()){
				query.setParameter(key, map.get(key));
			}
		}
		try {
			if(hql.trim().startsWith("select")){
				obj = query.list();
			}else{
				obj = query.executeUpdate();
				ts.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			ts.rollback();
		}finally{
			DBConfig.getInstance().closeSession();
		}
		return obj;
	}*/
	
	/**
	 * 执行HQL实体类
	 * @param list
	 * @return
	 */
	/*public static Object doHql(List<?> list){
		Object obj = null;
		Session session = DBConfig.getInstance().getSession();
		Transaction ts = session.beginTransaction();
		if(list != null && !list.isEmpty()){
			try {
				for(int i=0;i<list.size();i++){
					session.save(list.get(i));
				}
			} catch (Exception e) {
				e.printStackTrace();
				ts.rollback();
			}finally{
				DBConfig.getInstance().closeSession();
			}
		}
		return obj;
	}*/
	
	/**
	 * 将数据库查出来的数据转成list格式,可以根据key的变动而返回不同的数据,不需要的列以空代替
	 * 逗号相连,最后一个不为空的数据之后会被忽略,逗号间有空格,则视为一个字符串
	 * 若key中只有一个有效的列名，则返回一个用","隔开的字符串集合
	 * @param obj
	 * @param key
	 * @return
	 */
	public static List<?> getResultToList(List<?> obj,String key){
		key = key.trim();
		String tmp_key = key.replaceAll("^[,]*", "").replace("[,]*$", "");
		if(tmp_key.split(",").length == 1){
			List<String> list = new ArrayList<String>();
			int index = key.split(",").length;
			String result = "";
			for (Iterator<?> iter = obj.iterator(); iter.hasNext();) {
				Object[] a = (Object[]) iter.next();
				result += a[index-1].toString();
				result += ",";
			}
			list.add(result);
			return list;
		}else if(tmp_key.replaceAll("[,]+", ",").split(",").length == 2){
			List<LinkedHashMap<String,String>> list = new ArrayList<LinkedHashMap<String,String>>();
			String[] keys = key.split(",");
			LinkedHashMap<String,String> map = new LinkedHashMap<String, String>();
			for(Iterator<?> iter = obj.iterator(); iter.hasNext();){
				Object[] a = (Object[]) iter.next();
				String x = "";
				for(int i=0;i<keys.length;i++){
					if(Util.isNotNull(keys[i])){
						if(i == keys.length -1){
							map.put(x, a[i].toString());
							System.out.println("put x:"+x+",a["+i+"]:"+a[i]);
						}else{
							x = Util.isNotNull(a[i])?a[i].toString():"noAdmin";
							System.out.println("x:"+x+",a["+i+"]:"+a[i]);
						}
					}
				}
			}
			list.add(map);
			return list;
		}else{
			List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
			HashMap<String,String> map = new HashMap<String, String>();
			String[] keys = key.split(",");
			if(keys.length == 0){
				return list;
			}
			for (Iterator<?> iter = obj.iterator(); iter.hasNext();) {
				Object[] a = (Object[]) iter.next();
				map = new HashMap<String, String>();
				for(int i=0;i<keys.length;i++){
					if(Util.isNotNull(keys[i])){
						if(Util.isNotNull(a[i])){
							map.put(keys[i], a[i].toString());
						}else{
							map.put(keys[i], "");
						}
					}
				}
				list.add(map);
			}
			return list;
		}
	}
	
	public static boolean isNotNull(Object obj){
		return obj != null && !((List<?>)obj).isEmpty();
	}
}
