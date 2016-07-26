package bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

@ManagedBean(name="loginBean")
@RequestScoped
public class IndexBean {
	
	private String username;
	private String password;
	public Date date_from;
	public Date date_to;
	
	
	public IndexBean() {
		System.out.println("#### APP STARTED ####");
	}	      

	
	public String login(){
		
		Boolean check=true;
		
		if(this.username.equalsIgnoreCase("user") && this.password.equals("heslo")){
			System.out.println("Login User : date OK");
		}
		else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You're username or password isn't valid"));
			System.out.println("Login User : date NG");
			check=false;
		}
		
		if(date_from.compareTo(date_to)<0){
			// datum je v poradku
			System.out.println("Login check : date OK");
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Date from must be before Date to"));
			System.out.println("Login check : date NG");
			check=false;
		}
		
		if (check.equals(true)) {
			return "welcome";
		} else {
			return "login";
		}
		
	}
	
	public void reset(){
		username="";
		password="";
		date_from=null;
		date_to=null;
		System.out.println("reset..");
		
	}
	
	 public void onDateSelect(SelectEvent event) {
	        FacesContext facesContext = FacesContext.getCurrentInstance();
	        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
	 }
	
	// Getters Setters
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getDate_from() {
		return date_from;
	}

	public void setDate_from(Date date_from) {
		this.date_from = date_from;
	}

	public Date getDate_to() {
		return date_to;
	}

	public void setDate_to(Date date_to) {
		this.date_to = date_to;
	}

	
}