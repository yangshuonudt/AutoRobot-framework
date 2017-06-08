/**
 *  控制机器人走到指定位置
 *  节点名字：walker
 *  服务名字：walk
 *  服务类型：hardwarecontrol/walk
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

 public class Walker{
   RosProxy rp = RosProxy.getRosProxy() ;
   private static Walker walker ;
   Service walkToService , stopWanderService , avoidService , walkAlongService , stopWalkAlongService ;
   Topic wanderTopic ;

   private Walker() {
      this.rp.getRos( "127.0.0.1" , 9090 ) ;
      this.rp.connectRos() ;
      this.walkToService = new Service( RosProxy.ros , "/walk" , "hardwarecontrol/walk" ) ;
      this.stopWanderService = new Service( RosProxy.ros , "/stopWander" , "hardwarecontrol/stop" ) ;
      this.wanderTopic = new Topic ( RosProxy.ros , "/wander" , "std_msgs/String" ) ;
      this.avoidService = new Service( RosProxy.ros , "/avoid" , "hardwarecontrol/avoid") ;
      this.walkAlongService = new Service( RosProxy.ros , "/walkalong" , "hardwarecontrol/walk") ;
      this.stopWalkAlongService = new Service( RosProxy.ros , "/stopWalkalong" , "std_srvs/Empty") ;
   }

   public static synchronized Walker getWalker() {
      if( walker == null ) {
         walker = new Walker() ;
      }
      return walker ;
   }

   public synchronized void avoid( String str ) {
      ServiceRequest request = new ServiceRequest("{\"dir\":\"" + str + "\"}") ;
      ServiceResponse response = this.avoidService.callServiceAndWait(request) ;
      System.out.println("Walker: receive from avoid service: " + response.toString() ) ;
   }
   /**
    *  控制机器人行走
    *  @method walk
    *  @param   [description]
    *  @param   [description]
    *  @param   [description]
    */
   public synchronized void walkTo ( double x , double y , double z ) {
      ServiceRequest request = new ServiceRequest( "{\"x\": " + x + ", \"y\": " + y + ", \"z\": " + z + "}" ) ;
      ServiceResponse response = this.walkToService.callServiceAndWait(request) ;
      System.out.println("Actuator: receive from walkService: " + response.toString() ) ;

   }
   public void walkTo ( ArrayList poi ) {
      this.walkTo( (double)poi.get(0) , (double)poi.get(1) , (double)poi.get(2) ) ;
   }

   public synchronized void wander() {
      Message toSend = new Message( "{\"data\": \"wander\"}" ) ;
      this.wanderTopic.publish(toSend) ;
   }

   public void stopWander() {
      ServiceRequest request = new ServiceRequest( "{\"stop\":\"yes\"}" ) ;
      ServiceResponse response = this.stopWanderService.callServiceAndWait(request) ;
      System.out.println( "Walker: receive from stopWander service:" + response.toString() ) ;
   }
   public void walkAlong( ArrayList poi ){
      this.walkAlong( (double)poi.get(0) , (double)poi.get(1) , (double)poi.get(2) ) ;
   }
   public synchronized void walkAlong( double x , double y , double z ) {
      ServiceRequest request = new ServiceRequest( "{\"x\": " + x + ", \"y\": " + y + ", \"z\": " + z + "}" ) ;
      ServiceResponse response = this.walkAlongService.callServiceAndWait(request) ;
   }
   public void stopWalkAlong() {
      ServiceRequest request = new ServiceRequest( "{}" ) ;
      ServiceResponse response = this.stopWalkAlongService.callServiceAndWait(request) ;
      System.out.println("Actuator: receive from walkAlongService: " + response.toString() ) ;
   }

   public static void main( String[] args ) {
      Speaker speaker = Speaker.getSpeaker() ;
      Walker walk = Walker.getWalker() ;

      /*speaker.say("第一步") ;
      walk.walkTo( 0.5 , 0 , 0 ) ;
      speaker.say("第一步结束,开始漫游") ;
      walk.wander() ;
      try{
         Thread.sleep(20000) ;
      }catch( InterruptedException e ) {
         e.printStackTrace() ;
      }
      walk.stopWander() ;
      speaker.say("停止漫游") ;
      RosProxy.ros.disconnect() ;*/
      speaker.say("left") ;
      walk.avoid("left") ;

   }

}
