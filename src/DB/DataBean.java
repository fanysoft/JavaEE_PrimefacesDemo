package DB;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="dataBean")
@RequestScoped
public class DataBean implements Serializable {
	
	private List<Spotreba> event;
	
	@ManagedProperty("#{dbServiceBean}")
    private DbService service;
 
    @PostConstruct
    public void init() {
    	// nahraj data do dB
        event = service.createDB();
    }
     
    public List<Spotreba> getEvent() {
    	// vyzvedni data
        return event;
    }
 
    public void setService(DbService service) {
        this.service = service;
    }
}