package chart;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import DB.DataBean;
import DB.Spotreba;
import bean.IndexBean;

@ManagedBean
public class ChartView implements Serializable {

   private LineChartModel lineModel1;
   private LineChartModel lineModel2;
  
   // bean injection - loginBean - pro prenost values from, to
   @ManagedProperty(value="#{loginBean}")
   private IndexBean indexBean;
   public void setIndexBean(IndexBean indexBean) {
 		this.indexBean = indexBean;
   }
   
   // bean injection - dataBean - pro prenost hodnot z dB
   @ManagedProperty(value="#{dataBean}")
   private DataBean dataBean;
   public void setDataBean(DataBean dataBean) {
 		this.dataBean = dataBean;
   }
   
   
  
	
   @PostConstruct
   public void init() {
       createLineModels();
   }

   public LineChartModel getLineModel1() {
       return lineModel1;
   }
   
   public LineChartModel getLineModel2() {
       return lineModel2;
   }
   
   
    
   private void createLineModels() {
	   
       lineModel1 = initLinearModel1();
       lineModel1.setTitle("");
       lineModel1.setLegendPosition("e");
     
       lineModel2 = initLinearModel2();
       lineModel2.setTitle("");
       lineModel2.setLegendPosition("e");
       
       // y je horizontalni osa
       Axis yAxis = lineModel1.getAxis(AxisType.Y);
       yAxis.setMin(0);
       Axis yAxis2 = lineModel2.getAxis(AxisType.Y);
       yAxis2.setMin(0);

       // x osa
       DateAxis xAxis = new DateAxis("");
       xAxis.setTickAngle(-90);
       DateAxis xAxis2 = new DateAxis("");
       xAxis2.setTickAngle(-90);

       lineModel1.getAxes().put(AxisType.X, xAxis);
       lineModel2.getAxes().put(AxisType.X, xAxis2);
        
       
   }

   
// graf1 -  ele, plym   
   private LineChartModel initLinearModel1() {
	   
       LineChartModel model = new LineChartModel();

       LineChartSeries series1 = new LineChartSeries();
       series1.setLabel("Elekrina (kWh)");

       LineChartSeries series2 = new LineChartSeries();
       series2.setLabel("Plyn (kWh)");
       
       
       // nacti Bean z indexBean a omezitL    // format datumu > Thu Jun 02 00:00:00 CEST 2016   
	   Date datum_omezeni_from = (Date) indexBean.date_from;
	   Date datum_omezeni_to = (Date) indexBean.date_to;
		   
	   // zmena format datumu
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	   System.out.print("Chart1 - omezeni datum from=" + sdf.format(datum_omezeni_from) + " to=" + sdf.format(datum_omezeni_to) + " \n");
	
	   // nacti data z DbService
	   List<Spotreba> chartList = new ArrayList<Spotreba>();
	   chartList = dataBean.getEvent();

	   int pocet;
	   for (pocet = 0; pocet < chartList.size(); pocet++) {
		   
		   Spotreba spot = chartList.get(pocet);
		   	
		   String db_date  = spot.getTimestamp();
		   int db_value1 = spot.getEle();
		   int db_value2 = spot.getPlyn(); 
		       
		   series1.set(db_date, db_value1);
		   series2.set(db_date, db_value2);
	
		   // System.out.print("Chart - From DbService : date=" + db_date + " Ele=" + db_value1 + " Plyn=" + db_value2 + "\n");	       
		}
		   
		if (pocet ==  0 ) {
			   System.out.println("POZOR : v dB nic nenalezeno !");
		}
		   
	  
       model.addSeries(series1);
       model.addSeries(series2);
       
       return model;
   }
  
  

   // graf2- voda, temp
   private LineChartModel initLinearModel2() {
	   
       LineChartModel model2 = new LineChartModel();

       LineChartSeries series3 = new LineChartSeries();
       series3.setLabel("Voda (l)");
       
       LineChartSeries series4 = new LineChartSeries();
       series4.setLabel("Teplota (stC)");
       
       
       // nacti Bean z indexBean a omezitL    // format datumu > Thu Jun 02 00:00:00 CEST 2016   
	   Date datum_omezeni_from = (Date) indexBean.date_from;
	   Date datum_omezeni_to = (Date) indexBean.date_to;
		   
	   // zmena format datumu
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	   System.out.print("Chart2 - omezeni datum from=" + sdf.format(datum_omezeni_from) + " to=" + sdf.format(datum_omezeni_to) + " \n");
		   
	
	   // nacti data z DbService
	   List<Spotreba> chartList = new ArrayList<Spotreba>();
	   chartList = dataBean.getEvent();

	   int pocet;
	   for (pocet = 0; pocet < chartList.size(); pocet++) {
		   
		   Spotreba spot = chartList.get(pocet);
		   	
		   String db_date  = spot.getTimestamp();
		   int db_value1 = spot.getVoda();
		   int db_value2 = spot.getTemp(); 
		       
		   series3.set(db_date, db_value1);
		   series4.set(db_date, db_value2);
	
		   // System.out.print("Chart - From DbService : date=" + db_date + " Voda=" + db_value1 + " Temp=" + db_value2 + "\n");	       
		}
		   
		if (pocet ==  0 ) {
			   System.out.println("POZOR : v dB nic nenalezeno !");
		}
      
       
       model2.addSeries(series3);
       model2.addSeries(series4);
       
       return model2;
   }

   

}


