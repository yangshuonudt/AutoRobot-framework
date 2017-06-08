package masrobot.listeners;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import core.autorobotpackage.mas.event.Listener;
import masrobot.event.CameraEvent;
import masrobot.event.WalkerEvent;

public class ListenerManager {
	private Collection listeners;
	
	public void addListener(Listener listener){
		if(listeners == null){
			listeners = new HashSet();
		}
		listeners.add(listener);
	}
	
	public void removeListener(Listener listener){
		if(listeners == null){
			return;
		}
		listeners.remove(listener);
	}
	
//////////////////////////////////////////////////////////////////////////
	public void generateWalkerEvent(String eventStatus){
		if(listeners == null)
			return;
		WalkerEvent event = new WalkerEvent(this,eventStatus);
		notifyWalkerListeners(event);
	}
	
	public void generateCameraEvent(String eventStatus){
		if(listeners == null){
			return;
		}
		CameraEvent event = new CameraEvent(this,eventStatus);
		notifyCameraListeners(event);
	}
	
	private void notifyWalkerListeners(WalkerEvent event){
		Iterator iter = listeners.iterator();
		while (iter.hasNext()){
			Listener listener = (Listener) iter.next();
			listener.walkerEvent(event);
		}
	}
	
	private void notifyCameraListeners(CameraEvent event){
		Iterator iter = listeners.iterator();
		while (iter.hasNext()){
			Listener listener = (Listener) iter.next();
			listener.cameraEvent(event);
		}
	}
	

}
