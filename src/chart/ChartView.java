package chart;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import bean.IndexBean;

@ManagedBean
public class ChartView implements Serializable {

 	
   private LineChartModel lineModel1;
  
   // import IndexBean - pro prenost values from, to
   @ManagedProperty(value="#{loginBean}")
   private IndexBean indexBean;
   public void setIndexBean(IndexBean indexBean) {
 		this.indexBean = indexBean;
   }
 	
   
   // JDBC driver name and database URL
   final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   final String DB_URL = "jdbc:mysql://wh19.farma.gigaserver.cz:3306/vancura_cz_";
   final String USER = "";
   final String PASS = "";
   
   Connection conn = null;
   Statement stmt = null;
	
	
   @PostConstruct
   public void init() {
       createLineModels();
   }

   public LineChartModel getLineModel1() {
       return lineModel1;
   }
   
    
   private void createLineModels() {
	   
       lineModel1 = initLinearModel();
       lineModel1.setTitle("");
       lineModel1.setLegendPosition("e");

       // y je horizontalni osa
       Axis yAxis = lineModel1.getAxis(AxisType.Y);
       yAxis.setMin(0);
       //yAxis.setMax(4000); 
       
       // x osa
       DateAxis xAxis = new DateAxis("");
       xAxis.setTickAngle(-90);
       //xAxis.setMax("2016-06-25");
       lineModel1.getAxes().put(AxisType.X, xAxis);
        
       
   }
    
   private LineChartModel initLinearModel() {
       LineChartModel model = new LineChartModel();

       LineChartSeries series1 = new LineChartSeries();
       series1.setLabel("Elekrina (kWh)");

       LineChartSeries series2 = new LineChartSeries();
       series2.setLabel("Plyn (kWh)");
       
       LineChartSeries series3 = new LineChartSeries();
       series3.setLabel("Voda (l)");
       
       LineChartSeries series4 = new LineChartSeries();
       series4.setLabel("Teplota (stC)");
       
       
       try {
    	   
	       Class.forName("com.mysql.jdbc.Driver");
		   conn = DriverManager.getConnection(DB_URL,USER,PASS);
		   stmt = conn.createStatement();
		   
		   
		   // nacti Bean z indexBean a omezit SQL    // format datumu > Thu Jun 02 00:00:00 CEST 2016   
		   Date datum_omezeni_from = (Date) indexBean.date_from;
		   Date datum_omezeni_to = (Date) indexBean.date_to;
		   
		   // zmena format datumu
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		   System.out.print("Chart - omezeni datum from=" + sdf.format(datum_omezeni_from) + " to=" + sdf.format(datum_omezeni_to) + " \n");
		   
		   String sql = null;
		   
		   sql = "SELECT timestamp, ele, plyn, voda, temp FROM energie WHERE timestamp between '" + sdf.format(datum_omezeni_from) + "' and '" + sdf.format(datum_omezeni_to) + "'";
		   System.out.println("Chart - " + sql);
		   
		   ResultSet rs = stmt.executeQuery(sql);
		   
		   int pocet=0;
		   while(rs.next()){
	    	 
			   pocet=pocet+1;
			   
			   String db_date  = rs.getString("timestamp"); // format 2016-06-03 00:01:00
			   
		       int db_value1 = Integer.parseInt(rs.getString("ele"));
		       int db_value2 = Integer.parseInt(rs.getString("plyn"));
		       int db_value3 = Integer.parseInt(rs.getString("voda"));
		       int db_value4 = Integer.parseInt(rs.getString("temp"));
		       
			   series1.set(db_date.substring(0, 10), db_value1);
		       series2.set(db_date.substring(0, 10), db_value2);
		       series3.set(db_date.substring(0, 10), db_value3);
		       series4.set(db_date.substring(0, 10), db_value4);
		       
		       System.out.print("Chart - From dB : date=" + db_date + " value1=" + db_value1 + " value2=" + db_value2 +"\n");		       
		   }
		   
		   if (pocet ==  0 ) {
			   System.out.println("POZOR : v dB nic nenalezeno !");
		   }
		   
		   rs.close();
		   stmt.close();
		   conn.close();
       
       	  }catch(SQLException se){
		      //Handle errors for JDBC
			  se.printStackTrace();
			  System.out.println("JDBC Error " + se);
			  
		  }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		      System.out.println("Error " + e);
		      
		  }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
       
       
      
       model.addSeries(series1);
       model.addSeries(series2);
       model.addSeries(series3);
       model.addSeries(series4);
       
       return model;
   }
  

}


