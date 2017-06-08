package masrobot.event;

import java.util.EventObject;

public class WalkerEvent extends EventObject{


	/**
	 * 
	 */
	private static final long serialVersionUID = -393166443421798592L;
	
	private String status = "";
	
	public WalkerEvent(Object source, String status) {
		super(source);
		// TODO Auto-generated constructor stub
		this.status = status;
	}
	
	public void setWalkerStatus(String status){
		this.status = status;
	}
	
	public String getWalkerStatus(){
		return this.status;
	}

}
