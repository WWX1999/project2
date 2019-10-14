package myServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class ConnectionDb {
	
	private String Database;	
	private String UserName;
	private String PassWord;
	
	private Connection connection;
	
	String tablename="[实验数据上证某公司股市数据]";
	HashMap<String, String> map= new HashMap<String, String>();
	
	public ConnectionDb(String db,String user,String passw) {
		// TODO Auto-generated constructor stub
		Database=db;
		UserName=user;
		PassWord=passw;
	}
	

	public void ConnectDatabase() {
		init();
		System.out.println("Start Connect.....");
		String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";//设置SQL Server数据库引擎
		//Database = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=pubs";//指定数据库
		
		System.out.println(Driver);
		try {
			Class.forName(Driver);//加载数据库引擎
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		try {
			//连接数据库
			connection = DriverManager.getConnection(Database, UserName, PassWord);
			System.out.println("connect success");
			
		}catch(Exception e) {
			System.exit(0);
		}
	}
	
	public String SelectData(String []request) throws SQLException {
		
		Statement statement=connection.createStatement();
		ResultSet rs;
		String sql="";
		String co=map.get(request[3]);
		
		if(request[2].equals("00")) {
			sql="select [日期],"+tablename+"."+co+" from "
					+tablename+" where year([日期])="
					+"'"+request[0]+"'"+" and month([日期])="+"'"+request[1]+"'";
			
		}
		else {
			sql="select [日期],"+tablename+"."+co+" from "
					+tablename+" where [日期]="
					+"'"+request[0]+"-"+request[1]+"-"+request[2]+"'";
		}
		System.out.println(sql);
		rs=statement.executeQuery(sql);
		String result="";
		while(rs.next()) {
		  String k=rs.getString(1);
		  String v=rs.getString(2);
		  result=result+","+k+"&"+v;
		}
		System.out.println(result);
		return result;
	}
	
	
	private void init() {
		map.put("0","[前收盘价(元)]" );
		map.put("1","[开盘价(元)]");
		map.put("2","[最高价(元)]");
		map.put("3","[最低价(元)]");
		map.put("4","[收盘价(元)]");
		map.put("5","[成交量(股)]");
		map.put("6","[成交金额(元)]");
		map.put("7","[涨跌(元)]");
		map.put("8","[涨跌幅(%)]");
		map.put("9","[均价(元)]");
		map.put("A","[换手率(%)]");
		map.put("B","[A股流通市值(元)]");
		map.put("C","[B股流通市值(元)]");
		map.put("D","[总股本(股)]");
		map.put("E","[市盈率]");
		map.put("F","[市净率]");
		map.put("G","[市销率]");
		map.put("H","[市现率]");
	}
	
	
}
