/**
 *  控制机器人说话
 *  节点名字：speaker
 *  话题名字：speak
 *  话题类型：String
 *
 *  单例模式
 */
package FollowMe.javanode ;
import java.util.* ;
import javax.json.Json;
import javax.websocket.Session;

import core.autorobotengine.rosproxy.RosProxy;
import edu.wpi.rail.jrosbridge.*;
import edu.wpi.rail.jrosbridge.callback.*;
import edu.wpi.rail.jrosbridge.handler.*;
import edu.wpi.rail.jrosbridge.messages.*;
import edu.wpi.rail.jrosbridge.services.*;
import edu.wpi.rail.jrosbridge.primitives.*;

public class Speaker{
   RosProxy rp = RosProxy.getRosProxy() ;
   private static Speaker speaker ;
   Topic topic ;
   Service speakService ;

   private Speaker() {
      this.rp.getRos( "127.0.0.1" , 9090 ) ;
      this.rp.connectRos() ;
      this.topic = new Topic( RosProxy.ros , "/speak" , "std_msgs/String" ) ;
      this.speakService = new Service( RosProxy.ros , "/speak" , "hardwarecontrol/speak" ) ;
   }

   public static synchronized Speaker getSpeaker() {
      if( speaker == null ) {
         speaker = new Speaker() ;
      }

      return speaker ;
   }
   /**
    *  控制机器人说话
    *  @method say
    *  @param  content [需要说的内容]
    */
   /*public synchronized void say( String content ) {
      Message toSend = new Message( "{\"data\": \""+ content +"\"}" ) ;
      System.out.println( "Speaker: the content send to speaker node: " + toSend ) ;
      this.topic.publish(toSend) ;
   }*/
   public synchronized void say( String content ) {
      ServiceRequest request = new ServiceRequest( "{\"str\":\"" + content + "\"}" ) ;
      System.out.println( "Speaker: the content send to speaker node: " + request ) ;
      ServiceResponse response = this.speakService.callServiceAndWait(request) ;
   }
   public static void main(String[] args ) {
      Speaker speaker = Speaker.getSpeaker() ;

      while( true ) {
         try {
            Thread.sleep(1000) ;
            speaker.say("yang sen") ;
         }catch( InterruptedException e ) {
            e.printStackTrace() ;
         }
      }
   }
}
