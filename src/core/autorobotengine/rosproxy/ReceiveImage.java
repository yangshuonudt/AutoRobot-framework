/******************************************************
author : yangsen
time   : 2016-03-20, Monday

name  : ReceiveImage
type  : Class
package : autorobotengine.rosproxy
version: 0.1

description: this class is used to receive image from ros
******************************************************/

package core.autorobotengine.rosproxy ;

import java.io.* ;
import java.net.* ;

class ClientSocket {
	private String ip ;
	private int port ;
	private Socket socket = null ;
	DataOutputStream out = null ;
	DataInputStream getMessageStream = null ;

	public ClientSocket(String ip , int port) {
		this.ip = ip ;
		this.port = port ;
	}
	public void CreateCreation() throws Exception{
		try{
			socket = new Socket(this.ip , this.port) ;
		}catch(Exception e) {
			e.printStackTrace() ;
			if (socket != null) {
				socket.close() ;
			}
			throw e ;
		}
	}
	public void sendMessage(String sendMessage) throws Exception{
		try{
			out = new DataOutputStream(socket.getOutputStream()) ;
			out.writeUTF(sendMessage) ;
			out.flush() ;
			return ;
		}catch(Exception e) {
			e.printStackTrace() ;
			if(out != null) {
				out.close() ;
			}
		}
	}
	public DataInputStream getMessageStream() throws Exception {
		try {
			getMessageStream = new DataInputStream(socket.getInputStream());
		   return getMessageStream;
		}catch (Exception e) {
		  e.printStackTrace();
		  if (getMessageStream != null)
			   getMessageStream.close();
		  throw e;
	   }
  }
  public void shutDownConnection() {
	  try {
		  if (out != null)
		  		out.close();
        if (getMessageStream != null)
            getMessageStream.close();
        if (socket != null)
		  		socket.close();
     }catch (Exception e) {
   	  e.printStackTrace() ;
     }
  }
}

public class ReceiveImage {
	private ClientSocket cs = null ;
	private String ip = "localhost" ;
	private int port = 50007 ;

	private void setIp(String m_ip) {
		this.ip = m_ip ;
	}
	private void setPort(int m_port) {
		this.port = m_port ;
	}

	// 建立与python服务起的连接
 	protected boolean createCreation() {
		this.cs = new ClientSocket(ip , port) ;
		try {
			this.cs.CreateCreation() ;
			System.out.println("连接服务器（" + ip + ":" + port + ") 成功\n") ;
			return true ;
		} catch(Exception e){
			System.out.println("连接服务器（" + ip + ":" + port + ") 失败\n") ;
			e.printStackTrace() ;
			return false ;
		}
	}

	// 向服务器发送一条消息
	protected void sendMessage( String message ) {
		if(this.cs == null) {
			this.createCreation() ;
		}
		try {
			this.cs.sendMessage(message) ;
		}catch(Exception e) {
			System.out.println("发送消息:" + message + " 失败!") ;
			e.printStackTrace() ;
		}
	}

	//从服务器接收图片
	protected void getImage(String filePath) {

		this.sendMessage("\rrequest an image!") ;

		DataInputStream inputStream = null ;
		try {
			inputStream = cs.getMessageStream() ;
		}catch ( Exception e ) {
			System.out.println("接受消息缓存错误!\n") ;
			e.printStackTrace() ;
			return ;
		}

		try {
			String savePath = filePath ;
			int bufferSize = 1024 ;
			byte[] buf = new byte[bufferSize] ;
			int passedlen = 0 ;
			long len = 0 ;

			DataOutputStream fileOut = new DataOutputStream(
				new BufferedOutputStream(
					new BufferedOutputStream(
						new FileOutputStream(savePath)
					)
				)
			);
			System.out.println("开始接收文件!") ;

			while(true) {
				int read = 0 ;
				if (inputStream != null) {
					read = inputStream.read(buf) ;
				}
				passedlen += read ;
				if (read == -1) {
					break ;
				}
				fileOut.write(buf , 0 , read) ;
			}
			System.out.println("接收完成，文件存为" + savePath + "\n") ;
			fileOut.close() ;
			cs.shutDownConnection() ;
		}catch (Exception e) {
			e.printStackTrace() ;
			System.out.println("接收消息错误!") ;
			return ;
		}
	}
}
