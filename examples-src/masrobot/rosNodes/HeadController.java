package masrobot.rosNodes;

import core.autorobotengine.rosproxy.RosProxy;
import edu.wpi.rail.jrosbridge.Service;
import edu.wpi.rail.jrosbridge.Topic;
import edu.wpi.rail.jrosbridge.services.ServiceRequest;
import edu.wpi.rail.jrosbridge.services.ServiceResponse;

public class HeadController {
	
	RosProxy rp = RosProxy.getRosProxy();
	private static HeadController headController;
	Topic topic;
	Service service;
	
	private HeadController(){
		this.rp.getRos("127.0.0.1", 9090);
		this.rp.connectRos();
		this.topic = new Topic(RosProxy.ros, "/turn", "std_msgs/String");
		this.service = new Service(RosProxy.ros, "/turn", "hardwarecontrol/turnHead");
	}
	
	public static synchronized HeadController getHeadController() {
		if(headController == null){
			headController = new HeadController();
		}
		return headController;
	}
	
	public synchronized void turnHead(String content){
		ServiceRequest request = new ServiceRequest("{\"str\":\"" + content + "\"}");
	    ServiceResponse response = this.service.callServiceAndWait(request) ;
	}
	
	public static void main(String[] args){
		HeadController headController = HeadController.getHeadController();
		
		while(true){
			try{
				Thread.sleep(1000);
				headController.turnHead("");
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

}
