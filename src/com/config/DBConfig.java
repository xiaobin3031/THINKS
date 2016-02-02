package com.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.admin.SnapshotIF;

public class DBConfig {
    
	private static DBConfig instance;
	
	private static int activeCount = 0;
	
	private static int activeSecCount = 0;
	
    private static String DB_DRIVER = "org.logicalcobwebs.proxool.ProxoolDriver";
    
	public synchronized static DBConfig getInstance() {
		if (instance == null) {
			try {
				instance = new DBConfig();
			}
			catch(Exception e) {
				e.printStackTrace();
//				Log4j.error(StaticRef.ERROR_LOG, "DBPOOL.getInstance|创建连接失败:" + e.getMessage());
			}
		}
		return instance;
	}
    
    public Connection getSecConnection() {
    	try {
	    	Class.forName(DB_DRIVER);
            Connection conn = DriverManager.getConnection("proxool.sec");
            showSecSnapshotInfo();
            return conn;
    	}
    	catch(Exception e) {
    		showSecSnapshotInfo();
    		e.printStackTrace();
//    		Log4j.error(StaticRef.ERROR_LOG, "DBPOOL.getConnection|获取连接失败:" + e.getMessage());
    	}
    	return null;
    }
    
    public Connection getSSConnection() {
    	try {
	    	Class.forName(DB_DRIVER);
            Connection conn = DriverManager.getConnection("proxool.ss");
            showSnapshotInfo("ss");
            return conn;
    	}
    	catch(Exception e) {
    		showSnapshotInfo("ss");
    		e.printStackTrace();
//    		Log4j.error(StaticRef.ERROR_LOG, "DBPOOL.getConnection|获取连接失败:" + e.getMessage());
    	}
    	return null;
    }
    
    public Connection getOrclConnection() {
    	try {
	    	Class.forName(DB_DRIVER);
            Connection conn = DriverManager.getConnection("proxool.orcl");
            showSnapshotInfo("orcl");
            return conn;
    	}
    	catch(Exception e) {
    		showSnapshotInfo("orcl");
    		e.printStackTrace();
//    		Log4j.error(StaticRef.ERROR_LOG, "DBPOOL.getConnection|获取连接失败:" + e.getMessage());
    	}
    	return null;
    }
    public Connection getMSConnection() {
    	try {
	    	Class.forName(DB_DRIVER);
            Connection conn = DriverManager.getConnection("proxool.ms");
            showSnapshotInfo("ms");
            return conn;
    	}
    	catch(Exception e) {
    		showSnapshotInfo("ms");
    		e.printStackTrace();
//    		Log4j.error(StaticRef.ERROR_LOG, "DBPOOL.getConnection|获取连接失败:" + e.getMessage());
    	}
    	return null;
    }
    
    public Connection getTmsConnection() {
    	try {
	    	Class.forName(DB_DRIVER);
            Connection conn = DriverManager.getConnection("proxool.tmsdb2");
            showTmsSnapshotInfo("tmsdb2");
            return conn;
    	}
    	catch(Exception e) {
    		showSnapshotInfo("tmsdb2");
    		e.printStackTrace();
//    		Log4j.error(StaticRef.ERROR_LOG, "DBPOOL.getConnection|获取连接失败:" + e.getMessage());
    	}
    	return null;
    }
    
    /** 
    * 此方法可以得到连接池的信息 showSnapshotInfo 
    */ 
    private void showSecSnapshotInfo() { 
	    try { 
		    SnapshotIF snapshot = ProxoolFacade.getSnapshot("sec", true); 
		    int curActiveCount = snapshot.getActiveConnectionCount();// 获得活动连接数 
		    int availableCount = snapshot.getAvailableConnectionCount();// 获得可得到的连接数 
		    //int maxCount = snapshot.getMaximumConnectionCount();// 获得总连接数 
		    if (curActiveCount != activeSecCount)// 当活动连接数变化时输出的信息 
		    { 
		    	String info = "使用连接数:" + curActiveCount + ",可用连接数:" + availableCount;
			    System.out.println(info);
//		    	Log4j.error(StaticRef.ERROR_LOG, "DBPOOL.showSnapshotInfo|连接池信息:" + info);
		    	activeSecCount = curActiveCount; 
		    } 
	    } catch (ProxoolException e) { 
	    	e.printStackTrace(); 
//	    	Log4j.error(StaticRef.ERROR_LOG, "DBPOOL.showSnapshotInfo|获取连接池信息异常:" + e.getMessage());
	    } 
    } 
    
    /** 
    * 此方法可以得到连接池的信息 showSnapshotInfo 
    */ 
    private void showSnapshotInfo(String db) { 
	    try { 
		    SnapshotIF snapshot = ProxoolFacade.getSnapshot(db, true); 
		    int curActiveCount = snapshot.getActiveConnectionCount();// 获得活动连接数 
		    int availableCount = snapshot.getAvailableConnectionCount();// 获得可得到的连接数 
		    //int maxCount = snapshot.getMaximumConnectionCount();// 获得总连接数 
		    if (curActiveCount != activeCount)// 当活动连接数变化时输出的信息 
		    { 
		    	String info = "使用连接数:" + curActiveCount + ",可用连接数:" + availableCount;
			    System.out.println(info);
//		    	Log4j.error(StaticRef.ERROR_LOG, "DBPOOL.showSnapshotInfo|连接池信息:" + info);
			    activeCount = curActiveCount; 
		    } 
	    } catch (ProxoolException e) { 
	    	e.printStackTrace(); 
//	    	Log4j.error(StaticRef.ERROR_LOG, "DBPOOL.showSnapshotInfo|获取连接池信息异常:" + e.getMessage());
	    } 
    }
    
    /** 
    * 此方法可以得到连接池的信息 showSnapshotInfo 
    */ 
    private void showTmsSnapshotInfo(String db) { 
	    try { 
		    SnapshotIF snapshot = ProxoolFacade.getSnapshot(db, true); 
		    int curActiveCount = snapshot.getActiveConnectionCount();// 获得活动连接数 
		    int availableCount = snapshot.getAvailableConnectionCount();// 获得可得到的连接数 
		    //int maxCount = snapshot.getMaximumConnectionCount();// 获得总连接数 
		    if (curActiveCount != activeCount)// 当活动连接数变化时输出的信息 
		    { 
		    	String info = "使用连接数:" + curActiveCount + ",可用连接数:" + availableCount;
			    System.out.println(info);
//		    	Log4j.error(StaticRef.ERROR_LOG, "DBPOOL.showSnapshotInfo|连接池信息:" + info);
			    activeCount = curActiveCount; 
		    } 
	    } catch (ProxoolException e) { 
	    	e.printStackTrace(); 
//	    	Log4j.error(StaticRef.ERROR_LOG, "DBPOOL.showSnapshotInfo|获取连接池信息异常:" + e.getMessage());
	    } 
    }
    
    /** 
    * 获取连接 getConnection 
    * 
    * @param name 
    * @return 
    */ 
    /*public Connection getConnection(String name) throws SQLException { 
    	return getConnection(); 
    }*/ 
    
    /** 
    * 获取连接 getConnection 
    * 
    * @param name 
    * @return 
    */ 
    public Connection getSecConnection(String name) throws SQLException { 
    	return getSecConnection(); 
    } 
    
    /** 
    * 释放连接 freeConnection 
    * 
    * @param conn 
    */ 
    public void free(Connection conn) { 
	    if (conn != null) { 
		    try { 
		    	conn.close(); 
		    } catch (SQLException e) { 
		    	e.printStackTrace(); 
//		    	Log4j.error(StaticRef.ERROR_LOG, "DBPOOL.free|连接关闭异常:" + e.getMessage());
		    } 
	    } 
    }
    





    /** 
    * 释放连接 freeConnection 
    * 
    * @param name 
    * @param con 
    */ 
    public void freeConnection(String name, Connection con) { 
    	free(con); 
    }

	public void close(Connection con, PreparedStatement ps,ResultSet rs ) {
		try {
			if(rs!=null){
				rs.close();
				rs = null;
			}
    		if(ps != null) {
        		ps.close();
        		ps = null;
        	}
		    if (con != null) {
		    	con.close();
		    	con = null;
		    }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void close(Connection con,PreparedStatement ps){
		close(con,ps,null);
	}
}
