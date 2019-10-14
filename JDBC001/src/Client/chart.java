package Client;

import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

public class chart {

	public static JFreeChart createChart(CategoryDataset categoryDateset,
			String title,String valuelabel) {
		// 创建JFreeChart对象：ChartFactory.createLineChart  
		
        JFreeChart jfreechart = ChartFactory.createLineChart(
        		title, // 标题
                "日期",         //categoryAxisLabel （category轴，横轴，X轴标签）
                valuelabel,      // valueAxisLabel（value轴，纵轴，Y轴的标签）
                categoryDateset,  //Dataset  
                PlotOrientation.VERTICAL,
                true, // legend 
                false,          //Tooltips
                false);        //URLs
        
        Font titleFont=new Font("隶书", Font.ITALIC, 18);
		Font font=new Font("宋体",Font.BOLD,12);
		Font legendFont=new Font("宋体", Font.BOLD, 15);
		
		jfreechart.getTitle().setFont(titleFont);
		jfreechart.getLegend().setItemFont(legendFont);
        
        // 使用CategoryPlot设置各种参数。  
        CategoryPlot plot = jfreechart.getCategoryPlot();
        // 背景色 透明度  
        plot.setBackgroundAlpha(0.5f);  
        // 前景色 透明度  
        plot.setForegroundAlpha(1.0f);  
        plot.getDomainAxis().setLabelFont(font);
		plot.getDomainAxis().setTickLabelFont(font);
		plot.getRangeAxis().setLabelFont(font);
        
        // 其他设置 参考 CategoryPlot类  
        LineAndShapeRenderer renderer = (LineAndShapeRenderer)plot.getRenderer();  
        renderer.setBaseShapesVisible(true); // series 点（即数据点）可见  
        renderer.setBaseLinesVisible(true); // series 点（即数据点）间有连线可见  
        renderer.setUseSeriesOffset(true); // 设置偏移量  
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());  
        renderer.setBaseItemLabelsVisible(true);  
        
        
        return jfreechart;  
	}
	
	
	public static CategoryDataset createCategorydataset(String row,String[] colkey,double[][] data )
	{
		String[] rowkey= {row};
		if(colkey==null||data==null) {
			return null;
		}
		return DatasetUtilities.createCategoryDataset(rowkey, colkey, data);             
	}
}
