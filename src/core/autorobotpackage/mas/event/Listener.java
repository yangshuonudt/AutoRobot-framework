package core.autorobotpackage.mas.event;

import java.util.EventListener;

import masrobot.event.CameraEvent;
import masrobot.event.WalkerEvent;

public interface Listener extends EventListener{
	
	public void cameraEvent(CameraEvent cameraEvent);
	
	public void walkerEvent(WalkerEvent walkerEvent);
	

}
