/******************************************************
author : yangsen
time   : 2016-05-15, Saturday

name  : RosProxy
type  : Class
package : autorobotengine.rosproxy
version: 0.2

description: 单例模式，维护与ROS服务器的连接
******************************************************/
package core.autorobotengine.rosproxy ;

import java.util.* ;
import javax.json.Json;
import javax.websocket.Session;
import edu.wpi.rail.jrosbridge.*;
import edu.wpi.rail.jrosbridge.callback.*;
import edu.wpi.rail.jrosbridge.handler.*;
import edu.wpi.rail.jrosbridge.messages.*;
import edu.wpi.rail.jrosbridge.services.*;
import edu.wpi.rail.jrosbridge.primitives.*;


public class RosProxy {
   public static Ros ros = null ;
   private static boolean IS_GOTTEN = false ;
   private static boolean IS_CONNECTED = false ;
   private static RosProxy rosProxy ;


   private RosProxy() {
      //clientList = new LinkedList<>() ;
   }

   /**
    *  [此处采用单例模式，获取Rosproxy的实例]
    *  @method getRosProxy
    *  @return [description]
    */
   public static synchronized RosProxy getRosProxy() {
      if( rosProxy == null ) {
         rosProxy = new RosProxy() ;
      }
      return rosProxy ;
   }

   /**
    *  [获取到与ros服务器，默认端口号一般为9090]
    *  @method getRos
    *  @param  ip [ros服务器的ip地址]
    *  @param  port [ros服务器的端口号]
    */
   public synchronized void getRos(String ip , int port) {
      if ( IS_GOTTEN ) {
         return ;
      }
      IS_GOTTEN = true ;
      try{
         ros = new Ros(ip , port) ;
      }catch (Exception e) {
         System.out.println("实例化ros失败!") ;
         e.printStackTrace() ;
      }
   }
   public void getRos(String ip) {
      this.getRos(ip , 9090) ;
   }
   public void getRos() {
      this.getRos("127.0.0.1" , 9090) ;
   }

   /**
    *  [建立与ROS服务器的链接]
    *  @method connectRos
    */
   public synchronized void connectRos() {
      if(IS_CONNECTED) {
         System.out.println("ros服务器已经处于连接状态！") ;
         return ;
      }
      IS_CONNECTED = true ;
      try{
         ros.connect() ;
         System.out.println("连接ros服务器成功!") ;
      }catch (Exception e) {
         e.printStackTrace() ;
      }
      return ;
   }
   /**
    *  断开与ros服务器的连接
    *  @method disconnectRos
    */
   public synchronized void disconnectRos() {
      if(!IS_CONNECTED) {
         System.out.println("ros服务器已经处于断开状态!") ;
         return ;
      }
      IS_CONNECTED = false ;
      ros.disconnect() ;
   }

   /**
    *  [从ros端读取图片，并存到本地]
    *  @method getRecImg
    *  @param  filePath [图片将要存储到的位置]
    *  @return [读取图片是否成功]
    */
   public boolean getRecImg(String filePath) {
      try{
            ReceiveImage recImg = new ReceiveImage() ;
            recImg.getImage(filePath) ;
            recImg = null ;
      }catch(Exception e) {
         System.out.println("获取图片失败\n") ;
      }
      return true ;
   }
}
