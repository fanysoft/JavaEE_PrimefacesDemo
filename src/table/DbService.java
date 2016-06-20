package table;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import bean.IndexBean;

@ManagedBean(name = "dbServiceBean")
@SessionScoped
public class DbService {
	
	   // JDBC driver name and database URL
	   final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   final String DB_URL = "jdbc:mysql://wh19.farma.gigaserver.cz:3306/vancura_cz_";
	   final String USER = "vancura_cz";
	   final String PASS = "katerina";
	   
	   Connection conn = null;
	   Statement stmt = null;
	   
	   // import IndexBean - pro prenost values from, to
	   @ManagedProperty(value="#{loginBean}")
	   private IndexBean indexBean;
	   public void setIndexBean(IndexBean indexBean) {
	 		this.indexBean = indexBean;
	   }
	   
	    
	   public List<Spotreba> createDB() {
       
		   	List<Spotreba> list = new ArrayList<Spotreba>();

		    try {
			    	
			   	Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				stmt = conn.createStatement();
				
				// nacti Bean z indexBean a omezit SQL    // format datumu > Thu Jun 02 00:00:00 CEST 2016   
				Date datum_omezeni_from = (Date) indexBean.date_from;
				Date datum_omezeni_to = (Date) indexBean.date_to;
				   
				// zmena format datumu
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				System.out.print("Table - omezeni datum form=" + sdf.format(datum_omezeni_from) + " to=" + sdf.format(datum_omezeni_to) + " \n");
					
				String sql;
				sql = "SELECT timestamp, ele, plyn, voda, temp FROM energie WHERE timestamp between '" + sdf.format(datum_omezeni_from) + "' and '" + sdf.format(datum_omezeni_to) + "'";
				System.out.println("Table - " + sql);
				
				ResultSet rs = stmt.executeQuery(sql);
				
				while(rs.next()){
			    	 
					  String db_date  = rs.getString("timestamp");
					  DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
					  Date db_date2  = format.parse(db_date);
					  int db_value1 = Integer.parseInt(rs.getString("ele"));
					  int db_value2 = Integer.parseInt(rs.getString("plyn"));
					  int db_value3 = Integer.parseInt(rs.getString("voda"));
					  int db_value4 = Integer.parseInt(rs.getString("temp"));
					       
					  list.add(new Spotreba(db_date2, db_value1, db_value2, db_value3, db_value4));
					  System.out.print("Table - From dB : date=" + db_date + " value1=" + db_value1 + " value2=" + db_value2 +"\n");		       
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
	        return list;
	    }
	     
	 

	}
