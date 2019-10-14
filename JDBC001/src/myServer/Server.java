package myServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Server implements Runnable{

	private Socket clientsocket;
	private PrintWriter writer;
	private BufferedReader reader;
	//private InputStream in;
	//private OutputStream out;
	
	private ConnectionDb connection;
	
	
	public static void main(String[] args) {
		
		if(args.length!=1) {
			System.out.println("ERROE arguments");
			System.exit(0);
		}
		int port=Integer.parseInt(args[0]);
		String database="jdbc:sqlserver:"
				+ "//sqlserver001.cxotqpgy5pb9.us-east-1.rds.amazonaws.com:1433;"
				+ "DatabaseName=AWCRDS";
//		String database="jdbc:sqlserver:"
//				+ "//127.0.0.1:1433;"
//				+ "DatabaseName=pubs";
		String user="wwx";
		String password="wwx940512";
		//System.out.println(user);
		try {
			ServerSocket ssock = new ServerSocket(port);
			while (true) {
				System.out.println("wait connect");
				Socket sock = ssock.accept(); 
				new Thread(new Server(sock,database,user,password)).start();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Server(Socket sock,String database,String user,String password) throws Exception {
		clientsocket=sock;
		
		reader=new BufferedReader(new InputStreamReader(sock.getInputStream()));
		writer=new PrintWriter(sock.getOutputStream());
		//in=clientsocket.getInputStream();
		//out=clientsocket.getOutputStream();
		
		connection=new ConnectionDb(database, user, password);
		connection.ConnectDatabase();
		
		
	}
	
	private String[] getRequest() throws IOException {
		//byte[] by=new byte[1024];
		//int len=is.read(by);
		//String data=new String(by, 0, len);
		//System.out.println(data);
		String data=reader.readLine();
		System.out.println(data);
		String[] request=new String[]{data.substring(0, 4),data.substring(4,6),
				data.substring(6, 8),data.substring(8)};
		return request;
	}
	
	private String getData() throws IOException, SQLException {
		String[]request= getRequest();
		String result=connection.SelectData(request);
		return result;
	}
	
	
	private void Response(String s) throws Exception {
		//out.write(s.getBytes());
		writer.println(s);
		writer.flush();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			String s=getData();
			Response(s);
			//Response(out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
