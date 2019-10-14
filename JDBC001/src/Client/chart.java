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
		// ����JFreeChart����ChartFactory.createLineChart  
		
        JFreeChart jfreechart = ChartFactory.createLineChart(
        		title, // ����
                "����",         //categoryAxisLabel ��category�ᣬ���ᣬX���ǩ��
                valuelabel,      // valueAxisLabel��value�ᣬ���ᣬY��ı�ǩ��
                categoryDateset,  //Dataset  
                PlotOrientation.VERTICAL,
                true, // legend 
                false,          //Tooltips
                false);        //URLs
        
        Font titleFont=new Font("����", Font.ITALIC, 18);
		Font font=new Font("����",Font.BOLD,12);
		Font legendFont=new Font("����", Font.BOLD, 15);
		
		jfreechart.getTitle().setFont(titleFont);
		jfreechart.getLegend().setItemFont(legendFont);
        
        // ʹ��CategoryPlot���ø��ֲ�����  
        CategoryPlot plot = jfreechart.getCategoryPlot();
        // ����ɫ ͸����  
        plot.setBackgroundAlpha(0.5f);  
        // ǰ��ɫ ͸����  
        plot.setForegroundAlpha(1.0f);  
        plot.getDomainAxis().setLabelFont(font);
		plot.getDomainAxis().setTickLabelFont(font);
		plot.getRangeAxis().setLabelFont(font);
        
        // �������� �ο� CategoryPlot��  
        LineAndShapeRenderer renderer = (LineAndShapeRenderer)plot.getRenderer();  
        renderer.setBaseShapesVisible(true); // series �㣨�����ݵ㣩�ɼ�  
        renderer.setBaseLinesVisible(true); // series �㣨�����ݵ㣩�������߿ɼ�  
        renderer.setUseSeriesOffset(true); // ����ƫ����  
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
