package masrobot.domain;

import java.io.Serializable;

public class CameraInfo implements Serializable{
	
	boolean flag;
	float x;
	float y;
	float z;
	
	public CameraInfo(boolean flag, float x, float y, float z){
		this.flag = flag;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public boolean getFlag(){
		return this.flag;
	}
	
	public float getX(){
		return this.x;
	}
	
	public float getY(){
		return this.y;
	}
	
	public float getZ(){
		return this.z;
	}
}
