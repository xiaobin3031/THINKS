package com.x.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.config.DBConfig;
import com.util.Util;

public class Main extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7232784772247478092L;

	private StringBuffer sb = null;
//	private HashMap<String,Object> map = null;
	private String json = "";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		res.addHeader("Access-Control-Allow-Origin", "*");
		
		String action = req.getParameter("action");
		System.out.println("action:"+action);
		json = Util.getCommJson(false);
		if("getPojo".equals(action)){
			if(Util.isNotNull(req.getParameter("TABLENAME"))){
				json = Util.getJson("表不存在，请检查", -1, false);
				sb = new StringBuffer();
				Connection conn = null;
				PreparedStatement ps = null;
				ResultSet rs = null;
				try {
					switch (Integer.parseInt(req.getParameter("DBTYPE"))) {
					case 0:
						sb.append("select b.name as COLUMN_NAME,'' as COMMENTS,'' as DATA_TYPE,'' as DATA_LENGTH ")
						.append("from sys.objects a left join sys.all_columns b on a.object_id = b.object_id where a.name = ?");
						conn = DBConfig.getInstance().getSSConnection();
						break;
					case 1:
						sb.append("select a.COLUMN_NAME,b.COMMENTS,a.DATA_TYPE,a.DATA_LENGTH ")
						.append("from user_tab_columns a,user_col_comments b ")
						.append("where a.table_name = b.table_name(+) and a.column_name = b.column_name(+) and a.table_name = ?")
						.append(" order by a.COLUMN_NAME");
						conn = DBConfig.getInstance().getOrclConnection();
						break;
					case 2:
						conn = DBConfig.getInstance().getMSConnection();
						break;
					}
					ps = conn.prepareStatement(sb.toString());
					ps.setString(1, req.getParameter("TABLENAME").toUpperCase());
					System.out.println("tablename:"+req.getParameter("TABLENAME").toUpperCase());
					rs = ps.executeQuery();
					String cols = "",coms = "",dType = "",dlen = "";
					while (rs.next()){
						cols += rs.getString("COLUMN_NAME") + ",";
						coms += (rs.getString("COMMENTS") == null ? "" : rs.getString("COMMENTS")) + ",";
						dType += (rs.getString("DATA_TYPE") == null ? "" : rs.getString("DATA_TYPE")) + ",";
						dlen += (rs.getString("DATA_LENGTH") == null ? "" : rs.getString("DATA_LENGTH")) + ",";
					}
					if(!"".equals(cols)){
						cols = cols.replace(",","\",\"");
						coms = coms.replace(",","\",\"");
						dType = dType.replace(",","\",\"");
						dlen = dlen.replace(",","\",\"");
						json = "{data:{\"COLUMNS\":[\""+cols.substring(0,cols.length() - 2)+"],"
								+ "\"COMMENTS\":[\""+coms.substring(0,coms.length() - 2)+"],"
										+ "\"DATATYPE\":[\""+dType.substring(0,dType.length() - 2)+"],"
												+ "\"DATALENGTH\":[\""+dlen.substring(0,dlen.length()-2)+"]}}";
					}else
						json = Util.getJson("表名无效或者未创建表", -1, false);
				} catch (Exception e) {
					e.printStackTrace();
					json = Util.getJson("查询失败,请重试", -2, false);
				} finally{
					DBConfig.getInstance().close(conn, ps,rs);
				}
			}else
				json = Util.getJson("请输入表名或视图名", -1, false);
		}
		System.out.println("json:"+json);
		PrintWriter pw = null;
		try {
			pw = res.getWriter();
			pw.write(json);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(pw != null){
				pw.flush();
				pw.close();
				pw = null;
			}
		}
	}
}
