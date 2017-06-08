package core.autorobotpackage.mas.event;

import java.util.EventObject;

public class ActuatorEvent extends EventObject{
	
	private String actuatorEventName;

	public ActuatorEvent(Object source, String actuatorEventName) {
		super(source);
		// TODO Auto-generated constructor stub
		this.actuatorEventName = actuatorEventName;
	}
	
	public void setActuatorEventName(String actuatorEventName){
		this.actuatorEventName = actuatorEventName;
	}
	
	public String getActuatorEventName(){
		return this.actuatorEventName;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5851871107555404840L;

}
