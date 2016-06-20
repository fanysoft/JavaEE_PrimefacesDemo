package table;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="tableViewBean")
@SessionScoped
public class TableView implements Serializable {
	
	private List<Spotreba> event;
	
	@ManagedProperty("#{dbServiceBean}")
    private DbService service;
 
    @PostConstruct
    public void init() {
    	// provede se jen jednou pri startu
    	System.out.println("table event init \n");
        event = service.createDB();
    }
     
    public List<Spotreba> getEvent() {
        return event;
    }
 
    public void setService(DbService service) {
        this.service = service;
    }
}