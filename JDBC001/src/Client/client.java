package Client;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.time.TimeSeriesCollection;

import com.google.gson.Gson;


public class client extends JFrame {
	
	private JPanel panel;
	private JComboBox Name;
	private JComboBox Date_year;
	private JComboBox Date_month;
	private JComboBox Date_day;
	private JComboBox Column_type;
	private JButton Start;
	private JTextArea TArea;
	
	private PrintWriter writer;
	private BufferedReader reader;
	
	//private double data[][];
	
	private Socket ClientSocket;
	
	
	public static void main(String[] args) throws Exception {
		
		JFrame mainwindow=new JFrame("客户端登录");
		mainwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainwindow.setLayout(new GridLayout(3,1));
		JTextField JIP=new JTextField("127.0.0.1");
		JTextField JPort=new JTextField("8080");
		JButton JLoad=new JButton("Load");
		
		JLoad.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					String IP=JIP.getText();
					System.out.println(IP);
					int port=Integer.parseInt(JPort.getText());
					client c=new client(IP,port);
					mainwindow.hide();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		mainwindow.setSize(500, 200);
		mainwindow.add(JIP);
		mainwindow.add(JPort);
		mainwindow.add(JLoad);
		mainwindow.setVisible(true);
		
		//System.out.println();
	}
	
	@SuppressWarnings("unchecked")
	public client(String IP,int port) throws Exception {
		
		ClientSocket=new Socket(IP,port);
		
		reader=new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
		writer=new PrintWriter(ClientSocket.getOutputStream());
		
		initui();
		
	}
	
	
	private void doResponse(String mapstring) {
		CategoryDataset dataset;
		JFreeChart jfreechat;
		
		String[]map=mapstring.split(",");
		int keynum=map.length;
		String[] key=new String[keynum-1];
		double[] value=new double[keynum-1];
		for(int i=1;i<keynum;i++) {
			String[] kv=map[i].split("&");
			key[i-1]=kv[0].substring(kv[0].length()-2);
			value[i-1]=Double.parseDouble(kv[1]);
		}
		
		double[][] data= {value,};
		String year=(String)Date_year.getSelectedItem();
		String month=(String)Date_month.getSelectedItem();
		String valuelabel=(String)Column_type.getSelectedItem();
		String title=String.format("%s年%s月%s折线走势图",year,month,valuelabel);
		String row=(String)Column_type.getSelectedItem();
		dataset=chart.createCategorydataset(row,key,data);
		jfreechat=chart.createChart(dataset,title,valuelabel);
		Paint(jfreechat);
	}
	
	private void getResponse() throws IOException {
		
		String mapstring=reader.readLine();
		
		String d=(String)Date_day.getSelectedItem();
		if(d.equals("只看年和月")) //查询一个月
			doResponse(mapstring);
		else {
			mapstring=mapstring.replace(",", "");
			mapstring=mapstring.replace("&", ":   ");
			this.TArea.setText(mapstring);
		}
			
	}
	
	private String getRequestData() {
		String name=(String)Name.getSelectedItem();
		String y=(String)Date_year.getSelectedItem();
		String m=(String)Date_month.getSelectedItem();
		if(m.length()==1)m="0"+m;
		String d=(String)Date_day.getSelectedItem();
		if(d.equals("只看年和月"))d="00";
		else if(d.length()==1)d="0"+d;
		String column=(String)Column_type.getSelectedItem();
		column=column.substring(column.length()-1);
		return y+m+d+column;
	}
	
	private void Paint(JFreeChart jfchart) {
		ChartPanel chart=new ChartPanel(jfchart,true);
		chart.setSize(1400, 600);
		JFrame jf=new JFrame();
		jf.setSize(1000,600);
		jf.setLayout(new FlowLayout());
		jf.add(chart);
		jf.setVisible(true);
		
	}
	
	private void initui() {
		this.setTitle("股市数据查询");
		this.setSize(600, 200);
		this.setLayout(new GridLayout(2,1));
		panel=new JPanel();	
		panel.setLayout(new FlowLayout());
		
		String[] names= {"浦发银行"};
		String[] year=new String[21];
		String[] month=new String[12];
		String[] day=new String[32];
		String[] columns= {"[前收盘价(元)]0","[开盘价(元)]1","[最高价(元)]2",
				"[最低价(元)]3","[收盘价(元)]4","[成交量(股)]5","[成交金额(元)]6",
				"[涨跌(元)]7","[涨跌幅(%)]8","[均价(元)]9","[换手率(%)]A",
				"[A股流通市值(元)]B","[B股流通市值(元)]C",
				"[总股本(股)]D","[市盈率]E","[市净率]F","[市销率]G","[市现率]H"};
		for(int i=1999;i<=2019;i++) {
			year[i-1999]=String.valueOf(i);
		}
		for(int i=1;i<=12;i++) {
			month[i-1]=String.valueOf(i);
		}
		day[0]="只看年和月";
		for(int i=1;i<=31;i++) {
			day[i]=String.valueOf(i);
		}
		Name=new JComboBox(names);
		Date_year=new JComboBox(year);
		Date_month=new JComboBox(month);
		Date_day=new JComboBox(day);
		Column_type=new JComboBox(columns);
		TArea=new JTextArea();
		
		Start=new JButton("START");
		Start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String data=getRequestData();
				System.out.println(data);
				writer.println(data);
				writer.flush();
				try {
					getResponse();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		panel.add(Name);
		panel.add(Date_year);
		panel.add(Date_month);
		panel.add(Date_day);
		panel.add(Column_type);
		panel.add(Start);
		this.add(panel);
		this.add(TArea);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
}


