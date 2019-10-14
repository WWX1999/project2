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
	
	String tablename="[ʵ��������֤ĳ��˾��������]";
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
		String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";//����SQL Server���ݿ�����
		//Database = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=pubs";//ָ�����ݿ�
		
		System.out.println(Driver);
		try {
			Class.forName(Driver);//�������ݿ�����
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		try {
			//�������ݿ�
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
			sql="select [����],"+tablename+"."+co+" from "
					+tablename+" where year([����])="
					+"'"+request[0]+"'"+" and month([����])="+"'"+request[1]+"'";
			
		}
		else {
			sql="select [����],"+tablename+"."+co+" from "
					+tablename+" where [����]="
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
		map.put("0","[ǰ���̼�(Ԫ)]" );
		map.put("1","[���̼�(Ԫ)]");
		map.put("2","[��߼�(Ԫ)]");
		map.put("3","[��ͼ�(Ԫ)]");
		map.put("4","[���̼�(Ԫ)]");
		map.put("5","[�ɽ���(��)]");
		map.put("6","[�ɽ����(Ԫ)]");
		map.put("7","[�ǵ�(Ԫ)]");
		map.put("8","[�ǵ���(%)]");
		map.put("9","[����(Ԫ)]");
		map.put("A","[������(%)]");
		map.put("B","[A����ͨ��ֵ(Ԫ)]");
		map.put("C","[B����ͨ��ֵ(Ԫ)]");
		map.put("D","[�ܹɱ�(��)]");
		map.put("E","[��ӯ��]");
		map.put("F","[�о���]");
		map.put("G","[������]");
		map.put("H","[������]");
	}
	
	
}
