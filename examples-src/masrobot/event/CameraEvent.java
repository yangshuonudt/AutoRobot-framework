package masrobot.event;

import java.util.EventObject;

public class CameraEvent extends EventObject{



	/**
	 * 
	 */
	private static final long serialVersionUID = 3103499308043458298L;
	
	private String status = "";
	
		public CameraEvent(Object source, String status) {
		super(source);
		// TODO Auto-generated constructor stub
		this.status = status;
	}
		
		public void setCameraStatus(String status){
			this.status = status;
		}
		
		public String getCameraStatus(){
			return this.status;
		}

}
