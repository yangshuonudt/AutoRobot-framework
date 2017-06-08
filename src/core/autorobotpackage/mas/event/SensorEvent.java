package core.autorobotpackage.mas.event;

import java.util.EventObject;

import core.autorobotpackage.mas.SensorAgent;
import jade.content.AgentAction;

public class SensorEvent extends EventObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7833932041979031014L;
	private String sensorState = "";
	
	public SensorEvent(Object source, String sensorState){
		super(source);
		this.sensorState = sensorState;
	}
	
	public void setSensorState(String sensorState){
		this.sensorState = sensorState;
	}
	
	public String getSensorState(){
		return this.sensorState;
	}
	
	
	
	

}
